package score;

import org.jfugue.ParserListener;
import org.jfugue.elements.*;
import org.jfugue.parsers.MidiParser;

import javax.sound.midi.Sequence;
import java.util.*;

public class Score implements ParserListener {

	public double PaperHeight = 169.00937007874;
	public double PaperWidth = 119.50157480315;
    public Vector<NotePanel> notes[];
    public Vector<Chord> chords[];
    public Vector<Chord> combinedChords = new Vector<Chord>(), combinedAllChords = new Vector<Chord>();
    public Vector<NotePanel> combinedNotes = new Vector<NotePanel>(), combinedAllNotes = new Vector<NotePanel>();
    public Vector<String> fontInfo = new Vector<String>();
    public int staves = 1;
	
	private String Name;
	private String PrevLine;
	private int CurrPage;
	private int staffLine = 0; //the line where the lilypond starts defining the bottom staff
	private long time;
    private int tempo = 120;
	private int line = 1, layer = 0;
	private Iterator<NotePanel> currNotes[];
    private Vector<ArrayList<Integer>> ties = new Vector<ArrayList<Integer>>();
	
	public Score(String name) {
		this.Name = name;
		this.CurrPage = 1;
	}

    public void parseHeader(String S) {
        if (S.startsWith("/page-height")) {
            this.PaperHeight = Double.parseDouble(S.split(" ")[1]);
        } else if (S.startsWith("/page-width")) {
            this.PaperWidth = Double.parseDouble(S.split(" ")[1]);
        } else if (S.startsWith("/magfont")) {
            fontInfo.add(S);
        } else if (S.startsWith("/lily-output-units")) {
            float scale = Float.parseFloat(S.split(" ")[1]);
            for (String T : fontInfo) {
                NotePanel.addFont(T.split(" ")[0].substring(1), T.split(" ")[2].substring(1), scale * Float.parseFloat(T.split(" ")[3]));
            }
        }
    }

    public void finishHeader() {
        NotePanel.setPaper(PaperWidth, PaperHeight);
    }

    public void parseLY(String S) {
        //TODO: detect new staff location better
        if ((S.contains("\\new Staff") && S.contains("down")) || S.contains("NEW STAFF")) {
            staffLine = line;
            staves = 2;
        }

        for (int i = 0; i < S.length(); ++i) {
            if (S.charAt(i) == '~') {
                ArrayList<Integer> t = new ArrayList<Integer>();
                t.add(line);
                t.add(i);
                ties.add(t);
            }
        }

        ++line;
    }

    public void finishLy() {
        //called when PS is done parsing
        notes = (Vector<NotePanel>[]) new Vector[staves];
        chords = (Vector<Chord>[]) new Vector[staves];
        currNotes = (Iterator<NotePanel>[]) new Iterator[staves];
        for (int i = 0; i < staves; ++i) {
            notes[i] = new Vector<NotePanel>();
            chords[i] = new Vector<Chord>();
        }
    }

	public void parsePS(String S) {
		if (S.contains("noteheads") || S.contains("rests")) {
            // extract coordinate, glyph, and font information
			String T[] = S.split(" ");
			NotePanel N = new NotePanel();
			N.setCoordinates(Double.parseDouble(T[0]), -Double.parseDouble(T[1]))
             .setGlyph(T[4].substring(1))
             .setFont(T[3]);
			if (S.contains("rests")) {
				T = S.substring(S.lastIndexOf("rests")).split("[. ]");
				N.setRest(Integer.parseInt(T[1]));
			}

			T = PrevLine.substring(PrevLine.lastIndexOf(this.Name + ".ly")).split(":");
			N.setLine(Integer.parseInt(T[1]), Integer.parseInt(T[2]))
			 .setPage(this.CurrPage);
			
			if(N.lyLine < staffLine || staffLine == 0) {
				notes[0].add(N);
			} else {
				notes[1].add(N);
			}
		} else if (S.contains("accidentals")) {
            //String T[] = S.split(" ");
            //lastNote.setAccidentals(Double.parseDouble(T[0]), -Double.parseDouble(T[1]), T[4].substring(1), T[3]);
		} else if (S.startsWith("%%Page:")) {
			this.CurrPage = Integer.parseInt(S.split(" ")[1]);
		}
		
		if (S.contains(this.Name + ".ly")) {
            this.PrevLine = S;
        }
	}
	
