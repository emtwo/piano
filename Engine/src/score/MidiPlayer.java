package score;

import java.io.File;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.MidiSystem;

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
