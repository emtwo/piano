package piano.ui.buttons;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;

import javax.swing.JFrame;
import javax.swing.JPanel;

import piano.ui.ButtonClickCallback;
import piano.ui.Drawing;

public class KeyboardKey extends Button {

	private boolean isBlack;
    private Color hoverColor = Color.LIGHT_GRAY;
	public int id;

	public KeyboardKey(String text, int x, int y, int width, int height, boolean isBlack, int id) {
		super(text, x, y, width, height);
		this.isBlack = isBlack;
		this.id = id;
	}

	public KeyboardKey(String text, int x, int y, int width, int height,
			boolean isBlack, Drawing parent, ButtonClickCallback callback, int id) {
		super(text, x, y, width, height, parent, callback);
		this.isBlack = isBlack;
		this.id = id;
	}

	public void paintComponent(Graphics2D g2) {
		g2.setFont(font);

		FontMetrics metrics = g2.getFontMetrics(font);
		int hgt = metrics.getHeight();
		int adv = metrics.stringWidth(text);

		if (isBlack) {
			if (overButton) {
				g2.setColor(hoverColor);
			}
			g2.fillRoundRect(x, y, width, height, 5, 5);
			return;
		}
		if (overButton) {
			g2.setColor(hoverColor);
		}
		g2.fillRoundRect(x, y, width, height, 5, 5);

		g2.setColor(Color.BLACK);
		g2.drawRoundRect(x, y, width, height, 5, 5);
		g2.drawString(text, x + (width - adv) - 20, y + (height - hgt) / 2 + hgt - diff);
	}

	public int getId() {
		return id;
	}

    public void setHoverColor(Color c) {
        hoverColor = c;
    }

	@Override
	public void hoverOver() {
		// TODO: fill rectangle as grey
	}

	@Override
	public void hoverOut() {
		// TODO: fill rectangle as white
	}
}
