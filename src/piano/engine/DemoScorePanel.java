package piano.engine;

import javax.swing.*;

import java.awt.event.ActionEvent;

public class DemoScorePanel extends BaseScorePanel {

    private Action startPlayAction;

    private boolean playing = false;

    public DemoScorePanel(String name) {
        super(name);
        mute = false;

        startPlayAction = new AbstractAction() {
            public void actionPerformed(ActionEvent arg0) {
                if (!playing) {
                    playing = true;
                    play();
                } else {
                    refresh();
                    playing = false;
                }
            }
        };

        this.getInputMap().put(KeyStroke.getKeyStroke("SPACE"), "space");
        this.getActionMap().put("space", startPlayAction);
    }

    @Override
    String getHelpText() {
        return "Press space to start and stop the demo";
    }

    protected void finish() {
        super.finish();
        refresh();
    }
}
