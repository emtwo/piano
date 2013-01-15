package piano.prototypes.ui.buttons.marina;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JFrame;

import piano.prototypes.ui.marina.Fonts;
import piano.prototypes.ui.marina.SubView;
import piano.prototypes.ui.marina.Drawing;

public class MainMenuButton extends Button {

	public MainMenuButton(String text, int x, int y, int width, int height,
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
		
		outButton();
		nextView.switchToView(parentFrame);
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
