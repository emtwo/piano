package piano.ui;

public class AdvancedChordTrainingUI extends ChordTrainingUI {

  public AdvancedChordTrainingUI() {
    super();
    TITLE = "Advanced Chord Training";
  }

  @Override
  protected int getRootNote() {
    return (48 + (int)(Math.random() * ((71 - 48) + 1)));
  }
}
