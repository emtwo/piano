package piano.ui;

import java.awt.event.MouseEvent;
import java.util.ArrayList;

import org.jfugue.elements.Note;

import piano.ui.buttons.HelpButton;
import piano.ui.buttons.MainMenuButton;

public class AbstractEarTraining extends Drawing implements KeyPressedCallback {

  protected KeyboardView keyboard;
  protected boolean stopPainting = false;
  protected ArrayList<ChordToColourMap> nextNotesList;
  protected ArrayList<Note> chord;
  protected String playString;

  protected MainMenuButton mainMenu;
  protected HelpButton helpButton;

  public AbstractEarTraining(int width, int i) {
    super(width, i);
  }

  public AbstractEarTraining() {
    super();
  }

  @Override
  public void informExitLoop() {
    stopPainting = true;
    keyboard.informExitLoop();
  }

  @Override
  public void clearKeys() {
    repaint();
  }

   public void switchToView() {
      stopPainting = false;
      keyboard.switchToView();
    }

  @Override
  public ArrayList<ChordToColourMap> getNextNotes() {
    return nextNotesList;
  }

  @Override
  public String getPlayString() {
    return playString;
  }

  @Override
  public ArrayList<Note> getExpectedChord() {
    return chord;
  }

  @Override
  public void informChordPressed(ArrayList<Note> chord) {
    repaint();
    keyboard.informChordPressed(chord);
  }

  @Override
  public void informKeyReleased(int keyReleased) {
    repaint();
    keyboard.informKeyReleased();
  }

  @Override
  public void mouseClicked(MouseEvent e) {
    if (mainMenu.setMouseClicked(e.getX(), e.getY())) {
      informExitLoop();
      JFrameStack.popPanel();
    }
  }

  @Override
  public void mouseMoved(MouseEvent e) {
    mainMenu.computeMouseEntered(e.getX(), e.getY());
    mainMenu.computeMouseExited(e.getX(), e.getY());

    helpButton.computeMouseEntered(e.getX(), e.getY());
    helpButton.computeMouseExited(e.getX(), e.getY());
    this.repaint();
  }

  @Override
  public String getNewPlayString() { return null; }
}
