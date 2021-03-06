package piano.engine;

import org.jfugue.elements.*;

import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public abstract class ChordParserListener extends AdapterParserListener {

    static private final int chordThreshold = 50; //threshold for chord detection in milliseconds

    private boolean inChord = false;
    private ArrayList<Note> currChord = new ArrayList<Note>();
    private ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public abstract void chordEvent(ArrayList<Note> chord);

    private void finishChord() {
        inChord = false;
        chordEvent(currChord);
        currChord = new ArrayList<Note>();
    }

    public final void pressNoteEvent(Note note) {
        if (note.getMillisDuration() > 0) {
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

}
