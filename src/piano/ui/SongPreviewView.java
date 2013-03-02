package piano.ui;

import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.List;

import piano.engine.LilyImage;
import piano.repository.Song;

public class SongPreviewView extends Drawing {

	public List<Song> songs;

	public int startIndex = 0, width = 800;

	private LilyImage image;

	public SongPreviewView() {}

	public void paintComponent(Graphics g) {
		String text = "PREVIEW:";

		g.setFont(Fonts.italic);
		FontMetrics metrics = g.getFontMetrics(Fonts.italic);
		int adv = metrics.stringWidth(text);
		g.drawString(text, ((800 / 3) * 2 - adv / 2), 62);

		if (image != null) {
		  BufferedImage bImage = image.get(0);
		  if (bImage == null) {
		    return;
		  }
	    int x = (800 / 3) + 20;
	    g.drawImage(bImage.getScaledInstance(500, 600, Image.SCALE_DEFAULT), x, 75, this);
		}
	}

	public void setImage(LilyImage image) {
	  this.image = image;
	}
}
