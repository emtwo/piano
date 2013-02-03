package score;

import org.jfugue.elements.*;

import java.util.Date;

public class ExamParserListener extends AdapterParserListener {

    private static final int initialized = 0;
    private static final int counting = 1;
    private static final int playing = 2;
    private static final int finished = 3;

    private int state;
    private long start;

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

    public void noteEvent(Note note) {
        if (state == counting) {
            state = playing;
            metronome.end();
            metronome = null;
            SP.play(true);
            start = new Date().getTime();
        }

        System.out.println(new Date().getTime() - start);

        //To change body of implemented methods use File | Settings | File Templates.
    }


}
