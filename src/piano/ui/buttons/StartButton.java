package piano.ui.buttons;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.BorderFactory;
import javax.swing.border.Border;

import piano.ui.Fonts;

public class StartButton extends Button {

	public StartButton(String text, int x, int y, int width, int height) {
		super(text, x, y, width, height);
		super.setDiff(6);
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
	  ((Graphics2D)gc).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		((Graphics2D) gc).setStroke(new BasicStroke(1));
		gc.setFont(Fonts.italic);

		FontMetrics metrics = gc.getFontMetrics(Fonts.italic);
		int hgt = metrics.getHeight();
		int adv = metrics.stringWidth(text);

		if (overButton) {
		  GradientPaint gp = new GradientPaint(x, y, Color.LIGHT_GRAY, x, y + height, Color.white);
	    ((Graphics2D) gc).setPaint(gp);
			gc.fillRoundRect(x, y, width, height, 5, 5);
			gc.setColor(Color.BLACK);
			gc.drawRoundRect(x, y, width, height, 5, 5);
			gc.drawString(text, x + (width - adv) / 2, y + (height - hgt) / 2 + hgt - diff);
			return;
		}

		GradientPaint gp = new GradientPaint(x, y, Color.white, x, y + height, Color.LIGHT_GRAY);
    ((Graphics2D) gc).setPaint(gp);
		gc.fillRoundRect(x, y, width, height, 5, 5);

		gc.setColor(Color.BLACK);
		gc.drawRoundRect(x, y, width, height, 5, 5);
		gc.drawString(text, x + (width - adv) / 2, y + (height - hgt) / 2 + hgt - diff);
	}
}
