package piano.ui.buttons;

import java.awt.*;

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
    ((Graphics2D)gc).setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		((Graphics2D) gc).setStroke(new BasicStroke(1));
		gc.setFont(font);

		FontMetrics metrics = gc.getFontMetrics(font);
		int hgt = metrics.getHeight();
		int adv = metrics.stringWidth(text);

		if (overButton) {
		  GradientPaint gp = new GradientPaint(x, y, Color.gray, x, y + height, Color.LIGHT_GRAY);
			((Graphics2D) gc).setPaint(gp);
			gc.fillRoundRect(x, y, width, height, 10, 10);

			gc.setColor(Color.BLACK);
			gc.drawRoundRect(x, y, width, height, 10, 10);
			gc.drawString(text, x + (width - adv) / 2, y + (height - hgt) / 2 + hgt - diff);
			return;
		}

		//gc.setColor(Color.BLACK);
		GradientPaint gp = new GradientPaint(x, y, Color.LIGHT_GRAY, x, y + height, Color.gray);
    ((Graphics2D) gc).setPaint(gp);
		gc.fillRoundRect(x, y, width, height, 10, 10);

		gc.setColor(Color.BLACK);
		gc.drawRoundRect(x, y, width, height, 10, 10);
		gc.drawString(text, x + (width - adv) / 2, y + (height - hgt) / 2 + hgt - diff);
	}
}
