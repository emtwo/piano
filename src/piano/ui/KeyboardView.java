package piano.ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import org.jfugue.elements.Note;

import piano.Utils;
import piano.engine.MockAdapterParser;
import piano.engine.NotePlayer;
import piano.engine.PianoAdapterParser;
import piano.ui.KeyboardParserListener.Colour;
import piano.ui.buttons.ButtonType;
import piano.ui.buttons.KeyboardKey;

@SuppressWarnings("serial")
public class KeyboardView extends Drawing {

	private final int NUM_KEYS = 7;

	private int width, height, parentWidth, xVal, yVal, keyWidth, blackKeyWidth;
	private boolean pianoExists, drawX, drawCheck;

	private HashMap<Integer, String> menuData;
  private HashMap<Integer, Color> colorData = null;
	private NotesToPlayData noteData;

	private AtomicBoolean exitLoop = new AtomicBoolean();
	private AtomicBoolean replay = new AtomicBoolean();
	private AtomicInteger keyReleasedCount = new AtomicInteger(0);
	private AtomicReference<ArrayList<Note>> chordPressed = new AtomicReference<ArrayList<Note>>(null);

	private ArrayList<KeyboardKey> keyList = new ArrayList<KeyboardKey>();
	private ArrayList<KeyboardKey> blackKeyList = new ArrayList<KeyboardKey>();

	private MockAdapterParser mock;
	private static PianoAdapterParser real;
  private KeyboardParserListener keyboardParserListener;
  private ButtonClickCallback buttonClickCallback;
  private KeyPressedCallback keyPressedCallback;

  private boolean showKeyboardInput, waitForRelease;

  public KeyboardView(KeyPressedCallback practiceUI, NotesToPlayData noteData) {
    this(practiceUI, null, null, noteData);
  }

  public KeyboardView(ButtonClickCallback callback,
      HashMap<Integer, String> menuData, NotesToPlayData noteData) {
    this(null, callback, menuData, noteData);
  }

	public KeyboardView(KeyPressedCallback keyPressedCallback, ButtonClickCallback buttonClickCallback,
			HashMap<Integer, String> menuData, NotesToPlayData noteData) {
	  super();
    mock = new MockAdapterParser(this.getInputMap(), this.getActionMap());
    real = PianoAdapterParser.instance();

    this.buttonClickCallback = buttonClickCallback;
    this.menuData = menuData;
    this.noteData = noteData;
    this.keyPressedCallback = keyPressedCallback;
    keyboardParserListener = new KeyboardParserListener(keyPressedCallback);
	}

	public void switchToView() {
		JFrameStack.getFrame().getContentPane().add(this, 1);
		JFrameStack.getFrame().validate();

		addListeners();
		setFocusable(true);
		requestFocusInWindow();
		JFrameStack.getFrame().validate();
		exitLoop.set(false);
		startPlayThread();
	}

  private void setExpectedColours(Colour colour) throws InterruptedException {
    // Iterate through map and store each colour.
    ArrayList<ChordToColourMap> colours = keyPressedCallback.getNextNotes();
    for (ChordToColourMap map : colours) {
      keyboardParserListener.clear();
      for (Note noteInChord : map.chord) {
        if (colour != null) {
          keyboardParserListener.putColor(noteInChord.getValue(), colour);
        } else {
          if (map.colour != null) {
            keyboardParserListener.putColor(noteInChord.getValue(), map.colour);
          }
        }
      }
      Thread.sleep(500);
      keyboardParserListener.clear();
    }
  }

  private void waitForInput() throws InterruptedException {
    showKeyboardInput = true;
    // Wait for user to press key
    while (chordPressed.get() == null) {
      if (exitLoop.get() == true) {
        break;
      }
      if (replay.get()) {
        replay.set(false);
        NotePlayer.play(keyPressedCallback.getPlayString());
        setExpectedColours(null);
      }
      Thread.sleep(100); // Reduce CPU throttling.
    }
    showKeyboardInput = false;
  }

