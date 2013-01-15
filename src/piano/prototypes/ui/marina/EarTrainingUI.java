package piano.prototypes.ui.marina;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.HashMap;

import javax.swing.JFrame;

public class EarTrainingUI extends Drawing implements KeyPressedCallback {

  final static float dash1[] = {10.0f};

	Drawing interval, pitch, pitchAdvanced, chords;

	private KeyboardView keyboard;

	public EarTrainingUI(JFrame parentFrame, Drawing parent) throws IOException {

		HashMap<Integer, PianoMenuData> menuData = new HashMap<Integer, PianoMenuData>();
		menuData.put(1, new PianoMenuData("Interval Training", new IntervalTrainingUI(parentFrame, this)));
		menuData.put(2, new PianoMenuData("Pitch Training", new PitchTrainingUI(parentFrame, this)));
		menuData.put(3, new PianoMenuData("Advanced Pitch Training", new PitchTrainingUI(parentFrame, this)));
		menuData.put(4, new PianoMenuData("Chord Training", new PitchTrainingUI(parentFrame, this)));
		menuData.put(5, new PianoMenuData("Main Menu", parent));
		keyboard = new KeyboardView(this, true, this, menuData, parentFrame);
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
	public void informKeyPressed(char keyPressed) {}

	@Override
	public void clearKeys() {}
}
