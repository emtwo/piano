package piano.ui.buttons;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;

import javax.swing.JFrame;

import piano.ui.ButtonClickCallback;
import piano.ui.Drawing;
import piano.ui.Fonts;

public class SimpleButton extends Button {

	private boolean chosen = false;

	public SimpleButton(String text, int x, int y, int width, int height, Drawing parent, ButtonClickCallback callback) {

		super(text, x, y, width, height, parent, callback);
	}

	@Override
	public boolean setMouseClicked(int x, int y, ButtonType type) {
		if (!overButton) {
			return false;
		}

		chosen = true;
		return chosen;
	}

	@Override
	public void hoverOver() {
	}

	@Override
	public void hoverOut() {
	}

	public void setChosen(boolean chosen) {
		this.chosen = chosen;
	}

	public void paintComponent(Graphics gc) {
		super.paintComponent(gc);

		gc.setFont(Fonts.italic_big);

		FontMetrics metrics = gc.getFontMetrics(Fonts.italic_big);
		int hgt = metrics.getHeight();
		int adv = metrics.stringWidth(text);

		if (chosen) {
			// Grey background
			gc.setColor(Color.LIGHT_GRAY);
			gc.fillRoundRect(x, y, width, height, 10, 10);

			// Black outline
			gc.setColor(Color.black);
			gc.drawRoundRect(x, y, width, height, 10, 10);
			gc.drawString(text, x + (width - adv) / 2, y + (height - hgt) / 2 + 32);
			return;
		}
	}
}
