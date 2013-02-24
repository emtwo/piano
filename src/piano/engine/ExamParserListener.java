package piano.engine;

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

        Iterator<NotePanel> ghostIt = notes.iterator();
        int[] c = new int[S.staves];

        while (ghostIt.hasNext()) {
            NotePanel ghost = ghostIt.next();

            // move bounding chords up until the current ghost note is between them
            for (int layer = 0; layer < S.staves; ++layer) {
                while (c[layer] < S.chords[layer].size() - 1 && S.chords[layer].get(c[layer] + 1).getMillisTime() < ghost.getMillisTime()) {
                    ++c[layer];
                }

            }

            // pick which layer the ghost note should belong to
            int attachedLayer;
            if (S.staves == 1) {
                attachedLayer = 0;
            } else if (ghost.getValue() >= 60) {
                attachedLayer = 0;
            } else {
                attachedLayer = 1;
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

        SP.finishExam();
    }

    public void noteEvent(Note note, long time) {
        NotePanel ghost = new NotePanel(note);
        ghost.setGhost(true)
             .setMillisTime(time);

        notes.add(ghost);
    }

    public void noteEvent(Note note) {
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
