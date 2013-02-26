package piano;

import java.awt.Graphics;

public class Utils {
	// First width/height are to be nested in B.
	public static Point centerDrawing(int width, int height, Point B, int width2, int height2) {
		return new Point(B.x + (width2/2 - width/2), B.y + (height2/2 - height/2));
	}
}
