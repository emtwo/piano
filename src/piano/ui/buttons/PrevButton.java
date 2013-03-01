package piano.ui.buttons;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;

import piano.ui.Drawing;
import piano.ui.Fonts;
import piano.ui.SongView;

public class PrevButton extends Button {

	SongView parent;

	public PrevButton(String text, int x, int y, int width, int height, Drawing parent) {

		super(text, x, y, width, height, parent, null);
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
	public boolean setMouseClicked(int x, int y, ButtonType type) {
		if (!overButton) {
			return false;
		}

		int currStartIndex = ((SongView)parent).startIndex;
		int numSongs = ((SongView)parent).songs.size();

		if (numSongs <= 6) {
			return false;
		}

		parent.onLastPage = false;

		// If there are more songs, create a new page
		if (currStartIndex > 0) {
			((SongView)parent).startIndex = currStartIndex - 6;
		}

		// Note that this is the last page
		if (((SongView)parent).startIndex == 0) {
			parent.onFirstPage = true;
		}

		return true;
	}

	public void paintComponent(Graphics gc) {
		gc.setFont(font);

		FontMetrics metrics = gc.getFontMetrics(font);
		int hgt = metrics.getHeight();
		int adv = metrics.stringWidth(text);

		if (parent.onFirstPage) {

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
