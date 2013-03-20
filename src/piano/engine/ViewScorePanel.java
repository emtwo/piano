package piano.engine;

import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;

public class ViewScorePanel extends BaseScorePanel {

	private Action leftAction, rightAction;

	public ViewScorePanel(String name) {
        super(name);

        //listen for keystrokes
        leftAction = new AbstractAction() {
			public void actionPerformed(ActionEvent arg0) {
                prevPage();
			}
        };

        rightAction = new AbstractAction() {
			public void actionPerformed(ActionEvent arg0) {
                nextPage();
			}
        };

        this.getInputMap().put(KeyStroke.getKeyStroke("LEFT"), "left");
        this.getInputMap().put(KeyStroke.getKeyStroke("RIGHT"), "right");
        this.getInputMap().put(KeyStroke.getKeyStroke("SPACE"), "space");
        this.getActionMap().put("left", leftAction);
        this.getActionMap().put("right", rightAction);
        this.getActionMap().put("space", rightAction);

	}

    public void nextPage() {
        setPage(page + 1);
        repaint();
    }

    public void prevPage() {
        setPage(page - 1);
        repaint();
    }

    protected void mouseClickedNoButton(MouseEvent e) {
        if (SwingUtilities.isLeftMouseButton(e)) {
            nextPage();
        } else if (SwingUtilities.isRightMouseButton(e)) {
            prevPage();
        }
    }

    @Override
    String getHelpText() {
        return "Press space to go to the next page, or click/right click to navigate";
    }

}
