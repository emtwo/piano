package piano.ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import piano.ui.KeyboardParserListener.Colour;
import piano.ui.buttons.HelpButton;
import piano.ui.buttons.MainMenuButton;

public class IntervalTrainingUI extends Drawing implements KeyPressedCallback, GetNextNoteCallback {

	private static final String HELP_TEXT = "Observe the first note and listen to the second note. Play the note you think the second one is. Green means correct, red means incorrect.";
	private static final String TITLE = "Interval Training";
	private static final int WIDTH = 1400;

	private KeyboardView keyboard;
	private MainMenuButton mainMenu;
	private HelpButton helpButton;
	private boolean stopPainting = false;
	private NotesToPlayData data;

	public IntervalTrainingUI() {
		super(WIDTH, 800);
		data = new NotesToPlayData(this);
		data.minKey = 48;
		data.maxKey = 83;
		data.numOctaves = 3;
		data.useBlackKeys = true;

		keyboard = new KeyboardView(this, this, data);
		mainMenu = new MainMenuButton("< Ear Training Menu", 5, 5, 150, 20);
		helpButton = new HelpButton("?", HELP_TEXT, WIDTH - 25, 5, 20, 20);
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

		mainMenu.paintComponent(g);
		helpButton.paintComponent(g);
	}

	@Override
	public void informKeyPressed(int keyPressed) {
		repaint();
		keyboard.informKeyPressed(keyPressed);
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

	@Override
	public ArrayList<NoteToColourMap> getNextNotes() {
		ArrayList<NoteToColourMap> list = new ArrayList<NoteToColourMap>();
		int interval = (0 + (int)(Math.random() * 17)) - 8;
		int noteToPlay = (60 + (int)(Math.random() * ((71 - 60) + 1)));
		Colour colour;
		if (keyboard.isSharp(noteToPlay + interval)) {
			colour = Colour.BLACK;
		} else {
			colour = Colour.WHITE;
		}

		NoteToColourMap map1 = new NoteToColourMap(noteToPlay, Colour.BLUE);
		NoteToColourMap map2 = new NoteToColourMap(noteToPlay + interval, colour);

		list.add(map1);
		list.add(map2);
		return list;
	}

	public void switchToView() {
		stopPainting = false;
		keyboard.switchToView();
	}
}