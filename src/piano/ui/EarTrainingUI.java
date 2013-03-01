package piano.ui;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JFrame;

import piano.ui.buttons.ButtonType;

public class EarTrainingUI extends Drawing implements KeyPressedCallback, GetNextNoteCallback, ButtonClickCallback {

  final static float dash1[] = {10.0f};

	Drawing interval, pitch, pitchAdvanced, chords;

	private KeyboardView keyboard;

	public EarTrainingUI() {
		super();
		NotesToPlayData data = new NotesToPlayData(this);
		data.minKey = 60;
		data.maxKey = 71;
		data.numOctaves = 1;

		HashMap<Integer, String> menuData = new HashMap<Integer, String>();
		menuData.put(62, "Interval Training");
		menuData.put(64, "Pitch Training");
		menuData.put(65, "Advanced Pitch Training");
		menuData.put(67, "Chord Training");
		menuData.put(69, "Main Menu");
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
		g.drawString("Ear", 20, 150);
		g.drawString("Training", 20, 200);

		// Write "Choose Difficulty".
		g.setFont(Fonts.italic);
		g.drawString("Choose Difficulty", 20, 300);

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
		Drawing nextView = null;
		switch(buttonId) {
			case 62:
				nextView = new IntervalTrainingUI();
				break;
			case 64:
				nextView = new PitchTrainingUI();
				break;
			case 65:
				nextView = new AdvancedPitchTrainingUI();
				break;
			case 67:
				nextView = new PitchTrainingUI();
				break;
			case 69:
				JFrameStack.popPanel();
				return;
		}
		nextView.switchToView();
		JFrameStack.pushPanel(nextView);
	}
}
