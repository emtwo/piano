package piano.prototypes.ui.marina;

import java.awt.Color;
import java.awt.Graphics;
import java.io.IOException;

import javax.swing.JFrame;

public class PracticeUI extends Drawing {

	Keyboard keyboard;

	public PracticeUI(JFrame parentFrame) throws IOException {
		super();

		keyboard = new Keyboard(parentFrame);
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		// Clear screen.
		g.setColor(Color.white);
		g.fillRect(0, 0, getWidth(), getHeight());

		keyboard.setDimensions(getWidth(), getHeight());
		keyboard.paintComponent(g);
	}
}
