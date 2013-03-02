package piano.ui;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JFrame;

import piano.ui.KeyboardParserListener.Colour;
import piano.ui.buttons.ButtonType;

public class PianoUI extends Drawing implements KeyPressedCallback, GetNextNoteCallback, ButtonClickCallback{

  final static float dash1[] = {10.0f};

	Drawing play, practice;

	private NotesToPlayData data;
	private KeyboardView keyboard;

	public PianoUI() throws IOException {
		super();
		NotesToPlayData data = new NotesToPlayData(this);
		data.minKey = 60;
		data.maxKey = 71;
		data.numOctaves = 1;

		HashMap<Integer, String> menuData = new HashMap<Integer, String>();
		menuData.put(64, "Play");
		menuData.put(67, "Ear Training");
		keyboard = new KeyboardView(this, this, this, menuData, data);
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

	@Override
	public void informButtonClicked(ButtonType buttonType, int buttonId) {
		if (buttonId == 64) {
			JFrameStack.pushPanel(new PlayUI());
		} else {
			JFrameStack.pushPanel(new EarTrainingUI());
		}
	}
}
