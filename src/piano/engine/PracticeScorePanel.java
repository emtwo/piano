package piano.engine;

public class PracticeScorePanel extends BaseScorePanel {
    public static final int BOTH_HANDS = 0;
    public static final int LEFT_HAND = 1;
    public static final int RIGHT_HAND = 2;

    private PracticeParserListener practiceParserListener;

    public PracticeScorePanel(String name, int hand) {
        super(name);

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

}
