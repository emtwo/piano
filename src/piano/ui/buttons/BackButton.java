package piano.ui.buttons;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;

import javax.swing.JFrame;

import piano.ui.Drawing;
import piano.ui.Fonts;
import piano.ui.ListView;
import piano.ui.SongView;
import piano.ui.SubView;

public class BackButton extends Button {

	public BackButton(String text, int x, int y, int width, int height,
			Drawing parent, Drawing nextView, JFrame parentFrame) {
		super(text, x, y, width, height, parent, nextView, parentFrame);

		super.setDiff(4);
		super.setFont(Fonts.italic_small);
	}

	@Override
	public void hoverOver() {
	}

	@Override
	public void hoverOut() {
	}

	public boolean setMouseClicked(int x, int y) {
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