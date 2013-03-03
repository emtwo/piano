package piano.ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

import piano.engine.*;
import piano.repository.Song;
import piano.ui.buttons.ButtonType;
import piano.ui.buttons.HelpButton;
import piano.ui.buttons.MainMenuButton;

@SuppressWarnings("serial")
public class PlayUI extends Drawing implements ButtonClickCallback {
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
    listView = new SongListView(this);
    playView = new PlayChoicesView(this);
    previewView = new SongPreviewView();
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
	}

	public void mouseClicked(MouseEvent e) {
	  listView.mouseClicked(e);
	  playView.mouseClicked(e);
		if (mainMenu.setMouseClicked(e.getX(), e.getY())) {
			JFrameStack.popPanel();
		}
		repaint();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		listView.mouseMoved(e);
		playView.mouseMoved(e);
		mainMenu.computeMouseEntered(e.getX(), e.getY());
		mainMenu.computeMouseExited(e.getX(), e.getY());

		helpButton.computeMouseEntered(e.getX(), e.getY());
		helpButton.computeMouseExited(e.getX(), e.getY());
		repaint();
	}

  @Override
  public void informButtonClicked(ButtonType buttonType, int buttonId) {
    Song song = listView.getCurrentSelection();
    if (song != null) {
        if (buttonType == buttonType.SONG_SELECTION) {
            LilyImage image = new LilyImage(song.name);
            previewView.setImage(image);
        } else {
            BaseScorePanel score;
            switch (buttonType) {
              case VIEW_BUTTON:
                  score = new ViewScorePanel(song.name);
                  break;
              case DEMO_BUTTON:
                  score = new DemoScorePanel(song.name);
                  break;
              case PRACTICE_BUTTON:
                  score = new PracticeScorePanel(song.name, PracticeScorePanel.BOTH_HANDS);
                  break;
              case EXAM_BUTTON:
              case PRACTICE_LEFT_BUTTON:
                  score = new PracticeScorePanel(song.name, PracticeScorePanel.LEFT_HAND);
                  break;
              case PRACTICE_RIGHT_BUTTON:
                  score = new PracticeScorePanel(song.name, PracticeScorePanel.RIGHT_HAND);
                  break;
              default:
                  score = new ViewScorePanel(song.name);
            }
            //score.switchToView();
            JFrameStack.pushPanel(score);
    }

    }
  }
}
