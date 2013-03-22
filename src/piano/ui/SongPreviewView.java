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
		if (image != null) {
		  BufferedImage bImage = image.get(0);
		  if (bImage == null) {
		    return;
		  }
	    int x = (800 / 3) + 5;
	    g.drawImage(bImage.getScaledInstance(530, 660, Image.SCALE_DEFAULT), x, 32, this);
		}
	}

	public void setImage(LilyImage image) {
	  this.image = image;
	}
}
