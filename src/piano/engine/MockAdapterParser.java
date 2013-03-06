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
          inputMap.put(KeyStroke.getKeyStroke(Character.toString(Character.toUpperCase(c))), "z");
        }

        for (char c = '3'; c <= '6'; ++c) {
          inputMap.put(KeyStroke.getKeyStroke(Character.toString(Character.toUpperCase(c))), "z");
        }

        inputMap.put(KeyStroke.getKeyStroke('.'), "z");
        inputMap.put(KeyStroke.getKeyStroke('#'), "z");

        actionMap.put("z", new AbstractAction() {
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


      for (char c = 'a'; c <= 'g'; ++c) {
          inputMap.put(KeyStroke.getKeyStroke(Character.toString(Character.toUpperCase(c))), Character.toString(c));
      }

      for (char c = '1'; c <= '7'; ++c) {
          inputMap.put(KeyStroke.getKeyStroke(Character.toString(Character.toUpperCase(c))), Character.toString(c));
      }

      actionMap.put("a", new AbstractAction() {
          public void actionPerformed(ActionEvent actionEvent) {
              fireNoteEvent(new Note((byte) 69));
          }
      });
      actionMap.put("b", new AbstractAction() {
          public void actionPerformed(ActionEvent actionEvent) {
              fireNoteEvent(new Note((byte) 71));
          }
      });
      actionMap.put("c", new AbstractAction() {
          public void actionPerformed(ActionEvent actionEvent) {
              fireNoteEvent(new Note((byte) 60));
          }
      });
      actionMap.put("d", new AbstractAction() {
          public void actionPerformed(ActionEvent actionEvent) {
              fireNoteEvent(new Note((byte) 62));
          }
      });
      actionMap.put("e", new AbstractAction() {
          public void actionPerformed(ActionEvent actionEvent) {
              fireNoteEvent(new Note((byte) 64));
          }
      });
      actionMap.put("f", new AbstractAction() {
          public void actionPerformed(ActionEvent actionEvent) {
              fireNoteEvent(new Note((byte) 65));
          }
      });
      actionMap.put("g", new AbstractAction() {
          public void actionPerformed(ActionEvent actionEvent) {
              fireNoteEvent(new Note((byte) 67));
          }
      });

      actionMap.put("6", new AbstractAction() {
          public void actionPerformed(ActionEvent actionEvent) {
              fireNoteEvent(new Note((byte) 69));
          }
      });
      actionMap.put("7", new AbstractAction() {
          public void actionPerformed(ActionEvent actionEvent) {
              fireNoteEvent(new Note((byte) 71));
          }
      });
      actionMap.put("1", new AbstractAction() {
          public void actionPerformed(ActionEvent actionEvent) {
              fireNoteEvent(new Note((byte) 60));
          }
      });
      actionMap.put("2", new AbstractAction() {
          public void actionPerformed(ActionEvent actionEvent) {
              fireNoteEvent(new Note((byte) 62));
          }
      });
      actionMap.put("3", new AbstractAction() {
          public void actionPerformed(ActionEvent actionEvent) {
              fireNoteEvent(new Note((byte) 64));
          }
      });
      actionMap.put("4", new AbstractAction() {
          public void actionPerformed(ActionEvent actionEvent) {
              fireNoteEvent(new Note((byte) 65));
          }
      });
      actionMap.put("5", new AbstractAction() {
          public void actionPerformed(ActionEvent actionEvent) {
              fireNoteEvent(new Note((byte) 67));
          }
      });
    }


}
