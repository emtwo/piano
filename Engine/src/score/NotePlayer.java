package score;

import org.jfugue.Player;

public class NotePlayer extends Thread {

	private String musicString;
	private Player player;
	NotePlayer(String musicString) {
		this.musicString = musicString;
		player = new Player();
	}
	public void run() {
		player.play(musicString);
		player.close();
	}
}
