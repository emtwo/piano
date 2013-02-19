package piano.prototypes.ui.marina;

import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;

public class Keyboard extends KeyAdapter implements KeyListener{

	public enum Colour {RED, GREEN, BLUE, WHITE, BLACK};

	private HashMap<Integer, Colour> keyToColorMap = new HashMap<Integer, Colour>();
	private HashMap<Integer, String> intToKeyMap = new HashMap<Integer, String>();
	private HashMap<String, Integer> keyToIntMap = new HashMap<String, Integer>();

	private KeyPressedCallback callback;

	private String keyInput = "";

	private int expectedKey;

	public Keyboard(KeyPressedCallback callback) {
		keyToIntMap.put("c4", 48);
		keyToIntMap.put("h4", 49);
		keyToIntMap.put("d4", 50);
		keyToIntMap.put("i4", 51);
		keyToIntMap.put("e4", 52);
		keyToIntMap.put("f4", 53);
		keyToIntMap.put("j4", 54);
		keyToIntMap.put("g4", 55);
		keyToIntMap.put("k4", 56);
		keyToIntMap.put("a4", 57);
		keyToIntMap.put("l4", 58);
		keyToIntMap.put("b4", 59);
		keyToIntMap.put("c5", 60);
		keyToIntMap.put("h5", 61);
		keyToIntMap.put("d5", 62);
		keyToIntMap.put("i5", 63);
		keyToIntMap.put("e5", 64);
		keyToIntMap.put("f5", 65);
		keyToIntMap.put("j5", 66);
		keyToIntMap.put("g5", 67);
		keyToIntMap.put("k5", 68);
		keyToIntMap.put("a5", 69);
		keyToIntMap.put("l5", 70);
		keyToIntMap.put("b5", 71);
		keyToIntMap.put("c6", 72);
		keyToIntMap.put("h6", 73);
		keyToIntMap.put("d6", 74);
		keyToIntMap.put("i6", 75);
		keyToIntMap.put("e6", 76);
		keyToIntMap.put("f6", 77);
		keyToIntMap.put("j6", 78);
		keyToIntMap.put("g6", 79);
		keyToIntMap.put("k6", 80);
		keyToIntMap.put("a6", 81);
		keyToIntMap.put("l6", 82);
		keyToIntMap.put("b6", 83);

		intToKeyMap.put(48, "c4");
		intToKeyMap.put(49, "h4");
		intToKeyMap.put(50, "d4");
		intToKeyMap.put(51, "i4");
		intToKeyMap.put(52, "e4");
		intToKeyMap.put(53, "f4");
		intToKeyMap.put(54, "j4");
		intToKeyMap.put(55, "g4");
		intToKeyMap.put(56, "k4");
		intToKeyMap.put(57, "a4");
		intToKeyMap.put(58, "l4");
		intToKeyMap.put(59, "b4");
		intToKeyMap.put(60, "c5");
		intToKeyMap.put(61, "h5");
		intToKeyMap.put(62, "d5");
		intToKeyMap.put(63, "i5");
		intToKeyMap.put(64, "e5");
		intToKeyMap.put(65, "f5");
		intToKeyMap.put(66, "j5");
		intToKeyMap.put(67, "g5");
		intToKeyMap.put(68, "k5");
		intToKeyMap.put(69, "a5");
		intToKeyMap.put(70, "l5");
		intToKeyMap.put(71, "b5");
		intToKeyMap.put(72, "c6");
		intToKeyMap.put(73, "h6");
		intToKeyMap.put(74, "d6");
		intToKeyMap.put(75, "i6");
		intToKeyMap.put(76, "e6");
		intToKeyMap.put(77, "f6");
		intToKeyMap.put(78, "j6");
		intToKeyMap.put(79, "g6");
		intToKeyMap.put(80, "k6");
		intToKeyMap.put(81, "a6");
		intToKeyMap.put(82, "l6");
		intToKeyMap.put(83, "b6");
		resetKeyColours();

		this.callback = callback;
	}

	private void resetKeyColours() {
		for (String key : keyToIntMap.keySet()) {
		  Colour value;
			if (key.charAt(0) > 'g') {
				value = Colour.BLACK;
			} else {
				value = Colour.WHITE;
			}
			keyToColorMap.put(keyToIntMap.get(key), value);
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
    if (e.getID() != KeyEvent.KEY_PRESSED) {
	return;
    }

    if (keyInput.length() <= 1) {
	keyInput += e.getKeyChar();
    }

    if (keyInput.length() != 2) {
	return;
    }

    System.out.println("key? " + keyInput);

    if (keyToIntMap.get(keyInput) != null && keyToIntMap.get(keyInput) == expectedKey) {
	keyToColorMap.put(keyToIntMap.get(keyInput), Colour.GREEN);
    } else {
	Integer keyInt = keyToIntMap.get(keyInput);
	if (keyInt != null) {
		keyToColorMap.put(keyInt, Colour.RED);
	}
    }
    callback.informKeyPressed(keyInput);
    keyInput = "";
	}

	public void setExpectedKey(int expectedKey) {
		this.expectedKey = expectedKey;
	}

	public Integer getKeyInt(String key) {
		return keyToIntMap.get(key);
	}

	public String getKeyVal(int key) {
		return intToKeyMap.get(key);
	}

	public void setKeyColour(int key, Colour colour) {
		keyToColorMap.put(key, colour);
	}

	public Color getKeyColor(int i) {
		switch(keyToColorMap.get(i)) {
			case RED:
				return Color.RED;
			case GREEN:
				return Color.GREEN;
			case BLUE:
				return Color.BLUE;
			case WHITE:
				return Color.WHITE;
			default:
				return Color.BLACK;
		}
	}

	public boolean isSharp(int i) {
		String keyString = intToKeyMap.get(i);
		return keyString.charAt(0) > 'g';
	}
	public void clear() {
		resetKeyColours();
		callback.clearKeys();
	}
}
