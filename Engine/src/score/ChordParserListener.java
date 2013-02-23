package score;

import org.jfugue.elements.*;

import java.util.Vector;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public abstract class ChordParserListener extends AdapterParserListener {

    static private final int chordThreshold = 50; //threshold for chord detection in milliseconds

    private boolean inChord = false;
    private Vector<Note> currChord = new Vector<Note>();
    private ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    abstract void chordEvent(Vector<Note> chord);

    private void finishChord() {
        inChord = false;
        chordEvent(currChord);
        currChord = new Vector<Note>();
    }

    public final void noteEvent(Note note) {
        for (Note n : currChord) {
            if (note.getValue() == n.getValue()) {
                return;
            }
        }
        currChord.add(note);
        if (!inChord) {
            inChord = true;
            scheduler.schedule(new Runnable() {
                public void run() {
                    finishChord();
                }
            }, chordThreshold, TimeUnit.MILLISECONDS);
        }
    }

}
