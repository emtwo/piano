package piano.prototypes.ui.marina;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.sql.SQLException;

import javax.swing.JFrame;

import piano.prototypes.repository.marina.SongDatabaseAccessor;

public class ListButton extends Button {

	String column;
	
	public ListButton(String text, int x, int y, int width, int height,
			View parent, View nextView, JFrame parentFrame, String column) {
	
		super(text, x, y, width, height, parent, nextView, parentFrame);
		this.column = column;
	}

	@Override
	public void hoverOver() {
	}

	@Override
	public void hoverOut() {
	}
	
	@Override
	public boolean setMouseClicked(int x, int y) {
		if (!overButton) {
			return false;
		}
		
		SongDatabaseAccessor accessor = SongDatabaseAccessor.getDatabaseAccessor();
		try {
			((PlayUI)parent).setSongs(accessor.getAllByCriterion(column, text));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
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
