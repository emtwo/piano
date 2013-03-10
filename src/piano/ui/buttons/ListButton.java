package piano.ui.buttons;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.util.List;

import piano.repository.Song;
import piano.ui.Fonts;

public class ListButton extends Button {

  private boolean selected;
	List<Song> songs;

	Song song;

	public ListButton(Song song, int x, int y, int width, int height) {
		super(song.title, x, y, width, height);
		this.song = song;
	}

	@Override
	public void hoverOver() {
	}

	@Override
	public void hoverOut() {
	}

	public void decrementY() {
	  y -= 30;
	}

	public void incrementY() {
	  y += 30;
	}

	public int getY() {
	  return this.y;
	}

	public int getHeight() {
	  return this.height;
	}

	@Override
	public boolean setMouseClicked(int x, int y) {
		if (!overButton) {
			return false;
		}

		return true;
	}

	public void paintComponent(Graphics gc) {
		if (selected) {
		  gc.setColor(new Color(34, 139, 34));
			gc.fillRect(x, y, width, height);
		} else if (overButton) {
		  gc.setColor(Color.LIGHT_GRAY);
		  gc.fillRect(x, y, width, height);
		}
		if (overButton || selected) {
		  gc.setColor(Color.black);
      truncateAndPrintText(text, 800 / 3, x + 10, y, gc);
      gc.setFont(Fonts.italic_small);
      gc.drawString(song.composer, x + 10, y + 43);
      return;
		}

		gc.setColor(Color.white);
		gc.fillRect(x, y, width, height);

		gc.setColor(Color.black);
		truncateAndPrintText(text, 800 / 3, x + 10, y, gc);
		gc.setFont(Fonts.italic_small);
		gc.drawString(song.composer, x + 10, y + 43);
	}

	private void truncateAndPrintText(String text, int width, int currX, int currY, Graphics g) {
		g.setFont(Fonts.italic);
		FontMetrics metrics = g.getFontMetrics(Fonts.italic);
		int stringWidth = metrics.stringWidth(text);
		if (stringWidth > width) {
			int maxChars =  width / metrics.stringWidth("a") - 3;
			text = text.substring(0, maxChars) + "...";
		}

		g.drawString(text, currX, currY + 23);
	}

	public void setSelected(boolean selected) {
	  this.selected = selected;
	}

	public Song getSong() {
	  return song;
	}
}
