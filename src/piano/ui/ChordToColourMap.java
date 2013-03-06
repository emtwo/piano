package piano.ui;

import java.util.ArrayList;

import org.jfugue.elements.Note;

import piano.ui.KeyboardParserListener.Colour;

public class ChordToColourMap {
	ArrayList<Note> chord;
	Colour colour;

	public ChordToColourMap(ArrayList<Note> chord, Colour colour) {
		this.chord = chord;
		this.colour = colour;
	}
}
