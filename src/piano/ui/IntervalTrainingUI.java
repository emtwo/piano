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

public class IntervalTrainingUI extends AbstractEarTraining {

	private static final String HELP_TEXT = "Observe the first note and listen to the second note. Play the note you think the second one is. Green means correct, red means incorrect.";

	public IntervalTrainingUI() {
		super();
		TITLE = "Interval Training";
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
    int interval = (0 + (int)(Math.random() * 17)) - 8;
    int noteToPlay = (60 + (int)(Math.random() * ((71 - 60) + 1)));
    Colour colour;
    if (keyboard.isSharp(noteToPlay + interval)) {
      colour = Colour.BLACK;
    } else {
      colour = Colour.WHITE;
    }

    Note note1 = new Note((byte) noteToPlay);
    Note note2 = new Note((byte) (noteToPlay + interval));
    ChordToColourMap map1 = new ChordToColourMap(new ArrayList<Note>(Arrays.asList(note1)), Colour.BLUE);
    ChordToColourMap map2 = new ChordToColourMap(new ArrayList<Note>(Arrays.asList(note2)), colour);

    nextNotesList.add(map1);
    nextNotesList.add(map2);
    chord = new ArrayList<Note>();
    chord.add(note2);
    playString = "[" + String.valueOf(noteToPlay) + "] [" + String.valueOf(noteToPlay + interval) + "]";
    return playString;
  }
}