	public void finishPS() {
		//called when PS is done parsing
        //sort the notes we found from the PS by where they occurred in the .ly
        for (int i = 0; i < staves; ++i) {
		    Collections.sort(notes[i]);
        }

        //find tied notes
        for (int i = 0; i < staves; ++i) {
            int cTie = 0;
            int cNote = 0;

            while (cTie < ties.size() && cNote < notes[i].size() - 1) {
                ArrayList<Integer> tie = ties.get(cTie);
                NotePanel prevNote = notes[i].get(cNote);
                NotePanel nextNote = notes[i].get(cNote + 1);
                if (pairComp(tie.get(0), tie.get(1), prevNote.lyLine, prevNote.lyNumber) != -1 &&
                        pairComp(tie.get(0), tie.get(1), nextNote.lyLine, nextNote.lyNumber) != 1) {
                    // tie is located between these notes
                    nextNote.setTie(true);
                    ++cNote;
                    ++cTie;
                } else if (pairComp(tie.get(0), tie.get(1), prevNote.lyLine, prevNote.lyNumber) != -1) {
                    ++cNote;
                } else {
                    ++cTie;
                }
            }
        }
            /*Vector<NotePanel> cNotes = notes[i];
            for (int cNote = 1; cNote < cNotes.size(); ++cNote) {
                //increment until tie position is higher than note position
                ArrayList<Integer> tie = ties.get(cTie);
                while (tie.get(0) < cNotes.get(cNote).lyLine ||
                        (tie.get(0) == cNotes.get(cNote).lyLine && tie.get(1) < cNotes.get(cNote).lyNumber)) {
                    if (cTie == ties.size() - 1) {
                        break;
                    }
                    ++cTie;
                    tie = ties.get(cTie);
                }



                if (note.lyLine > tie.get(0) || (note.lyLine == tie.get(0) && note.lyNumber > tie.get(1))) {
                    System.out.println("FOUND TIE");
                    note.setTie(true);
                    ++cTie;
                }
            }    */

	}
	
	public void parseMidi(Sequence sequence) {
        NotePanel.setResolution(sequence.getResolution());

		MidiParser parser = new MidiParser();
		parser.addParserListener(this);
        for (int i = 0; i < staves; ++i) {
            currNotes[i] = notes[i].iterator();
        }
		parser.parse(sequence);

        // initialize any rests trailing at the end
        for (int i = 0; i < staves; ++i) {
            Vector<Chord> currChords = chords[i];
            Iterator<NotePanel> currNote = currNotes[i];
            long tempTime = currChords.isEmpty() ? 0 : currChords.lastElement().getTime() + currChords.lastElement().getDuration();

            while (currNote.hasNext()) {
                NotePanel notePanel = currNote.next();
                if (notePanel.getNote() != null) {
                notePanel.setTime(tempTime)
                    .setTempo(tempo);

                tempTime += notePanel.getDuration();
                Chord chord = new Chord(notePanel);
                currChords.add(chord);
                }
            }
        }

        //populate combined chords

        long[] time = {0L, 0L};
        int cChord[] = {0, 0};

        if (staves > 1) {
            while (cChord[0] < chords[0].size() && cChord[1] < chords[1].size()) {
                for (int layer = 0; layer < staves; ++layer) {
                    time[layer] = chords[layer].get(cChord[layer]).getMillisTime();
                }
                Chord chord = new Chord();
                Chord allChord = new Chord();

                if (time[0] == time[1]) {
                    for (int layer = 0; layer < staves; ++layer) {
                        if (!chords[layer].get(cChord[layer]).isRest()) {
                            chord.addChord(chords[layer].get(cChord[layer]));
                        }
                        allChord.addChord(chords[layer].get(cChord[layer]));
                        ++(cChord[layer]);
                    }
                } else if (time[0] < time[1]) {
                    if (!chords[0].get(cChord[0]).isRest()) {
                        chord.addChord(chords[0].get(cChord[0]));
                    }
                    allChord.addChord(chords[layer].get(cChord[layer]));
                    ++(cChord[0]);
                } else {
                    if (!chords[1].get(cChord[1]).isRest()) {
                        chord.addChord(chords[1].get(cChord[1]));
                    }
                    allChord.addChord(chords[layer].get(cChord[layer]));
                    ++(cChord[1]);
                }
                if (!chord.notes.isEmpty()) {
                    combinedChords.add(chord);
                }
                combinedAllChords.add(allChord);
            }
        }

        // schedule last notes if one hand finishes before the other or if there was only one hand to begin with
        for (int layer = 0; layer < staves; ++layer) {
            while (cChord[layer] < chords[layer].size()) {
                time[layer] = chords[layer].get(cChord[layer]).getMillisTime();
                if (!chords[layer].get(cChord[layer]).isRest()) {
                    combinedChords.add(new Chord(chords[layer].get(cChord[layer])));
                }
                combinedAllChords.add(new Chord(chords[layer].get(cChord[layer])));
                ++(cChord[layer]);
            }
        }

        // populate combined notes
        for (Chord chord : combinedChords) {
            for (NotePanel note : chord.notes) {
                combinedNotes.add(note);
            }
        }
        for (Chord chord : combinedAllChords) {
            for (NotePanel note : chord.notes) {
                combinedAllNotes.add(note);
            }
        }
	}

