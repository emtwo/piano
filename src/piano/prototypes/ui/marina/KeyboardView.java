package piano.prototypes.ui.marina;

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

import piano.prototypes.marina.NotePlayer;
import piano.prototypes.ui.buttons.marina.KeyboardKey;

@SuppressWarnings("serial")
public class KeyboardView extends Drawing {

	private static final int NUM_KEYS = 7;
	private static final int NUM_OCTAVES = 1;

	private int width, height, xVal, yVal, keyWidth, blackKeyWidth;
	private boolean pianoExists;

	private HashMap<Integer, PianoMenuData> menuData;
	private Drawing parent;
	private JFrame parentFrame;
	private Keyboard keyboard;

	private AtomicBoolean correct = new AtomicBoolean();
	private AtomicInteger keyPressed = new AtomicInteger();

	private ArrayList<KeyboardKey> keyList = new ArrayList<KeyboardKey>();
	private ArrayList<KeyboardKey> blackKeyList = new ArrayList<KeyboardKey>();

	public KeyboardView(KeyPressedCallback practiceUI, boolean vertical, Drawing parent,
			HashMap<Integer, PianoMenuData> menuData, final JFrame parentFrame) throws IOException {
		super();
		this.parent = parent;
		this.menuData = menuData;
		this.parentFrame = parentFrame;
		keyboard = new Keyboard(practiceUI);
		NotePlayer.init();
	}

	@Override
	public void switchToView(final JFrame parentFrame) {
		parentFrame.getContentPane().add(KeyboardView.this, 0);
		addKeyListener(keyboard);
		setFocusable(true);
		requestFocusInWindow();
		startPlayThread();
	}

	private void startPlayThread() {
		Thread thread = new Thread(new Runnable() {
			private char noteToPlay;
			public void computeNoteToPlay() {
				noteToPlay =  (char) (97 + (int)(Math.random() * ((103 - 97) + 1)));
			}

			public void run() {
				computeNoteToPlay();
				while (true) {
					try {
						Thread.sleep(1000);
						NotePlayer.play(Character.toString(noteToPlay));
						keyboard.setExpectedKey(noteToPlay);

						// Wait for user to press key
						while(keyPressed.get() == 0){
							Thread.sleep(100); // Reduce CPU throttling.
						}

						// Key was played correctly,
						if (keyPressed.get() == noteToPlay) {
							correct.set(true);
							computeNoteToPlay();
						} else {
							correct.set(false);
						}
						keyPressed.set(0);

						// Clear keyboard colours after 1.5 seconds.
						Thread.sleep(1500);
						keyboard.clear();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});
		thread.start();
	}

	public void informKeyPressed(final char keyPressed) {
		this.keyPressed.set(keyPressed);
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.BLACK);

		for (int i = 0; i < keyList.size(); i++) {
			KeyboardKey whiteKey = keyList.get(i);
			g2.setColor(keyboard.getKeyColor(i));
			g2.setStroke(new BasicStroke(20, BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL));
			if (keyboard.getKeyColor(i) == Color.RED) {
				g2.drawLine(300, 80, 500, 280);
				g2.drawLine(300, 280, 500, 80);
			} else if (keyboard.getKeyColor(i) == Color.GREEN) {
				g2.drawLine(350, 280, 500, 80);
				g2.drawLine(300, 210, 350, 280);
			}
			g2.setStroke(new BasicStroke(5));
			whiteKey.paintComponent(g2);
		}
		for (int i = 0; i < blackKeyList.size(); i++) {
			KeyboardKey blackKey = blackKeyList.get(i);
			blackKey.paintComponent(g2);
		}
	}

	/**
	 * Horizontal Piano Functions
	 */
	public void setDimensions(int parentWidth, int parentHeight) {
		width = (int)(parentWidth * 0.95);
		height = parentHeight / 2;
		xVal = (parentWidth - width) / 2;
		yVal = height - 50;
		keyWidth = width / NUM_KEYS;
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
		for (int i = 0; i < NUM_KEYS; i++) {
			keyList.add(new KeyboardKey("", keyXVal, yVal, keyWidth, height, false));
			keyXVal += keyWidth;
		}

		// Draw the black keys
		for (int i = 0; i < NUM_KEYS - 1; i++) {
			if (i != 2) {
				blackKeyList.add(new KeyboardKey("", blackKeyXVal, yVal, blackKeyWidth, (int) (height * 0.6), true));
			}
			blackKeyXVal += keyWidth;
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
		for (int i = 0; i < NUM_KEYS; i++) {
			PianoMenuData data = menuData.get(i);
			if (data != null) {
				keyList.add(new KeyboardKey(data.menuText, xVal, keyYVal, width, keyWidth,
						false, parent, data.nextView, parentFrame));
			} else {
				keyList.add(new KeyboardKey("", xVal, keyYVal, width, keyWidth, false));
			}
			keyYVal += keyWidth;
		}
		for (int i = 0; i < NUM_KEYS - 1; i++) {
			if (i != 2) {
				blackKeyList.add(new KeyboardKey("", xVal, blackKeyYVal, (int) (width * 0.6), blackKeyWidth, true));
			}
			blackKeyYVal += keyWidth;
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
			button.setMouseClicked(e.getX(), e.getY());
		}
	}
}
