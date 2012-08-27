package piano.prototypes.ui.buttons.marina;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;

import javax.swing.JFrame;

import piano.prototypes.ui.marina.Fonts;
import piano.prototypes.ui.marina.PlayUI;
import piano.prototypes.ui.marina.SongView;
import piano.prototypes.ui.marina.SubView;
import piano.prototypes.ui.marina.Drawing;

public class NextButton extends Button {

	SongView parent;
	
	public NextButton(String text, int x, int y, int width, int height,
			Drawing parent, Drawing nextView, JFrame parentFrame) {

		super(text, x, y, width, height, parent, nextView, parentFrame);
		super.setDiff(4);
		super.setFont(Fonts.italic_small);
		
		this.parent = (SongView) parent;
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

		int currStartIndex = ((SongView)parent).startIndex;
		int numSongs = ((SongView)parent).songs.size();
		
		if (numSongs <= 6) {
			return false;
		}
		
		parent.onFirstPage = false;
		
		// If there are more songs, create a new page
		System.out.println("currstartindex: " + currStartIndex);
		System.out.println("numSongs: " + numSongs);

		if ((numSongs - 1) - currStartIndex >= 6) {
			((SongView)parent).startIndex = currStartIndex + 6;
		}
		
		// Note that this is the last page
		if (numSongs - ((SongView)parent).startIndex <= 6) {
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
