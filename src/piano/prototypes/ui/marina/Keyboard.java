package piano.prototypes.ui.marina;

import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;

public class Keyboard extends KeyAdapter implements KeyListener{

	private enum Colour {RED, GREEN, NORMAL};
	
	private HashMap<Integer, Colour> keyToColorMap = new HashMap<Integer, Colour>();
	private HashMap<Integer, Integer> intToKeyMap = new HashMap<Integer, Integer>();

	private KeyPressedCallback callback;

	private char expectedKey;

	public Keyboard(KeyPressedCallback callback) {
		resetKeyColours();

		intToKeyMap.put(0, (int) 'c');
		intToKeyMap.put(1, (int) 'd');
		intToKeyMap.put(2, (int) 'e');
		intToKeyMap.put(3, (int) 'f');
		intToKeyMap.put(4, (int) 'g');
		intToKeyMap.put(5, (int) 'a');
		intToKeyMap.put(6, (int) 'b');

		this.callback = callback;
	}

	private void resetKeyColours() {
		keyToColorMap.put((int) 'a', Colour.NORMAL);
		keyToColorMap.put((int) 'b', Colour.NORMAL);
		keyToColorMap.put((int) 'c', Colour.NORMAL);
		keyToColorMap.put((int) 'd', Colour.NORMAL);
		keyToColorMap.put((int) 'e', Colour.NORMAL);
		keyToColorMap.put((int) 'f', Colour.NORMAL);
		keyToColorMap.put((int) 'g', Colour.NORMAL);
	}

	@Override
	public void keyPressed(KeyEvent e) {
    if (e.getID() != KeyEvent.KEY_PRESSED || expectedKey == '\u0000') {
	return;
    }

    if (expectedKey == e.getKeyChar()) {
	keyToColorMap.put((int)e.getKeyChar(), Colour.GREEN);
    } else {
	keyToColorMap.put((int)e.getKeyChar(), Colour.RED);
    }
    callback.informKeyPressed(e.getKeyChar());
	}

	public void setExpectedKey(char expectedKey) {
		this.expectedKey = expectedKey;
	}

	public Color getKeyColor(int i) {
		switch(keyToColorMap.get(intToKeyMap.get(i))) {
			case RED:
				return Color.RED;
			case GREEN:
				return Color.GREEN;
			default:
				return Color.WHITE;
		}
	}

	public void clear() {
		resetKeyColours();
		callback.clearKeys();
	}
}
