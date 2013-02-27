package piano.engine;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class Metronome {

    private static final String beatString = "V9 [CLOSED_HI_HAT]q";

    private ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private ScheduledFuture beatFuture;
    private boolean active = true;
    private Chord firstChord;
    private int tempo;

    Metronome(Score S) {
        tempo = S.allChords[0].get(0).notes.get(0).getTempo();
        firstChord = S.combinedChords.get(0);
    }

    void start() {
        beatFuture = scheduler.scheduleAtFixedRate(new Runnable() {
            public void run() {
                if (active) {
                    NotePlayer.play(beatString);
                }
                firstChord.setActive(active);
                firstChord.paint();
                active = !active;
            }
        }, 0L, (long) 112500 / tempo, TimeUnit.MILLISECONDS);
    }

    void end() {
        beatFuture.cancel(true);
    }
}
