package piano.engine;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;

public class PracticeScorePanel extends BaseScorePanel {
    public static final int BOTH_HANDS = 0;
    public static final int LEFT_HAND = 1;
    public static final int RIGHT_HAND = 2;

    private PracticeParserListener practiceParserListener;

    private int hand;

    private Action replayAction;

    public PracticeScorePanel(String name, int hand) {
        super(name);

        replayAction = new AbstractAction() {
            public void actionPerformed(ActionEvent arg0) {
                replay();
            }
        };

        this.getInputMap().put(KeyStroke.getKeyStroke("R"), "r");
        this.getActionMap().put("r", replayAction);

        this.hand = hand;

        initParserListener();
    }

    public void initParserListener() {
        if (hand == BOTH_HANDS) {
            practiceParserListener = new PracticeParserListener(this, S.combinedChords);
        } else if (hand == LEFT_HAND) {
            if (S.staves < 2) {
                System.err.println("Attempting to play left hand of single staffed score");
            } else {
                practiceParserListener = new PracticeParserListener(this, S.chords[1]);
            }
        } else if (hand == RIGHT_HAND) {
            practiceParserListener = new PracticeParserListener(this, S.chords[0]);
        } else {
            System.err.println("Invalid hand: " + hand);
        }
        piano.addParserListener(practiceParserListener);
    }

    public void refresh() {
        super.refresh();
        piano.removeParserListener(practiceParserListener);
    }

    public void replay() {
        piano.removeParserListener(practiceParserListener);
        refresh();
        initParserListener();
    }

    protected void mouseClickedNoButton(MouseEvent e) {
        replay();
    }

    @Override
    String getHelpText() {
        return "Play at your own pace. The red notes indicate where you are in the music. Press r or click to restart";
    }
}
