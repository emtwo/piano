package score;

import java.util.Iterator;
import java.util.Vector;
import java.util.Collections;

import javax.sound.midi.Sequence;

import org.jfugue.ChannelPressure;
import org.jfugue.Controller;
import org.jfugue.Instrument;
import org.jfugue.KeySignature;
import org.jfugue.Layer;
import org.jfugue.Measure;
import org.jfugue.MidiParser;
import org.jfugue.Note;
import org.jfugue.ParserListener;
import org.jfugue.PitchBend;
import org.jfugue.PolyphonicPressure;
import org.jfugue.Time;
import org.jfugue.Voice;

public class Score implements ParserListener {
	public double PaperHeight = 169.00937007874;
	public double PaperWidth = 119.50157480315;
	public int Tempo;
	public Vector<NotePanel> rightNotes, leftNotes;
	public Vector<Chord> rightChords, leftChords;

	
	private String Name;
	private String PrevLine;
	private int CurrPage;
	private boolean finishedLy = false, finishedPS = false;
	private int staffLine = 0; //the line where the lilypond starts defining the bottom staff
	private int line = 1, layer = 0;
	private int currNote;
	private Chord currChord;
	
	
	public Score(String name) {
		this.Name = name;
		this.CurrPage = 1;
		this.Tempo = 72;
		leftNotes = new Vector<NotePanel>();
		rightNotes = new Vector<NotePanel>();
		leftChords = new Vector<Chord>();
		rightChords = new Vector<Chord>();
	}

	public void parsePS(String S) {
		if (!finishedLy) {
			System.err.println("Error: started parsing postscript before parsing lilypond.");
		}
		if (S.startsWith("/page-height")) {
			this.PaperHeight = Double.parseDouble(S.split(" ")[1]);
		} else if (S.startsWith("/page-width")) {
			this.PaperWidth = Double.parseDouble(S.split(" ")[1]);
		} else if (S.contains("noteheads") || S.contains("rests")) {
			String T[] = S.split(" ");
			NotePanel N = new NotePanel();
			N.setCoordinates(Double.parseDouble(T[0]), -Double.parseDouble(T[1]));
			if (S.contains("rests")) {
				T = S.substring(S.lastIndexOf("rests")).split("[. ]");
				//System.out.println(T);
				N.setRest(Integer.parseInt(T[1]));
				//N.rest = true;
			}
			//TODO make safer
			T = PrevLine.substring(PrevLine.lastIndexOf(this.Name + ".ly")).split(":");
			N.setLine(Integer.parseInt(T[1]), Integer.parseInt(T[2]));
			N.setPage(this.CurrPage);
			
			if(N.lyLine < staffLine || staffLine == 0) {
				rightNotes.add(N);
			} else {
				leftNotes.add(N);
			}
		} else if (S.contains("accidentals")) {
			
		} else if (S.startsWith("%%Page:")) {
			this.CurrPage = Integer.parseInt(S.split(" ")[1]);
		}
		
		this.PrevLine = S;
	}
	
	public void finishPS() {
		//called when PS is done parsing
		Collections.sort(leftNotes);
		Collections.sort(rightNotes);

        NotePanel.setPaper(PaperWidth, PaperHeight);
	}
	
	public void parseLY(String S) {
		//if (S.contains("\\new Staff") && S.contains("down")) {
		if (S.contains("NEW STAFF")) {
			staffLine = line;
		}
		++line;
	}
	
	public void finishLy() {
		//called when PS is done parsing
		finishedLy = true;
	}
	
	public void parseMidi(Sequence sequence) {
		currNote = 0;
		MidiParser parser = new MidiParser();
		parser.addParserListener(this);
		parser.parse(sequence);
	}

	// ParserListener methods
	@Override
	public void noteEvent(Note note) {
		System.out.println(note.getMusicString());
		Vector<Chord> chords;
		Vector<NotePanel> notes;
		if (layer == 0) {
			chords = rightChords;
			notes = rightNotes;
		} else {
			chords = leftChords;
			notes = leftNotes;
		}
		if (currNote < notes.size()) {
			if (note.getDuration() > 0) {
			
				NotePanel notePanel = notes.elementAt(currNote);
				notePanel.setNote(note);
				
		    	notePanel.setChord(currChord);
		    	currChord.addNote(notePanel);
		    	
		    	while (true) {
		    		++currNote;
		    		if (currNote < notes.size()) {
			    		notePanel = notes.elementAt(currNote);
			    		if (notePanel.isRest()) {
			    			currChord = new Chord();
			    			chords.add(currChord);
					    	notePanel.setChord(currChord);
					    	currChord.addNote(notePanel);
					    	System.out.println("REST");
			    		} else {
			    			break;
			    		}
		    		} else {
		    			break;
		    		}
		    	} 
			} else {
				if (currChord == null || currChord.size() > 0) {
					currChord = new Chord();
					chords.add(currChord);
				}
			}
		}
		
		if (currNote == notes.size()) {
			currNote = 0;
			++layer;
			System.out.println("HERE");
		}
		//System.out.println(note.hasAccompanyingNotes());
	}

	@Override
	public void sequentialNoteEvent(Note note) {
		// TODO Auto-generated method stub
		noteEvent(note);
		System.out.println("sequential");
	}

	@Override
	public void parallelNoteEvent(Note note) {
		// TODO Auto-generated method stub
		noteEvent(note);
		System.out.println("parallel");
	}
	
	@Override
	public void layerEvent(Layer arg0) {
		//++layer;
		System.out.println("YAAY");
	}
	
	@Override
	public void channelPressureEvent(ChannelPressure arg0) {
		// TODO Auto-generated method stub
		//System.out.println("pressure");
	}

	@Override
	public void controllerEvent(Controller arg0) {
		// TODO Auto-generated method stub
		//System.out.println(arg0.getValue());
	}

	@Override
	public void instrumentEvent(Instrument arg0) {
		// TODO Auto-generated method stub
		//System.out.println("instr");
	}

	@Override
	public void keySignatureEvent(KeySignature arg0) {
		// TODO Auto-generated method stub
		//System.out.println("key");
		
	}

	@Override
	public void measureEvent(Measure arg0) {
		// TODO Auto-generated method stub
		//System.out.println("measure");
	}

	@Override
	public void pitchBendEvent(PitchBend arg0) {
		// TODO Auto-generated method stub
		//System.out.println("bend");
		
	}

	@Override
	public void polyphonicPressureEvent(PolyphonicPressure arg0) {
		// TODO Auto-generated method stub
		//System.out.println("poly");
		
	}


	@Override
	public void tempoEvent(org.jfugue.Tempo arg0) {
		// TODO Auto-generated method stub
		//System.out.println("tempo");
		
	}

	@Override
	public void timeEvent(Time arg0) {
		// TODO Auto-generated method stub
		//System.out.println("time");
		
	}

	@Override
	public void voiceEvent(Voice arg0) {
		// TODO Auto-generated method stub
		//System.out.println("voice");
		
	}
}
