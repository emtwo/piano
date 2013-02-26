package piano.engine;

import org.jfugue.Player;

public class NotePlayer implements Runnable {

    private String musicString;
    private Player player = new Player();

    private NotePlayer(String musicString) {
        this.musicString = musicString;
    }

    public void run() {
        System.out.println(musicString);
        player.play(musicString);
        player.close();
    }

    public static void init() {
        play("");
    }

    public static void play(String musicString) {
        Thread notePlayer = new Thread(new NotePlayer(musicString));
        notePlayer.start();
    }
}