  private void waitForRelease() throws InterruptedException {
    if (!PianoAdapterParser.attached) {
      keyReleasedCount.set(0);
      return;
    }
    waitForRelease = true;
    while (keyReleasedCount.get() < chordPressed.get().size()) {
      if (exitLoop.get() == true) {
        break;
      }
      Thread.sleep(100); // Reduce CPU throttling.
    }
    keyReleasedCount.set(0);
    waitForRelease = false;
  }

  // Although we already checked if the chord was played correctly in order to colour it
  // we check here once keys are released because now the correct keys can be shown.
  private boolean validateNote() throws InterruptedException {
    Thread.sleep(600);
    keyboardParserListener.clear();
    if (Utils.chordsEqual(chordPressed.get(), keyPressedCallback.getIgnoreInput())) {
      System.out.println("the chord is to be ignored");
      chordPressed.set(null);
      return false;
    } else if (!Utils.chordsEqual(chordPressed.get(), keyPressedCallback.getExpectedChord())) {
      Thread.sleep(800);
      //play correct input
      NotePlayer.play(keyPressedCallback.getPlayString());
      setExpectedColours(Colour.GREEN);
      keyPressedCallback.informKeyValid(false);
    } else {
      keyPressedCallback.informKeyValid(true);
    }
    chordPressed.set(null);
    Thread.sleep(500);

    keyboardParserListener.clear();
    return true;
  }

  public void replay() throws InterruptedException {
    replay.set(true);
  }

	private void startPlayThread() {
		Thread thread = new Thread(new Runnable() {
			public void run() {
			  boolean validNote = true;
			  keyPressedCallback.roundComplete();
				while (!exitLoop.get()) {
				  try {
				    Thread.sleep(1000); // Wait for a bit before starting
				    if (validNote) {
				      NotePlayer.play(keyPressedCallback.getNewPlayString());
				      setExpectedColours(null);
				    }
				    validNote = true;
	          waitForInput();
	          if (exitLoop.get() == true) {
              break;
            }
	          waitForRelease();
	          if (!validateNote()) {
	            validNote = false;
	            continue;
	          }
	          keyPressedCallback.roundComplete();
				  } catch (InterruptedException e) {
				    e.printStackTrace();
				  }
				}
			}
		});
		thread.start();
	}

	public void informChordPressed(final ArrayList<Note> chordPressed) {
	  if (!showKeyboardInput) {
	    return;
	  }

	  this.chordPressed.set(chordPressed);
	  Colour colour;
	  if (Utils.chordsEqual(chordPressed, keyPressedCallback.getIgnoreInput())) {
	    colour = Colour.BLUE;
	  } else if (Utils.chordsEqual(chordPressed, keyPressedCallback.getExpectedChord())) {
	    colour = Colour.GREEN;
	    drawCheck = true;
    } else {
      colour = Colour.RED;
      drawX = true;
    }
	  for (Note note : chordPressed) {
	    byte noteVal = note.getValue();
	    if (noteVal >= 48 && noteVal <= 83) {
	      keyboardParserListener.putColor(noteVal, colour);
	    }
	  }
	}

