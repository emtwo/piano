package piano.ui;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import org.jfugue.elements.Note;

import piano.ui.buttons.HelpButton;
import piano.ui.buttons.MainMenuButton;

public class AbstractEarTraining extends Drawing implements KeyPressedCallback {

  protected final int WIDTH;
  protected String TITLE;
  protected static final String MIDDLE_C = "Middle C";

  protected int roundNum = 0;
  protected int score = 0;
  protected int streakCount = 0;
  protected KeyboardView keyboard;
  protected boolean stopPainting = false;
  protected ArrayList<ChordToColourMap> nextNotesList;
  protected ArrayList<Note> chord;
  protected String playString;

  protected boolean printRound = false;

  protected MainMenuButton mainMenu;
  protected HelpButton helpButton;

  public AbstractEarTraining(int width, int i) {
    super(width, i);
    WIDTH = width;
  }

  public AbstractEarTraining() {
    super(1400, 800);
    WIDTH = 1400;
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

  @Override
  public void informKeyValid(boolean valid) {
    if (valid) {
      score++;
      streakCount++;
      return;
    }
    streakCount = 0;
  }

  @Override
  public void roundComplete() {
    roundNum++;
    printRound = true;
    repaint();
    try {
      Thread.sleep(1500);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    printRound = false;
    repaint();
  }

  @Override
  public void paintComponent(Graphics g) {
    if (printRound) {
      g.setColor(Color.RED);
      FontMetrics metrics = g.getFontMetrics(Fonts.italic_very_big);
      int adv = metrics.stringWidth("ROUND " + roundNum);
      g.drawString("ROUND " + roundNum, (WIDTH/2 - adv/2) + 45, 200);
    }

    g.setColor(Color.BLUE);
    FontMetrics metrics = g.getFontMetrics(Fonts.italic_very_big);
    String text = "Score: " + score + " / " + (roundNum - 1);
    int adv = metrics.stringWidth(text);
    g.drawString(text, (WIDTH - adv) + 85, 65);

    text = "Streak: " + streakCount;
    g.drawString(text, (WIDTH - adv) + 100, 105);
  }
}
