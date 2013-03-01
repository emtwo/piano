package piano.ui.buttons;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;

import piano.ui.Drawing;
import piano.ui.Fonts;
import piano.ui.SongView;

public class BackButton extends Button {

	public BackButton(String text, int x, int y, int width, int height, Drawing parent) {
		super(text, x, y, width, height, parent, null);

		super.setDiff(4);
		super.setFont(Fonts.italic_small);
	}

	@Override
	public void hoverOver() {
	}

	@Override
	public void hoverOut() {
	}

	@Override
	public boolean setMouseClicked(int x, int y, ButtonType type) {
		if (!overButton) {
			return false;
		}

		((SongView)parent).switchView();
		return true;
	}

	public void paintComponent(Graphics gc) {
		gc.setFont(font);

		FontMetrics metrics = gc.getFontMetrics(font);
		int hgt = metrics.getHeight();
		int adv = metrics.stringWidth(text);

		if (overButton) {
			gc.setColor(Color.BLACK);
			gc.drawRoundRect(x, y, width, height, 10, 10);
			gc.drawString(text, x + (width - adv) / 2, y + (height - hgt) / 2 + hgt - diff);
			return;
		}

		gc.setColor(Color.BLACK);
		gc.fillRoundRect(x, y, width, height, 10, 10);

		gc.setColor(Color.WHITE);
		gc.drawString(text, x + (width - adv) / 2, y + (height - hgt) / 2 + hgt - diff);
	}
}
