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
import java.util.concurrent.atomic.AtomicReference;

import javax.swing.JFrame;

import piano.prototypes.marina.NotePlayer;
import piano.prototypes.ui.buttons.marina.KeyboardKey;

@SuppressWarnings("serial")
public class KeyboardView extends Drawing {

	private final int NUM_KEYS = 7;
	private int NUM_OCTAVES;

	private int minKey;
	private int maxKey;

	private int width, height, xVal, yVal, keyWidth, blackKeyWidth;
	private boolean pianoExists, useBlackKeys;

	private HashMap<Integer, PianoMenuData> menuData;
	private Drawing parent;
	private JFrame parentFrame;
	private Keyboard keyboard;

	private AtomicBoolean exitLoop = new AtomicBoolean();
	private AtomicReference<String> keyPressed = new AtomicReference<String>();

	private ArrayList<KeyboardKey> keyList = new ArrayList<KeyboardKey>();
	private ArrayList<KeyboardKey> blackKeyList = new ArrayList<KeyboardKey>();

	public KeyboardView(KeyPressedCallback practiceUI, boolean useBlackKeys, int minKey, int maxKey, Drawing parent,
    HashMap<Integer, PianoMenuData> menuData, final JFrame parentFrame) throws IOException {
	 this(practiceUI, useBlackKeys, minKey, maxKey, parent, menuData, parentFrame, 1);
	}

	public KeyboardView(KeyPressedCallback practiceUI, boolean useBlackKeys, int minKey, int maxKey, Drawing parent,
			HashMap<Integer, PianoMenuData> menuData, final JFrame parentFrame, int numOctaves) throws IOException {
		super();
		this.parent = parent;
		this.menuData = menuData;
		this.parentFrame = parentFrame;
		this.useBlackKeys = useBlackKeys;
		this.minKey = minKey;
		this.maxKey = maxKey;
		keyboard = new Keyboard(practiceUI);
		NUM_OCTAVES = numOctaves;
		NotePlayer.init();
	}

	@Override
	public void switchToView(final JFrame parentFrame) {
		parentFrame.getContentPane().add(KeyboardView.this, 1);
		addKeyListener(keyboard);
		setFocusable(true);
		requestFocusInWindow();
		exitLoop.set(false);
		startPlayThread();
	}

	private void startPlayThread() {
		Thread thread = new Thread(new Runnable() {
			private int noteToPlay;

			public void computeNoteToPlay() {
				/*
				int maxVal = ASCII_g;
				if (useBlackKeys) {
					maxVal = ASCII_l;
				}
				noteToPlay =  (char) (ASCII_a + (int)(Math.random() * ((maxVal - ASCII_a) + 1)));
				*/
				noteToPlay = (minKey + (int)(Math.random() * ((maxKey - minKey) + 1)));
			}

			public void run() {
				computeNoteToPlay();
				while (!exitLoop.get()) {
					try {
						Thread.sleep(1000);
						System.out.println("Note being played: " + noteToPlay);
						NotePlayer.play("[" + Integer.toString(noteToPlay) + "]");
						keyboard.setExpectedKey(noteToPlay);

						// Wait for user to press key
						while(keyPressed.get() == null){
							if (exitLoop.get() == true) {
								break;
							}
							Thread.sleep(100); // Reduce CPU throttling.
						}
						if (exitLoop.get() == true) {
							break;
						}

						// Key was played correctly,
						if (keyboard.getKeyInt(keyPressed.get()) != null && keyboard.getKeyInt(keyPressed.get()) == noteToPlay) {
							computeNoteToPlay();
						}
						keyPressed.set(null);

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

	public void informKeyPressed(final String keyPressed) {
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
		for (int i = 0; i < keySet.size(); i++) {
			KeyboardKey key = keySet.get(i);
			g2.setColor(keyboard.getKeyColor(key.getId()));
			g2.setStroke(new BasicStroke(20, BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL));
			if (keyboard.getKeyColor(key.getId()) == Color.RED) {
				g2.drawLine(300, 80, 500, 280);
				g2.drawLine(300, 280, 500, 80);
			} else if (keyboard.getKeyColor(key.getId()) == Color.GREEN) {
				g2.drawLine(350, 280, 500, 80);
				g2.drawLine(300, 210, 350, 280);
			}
			g2.setStroke(new BasicStroke(5));
			key.paintComponent(g2);
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
		keyWidth = width / (NUM_KEYS * NUM_OCTAVES);
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
		for (int i = minKey; i < maxKey + 1; i++) {
			if (keyboard.isSharp(i)) {
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
		for (int i = minKey; i < maxKey + 1; i++) {
			if (keyboard.isSharp(i)) {
				blackKeyList.add(new KeyboardKey("", xVal, blackKeyYVal, (int) (width * 0.6), blackKeyWidth, true, i));
				blackKeyYVal += keyWidth;
			} else {
				PianoMenuData data = menuData.get(i);
				if (data != null) {
					keyList.add(new KeyboardKey(data.menuText, xVal, keyYVal, width, keyWidth,
							false, parent, data.nextView, parentFrame, i));
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
			button.setMouseClicked(e.getX(), e.getY());
		}
	}

	public void informExitLoop() {
		exitLoop.set(true);
		removeKeyListener(keyboard);
	}
}
