package piano.engine;

import org.jfugue.elements.Note;
import org.jfugue.parsers.Parser;

import piano.ui.KeyboardParserListener;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class MockAdapterParser extends Parser {

    private InputMap inputMap;
    private ActionMap actionMap;

    private String key = "";

    public MockAdapterParser(InputMap inputMap, ActionMap actionMap) {
        this.inputMap = inputMap;
        this.actionMap = actionMap;

        for (char c = 'a'; c <= 'g'; ++c) {
          inputMap.put(KeyStroke.getKeyStroke(Character.toString(Character.toUpperCase(c))), "b");
        }

        for (char c = '3'; c <= '6'; ++c) {
		inputMap.put(KeyStroke.getKeyStroke(Character.toString(Character.toUpperCase(c))), "b");
        }

        inputMap.put(KeyStroke.getKeyStroke('.'), "b");
        inputMap.put(KeyStroke.getKeyStroke('#'), "b");

        actionMap.put("b", new AbstractAction() {
		public void actionPerformed(ActionEvent actionEvent) {
						String keyboardInput = actionEvent.getActionCommand();
						System.out.println("keyboard input " + keyboardInput);
						if (!keyboardInput.equals(".")) {
							key += keyboardInput;
							return;
						}

						System.out.println("key: " + key);

            Integer keyToInt = KeyboardParserListener.getKeyInt(key);
            if (keyToInt != null) {
		fireNoteEvent(new Note((byte) (int)keyToInt));
            }
            key = "";

          }
      });
    }


}
