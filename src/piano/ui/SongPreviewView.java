package piano.ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import piano.Point;
import piano.Utils;
import piano.engine.ScorePanel;
import piano.engine.ScoreWriter;
import piano.repository.Song;
import piano.repository.SongDatabaseAccessor;
import piano.ui.buttons.BackButton;
import piano.ui.buttons.ButtonType;
import piano.ui.buttons.ListButton;
import piano.ui.buttons.NextButton;
import piano.ui.buttons.PrevButton;

public class SongPreviewView extends Drawing {

	public List<Song> songs;

	//NextButton nextButton;
	//PrevButton prevButton;
	//BackButton backButton;

	public boolean onLastPage = false;
	public boolean onFirstPage = true;
	private boolean filledBoxes = false;

	Box[] boxes = new Box[6];

	public int startIndex = 0, width = 800;
	SubView parentView;
	ScorePanel score;

	public SongPreviewView() {
		/*this.parentView = parentView;

		SongDatabaseAccessor accessor = SongDatabaseAccessor.getDatabaseAccessor();
		try {
			songs = accessor.getAllSongs();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		nextButton = new NextButton("Next", width/5 * 4 + 70, width - 60, width/10, 25, this);
		prevButton = new PrevButton("Previous", width/5 * 4 - 15, width - 60, width/10, 25, this);
		backButton = new BackButton("< Back", width/5 - 70 - width/10, width - 60, width/10, 25, this);
		score = new ScorePanel(JFrameStack.getFrame(), "furelise");
		*/
	}


/*
	public void mouseMoved(MouseEvent e) {
		nextButton.computeMouseEntered(e.getX(), e.getY());
		nextButton.computeMouseExited(e.getX(), e.getY());
		prevButton.computeMouseEntered(e.getX(), e.getY());
		prevButton.computeMouseExited(e.getX(), e.getY());

		for (Box button : boxes) {
			if (button == null) continue;
			button.computeMouseEntered(e.getX(), e.getY());
			button.computeMouseExited(e.getX(), e.getY());
		}

		if (parentView.hasBack)  {
			backButton.computeMouseEntered(e.getX(), e.getY());
			backButton.computeMouseExited(e.getX(), e.getY());
		}
	}

	public void mouseClicked(MouseEvent e) {
		System.out.println("SongView mouse clicked.");
    nextButton.setMouseClicked(e.getX(), e.getY(), ButtonType.NEXT_BUTTON);
    prevButton.setMouseClicked(e.getX(), e.getY(), ButtonType.PREVIOUS_BUTTON);

    for (Box button : boxes) {
			if (button == null) continue;
			button.setMouseClicked(e.getX(), e.getY(), ButtonType.BOX_BUTTON);
		}

    if (parentView.hasBack)  {
	backButton.setMouseClicked(e.getX(), e.getY(), ButtonType.BACK);
    }
	}*/

	public void paintComponent(Graphics g) {
		String text = "PREVIEW:";


		g.setFont(Fonts.italic);
		FontMetrics metrics = g.getFontMetrics(Fonts.italic);
		int hgt = metrics.getHeight();
		int adv = metrics.stringWidth(text);
		g.drawString(text, ((800 / 3) * 2 - adv / 2), 62);

		/*int initialX = 18;
		int initialY = 200;

		int currX = initialX;
		int currY = initialY;

		try {
			int max = Math.min(startIndex + 6, songs.size());
			for (int i = startIndex, j = 0; i < max; i++, j++) {
				// Draw outline
				Graphics2D g2D = (Graphics2D) g;
				g2D.setStroke(new BasicStroke(1F));  // set stroke width of 10
				g.setColor(Color.BLACK);

				if (boxes[j] != null && boxes[j].overButton) {
			    g2D.setStroke(new BasicStroke(6F));  // set stroke width of 10
				}
				g.drawRoundRect(currX - 10, currY - 10, 250, 255, 10, 10);
				if (!filledBoxes) {
					Drawing val;
					if (i == 0) {
						val = score;
					} else {
						val = null;
					}
					boxes[j] = new Box(currX - 10, currY - 10, 250, 255);
				}

				// Draw music image.
				Song s = songs.get(i);
				File f = new File(s.imagePath);
				BufferedImage image;
				image = ImageIO.read(f);

				// Center and draw image.
				Image im = image.getScaledInstance(150, 150, Image.SCALE_DEFAULT);
				Point centered = Utils.centerDrawing(150, 150, new Point(currX-10, currY-10), 250, 255);
				g.drawImage(im, centered.x, currY + 10, null);

				// Draw Title
				truncateAndPrintText(s.title, 240, currX, currY, 185, g);
				truncateAndPrintText(s.author, 240, currX, currY, 205, g);
				truncateAndPrintText(s.category, 240, currX, currY, 225, g);

				// Recalculate x & y.
				currX += width / 3;
				if (currX > width) {
					currX = initialX;
					currY += 270;
				}
			}
			filledBoxes = true;

			prevButton.paintComponent(g);
			nextButton.paintComponent(g);
			if (parentView.hasBack)  {
				backButton.paintComponent(g);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		*/
	}
}
