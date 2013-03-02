package piano.ui.buttons;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JFrame;

import piano.ui.ButtonClickCallback;
import piano.ui.Drawing;
import piano.ui.Fonts;

public class MainMenuButton extends Button {

	public MainMenuButton(String text, int x, int y, int width, int height) {
		super(text, x, y, width, height);

		super.setDiff(4);
		super.setFont(Fonts.italic_small);
	}

	@Override
	public void hoverOver() {
	}

	@Override
	public void hoverOut() {
	}

	public boolean setMouseClicked(int x, int y, ButtonType buttonType) {
		if (!overButton) {
			return false;
		}

		outButton();
		callback.informButtonClicked(buttonType, 0);
		return true;
	}

	public void paintComponent(Graphics gc) {
		((Graphics2D) gc).setStroke(new BasicStroke(1));
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
