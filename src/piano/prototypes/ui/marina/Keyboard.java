package piano.prototypes.ui.marina;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.io.IOException;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class Keyboard extends Drawing {
	
	private static final int NUM_KEYS = 7;
	private static final int NUM_OCTAVES = 1;

	private int width, height, xVal, yVal, keyWidth, blackKeyWidth;

	public Keyboard(JFrame parentFrame) throws IOException {
		super();
	}

	public void setDimensions(int parentWidth, int parentHeight) {
		width = (int)(parentWidth * 0.95);
		height = parentHeight / 2;
		xVal = (parentWidth - width) / 2;
		yVal = (parentHeight - height) / 2;
		keyWidth = width / NUM_KEYS;
		blackKeyWidth = keyWidth / 2;
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.BLACK);
		g2.setStroke(new BasicStroke(5));

		int keyXVal = xVal;
		int blackKeyXVal = keyXVal + (keyWidth - blackKeyWidth / 2);
		for (int i = 0; i < NUM_KEYS; i++) {
			g2.drawRect(keyXVal, yVal, keyWidth, height);
			keyXVal += keyWidth;
		}

		for (int i = 0; i < NUM_KEYS - 1; i++) {
			if (i != 2) {
				g2.fillRect(blackKeyXVal, yVal, blackKeyWidth, (int) (height * 0.6));
			}
			blackKeyXVal += keyWidth;
		}
	}
}