package piano;

import java.util.ArrayList;
import java.util.HashMap;

import org.jfugue.elements.Note;

public class Utils {
	// First width/height are to be nested in B.
	public static Point centerDrawing(int width, int height, Point B, int width2, int height2) {
		return new Point(B.x + (width2/2 - width/2), B.y + (height2/2 - height/2));
	}

	public static boolean chordsEqual(ArrayList<Note> c1, ArrayList<Note> c2) {
	  // Both are null.
	  if (c1 == c2) {
	    return true;
	  }

	  // If both are not null (previous case) then 1 is null and the other isn't.
	  if (c1 == null || c2 == null) {
	    return false;
	  }

	  // If neither are null they must have the same size to be equal.
	  if (c1.size() != c2.size()) {
	    return false;
	  }

	  HashMap<Integer, Integer> noteMap = new HashMap<Integer, Integer>();
	  for (int i = 0; i < c1.size(); i++) {
	    byte key1 = c1.get(i).getValue();
	    Integer chord1NoteCount = noteMap.get(key1);
	    if (chord1NoteCount == null) {
        chord1NoteCount = 0;
      }
	    noteMap.put((int) key1, ++chord1NoteCount);

	    byte key2 = c2.get(i).getValue();
	    Integer chord2NoteCount = noteMap.get((int) key2);
	    if (chord2NoteCount == null) {
	      chord2NoteCount = 0;
	    }
	    noteMap.put((int) key2, ++chord2NoteCount);
	  }
	  if (noteMap.size() != c1.size()) {
	    return false;
	  }
	  for (Integer count : noteMap.values()) {
	    if (count != 2) {
	      return false;
	    }
	  }
	  return true;
	}
}
