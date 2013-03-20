package piano.engine;

import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;

public class DemoScorePanel extends BaseScorePanel {

    private Action startPlayAction;

    private boolean playing = false;

    public DemoScorePanel(String name) {
        super(name);
        mute = false;

        startPlayAction = new AbstractAction() {
            public void actionPerformed(ActionEvent arg0) {
                playPause();
            }
        };

        this.getInputMap().put(KeyStroke.getKeyStroke("SPACE"), "space");
        this.getActionMap().put("space", startPlayAction);
    }

    public void playPause() {
        if (!playing) {
            playing = true;
            play();
        } else {
            refresh();
            playing = false;
        }
    }

    protected void mouseClickedNoButton(MouseEvent e) {
        playPause();
    }

    @Override
    String getHelpText() {
        return "Press space or click to start and stop the demo";
    }

    protected void finish() {
        super.finish();
        playing = false;
        refresh();
    }
}
