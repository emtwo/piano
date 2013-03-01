package piano.ui;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import piano.repository.Song;
import piano.repository.SongDatabaseAccessor;
import piano.ui.buttons.ButtonType;
import piano.ui.buttons.ListButton;

public class SongListView extends Drawing {
	public List<Song> listSongs;
	ArrayList<ListButton> listButtons = new ArrayList<ListButton>();

	int width = 800 / 3;

	public SongListView() {
		SongDatabaseAccessor accessor = SongDatabaseAccessor.getDatabaseAccessor();
		try {
			listSongs = accessor.getAllSongs();

			int y = 73;
			for (Song song : listSongs) {
				listButtons.add(new ListButton(song.title, song.author, 0, y, width, 50));
				y += 50;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void mouseMoved(MouseEvent e) {
		for (ListButton button : listButtons) {
			button.computeMouseEntered(e.getX(), e.getY());
			button.computeMouseExited(e.getX(), e.getY());
		}
	}

	public void mouseClicked(MouseEvent e) {
    for (ListButton button : listButtons) {
			button.setMouseClicked(e.getX(), e.getY(), ButtonType.LIST_BUTTON);
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
}
