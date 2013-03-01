package piano.ui.buttons;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;

import apple.awt.CColor;

import piano.ui.Drawing;
import piano.ui.Fonts;
import piano.ui.SongView;

public class StartButton extends Button {

	SongView parent;

	public StartButton(String text, int x, int y, int width, int height) {
		super(text, x, y, width, height);
		super.setDiff(4);
	}

	@Override
	public void hoverOver() {
	}

	@Override
	public void hoverOut() {
	}

	@Override
	public boolean setMouseClicked(int x, int y, ButtonType type) {
		return false;
	}

	public void paintComponent(Graphics gc) {
		((Graphics2D) gc).setStroke(new BasicStroke(1));
		gc.setFont(Fonts.italic);

		FontMetrics metrics = gc.getFontMetrics(Fonts.italic);
		int hgt = metrics.getHeight();
		int adv = metrics.stringWidth(text);

		if (overButton) {
			gc.setColor(Color.ORANGE);
			gc.fillRect(x, y, width, height);
			gc.setColor(Color.BLACK);
			gc.drawRect(x, y, width, height);
			gc.drawString(text, x + (width - adv) / 2, y + (height - hgt) / 2 + hgt - diff);
			return;
		}

		gc.setColor(new Color(34, 139, 34));
		gc.fillRect(x, y, width, height);

		gc.setColor(Color.BLACK);
		gc.drawRect(x, y, width, height);
		gc.drawString(text, x + (width - adv) / 2, y + (height - hgt) / 2 + hgt - diff);
	}
}
