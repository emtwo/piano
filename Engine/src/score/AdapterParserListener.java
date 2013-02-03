package score;

import org.jfugue.ParserListener;
import org.jfugue.elements.*;

import java.util.Vector;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public abstract class AdapterParserListener implements ParserListener {



    // Usually never called
    public void voiceEvent(Voice voice) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void tempoEvent(Tempo tempo) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void instrumentEvent(Instrument instrument) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void layerEvent(Layer layer) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void measureEvent(Measure measure) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void timeEvent(Time time) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void keySignatureEvent(KeySignature keySignature) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void systemExclusiveEvent(SystemExclusive systemExclusive) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void controllerEvent(Controller controller) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void channelPressureEvent(ChannelPressure channelPressure) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void polyphonicPressureEvent(PolyphonicPressure polyphonicPressure) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void pitchBendEvent(PitchBend pitchBend) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void sequentialNoteEvent(Note note) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void parallelNoteEvent(Note note) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

}
