package piano.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import piano.repository.Song;
import piano.repository.SongDatabaseAccessor;
import piano.ui.buttons.ButtonType;
import piano.ui.buttons.ListButton;
import piano.ui.buttons.ScrollButton;

public class SongListView extends Drawing {
  private ButtonClickCallback callback;
	private Song currentSelection;

  public List<Song> listSongs;
	ArrayList<ListButton> listButtons = new ArrayList<ListButton>();
	private ScrollButton scrollDownButton, scrollUpButton;

	int width = 800 / 3;

	public SongListView(ButtonClickCallback callback) {
		SongDatabaseAccessor accessor = SongDatabaseAccessor.getDatabaseAccessor();
		try {
			listSongs = accessor.getAllSongs();

			int y = 73;
			for (Song song : listSongs) {
				listButtons.add(new ListButton(song, 0, y, width, 50));
				y += 50;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		scrollDownButton = new ScrollButton(new String("\u25BC"), width / 2, 799 - 50, width / 2, 50);
		scrollUpButton = new ScrollButton(new String("\u25B2"), 0, 799 - 50, width / 2, 50);
		this.callback = callback;
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		for (ListButton button : listButtons) {
		  if (button.getY() + button.getHeight() < 72 || button.getY() + button.getHeight() > 750) {
        continue;
      }
			button.computeMouseEntered(e.getX(), e.getY());
			button.computeMouseExited(e.getX(), e.getY());
		}
		scrollDownButton.computeMouseEntered(e.getX(), e.getY());
		scrollDownButton.computeMouseExited(e.getX(), e.getY());

		scrollUpButton.computeMouseEntered(e.getX(), e.getY());
		scrollUpButton.computeMouseExited(e.getX(), e.getY());
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	  ListButton firstButton = listButtons.get(0);
	  ListButton lastButton = listButtons.get(listButtons.size() - 1);
	  if (scrollDownButton.setMouseClicked(e.getX(), e.getY())) {
	    if (lastButton.getY() + lastButton.getHeight() <= 750) {
        scrollDownButton.outButton();
        return;
      }
	    for (ListButton listButton : listButtons) {
	      listButton.decrementY();
	    }
	    return;
	  }
    if (scrollUpButton.setMouseClicked(e.getX(), e.getY())) {
      if (firstButton.getY() >= 72) {
        scrollUpButton.outButton();
        return;
      }
      for (ListButton listButton : listButtons) {
        listButton.incrementY();
      }
      return;
    }
    for (ListButton button : listButtons) {
      // Don't register clicks on non-visible buttons.
      if (button.getY() + button.getHeight() < 72 || button.getY() + button.getHeight() > 750) {
        continue;
      }
			if (button.setMouseClicked(e.getX(), e.getY())) {
			  for (ListButton buttonToClear : listButtons) {
			    buttonToClear.setSelected(false);
			  }
			  button.setSelected(true);
			  currentSelection = button.getSong();
			  callback.informButtonClicked(ButtonType.SONG_SELECTION, 0);
			  return;
			}
		}
	}

	public void paintComponent(Graphics g) {
	  for (ListButton button : listButtons) {
      button.paintComponent(g);
    }
    g.setColor(Color.white);
    g.fillRect(0, 30, 800, 42);

	  g.setColor(Color.BLACK);
    g.setFont(Fonts.italic);
		g.drawString("CHOOSE A SONG:", 10, 62);
		g.drawLine(0, 72, 800, 72);
		g.drawLine(800 / 3, 30, 800 / 3, 800);

		scrollUpButton.paintComponent(g);
		scrollDownButton.paintComponent(g);
	}

	public Song getCurrentSelection() {
	  return currentSelection;
	}
}
