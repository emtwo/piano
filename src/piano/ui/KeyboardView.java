package piano.ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import javax.swing.JFrame;

import piano.engine.MockAdapterParser;
import piano.engine.NotePlayer;
import piano.engine.PianoAdapterParser;
import piano.ui.buttons.ButtonType;
import piano.ui.buttons.KeyboardKey;

@SuppressWarnings("serial")
public class KeyboardView extends Drawing {

	private final int NUM_KEYS = 7;

	private int width, height, parentWidth, xVal, yVal, keyWidth, blackKeyWidth;
	private boolean pianoExists;

	private HashMap<Integer, String> menuData;
	private NotesToPlayData noteData;

	private AtomicBoolean exitLoop = new AtomicBoolean();
	private AtomicInteger keyPressed = new AtomicInteger(-1);

	private ArrayList<KeyboardKey> keyList = new ArrayList<KeyboardKey>();
	private ArrayList<KeyboardKey> blackKeyList = new ArrayList<KeyboardKey>();

	private MockAdapterParser mock;
	private PianoAdapterParser real;
  private KeyboardParserListener keyboardParserListener;
  private ButtonClickCallback callback;

  public KeyboardView(KeyPressedCallback practiceUI, Drawing parent, NotesToPlayData noteData) {
	this(practiceUI, parent, null, null, noteData);
  }

	public KeyboardView(KeyPressedCallback practiceUI, Drawing parent, ButtonClickCallback callback,
			HashMap<Integer, String> menuData, NotesToPlayData noteData) {
		super();
		keyboardParserListener = new KeyboardParserListener(practiceUI);
		mock = new MockAdapterParser(this.getInputMap(), this.getActionMap());
		real = PianoAdapterParser.instance();

		this.callback = callback;
		this.menuData = menuData;
		this.noteData = noteData;
	}

	public void switchToView() {
		JFrameStack.getFrame().getContentPane().add(this, 1);
		JFrameStack.getFrame().validate();

		mock.addParserListener(keyboardParserListener);
		real.addParserListener(keyboardParserListener);
		setFocusable(true);
		requestFocusInWindow();
		JFrameStack.getFrame().validate();
		exitLoop.set(false);
		startPlayThread();
	}

	private void startPlayThread() {
		Thread thread = new Thread(new Runnable() {

			public ArrayList<NoteToColourMap> computeNoteToPlay() {
				return noteData.getNotesToPlay();
			}

			public void run() {
				ArrayList<NoteToColourMap> mapList = computeNoteToPlay();
				while (!exitLoop.get()) {
					try {
						Thread.sleep(1000);
						String noteString = "";
						for (NoteToColourMap map : mapList) {
							System.out.println("Note being added: " + map.note);
							noteString += ("[" + map.note + "] ");
						}
						NotePlayer.play(noteString);
						for (NoteToColourMap map : mapList) {
							keyboardParserListener.clear();
							keyboardParserListener.setKeyColour(map.note, map.colour);
							Thread.sleep(500);
						}
						keyboardParserListener.clear();

						int noteToPlay = mapList.get(mapList.size() - 1).note;
						keyboardParserListener.setExpectedKey(noteToPlay);

						// Wait for user to press key
						while(keyPressed.get() == -1){
							if (exitLoop.get() == true) {
								break;
							}
							Thread.sleep(100); // Reduce CPU throttling.
						}
						if (exitLoop.get() == true) {
							break;
						}

						// Key was played correctly,
						if (keyPressed.get() != -1 && keyPressed.get() == noteToPlay) {
							mapList = computeNoteToPlay();
						}
						keyPressed.set(-1);

						// Clear keyboard colours after 1.5 seconds.
						Thread.sleep(1500);
						keyboardParserListener.clear();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});
		thread.start();
	}

	public void informKeyPressed(final int keyPressed) {
		this.keyPressed.set(keyPressed);
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
			g2.setColor(keyboardParserListener.getKeyColor(key.getId()));
			g2.setStroke(new BasicStroke(20, BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL));
			int startX = parentWidth / 2 - 100;
			int endX = parentWidth / 2 + 100;
			if (keyboardParserListener.getKeyColor(key.getId()) == Color.RED) {
				g2.drawLine(startX, 80, endX, 280);
				g2.drawLine(startX, 280, endX, 80);
			} else if (keyboardParserListener.getKeyColor(key.getId()) == Color.GREEN) {
				g2.drawLine(startX + 50, 280, endX, 80);
				g2.drawLine(startX, 210, startX + 50, 280);
			}
			g2.setStroke(new BasicStroke(5));
			key.paintComponent(g2);
		}
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
			if (keyboardParserListener.isSharp(i)) {
				blackKeyList.add(new KeyboardKey("", blackKeyXVal, yVal, blackKeyWidth, (int) (height * 0.6), true, i));
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
			if (keyboardParserListener.isSharp(i)) {
				blackKeyList.add(new KeyboardKey("", xVal, blackKeyYVal, (int) (width * 0.6), blackKeyWidth, true, i));
				blackKeyYVal += keyWidth;
			} else {
				String data = menuData.get(i);
				if (data != null) {
					keyList.add(new KeyboardKey(data, xVal, keyYVal, width, keyWidth, false, i));
				} else {
					keyList.add(new KeyboardKey("", xVal, keyYVal, width, keyWidth, false, i));
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
				callback.informButtonClicked(ButtonType.KEYBOARD_KEY, button.id);
			}
		}
	}

	public void informExitLoop() {
		exitLoop.set(true);
		mock.removeParserListener(keyboardParserListener);
		real.removeParserListener(keyboardParserListener);
	}

	public boolean isSharp(int i) {
		return keyboardParserListener.isSharp(i);
	}
}
