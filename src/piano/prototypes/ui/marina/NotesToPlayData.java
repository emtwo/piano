package piano.prototypes.ui.marina;

import java.util.ArrayList;

public class NotesToPlayData {

	public boolean useBlackKeys;
	public int minKey, maxKey, numOctaves;
	private GetNextNoteCallback callback;

	public NotesToPlayData(GetNextNoteCallback callback) {
		this.callback = callback;
	}

	public ArrayList<NoteToColourMap> getNotesToPlay() {
		return callback.getNextNotes();
	}
}
