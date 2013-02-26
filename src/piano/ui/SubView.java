package piano.ui;

import java.awt.Graphics;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;

public class SubView extends Drawing {

	protected ListView listView;
	protected SongView songView;
	protected Drawing currentView;
	public final boolean hasBack;

	public SubView(String column, JFrame parentFrame, boolean hasBack) {
		this.hasBack = hasBack;

		songView = new SongView(parentFrame, this);
		if (column != null) {
			listView = new ListView(column, parentFrame, songView, this);
			currentView = listView;
			return;
		}
		currentView = songView;
	}

	public void paintComponent(Graphics g) {
		if (currentView == null) {
			super.paintComponent(g);
			return;
		}

		if (currentView instanceof SongView) {
			((SongView)currentView).paintComponent(g);
		} else {
			((ListView)currentView).paintComponent(g);
		}
	}

	public void mouseClicked(MouseEvent e) {
		currentView.mouseClicked(e);
	}

	public void mouseMoved(MouseEvent e) {
		currentView.mouseMoved(e);
	}

	public void switchView() {
		if (currentView instanceof SongView) {
			currentView = listView;
			return;
		}
		currentView = songView;
	}
}
