package piano;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class TestJPanelChild extends JPanel {
	private Color myColour;

	public TestJPanelChild(Color colour) {
		myColour = colour;
	}

	public void paintComponent(Graphics g) {
		g.setColor(myColour);
		g.fillRect(0, 0, 800, 800);
	}
}
