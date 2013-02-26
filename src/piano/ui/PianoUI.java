package piano.ui;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JFrame;

import piano.ui.KeyboardParserListener.Colour;

public class PianoUI extends Drawing implements KeyPressedCallback, GetNextNoteCallback {

  final static float dash1[] = {10.0f};

	Drawing play, practice;

	private NotesToPlayData data;
	private KeyboardView keyboard;

	public PianoUI(JFrame parentFrame) throws IOException {
		super();
		NotesToPlayData data = new NotesToPlayData(this);
		data.minKey = 60;
		data.maxKey = 71;
		data.numOctaves = 1;

		HashMap<Integer, PianoMenuData> menuData = new HashMap<Integer, PianoMenuData>();
		menuData.put(64, new PianoMenuData("Play", new PlayUI(parentFrame, this)));
		menuData.put(67, new PianoMenuData("Ear Training", new EarTrainingUI(parentFrame, this)));
		keyboard = new KeyboardView(this, this, menuData, parentFrame, data);
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		// Clear screen.
		g.setColor(Color.white);
	  g.fillRect(0, 0, getWidth(), getHeight());

		// draw entire component grey
    g.setColor(Color.lightGray);
    g.fillRect(0, 0, getWidth(), getHeight());

    // Write title.
		g.setColor(Color.BLACK);
		g.setFont(Fonts.big);
		g.drawString("Piano", 20, 150);
		g.drawString("Like a", 20, 200);
		g.drawString("Pro", 20, 250);

		// Write "by Mastodon".
		g.setFont(Fonts.italic);
		g.drawString("By: Mastodon", 20, 300);

		// Draw piano image
		keyboard.setDimensions(250, 0, getWidth(), getHeight());
		keyboard.paintComponent(g);
	}

	public void mouseClicked(MouseEvent e) {
		keyboard.mouseClicked(e);
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		keyboard.mouseMoved(e);
		this.repaint();
	}

	@Override
	public void informKeyPressed(int keyPressed) {}

	@Override
	public void clearKeys() {}

	@Override
	public void informExitLoop() {}

	@Override
	public ArrayList<NoteToColourMap> getNextNotes() { return null; }
}