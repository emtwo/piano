package piano.prototypes.ui.marina;

import javax.swing.JFrame;

import piano.prototypes.repository.marina.SongDatabase;
import piano.prototypes.ui.buttons.marina.BackButton;

public class GenreSubView extends SubView {
	
	public GenreSubView(JFrame parentFrame) {
		super(SongDatabase.COL_CATEGORY, parentFrame);
		currentView = listView;
	}
}
