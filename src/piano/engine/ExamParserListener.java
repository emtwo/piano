package piano.engine;

import org.jfugue.elements.*;

import java.util.*;

public class ExamParserListener extends AdapterParserListener {

    private static final int initialized = 0;
    private static final int counting = 1;
    private static final int playing = 2;
    private static final int finished = 3;

    private static final double attach_thres = 0.8; //attach a note if it's within 80% of its duration from starting time
    private static final double wrong_note_penalty = -1.0;
    private static final double correct_note_bonus = 2.0;
    private static final double correct_note_dropoff = 100.0; //correct bonus score drops off 1/2 for this much offset
    private static final double min_tempo = 0.6, max_tempo = 3.0; //min and max tempo variations
    private static final double granularity = 0.000001; //attempt to find best tempo to this degree

    private int state;
    private long start;
    private LinkedList<NotePanel> notes = new LinkedList<NotePanel>();
    private Metronome metronome;
    private ExamScorePanel SP;
    private Score S;

    ExamParserListener(ExamScorePanel SP) {
        this.SP = SP;
        this.S = SP.S;
        state = initialized;
    }

    public void start() {
        //starts the metronome and waits for the user to start playing
        state = counting;
        metronome = new Metronome(S);
        metronome.start();
    }

    public void stop() {
        if (state == counting) {
            metronome.end();
        }
    }

    public void finish() {
        if (state == finished) {
            return;
        }
        if (state == counting) {
            metronome.end();
        }

        state = finished;


        double bestScale = 1.0;
        double bestScore = attachNotes(bestScale);

        //find the scale by matching each ghost note to each real note
        for (NotePanel note : S.combinedNotes) {
            for (NotePanel ghost : notes) {
                double timeScale = (double) ghost.getMillisTime() / note.getMillisTime();
                if (timeScale > min_tempo && timeScale < max_tempo) {
                    double tScore = attachNotes(timeScale);
                    if (tScore > bestScore) {
                        bestScale = timeScale;
                        bestScore = tScore;
                    }
                }
            }
        }

        // attach the notes
        attachNotes(bestScale, true);
    }

    public void noteEvent(Note note, long time) {
        NotePanel ghost = new NotePanel(note);
        ghost.setGhost(true)
             .setMillisTime(time)
             .setScore(S);


        notes.add(ghost);
    }

    public void pressNoteEvent(Note note) {
        if (note.getMillisDuration() > 0) {
            //end metronome counting and start timing
            if (state == counting) {
                state = playing;
                metronome.end();
                metronome = null;
                //SP.play();
                start = new Date().getTime();
            }

            noteEvent(note, new Date().getTime() - start);
        }
    }

    @Override
    public void releaseNoteEvent(Note note) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    private double attachNotes(double scale) {
        return attachNotes(scale, false);
    }

    private double attachNotes(double scale, boolean attach) {

        // change all the times
        if (attach) {
            for (NotePanel ghost : notes) {
                ghost.setMillisTime(Math.round(ghost.getMillisTime() * scale));
            }
        }

        double score = 0.0;
        int[] c = new int[S.staves];

        for (NotePanel ghost : notes) {

            long ghostTime;

            if (attach) {
                ghostTime = ghost.getMillisTime();
            } else {
                ghostTime = Math.round(ghost.getMillisTime() * scale);
            }

            // move bounding chords up until the current ghost note is between them
            for (int layer = 0; layer < S.staves; ++layer) {
                while (c[layer] < S.allChords[layer].size() - 1 && S.allChords[layer].get(c[layer] + 1).getMillisTime() < ghostTime) {
                    ++c[layer];
                }
            }

            // pick which layer the ghost note should belong to
            int attachedLayer = -1;
            if (S.staves == 1) {
                attachedLayer = 0;
            } else {
                long matchOffset = -1L; // distance to nearest matching node
                for (int layer = 0; layer < S.staves; ++layer) {
                    for (int i = 0; i <= 1; ++i) {
                        if (c[layer] + i >= S.allChords[layer].size()) {
                            break;
                        }
                        Chord chord = S.allChords[layer].get(c[layer] + i);
                        if (chord.find(ghost) != null) {
                            long offSet = Math.abs(chord.getMillisTime() - ghostTime);
                            if (matchOffset == -1L || offSet < matchOffset) {
                                attachedLayer = layer;
                                matchOffset = offSet;
                            }
                        }
                    }
                }
            }
            if (attachedLayer == -1) {
                if (ghost.getValue() >= 60) {
                    attachedLayer = 0;
                } else {
                    attachedLayer = 1;
                }
            }

            // pick which chord to attach the note to. this is based on the constant attach_thres
            Chord attachedChord;
            NotePanel referenceNote;
            if (c[attachedLayer] >= S.allChords[attachedLayer].size() - 1) {
                attachedChord = S.allChords[attachedLayer].lastElement();
                referenceNote = null;
            } else {
                Chord chord = S.allChords[attachedLayer].get(c[attachedLayer]);
                Chord nextChord = S.allChords[attachedLayer].get(c[attachedLayer] + 1);
                if (chord.find(ghost) != null && chord.find(ghost).getMatchedGhost() == null) {
                    attachedChord = chord;
                    referenceNote = nextChord.notes.firstElement();
                } else if (nextChord.find(ghost) != null) {
                    attachedChord = nextChord;
                    referenceNote = chord.notes.firstElement();
                } else if (ghostTime <= chord.getMillisTime() + attach_thres * chord.getMillisDuration()) {
                    attachedChord = chord;
                    referenceNote = nextChord.notes.firstElement();
                } else {
                    attachedChord = nextChord;
                    referenceNote = chord.notes.firstElement();
                }
            }

            // find which note in the chord to attach the ghost note to
            NotePanel attachedNote = null;
            for (NotePanel note : attachedChord.notes) {
                if (attachedNote == null || Math.abs(note.getValue() - ghost.getValue()) < Math.abs(attachedNote.getValue() - ghost.getValue())) {
                    attachedNote = note;
                }
            }

            //attach the node
            if (attach) {
                attachedNote.addGhostNote(ghost, referenceNote);
            }

            //calculate score
            if (!ghost.getValue().equals(attachedNote.getValue())) {
                score += wrong_note_penalty;
            } else {
                double offset = Math.abs(ghost.getMillisTime() * scale - attachedNote.getMillisTime());
                score += correct_note_bonus * Math.pow(0.5, offset/correct_note_dropoff);
            }
        }



        return score;
    }


}
