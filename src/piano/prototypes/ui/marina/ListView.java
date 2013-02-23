package piano.prototypes.ui.marina;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import piano.prototypes.repository.marina.Song;
import piano.prototypes.repository.marina.SongDatabaseAccessor;
import piano.prototypes.ui.buttons.marina.ListButton;

public class ListView extends Drawing {
	public List<String> listTitles;
	ArrayList<ListButton> listButtons = new ArrayList<ListButton>();
	
	SongView songView;
	SubView parentView;
	int width = 800;
	
	public ListView(String column, JFrame parentFrame, SongView songView, SubView parentView) {
		this.songView = songView;
		this.parentView = parentView;

		SongDatabaseAccessor accessor = SongDatabaseAccessor.getDatabaseAccessor();
		try {
			listTitles = accessor.getAllOfColumn(column);
			
			int y = 200;
			for (String listTitle : listTitles) {
				listButtons.add(new ListButton(listTitle, 0, y, width, 50, this, null, parentFrame, column));
				y += 50;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void setSongs(List<Song> songs) {
		songView.setSongs(songs);
	}
	
	public void switchView() {
		parentView.switchView();
	}
	
	public void mouseMoved(MouseEvent e) {
		for (ListButton button : listButtons) {
			button.computeMouseEntered(e.getX(), e.getY());
			button.computeMouseExited(e.getX(), e.getY());
		}
	}
	
	public void mouseClicked(MouseEvent e) {
    for (ListButton button : listButtons) {
			button.setMouseClicked(e.getX(), e.getY());
		}
	}
	
	public void paintComponent(Graphics g) {
		for (ListButton button : listButtons) {
			button.paintComponent(g);
		}
	}

}
