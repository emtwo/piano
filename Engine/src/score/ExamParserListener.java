package score;

import org.jfugue.elements.*;

import java.util.*;

public class ExamParserListener extends AdapterParserListener {

    private static final int initialized = 0;
    private static final int counting = 1;
    private static final int playing = 2;
    private static final int finished = 3;

    private static final double attachThres = 0.8; //attach a note if it's within 80% of its duration from starting time

    private int state;
    private long start;
    private LinkedList<NotePanel> notes = new LinkedList<NotePanel>();
    private Metronome metronome;
    private ScorePanel SP;
    private Score S;

    ExamParserListener(ScorePanel SP) {
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

    public void finish() {
        if (state == finished) {
            return;
        }
        state = finished;

        // calculate total offset of notes with their matched notes
        long totalOffset = 0L;
        int totalMatchedNotes = 0;
        attachNotes();
        for (NotePanel note : S.combinedNotes) {
            if(note.getMatchedGhost() != null) {
                totalOffset += note.getMillisTime() - note.getMatchedGhost().getMillisTime();
                ++totalMatchedNotes;
            }
        }

        long offset = Math.round((double) totalOffset / totalMatchedNotes);

        if (offset != 0) {
            //individually offset each note
            for (NotePanel ghost : notes) {
                ghost.setMillisTime(ghost.getMillisTime() + offset);
            }

            //clear all previously matched notes
            for (NotePanel note : S.combinedNotes) {
                note.clearGhostNotes();
            }

            //reattach notes
            attachNotes();
        }

        SP.finishExam();
    }

    public void noteEvent(Note note, long time) {
        NotePanel ghost = new NotePanel(note);
        ghost.setGhost(true)
             .setMillisTime(time);

        notes.add(ghost);
    }

    public void noteEvent(Note note) {
        if (note.getMillisDuration() > 0) {
            //end metronome counting and start timing
            if (state == counting) {
                state = playing;
                metronome.end();
                metronome = null;
                SP.play(true);
                start = new Date().getTime();
            }

            noteEvent(note, new Date().getTime() - start);
        }
    }

    private void attachNotes() {
        int[] c = new int[S.staves];

        for (NotePanel ghost : notes) {

            // move bounding chords up until the current ghost note is between them
            for (int layer = 0; layer < S.staves; ++layer) {
                while (c[layer] < S.chords[layer].size() - 1 && S.chords[layer].get(c[layer] + 1).getMillisTime() < ghost.getMillisTime()) {
                    ++c[layer];
                }
            }

            // pick which layer the ghost note should belong to
            int attachedLayer = -1;
            if (S.staves == 1) {
                attachedLayer = 0;
            } else {
                long matchOffset = 0L; // distance to nearest matching node
                for (int layer = 0; layer < S.staves; ++layer) {
                    for (int i = 0; i <= 1; ++i) {
                        if (c[layer] + i >= S.chords[layer].size()) {
                            break;
                        }
                        Chord chord = S.chords[layer].get(c[layer] + i);
                        if (chord.contains(ghost)) {
                            long offSet = Math.abs(chord.getMillisTime() - ghost.getMillisTime());
                            if (matchOffset == 0L || offSet < matchOffset) {
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

            // pick which chord to attach the note to. this is based on the constant attachThres
            Chord attachedChord;
            NotePanel referenceNote;
            if (c[attachedLayer] >= S.chords[attachedLayer].size() - 1) {
                attachedChord = S.chords[attachedLayer].lastElement();
                referenceNote = null;
            } else {
                Chord chord = S.chords[attachedLayer].get(c[attachedLayer]);
                Chord nextChord = S.chords[attachedLayer].get(c[attachedLayer] + 1);
                if (ghost.getMillisTime() <= chord.getMillisTime() + attachThres * chord.getMillisDuration()) {
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

            attachedNote.addGhostNote(ghost, referenceNote);
        }
    }


}
