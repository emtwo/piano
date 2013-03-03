package piano.engine;

import javax.swing.*;

import java.awt.event.ActionEvent;

public class DemoScorePanel extends BaseScorePanel {

    private Action startPlayAction;

    public DemoScorePanel(String name) {
        super(name);
        mute = false;

        startPlayAction = new AbstractAction() {
            public void actionPerformed(ActionEvent arg0) {
                play(false);
            }
        };

        this.getInputMap().put(KeyStroke.getKeyStroke("SPACE"), "space");
        this.getActionMap().put("space", startPlayAction);
    }

    protected void finish() {
        super.finish();
        refresh();
    }
}
