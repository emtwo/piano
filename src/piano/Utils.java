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

	  for (Note note1 : c1) {
	    boolean noteExists = false;
	    for (Note note2 : c2) {
	      if (note1.getValue() == note2.getValue()) {
	        noteExists = true;
	      }
	    }
	    if (!noteExists) {
	      return false;
	    }
	  }
	  return true;
	}
}