	public void informKeyReleased() {
	  if (!waitForRelease) {
      return;
    }
	  keyReleasedCount.getAndIncrement();
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.white);
    g.fillRect(0, 0, getWidth(), getHeight());

		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.BLACK);

		paintKeys(keyList, g2);
		paintKeys(blackKeyList, g2);
	}

	private void paintKeys(ArrayList<KeyboardKey> keySet, Graphics2D g2) {
		for (KeyboardKey key : keySet) {
		  Color color = keyboardParserListener.getKeyColor(key.getId());
			g2.setColor(color);
			g2.setStroke(new BasicStroke(20, BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL));
	    int startX = parentWidth / 2 - 100;
	    int endX = parentWidth / 2 + 100;
	    if (color.equals(Color.RED) && drawX) {
	      g2.drawLine(startX, 80, endX, 280);
	      g2.drawLine(startX, 280, endX, 80);
	    } else if (color.equals(Color.GREEN) && drawCheck) {
	      g2.drawLine(startX + 50, 280, endX, 80);
	      g2.drawLine(startX, 210, startX + 50, 280);
	    }
			g2.setStroke(new BasicStroke(5));
			key.paintComponent(g2);
		}
		drawX = drawCheck = false;
	}

	/**
	 * Horizontal Piano Functions
	 */
	public void setDimensions(int parentWidth, int parentHeight) {
		this.parentWidth = parentWidth;
		width = (int)(parentWidth * 0.95);
		height = parentHeight / 2;
		xVal = (parentWidth - width) / 2;
		yVal = height - 50;
		keyWidth = width / (NUM_KEYS * noteData.numOctaves);
		blackKeyWidth = keyWidth / 2;
		if (pianoExists) {
			return;
		}
		createHorizontalPiano();
		pianoExists = true;
	}

	private void createHorizontalPiano() {
		int keyXVal = xVal;
		int blackKeyXVal = keyXVal + (keyWidth - blackKeyWidth / 2);
		for (int i = noteData.minKey; i < noteData.maxKey + 1; i++) {
			if (Utils.isSharp(i)) {
				blackKeyList.add(new KeyboardKey("", blackKeyXVal, yVal + 3, blackKeyWidth, (int) (height * 0.6), true, i));
				blackKeyXVal += keyWidth;
			} else {
				keyList.add(new KeyboardKey("", keyXVal, yVal, keyWidth, height, false, i));
				keyXVal += keyWidth;
			}
			if (i == 52 || i == 64 || i == 76 || i == 59 || i == 70 || i == 83) {
				blackKeyXVal += keyWidth;
			}
		}
	}

	/**
	 * Vertical Piano Functions
	 */
	public void setDimensions(int x, int y, int parentWidth, int parentHeight) {
		width = parentWidth - x;
		height = parentHeight;
		xVal = x;
		yVal = y;
		keyWidth = height / NUM_KEYS;
		blackKeyWidth = keyWidth / 2;
		if (pianoExists) {
			return;
		}
		createVerticalPiano();
		pianoExists = true;
	}

	private void createVerticalPiano() {
		int keyYVal = yVal;
		int blackKeyYVal = keyYVal + (keyWidth - blackKeyWidth / 2);
		for (int i = noteData.minKey; i < noteData.maxKey + 1; i++) {
			if (Utils.isSharp(i)) {
				blackKeyList.add(new KeyboardKey("", xVal, blackKeyYVal, (int) (width * 0.6), blackKeyWidth, true, i));
				blackKeyYVal += keyWidth;
			} else {
				String data = menuData.get(i);
				if (data != null) {
				  KeyboardKey k = new KeyboardKey(data, xVal - 3, keyYVal, width, keyWidth, false, i);
				  if (colorData != null && colorData.get(i) != null)
				    k.setHoverColor(colorData.get(i));
				  keyList.add(k);
				} else {
					keyList.add(new KeyboardKey("", xVal - 3, keyYVal, width, keyWidth, false, i));
				}
				keyYVal += keyWidth;
			}
			if (i == 52 || i == 64 || i == 76) {
				blackKeyYVal += keyWidth;
			}
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		for (KeyboardKey button : keyList) {
			button.computeMouseEntered(e.getX(), e.getY());
			button.computeMouseExited(e.getX(), e.getY());
		}
		this.repaint();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		for (KeyboardKey button : keyList) {
			if(button.setMouseClicked(e.getX(), e.getY()) && button.text != null && !button.text.equals("")) {
				buttonClickCallback.informButtonClicked(ButtonType.KEYBOARD_KEY, button.id);
			}
		}
	}

	private void addListeners() {
	  mock.addParserListener(keyboardParserListener);
    real.addParserListener(keyboardParserListener);
	}

	private void removeListeners() {
	  mock.removeParserListener(keyboardParserListener);
    real.removeParserListener(keyboardParserListener);
	}

	public void informExitLoop() {
		exitLoop.set(true);
		removeListeners();
	}

	public boolean isSharp(int i) {
		return Utils.isSharp(i);
	}

    public void addColorData(HashMap<Integer, Color> ls) {
        this.colorData = ls;
    }
}
