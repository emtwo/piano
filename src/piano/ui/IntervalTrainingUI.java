package piano.ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;

import org.jfugue.elements.Note;

import piano.ui.KeyboardParserListener.Colour;
import piano.ui.buttons.HelpButton;
import piano.ui.buttons.MainMenuButton;

public class IntervalTrainingUI extends Drawing implements KeyPressedCallback {

	private static final String HELP_TEXT = "Observe the first note and listen to the second note. Play the note you think the second one is. Green means correct, red means incorrect.";
	private static final String TITLE = "Interval Training";
	private static final int WIDTH = 1400;
	private static final String MIDDLE_C = "Middle C";

	private KeyboardView keyboard;
	private MainMenuButton mainMenu;
	private HelpButton helpButton;
	private boolean stopPainting = false;
	private NotesToPlayData data;

	private ArrayList<ChordToColourMap> nextNotesList;
  private ArrayList<Note> chord;
  private String playString;

	public IntervalTrainingUI() {
		super(WIDTH, 800);
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
		if (stopPainting) {
			return;
		}
		super.paintComponent(g);
		// Clear screen.
		g.setColor(Color.white);
		g.fillRect(0, 0, getWidth(), getHeight());

		keyboard.setDimensions(getWidth(), getHeight());
		keyboard.paintComponent(g);

		// Set font and colour
		g.setColor(Color.BLACK);
		g.setFont(Fonts.italic);
		((Graphics2D) g).setStroke(new BasicStroke(1));

		// Write title.
		FontMetrics metrics = g.getFontMetrics(Fonts.italic);
		int adv = metrics.stringWidth(TITLE);
		g.drawString(TITLE, getWidth()/2 - adv/2, 23);
		g.drawLine(0, 30, getWidth(), 30);

		// Middle C
    g.setColor(Color.RED);
    g.drawString(MIDDLE_C, 465, 790);
    g.drawLine(507, 765, 507, 753);

		mainMenu.paintComponent(g);
		helpButton.paintComponent(g);
	}

	@Override
	public void informChordPressed(ArrayList<Note> chord) {
		repaint();
		keyboard.informChordPressed(chord);
	}

	@Override
  public void informKeyReleased(int keyReleased) {
    keyboard.informKeyReleased();
  }

	public void mouseClicked(MouseEvent e) {
		if (mainMenu.setMouseClicked(e.getX(), e.getY())) {
			JFrameStack.popPanel();
			informExitLoop();
			return;
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		mainMenu.computeMouseEntered(e.getX(), e.getY());
		mainMenu.computeMouseExited(e.getX(), e.getY());

		helpButton.computeMouseEntered(e.getX(), e.getY());
		helpButton.computeMouseExited(e.getX(), e.getY());
		this.repaint();
	}

	@Override
	public void clearKeys() {
		repaint();
	}

	@Override
	public void informExitLoop() {
		stopPainting = true;
		keyboard.informExitLoop();
	}

	 public void switchToView() {
	    stopPainting = false;
	    keyboard.switchToView();
	  }

	@Override
	public ArrayList<ChordToColourMap> getNextNotes() {
	  return nextNotesList;
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

  @Override
  public String getPlayString() {
    return playString;
  }

  @Override
  public ArrayList<Note> getExpectedChord() {
    return chord;
  }
}