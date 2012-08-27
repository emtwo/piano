package piano.prototypes.ui.marina;

import java.awt.Color;
import java.awt.Graphics;
import java.io.IOException;

import javax.swing.JFrame;

public class Keyboard extends Drawing {

	public Keyboard(JFrame parentFrame) throws IOException {
		super();
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		g.setColor(Color.BLACK);
		g.fillRect(0, 0, 20, 20);
	}
}
