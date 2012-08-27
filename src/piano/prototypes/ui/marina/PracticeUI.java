package piano.prototypes.ui.marina;

import java.awt.Graphics;
import java.io.IOException;

import javax.swing.JFrame;

public class PracticeUI extends Drawing {

	Drawing keyboard;

	public PracticeUI(JFrame parentFrame) throws IOException {
		super();

		keyboard = new Keyboard(parentFrame);
	}

	public void paintComponent(Graphics g) {
		keyboard.paintComponent(g);
	}
}
