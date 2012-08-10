package piano.prototypes.ui.marina;

import java.awt.Graphics;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;

import piano.prototypes.repository.marina.SongDatabase;
import piano.prototypes.ui.buttons.marina.BackButton;
import piano.prototypes.ui.buttons.marina.PrevButton;

public class ComposerSubView extends SubView {
	
	public ComposerSubView(JFrame parentFrame) {
		super(SongDatabase.COL_AUTHOR, parentFrame);
		currentView = listView;
	}
}
