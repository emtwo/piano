package piano.ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;

import piano.repository.SongDatabase;
import piano.ui.buttons.ButtonType;
import piano.ui.buttons.HelpButton;
import piano.ui.buttons.MainMenuButton;
import piano.ui.buttons.SimpleButton;

@SuppressWarnings("serial")
public class PlayUI extends Drawing {
	private MainMenuButton mainMenu;
	private HelpButton helpButton;
	private SongListView listView;
	private PlayChoicesView playView;
	private SongPreviewView previewView;

	private static final String HELP_TEXT = "Choose a song. Preview it in the preview panel. Choose view the song, see a demo of it, test yourself on it, or practice playing it with one or both hands.";
	private static final String TITLE = "Play";

	public PlayUI() {
		super();

		mainMenu = new MainMenuButton("< Main Menu", 5, 5, 150, 20);
		helpButton = new HelpButton("?", HELP_TEXT, 775, 5, 20, 20);
    listView = new SongListView();
    playView = new PlayChoicesView();
    previewView = new SongPreviewView();


		//buttons.add(new SimpleButton("All", 0, 100, width/3, 60, this, null));
		//buttons.add(new SimpleButton("Genre", width/3, 100, width/3, 60, this, null));
		//buttons.add(new SimpleButton("Composer", width/3 * 2, 100, width/3, 60, this, null));
		//buttons.get(0).setChosen(true);

		//menuView = mainMenuView;
		//mainMenu = new MainMenuButton("Main Menu", width/2 - width/12, width - 60, width/6, 25);

		//allView = new SubView(null, false);
		//genreView = new SubView(SongDatabase.COL_CATEGORY, true);
		//composerView = new SubView(SongDatabase.COL_AUTHOR, true);

		//currentSubView = allView;
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		// Clear screen.
		g.setColor(Color.white);
		g.fillRect(0, 0, getWidth(), getHeight());

		// Set font and colour
		g.setColor(Color.BLACK);
		g.setFont(Fonts.italic);
		((Graphics2D) g).setStroke(new BasicStroke(1));

		// Write title.
		FontMetrics metrics = g.getFontMetrics(Fonts.italic);
		int adv = metrics.stringWidth(TITLE);
		g.drawString(TITLE, getWidth()/2 - adv/2, 23);
		g.drawLine(0, 30, getWidth(), 30);

		listView.paintComponent(g);
		playView.paintComponent(g);
		previewView.paintComponent(g);
		mainMenu.paintComponent(g);
		helpButton.paintComponent(g);
		/*
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

		mainMenu.paintComponent(g);

		// Choose based on tab what to display.
		currentSubView.paintComponent(g);
		*/
	}

	public void mouseClicked(MouseEvent e) {
		if (mainMenu.setMouseClicked(e.getX(), e.getY())) {
			JFrameStack.popPanel();
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		listView.mouseMoved(e);
		playView.mouseMoved(e);
		mainMenu.computeMouseEntered(e.getX(), e.getY());
		mainMenu.computeMouseExited(e.getX(), e.getY());

		helpButton.computeMouseEntered(e.getX(), e.getY());
		helpButton.computeMouseExited(e.getX(), e.getY());
		this.repaint();
	}
}
