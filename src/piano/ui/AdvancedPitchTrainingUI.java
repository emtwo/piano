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

public class AdvancedPitchTrainingUI extends AbstractEarTraining {

	private static final String HELP_TEXT = "Listen to the note and play the note you think it is. Green means correct, red means incorrect.";
	private NotesToPlayData data;

	public AdvancedPitchTrainingUI() {
		super();
		TITLE = "Advanced Pitch Training";
		data = new NotesToPlayData();
		data.minKey = 48;
		data.maxKey = 83;
		data.numOctaves = 3;
		data.useBlackKeys = true;

		keyboard = new KeyboardView(this, data);
		mainMenu = new MainMenuButton("< Ear Training Menu", 5, 5, 150, 20);
		helpButton = new HelpButton("?", HELP_TEXT, WIDTH - 25, 5, 20, 20, this);
	}

	public void paintComponent(Graphics g) {
	  super.paintComponent(g);
		// Middle C
	  g.setFont(Fonts.italic);
    g.setColor(Color.RED);
    g.drawString(MIDDLE_C, 465, 790);
    g.drawLine(507, 765, 507, 753);
	}

  @Override
  public String getNewPlayString() {
    nextNotesList = new ArrayList<ChordToColourMap>();
    int noteToPlay = (data.minKey + (int)(Math.random() * ((data.maxKey - data.minKey) + 1)));
    Colour colour;
    if (keyboard.isSharp(noteToPlay)) {
      colour = Colour.BLACK;
    } else {
      colour = Colour.WHITE;
    }
    Note note = new Note((byte) noteToPlay);
    ChordToColourMap map1 = new ChordToColourMap(new ArrayList<Note>(Arrays.asList(note)), colour);
    nextNotesList.add(map1);
    chord = new ArrayList<Note>();
    chord.add(note);
    playString = "[" + String.valueOf(noteToPlay) + "]";
    return playString;
  }
}