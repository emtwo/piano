package piano.prototypes.ui.marina;

import piano.prototypes.ui.marina.KeyboardParserListener.Colour;

public class NoteToColourMap {
	int note;
	Colour colour;

	public NoteToColourMap(int note, Colour colour) {
		this.note = note;
		this.colour = colour;
	}
}
