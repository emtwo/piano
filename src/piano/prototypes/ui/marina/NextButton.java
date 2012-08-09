package piano.prototypes.ui.marina;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;

import javax.swing.JFrame;

public class NextButton extends Button {

	PlayUI parent;
	
	public NextButton(String text, int x, int y, int width, int height,
			View parent, View nextView, JFrame parentFrame) {

		super(text, x, y, width, height, parent, nextView, parentFrame);
		super.setDiff(4);
		super.setFont(Fonts.italic_small);
		
		this.parent = (PlayUI) parent;
	}

	@Override
	public void hoverOver() {
	}

	@Override
	public void hoverOut() {
	}
	
	@Override
	public boolean setMouseClicked(int x, int y) {
		if (!overButton) {
			return false;
		}

		parent.onFirstPage = false;
		int currStartIndex = ((PlayUI)parent).getStartIndex();
		int numSongs = ((PlayUI)parent).songs.size();
		
		// If there are more songs, create a new page
		if (currStartIndex < numSongs - 1) {
			((PlayUI)parent).setStartIndex(currStartIndex + 6);
		}
		
		// Note that this is the last page
		if (numSongs - ((PlayUI)parent).getStartIndex() <= 6) {
			parent.onLastPage = true;
		}
		
		return true;
	}

	public void paintComponent(Graphics gc) {
		gc.setFont(font);
		
		FontMetrics metrics = gc.getFontMetrics(font);
		int hgt = metrics.getHeight();
		int adv = metrics.stringWidth(text);
		
		if (parent.onLastPage) {
			
			gc.setColor(Color.LIGHT_GRAY);
			gc.fillRoundRect(x, y, width, height, 10, 10);
			
			gc.setColor(Color.black);
			gc.drawString(text, x + (width - adv) / 2, y + (height - hgt) / 2 + hgt - diff);
			return;
		}
		
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