    public void finishMidi() {

        /*long time = 0;

        for (int i = 0; i < rightChords.size(); ++i) {
            //search for a rest
            while (i < rightChords.size() && !rightChords.get(i).isRest()) {
                time = rightChords.get(i).getTime();
                ++i;
            }
            if (i >= rightChords.size()) {
                break;
            }

            //found a rest

        }    */

    }

	// ParserListener methods
	public void noteEvent(Note note) {
        if (layer >= staves) {
            return;
        }
	    //System.out.println(note.getMusicString() + " " + note.getMillisDuration() + " " + note.getDecimalDuration());
		Vector<Chord> currChords = chords[layer];
        Iterator<NotePanel> currNote = currNotes[layer];

        if (!currNote.hasNext()) {
            System.err.println("Received noteEvent, but no PostScript note are left");
            return;
        }

        if (note.getMillisDuration() > 0) {
            NotePanel notePanel = currNote.next();
            // time the last chord ended
            long tempTime = currChords.isEmpty() ? 0 : currChords.lastElement().getTime() + currChords.lastElement().getDuration();
            while (notePanel.isRest || notePanel.isTie) {
                notePanel.setTime(Math.min(tempTime, time - 1)) //hack, in case the rest should be trimmed
                         .setTempo(tempo);
                tempTime += notePanel.getDuration();
                Chord chord = new Chord(notePanel);
                currChords.add(chord);
                //System.out.println("REST: " + notePanel.getMusicString() + " " + notePanel.getDuration());
                notePanel = currNote.next();
            }
            notePanel.setNote(note)
                     .setTime(time)
                     .setTempo(tempo);
            if (currChords.isEmpty() || currChords.lastElement().getTime() != time) {
                Chord chord = new Chord(notePanel);
                currChords.add(chord);
            } else {
                currChords.lastElement().addNote(notePanel);
            }
        }
	}

	public void sequentialNoteEvent(Note note) {
		noteEvent(note);
		System.out.println("sequential");
	}

	public void parallelNoteEvent(Note note) {
		noteEvent(note);
		System.out.println("parallel");
	}

	public void layerEvent(Layer arg0) {
		//++layer;
		System.out.println("layer");
	}

	public void channelPressureEvent(ChannelPressure arg0) {
		System.out.println("pressure");
	}

	public void controllerEvent(Controller arg0) {
		//System.out.println("controller");
	}

	public void instrumentEvent(Instrument arg0) {
		System.out.println("instr");
	}

	public void keySignatureEvent(KeySignature arg0) {
		System.out.println("key");
	}

    public void systemExclusiveEvent(SystemExclusive systemExclusive) {
        System.out.println("system exclusive");
    }

	public void measureEvent(Measure arg0) {
		System.out.println("measure");
	}

	public void pitchBendEvent(PitchBend arg0) {
		System.out.println("bend");
		
	}

	public void polyphonicPressureEvent(PolyphonicPressure arg0) {
		System.out.println("poly");
	}

	public void tempoEvent(Tempo arg0) {
		 //System.out.println("Tempo: " + arg0.getTempo());
         tempo = arg0.getTempo();
	}

	public void timeEvent(Time arg0) {
		//System.out.println("time " + arg0.getTime());
		time = arg0.getTime();
		
	}

	public void voiceEvent(Voice arg0) {
		layer = arg0.getVoice();
	}

    private static int pairComp(int n1, int n2, int m1, int m2) {
        if (n1 < m1) {
            return -1;
        } else if (n1 > m1) {
            return 1;
        } else if (n2 < m2) {
            return -1;
        } else if (n2 > m2) {
            return 1;
        }
        return 0;
    }
}
