package piano.ui;

import java.awt.Graphics;
import java.awt.event.MouseEvent;

import piano.ui.buttons.ButtonType;
import piano.ui.buttons.ListButton;
import piano.ui.buttons.StartButton;

public class PlayChoicesView extends Drawing {

	StartButton view, demo, practice, exam, practicel, practicer;

	private ButtonClickCallback callback;

	public PlayChoicesView(ButtonClickCallback callback) {
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
		this.callback = callback;
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

	public void mouseClicked(MouseEvent e) {
	  if(view.setMouseClicked(e.getX(), e.getY())) {
	    callback.informButtonClicked(ButtonType.VIEW_BUTTON, 0);
	  } else if (demo.setMouseClicked(e.getX(), e.getY())) {
	    callback.informButtonClicked(ButtonType.DEMO_BUTTON, 0);
	  } else if (practice.setMouseClicked(e.getX(), e.getY())) {
	    callback.informButtonClicked(ButtonType.PRACTICE_BUTTON, 0);
    } else if (exam.setMouseClicked(e.getX(), e.getY())) {
      callback.informButtonClicked(ButtonType.EXAM_BUTTON, 0);
    } else if (practicel.setMouseClicked(e.getX(), e.getY())) {
      callback.informButtonClicked(ButtonType.PRACTICE_LEFT_BUTTON, 0);
    } else if (practicer.setMouseClicked(e.getX(), e.getY())) {
      callback.informButtonClicked(ButtonType.PRACTICE_RIGHT_BUTTON, 0);
    }
	}

	public void paintComponent(Graphics g) {
		view.paintComponent(g);
		demo.paintComponent(g);
		practice.paintComponent(g);
		exam.paintComponent(g);
		practicel.paintComponent(g);
		practicer.paintComponent(g);
	}
}
