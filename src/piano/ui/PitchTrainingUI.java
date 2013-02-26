package piano.ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFrame;

import piano.ui.KeyboardParserListener.Colour;
import piano.ui.buttons.HelpButton;
import piano.ui.buttons.MainMenuButton;

public class PitchTrainingUI extends Drawing implements KeyPressedCallback, GetNextNoteCallback {

	private static final String HELP_TEXT = "Listen to the note and play the note you think it is. Green means correct, red means incorrect.";
	private static final String TITLE = "Pitch Training";

	private NotesToPlayData data;
	private KeyboardView keyboard;
	private MainMenuButton mainMenu;
	private HelpButton helpButton;
	private boolean stopPainting = false;

	public PitchTrainingUI(JFrame parentFrame, Drawing parent) throws IOException {
		super();
		data = new NotesToPlayData(this);
		data.minKey = 60;
		data.maxKey = 71;
		data.numOctaves = 1;
		data.useBlackKeys = true;

		keyboard = new KeyboardView(this, this, null, parentFrame, data);
		mainMenu = new MainMenuButton("< Ear Training Menu", 5, 5, 150, 20, this, parent, parentFrame);
		helpButton = new HelpButton("?", HELP_TEXT, 775, 5, 20, 20);
	}

	@Override
	public void switchToView(JFrame parentFrame) {
		super.switchToView(parentFrame);
		keyboard.switchToView(parentFrame);
		stopPainting = false;
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
		int noteToPlay = (data.minKey + (int)(Math.random() * ((data.maxKey - data.minKey) + 1)));
		if (keyboard.isSharp(noteToPlay)) {
			noteToPlay++;
		}
		NoteToColourMap map1 = new NoteToColourMap(noteToPlay, Colour.WHITE);
		list.add(map1);
		return list;
	}
}