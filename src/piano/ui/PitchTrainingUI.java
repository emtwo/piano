package piano.ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Arrays;

import org.jfugue.elements.Note;

import piano.ui.KeyboardParserListener.Colour;
import piano.ui.buttons.HelpButton;
import piano.ui.buttons.MainMenuButton;

public class PitchTrainingUI extends AbstractEarTraining {

	private static final String HELP_TEXT = "Listen to the note and play the note you think it is. Green means correct, red means incorrect.";

	public PitchTrainingUI() {
		super(800, 800);
		TITLE = "Pitch Training";
		data = new NotesToPlayData();
		data.minKey = 60;
		data.maxKey = 71;
		data.numOctaves = 1;
		data.useBlackKeys = true;

		keyboard = new KeyboardView(this, data);
		mainMenu = new MainMenuButton("< Ear Training Menu", 5, 5, 150, 20);
		helpButton = new HelpButton("?", HELP_TEXT, 775, 5, 20, 20, this);
	}

	public void paintComponent(Graphics g) {
	  super.paintComponent(g);
		// Middle C
	  g.setFont(Fonts.italic);
    g.setColor(Color.RED);
    g.drawString(MIDDLE_C, 25, 790);
    g.drawLine(67, 765, 67, 753);
	}

  @Override
  public String getNewPlayString() {
    nextNotesList = new ArrayList<ChordToColourMap>();
    int noteToPlay = (data.minKey + (int)(Math.random() * ((data.maxKey - data.minKey) + 1)));
    if (keyboard.isSharp(noteToPlay)) {
      noteToPlay++;
    }
    Note note = new Note((byte) noteToPlay);
    ChordToColourMap map1 = new ChordToColourMap(new ArrayList<Note>(Arrays.asList(note)), Colour.WHITE);
    nextNotesList.add(map1);
    chord = new ArrayList<Note>();
    chord.add(note);
    playString = "[" + String.valueOf(noteToPlay) + "]";
    return playString;
  }
}