package piano.ui.buttons;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JFrame;

import piano.Point;
import piano.Utils;
import piano.repository.Song;
import piano.repository.SongDatabaseAccessor;
import piano.ui.ButtonClickCallback;
import piano.ui.Drawing;
import piano.ui.Fonts;
import piano.ui.ListView;
import piano.ui.PlayUI;
import piano.ui.SubView;

public class ListButton extends Button {

	String column;
	List<Song> songs;

	String title, author;

	public ListButton(String title, String author, int x, int y, int width, int height) {
		/*
	}
		public ListButton(String text, int x, int y, int width, int height,
				Drawing parent, String column) {
*/
		super(title, x, y, width, height);
		this.title = title;
		this.author = author;
/*
		SongDatabaseAccessor accessor = SongDatabaseAccessor.getDatabaseAccessor();
		try {
			songs = accessor.getAllByCriterion(column, text);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		*/
	}

	@Override
	public void hoverOver() {
	}

	@Override
	public void hoverOut() {
	}
/*
	@Override
	public boolean setMouseClicked(int x, int y, ButtonType buttonType) {
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
	*/

	public void paintComponent(Graphics gc) {
		if (overButton) {
			gc.setColor(Color.LIGHT_GRAY);
			gc.fillRect(x, y, width, height);

			gc.setColor(Color.black);
			truncateAndPrintText(text, 800 / 3, x + 10, y, gc);
			gc.setFont(Fonts.italic_small);
			gc.drawString(author, x + 10, y + 43);
			return;
		}

		gc.setColor(Color.white);
		gc.fillRect(x, y, width, height);

		gc.setColor(Color.black);
		truncateAndPrintText(text, 800 / 3, x + 10, y, gc);
		gc.setFont(Fonts.italic_small);
		gc.drawString(author, x + 10, y + 43);
	}

	private void truncateAndPrintText(String text, int width, int currX, int currY, Graphics g) {
		g.setFont(Fonts.italic);
		FontMetrics metrics = g.getFontMetrics(Fonts.italic);
		int stringWidth = metrics.stringWidth(text);
		if (stringWidth > width) {
			int maxChars =  width / metrics.stringWidth("a") - 3;
			text = text.substring(0, maxChars) + "...";
		}

		g.drawString(text, currX, currY + 23);
	}
}
