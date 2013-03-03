package piano.engine;

import javax.swing.*;

import java.awt.event.ActionEvent;

public class ViewScorePanel extends BaseScorePanel {

	private Action leftAction, rightAction;

	public ViewScorePanel(String name) {
        super(name);

        //listen for keystrokes
        leftAction = new AbstractAction() {
			public void actionPerformed(ActionEvent arg0) {
                setPage(page - 1);
                repaint();
			}
        };

        rightAction = new AbstractAction() {
			public void actionPerformed(ActionEvent arg0) {
                setPage(page + 1);
                repaint();
			}
        };

        this.getInputMap().put(KeyStroke.getKeyStroke("LEFT"), "left");
        this.getInputMap().put(KeyStroke.getKeyStroke("RIGHT"), "right");
        this.getActionMap().put("left", leftAction);
        this.getActionMap().put("right", rightAction);

	}

}
