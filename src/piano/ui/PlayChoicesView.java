package piano.ui;

import java.awt.Graphics;
import java.awt.event.MouseEvent;

import piano.ui.buttons.StartButton;

public class PlayChoicesView extends Drawing {

	StartButton view, demo, practice, exam, practicel, practicer;

	public PlayChoicesView() {
		super();
		int x = 800 / 3 + 1;
		int width1 = 133;
		int width2 = 266;
		int y = 699;
		view = new StartButton("View", x, y, width1, 50);
		demo = new StartButton("Demo", (x + width1), y, width1, 50);
		practice = new StartButton("Practice", x + width1*2, y, width1, 50);
		exam = new StartButton("Exam", x + width1*3, y, width1, 50);
		practicel = new StartButton("Practice (Left)", x, y + 50, width2, 50);
		practicer = new StartButton("Practice (Right)", x + width2, y + 50, width2, 50);
		/*this.parentView = parentView;

		SongDatabaseAccessor accessor = SongDatabaseAccessor.getDatabaseAccessor();
		try {
			songs = accessor.getAllSongs();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		nextButton = new NextButton("Next", width/5 * 4 + 70, width - 60, width/10, 25, this);
		prevButton = new PrevButton("Previous", width/5 * 4 - 15, width - 60, width/10, 25, this);
		backButton = new BackButton("< Back", width/5 - 70 - width/10, width - 60, width/10, 25, this);
		score = new ScorePanel(JFrameStack.getFrame(), "furelise");
		*/
	}



	public void mouseMoved(MouseEvent e) {
		view.computeMouseEntered(e.getX(), e.getY());
		view.computeMouseExited(e.getX(), e.getY());

		demo.computeMouseEntered(e.getX(), e.getY());
		demo.computeMouseExited(e.getX(), e.getY());

		practice.computeMouseEntered(e.getX(), e.getY());
		practice.computeMouseExited(e.getX(), e.getY());

		exam.computeMouseEntered(e.getX(), e.getY());
		exam.computeMouseExited(e.getX(), e.getY());

		practicel.computeMouseEntered(e.getX(), e.getY());
		practicel.computeMouseExited(e.getX(), e.getY());

		practicer.computeMouseEntered(e.getX(), e.getY());
		practicer.computeMouseExited(e.getX(), e.getY());
	}
/*
	public void mouseClicked(MouseEvent e) {
		System.out.println("SongView mouse clicked.");
    nextButton.setMouseClicked(e.getX(), e.getY(), ButtonType.NEXT_BUTTON);
    prevButton.setMouseClicked(e.getX(), e.getY(), ButtonType.PREVIOUS_BUTTON);

    for (Box button : boxes) {
			if (button == null) continue;
			button.setMouseClicked(e.getX(), e.getY(), ButtonType.BOX_BUTTON);
		}

    if (parentView.hasBack)  {
	backButton.setMouseClicked(e.getX(), e.getY(), ButtonType.BACK);
    }
	}*/

	public void paintComponent(Graphics g) {
		view.paintComponent(g);
		demo.paintComponent(g);
		practice.paintComponent(g);
		exam.paintComponent(g);
		practicel.paintComponent(g);
		practicer.paintComponent(g);
	}
}
