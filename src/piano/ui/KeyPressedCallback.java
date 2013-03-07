package piano.ui;

import java.util.ArrayList;

import org.jfugue.elements.Note;

public interface KeyPressedCallback {
	public void informChordPressed(ArrayList<Note> chord);
	public void informKeyReleased(int keyReleased);
	public String getNewPlayString();
	public ArrayList<ChordToColourMap> getNextNotes();
	public ArrayList<Note> getExpectedChord();
	public void informExitLoop();
	public void clearKeys();
	public String getPlayString();
	public void informKeyValid(boolean valid);
  public void roundComplete();
}
