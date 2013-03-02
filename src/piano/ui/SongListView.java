package piano.ui;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import piano.repository.Song;
import piano.repository.SongDatabaseAccessor;
import piano.ui.buttons.ButtonType;
import piano.ui.buttons.ListButton;

public class SongListView extends Drawing {
  private ButtonClickCallback callback;
	private Song currentSelection;

  public List<Song> listSongs;
	ArrayList<ListButton> listButtons = new ArrayList<ListButton>();

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
		this.callback = callback;
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		for (ListButton button : listButtons) {
			button.computeMouseEntered(e.getX(), e.getY());
			button.computeMouseExited(e.getX(), e.getY());
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
    for (ListButton button : listButtons) {
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
		g.drawString("CHOOSE A SONG:", 10, 62);
		g.drawLine(0, 72, 800, 72);
		g.drawLine(800 / 3, 30, 800 / 3, 800);
		for (ListButton button : listButtons) {
			button.paintComponent(g);
		}
	}

	public Song getCurrentSelection() {
	  return currentSelection;
	}
}
