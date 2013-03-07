package piano.engine;

import org.jfugue.elements.*;

import java.util.Iterator;
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
        expectedChords.get(nextExpected).setActive(true).paint();
    }

    private void finished() {
        System.out.println("yay you finished");
    }

    private void nextExpected() {
        int lastPage = expectedChords.get(nextExpected).getPage();
        expectedChords.get(nextExpected).setActive(false).paint();
        ++nextExpected;
        if (nextExpected >= expectedChords.size()) {
            finished();
        } else {
            expectedChords.get(nextExpected).setActive(true).paint();
        }
        if (expectedChords.get(nextExpected).getPage() != lastPage) {
            SP.setPage(expectedChords.get(nextExpected).getPage());
            SP.repaint();
        }
    }

    public void chordEvent(Vector<Note> chord) {
        /*for (Note note : chord) {
            System.out.print(note.getMusicString() + " ");
        }
        System.out.println();*/
        //if (chord.size() == expectedChords.get(nextExpected).size()) {
            for (NotePanel notePanel : expectedChords.get(nextExpected).notes) {
                boolean matches = false;
                for (Note note : chord) {
                    if (note.getValue() == notePanel.getValue()) {
                        matches = true;
                        break;
                    }
                }
                if (!matches) {
                    return;
                }
            }
            nextExpected();
        //}
    }

		@Override
		public void releaseNoteEvent(Note note) {}
}
