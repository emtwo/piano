package score;

import javax.sound.midi.*;
import java.io.File;

public class MidiPlayer {
	
	private String name;
	
	public MidiPlayer(String name) {
		this.name = name;
	}
	
	public void play() {
		try {
	        // From file
	        Sequence sequence = MidiSystem.getSequence(new File("data/out/" + name + ".midi"));
	    
	        // Create a sequencer for the sequence
	        Sequencer sequencer = MidiSystem.getSequencer();
	        sequencer.open();
	        sequencer.setSequence(sequence);
	    
	        // Start playing
	        sequencer.start();
	    } catch (java.io.IOException e) {
	    } catch (MidiUnavailableException e) {
	    } catch (InvalidMidiDataException e) {
	    }
	}
}
