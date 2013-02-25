package piano.prototypes.ui.marina;

import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;

import javax.swing.JPanel;

import org.jfugue.elements.Note;

import piano.engine.AdapterParserListener;
import piano.engine.ExamParserListener;
import piano.engine.MockAdapterParser;
import piano.engine.PracticeParserListener;

public class KeyboardParserListener extends AdapterParserListener/*extends KeyAdapter implements KeyListener*/ {

	public enum Colour {RED, GREEN, BLUE, WHITE, BLACK};

	private HashMap<Integer, Colour> keyToColorMap = new HashMap<Integer, Colour>();
	//private static HashMap<Integer, String> intToKeyMap = new HashMap<Integer, String>();
	private static HashMap<String, Integer> keyToIntMap = new HashMap<String, Integer>();

	private KeyPressedCallback callback;

	private int expectedKey;

	public KeyboardParserListener(KeyPressedCallback callback) {
		keyToIntMap.put("c4", 48);
		keyToIntMap.put("c#4", 49);
		keyToIntMap.put("d4", 50);
		keyToIntMap.put("d#4", 51);
		keyToIntMap.put("e4", 52);
		keyToIntMap.put("f4", 53);
		keyToIntMap.put("f#4", 54);
		keyToIntMap.put("g4", 55);
		keyToIntMap.put("g#4", 56);
		keyToIntMap.put("a4", 57);
		keyToIntMap.put("a#4", 58);
		keyToIntMap.put("b4", 59);
		keyToIntMap.put("c5", 60);
		keyToIntMap.put("c#5", 61);
		keyToIntMap.put("d5", 62);
		keyToIntMap.put("d#5", 63);
		keyToIntMap.put("e5", 64);
		keyToIntMap.put("f5", 65);
		keyToIntMap.put("f#5", 66);
		keyToIntMap.put("g5", 67);
		keyToIntMap.put("g#5", 68);
		keyToIntMap.put("a5", 69);
		keyToIntMap.put("a#5", 70);
		keyToIntMap.put("b5", 71);
		keyToIntMap.put("c6", 72);
		keyToIntMap.put("c#6", 73);
		keyToIntMap.put("d6", 74);
		keyToIntMap.put("d#6", 75);
		keyToIntMap.put("e6", 76);
		keyToIntMap.put("f6", 77);
		keyToIntMap.put("f#6", 78);
		keyToIntMap.put("g6", 79);
		keyToIntMap.put("g#6", 80);
		keyToIntMap.put("a6", 81);
		keyToIntMap.put("a#6", 82);
		keyToIntMap.put("b6", 83);
		resetKeyColours();

		this.callback = callback;
	}

	private void resetKeyColours() {
		for (String key : keyToIntMap.keySet()) {
		  Colour value;
			if (key.contains("#")) {
				value = Colour.BLACK;
			} else {
				value = Colour.WHITE;
			}
			keyToColorMap.put(keyToIntMap.get(key), value);
		}
	}

	public void setExpectedKey(int expectedKey) {
		this.expectedKey = expectedKey;
	}

	public static Integer getKeyInt(String key) {
		return keyToIntMap.get(key);
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
		int mod = i % 12;
		if (mod == 1 || mod == 3 || mod == 6 || mod == 8 || mod == 10) {
			return true;
		}
		return false;
	}

	public void clear() {
		resetKeyColours();
		callback.clearKeys();
	}

    @Override
	public void pressNoteEvent(Note note) {
            //String noteString = note.getMusicString().replace("q", "").toLowerCase();
        //System.out.println("key? " + noteString);
        int keyInt = (int)note.getValue();

        if (keyInt == expectedKey) {
            keyToColorMap.put(keyInt, Colour.GREEN);
        } else {
            if (keyInt >= 48 && keyInt <= 83) {
                keyToColorMap.put(keyInt, Colour.RED);
            }
        }
        callback.informKeyPressed(keyInt);
	}

    @Override
    public void releaseNoteEvent(Note note) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
