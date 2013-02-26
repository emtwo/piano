package piano.ui;

import piano.ui.KeyboardParserListener.Colour;

public class NoteToColourMap {
	int note;
	Colour colour;

	public NoteToColourMap(int note, Colour colour) {
		this.note = note;
		this.colour = colour;
	}
}
