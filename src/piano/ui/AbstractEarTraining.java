package piano.ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import org.jfugue.elements.Note;

import piano.ui.buttons.HelpButton;
import piano.ui.buttons.MainMenuButton;
import piano.ui.buttons.ReplayButton;

public abstract class AbstractEarTraining extends Drawing implements KeyPressedCallback {

  protected final int WIDTH;
  protected String TITLE;
  protected static final String MIDDLE_C = "Middle C";

  protected NotesToPlayData data;
  protected int roundNum = 1;
  protected int score = 0;
  protected int streakCount = 0;
  protected KeyboardView keyboard;
  protected boolean stopPainting = false;
  protected ArrayList<ChordToColourMap> nextNotesList;
  protected ArrayList<Note> chord;
  protected String playString;

  protected boolean printRound = false;

  protected MainMenuButton mainMenu;
  protected ReplayButton replay;
  protected HelpButton helpButton;

  public AbstractEarTraining(int width, int i) {
    super(width, i);
    WIDTH = width;
    replay = new ReplayButton(WIDTH - 150, 800 - 30, 140, 20);
  }

  public AbstractEarTraining() {
    this(1400, 800);
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
    } else if (replay.setMouseClicked(e.getX(), e.getY())) {
      try {
        keyboard.replay();
      } catch (InterruptedException e1) {
        e1.printStackTrace();
      }
    }
  }

  @Override
  public void mouseMoved(MouseEvent e) {
    replay.computeMouseEntered(e.getX(), e.getY());
    replay.computeMouseExited(e.getX(), e.getY());

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
    roundNum++;
    if (valid) {
      score++;
      streakCount++;
      return;
    }
    streakCount = 0;
  }

  @Override
  public void roundComplete() {
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
  public ArrayList<Note> getIgnoreInput() {
    return null;
  }

  @Override
  public void paintComponent(Graphics g) {
    if (stopPainting) {
      return;
    }
    // Clear screen.
    g.setColor(Color.white);
    g.fillRect(0, 0, getWidth(), getHeight());

    keyboard.setDimensions(getWidth(), getHeight());
    keyboard.paintComponent(g);

    FontMetrics metrics = g.getFontMetrics(Fonts.italic_very_big);
    if (printRound) {
      g.setColor(Color.RED);
      int adv = metrics.stringWidth("ROUND " + roundNum);
      g.drawString("ROUND " + roundNum, (WIDTH/2 - adv/2) + 45, 200);
    }

    g.setColor(Color.BLUE);
    String text = "Score: " + score + " / " + (roundNum - 1);
    int adv = metrics.stringWidth(text);
    int xPos = (WIDTH - adv) + 98;
    g.drawString(text, (WIDTH - adv) + 98, 75);

    text = "Streak: " + streakCount;
    g.drawString(text, xPos, 120);

    GradientPaint gp = new GradientPaint(0, 0, Color.white, 0, 30, Color.LIGHT_GRAY);
    ((Graphics2D) g).setPaint(gp);
    g.fillRect(0, 0, getWidth(), 30);
    g.drawLine(0, 30, getWidth(), 30);

    // Set font and colour
    g.setColor(Color.BLACK);
    g.setFont(Fonts.italic);
    ((Graphics2D) g).setStroke(new BasicStroke(1));

    // Write title.
    metrics = g.getFontMetrics(Fonts.italic);
    adv = metrics.stringWidth(TITLE);
    g.drawString(TITLE, getWidth()/2 - adv/2, 23);

    mainMenu.paintComponent(g);
    replay.paintComponent(g);
    helpButton.paintComponent(g);
  }
}
