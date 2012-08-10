package piano.prototypes.ui.buttons.marina;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JFrame;

import piano.prototypes.repository.marina.Song;
import piano.prototypes.repository.marina.SongDatabaseAccessor;
import piano.prototypes.ui.marina.ListView;
import piano.prototypes.ui.marina.PlayUI;
import piano.prototypes.ui.marina.SubView;
import piano.prototypes.ui.marina.View;

public class ListButton extends Button {

	String column;
	List<Song> songs;
	
	public ListButton(String text, int x, int y, int width, int height,
			SubView parent, View nextView, JFrame parentFrame, String column) {
	
		super(text, x, y, width, height, parent, nextView, parentFrame);
		this.column = column;
		
		SongDatabaseAccessor accessor = SongDatabaseAccessor.getDatabaseAccessor();
		try {
			songs = accessor.getAllByCriterion(column, text);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void hoverOver() {
	}

	@Override
	public void hoverOut() {
	}
	
	@Override
	public boolean setMouseClicked(int x, int y) {
		System.out.println("ListButton clicked");
		if (!overButton) {
			return false;
		}

		switchView();
		return true;
	}
	
	public void switchView() {
		((ListView)parent).setSongs(songs);
		((ListView)parent).switchView();
	}

	public void paintComponent(Graphics gc) {
		gc.setFont(font);
		
		FontMetrics metrics = gc.getFontMetrics(font);
		int hgt = metrics.getHeight();
		int adv = metrics.stringWidth(text);
		
		if (overButton) {
			gc.setColor(Color.LIGHT_GRAY);
			gc.fillRect(x, y, width, height);
			
			gc.setColor(Color.black);
			gc.drawString(text, x + (width - adv) / 2, y + (height - hgt) / 2 + hgt - diff);
			return;
		}
		
		gc.setColor(Color.white);
		gc.fillRect(x, y, width, height);
		
		gc.setColor(Color.black);
		gc.drawString(text, x + (width - adv) / 2, y + (height - hgt) / 2 + hgt - diff);
	}
}
