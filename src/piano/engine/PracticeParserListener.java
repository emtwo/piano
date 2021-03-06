package piano.engine;

import org.jfugue.elements.*;

import java.util.Iterator;
import java.util.ArrayList;
import java.util.Vector;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class PracticeParserListener extends ChordParserListener {

    private PracticeScorePanel SP;
    private Vector<Chord> expectedChords;
    private int nextExpected;

    PracticeParserListener(PracticeScorePanel SP, Vector<Chord> expectedChords) {
        this.SP = SP;
        this.expectedChords = expectedChords;
        this.nextExpected = 0;
        expectedChords.get(nextExpected).setVisible(true);
    }

    private void finished() {
        System.out.println("yay you finished");
    }

    private void nextExpected() {
        int lastPage = expectedChords.get(nextExpected).getPage();
        expectedChords.get(nextExpected).setVisible(false);
        ++nextExpected;
        if (nextExpected >= expectedChords.size()) {
            finished();
        } else {
            expectedChords.get(nextExpected).setVisible(true);
        }
        if (expectedChords.get(nextExpected).getPage() != lastPage) {
            SP.setPage(expectedChords.get(nextExpected).getPage());
            SP.repaint();
        }
    }

    public void chordEvent(ArrayList<Note> chord) {
        int latestNote = 0;
        for (NotePanel notePanel : expectedChords.get(nextExpected).notes) {
            boolean matches = false;
            int noteIndex = 0;
            for (Note note : chord) {
                if (note.getValue() == notePanel.getValue()) {
                    matches = true;
                    break;
                }
                ++noteIndex;
            }
            if (!matches) {
                return;
            }
            if (latestNote < noteIndex) {
                latestNote = noteIndex;
            }
        }

        //if we get here, we have a match
        nextExpected();

        //remove notes earlier than the ones used for the chord
        ArrayList<Note> newChord = new ArrayList<Note>();
        for (int i = latestNote + 1; i < chord.size(); ++i) {
            newChord.add(chord.get(i));
        }

        //fire another chord event for the unused notes
        chordEvent(newChord);

    }

		@Override
		public void releaseNoteEvent(Note note) {}
}
