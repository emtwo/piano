package piano.prototypes.ui.marina;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import piano.prototypes.marina.Point;
import piano.prototypes.marina.Utils;
import piano.prototypes.repository.marina.Song;
import piano.prototypes.repository.marina.SongDatabase;
import piano.prototypes.repository.marina.SongDatabaseAccessor;

public class PlayUI extends View {

	ArrayList<SimpleButton> buttons = new ArrayList<SimpleButton>();
	ArrayList<ListButton> genreButtons = new ArrayList<ListButton>();
	ArrayList<ListButton> composerButtons = new ArrayList<ListButton>();
	NextButton nextButton;
	PrevButton prevButton;
	
	public boolean onLastPage = false;
	public boolean onFirstPage = true;
	
	public List<Song> songs;
	public List<String> genres, composers;
	int width = 800;
	int startIndex = 0;
	
	JFrame parentFrame;
	
	public PlayUI(JFrame parentFrame) throws IOException {
		super();
	
		this.parentFrame = parentFrame;
		
		buttons.add(new SimpleButton("All", 0, 100, width/3, 60, this, null, parentFrame));
		buttons.add(new SimpleButton("Genre", width/3, 100, width/3, 60, this, null, parentFrame));
		buttons.add(new SimpleButton("Composer", width/3 * 2, 100, width/3, 60, this, null, parentFrame));
		buttons.get(0).setChosen(true);
		setScreenData(buttons.get(0));
		
		nextButton = new NextButton("Next", width/5 * 4 + 70, width - 60, width/10, 25, this, null, parentFrame);
		prevButton = new PrevButton("Previous", width/5 - 70 - width/10, width - 60, width/10, 25, this, null, parentFrame);
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		// Clear screen.
		g.setColor(Color.white);
		g.fillRect(0, 0, getWidth(), getHeight());
		
		// Set font and colour
		g.setColor(Color.BLACK);
		g.setFont(Fonts.big);
		
		// Write "Choose a Song".
		String text = "Choose a Song!";
		FontMetrics metrics = g.getFontMetrics(Fonts.big);
		int adv = metrics.stringWidth(text);
		g.drawString(text, getWidth()/2 - adv/2, 65);
		
		g.drawLine(0, 85, getWidth(), 85);
		g.drawLine(0, 10, getWidth(), 10);
		
		for (SimpleButton button : buttons) {
			button.paintComponent(g);
		}
		// Choose based on tab what to display.
		if (songs != null) {
			displaySongList(g);
		} else if (genres != null) {
			displayGenreList(g);
		} else {
			displayComposerList(g);
		}
		
		prevButton.paintComponent(g);
		nextButton.paintComponent(g);
	}

	public void mouseClicked(MouseEvent e) {
		if (nextButton.setMouseClicked(e.getX(), e.getY()) ||
				prevButton.setMouseClicked(e.getX(), e.getY())) {
			this.repaint();
		}
		
		SimpleButton chosenButton = null;
		
		// Find the chosen button.
    for (SimpleButton button : buttons) {
    	if (button.setMouseClicked(e.getX(), e.getY())) {
    		chosenButton = button;
    	}
    }
    for (ListButton button : genreButtons) {
			button.setMouseClicked(e.getX(), e.getY());
		}
		for (ListButton button : composerButtons) {
			button.setMouseClicked(e.getX(), e.getY());
		}
    
    if (chosenButton == null) {
    	return;
    }

    // Make all buttons not chosen.
    for (SimpleButton button : buttons) {
    	button.setChosen(false);
    }
    
    // Set our chosen button to chosen.
    chosenButton.setChosen(true);
    setScreenData(chosenButton);
    this.repaint();
	}
	
	@Override
	public void mouseMoved(MouseEvent e) {
		for (SimpleButton button : buttons) {
			button.computeMouseEntered(e.getX(), e.getY());
			button.computeMouseExited(e.getX(), e.getY());
		}
		for (ListButton button : genreButtons) {
			button.computeMouseEntered(e.getX(), e.getY());
			button.computeMouseExited(e.getX(), e.getY());
		}
		for (ListButton button : composerButtons) {
			button.computeMouseEntered(e.getX(), e.getY());
			button.computeMouseExited(e.getX(), e.getY());
		}
		nextButton.computeMouseEntered(e.getX(), e.getY());
		nextButton.computeMouseExited(e.getX(), e.getY());
		prevButton.computeMouseEntered(e.getX(), e.getY());
		prevButton.computeMouseExited(e.getX(), e.getY());
		
		super.mouseMoved(e);
	}
	
	public int getStartIndex() {
		return startIndex;
	}
	
	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
		System.out.println("Set start index to: " + startIndex);
	}
	
	public void displayGenreList(Graphics g) {
		for (ListButton button : genreButtons) {
			button.paintComponent(g);
		}
	}
	
	public void displayComposerList(Graphics g) {
		for (ListButton button : composerButtons) {
			button.paintComponent(g);
		}
	}
	
	public void displaySongList(Graphics g) {
		int initialX = 18;
		int initialY = 200;
		
		int currX = initialX;
		int currY = initialY;
		
		try {
			int max = Math.min(startIndex + 6, songs.size());
			for (int i = startIndex; i < max; i++) {
				// Draw outline
				g.setColor(Color.BLACK);
				g.drawRoundRect(currX - 10, currY - 10, 250, 255, 10, 10);
				
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
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void truncateAndPrintText(String text, int width, int currX, int currY, int yVal, Graphics g) {
		g.setFont(Fonts.italic_small);
		FontMetrics metrics = g.getFontMetrics(Fonts.italic_small);
		int stringWidth = metrics.stringWidth(text);
		if (stringWidth > 240) {
			int maxChars =  240 / metrics.stringWidth("a") - 3;
			text = text.substring(0, maxChars) + "...";
		}
		
		Point centered = Utils.centerDrawing(stringWidth, 150, new Point(currX-10, currY-10), 250, 255);
		
		g.drawString(text, centered.x, currY + yVal);
	}
	
	public void setSongs(List<Song> songs) {
		composers = genres = null;
		this.songs = songs;
	}
	
	public void setScreenData(SimpleButton button) {
		SongDatabaseAccessor accessor = SongDatabaseAccessor.getDatabaseAccessor();
		try {
			songs = null;
			composers = genres = null;
			if (button.text.equals("All")) {
				songs = accessor.getAllSongs();
			} else if (button.text.equals("Genre")) {
				genres = accessor.getAllOfColumn(SongDatabase.COL_CATEGORY);
				genreButtons.clear();
				
				int x = 0;
				int y = 200;
				for (String genre : genres) {
					genreButtons.add(new ListButton(genre, x, y, width, 50, this, null, parentFrame, "genre"));
					y += 50;
				}
			} else if (button.text.equals("Composer")) {
				composers = accessor.getAllOfColumn(SongDatabase.COL_AUTHOR);
				composerButtons.clear();
				
				int x = 0;
				int y = 200;
				for (String genre : composers) {
					composerButtons.add(new ListButton(genre, x, y, width, 50, this, null, parentFrame, "composer"));
					y += 50;
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
