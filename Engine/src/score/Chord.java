package score;

import java.util.Iterator;
import java.util.Vector;

import org.jfugue.Player;

public class Chord {
	public Vector<NotePanel> notes;
	public boolean active = false;
	
	public Chord() {
		notes = new Vector<NotePanel>();
	}
	
	public Chord(NotePanel note) {
		notes = new Vector<NotePanel>();
		addNote(note);
	}
	
	public void addNote(NotePanel note) {
		notes.add(note);
	}
	
	public void setActive(boolean active) {
		this.active = active;
	}
	
	public int size() {
		return notes.size();
	}
	
	public void play(boolean wait) {
		if (size() > 0) {
			String musicString = notes.elementAt(0).getMusicString();
			Iterator<NotePanel> it = notes.iterator();
			it.next();
		    while (it.hasNext()) {
		        musicString = musicString.concat("+" + it.next().getMusicString());
		    }
		    
		    System.out.println(musicString);
		    
		    if (wait) {
		    	//don't run in a new thread, function only returns after finished playing
				Player p = new Player();
				p.play(musicString);
				p.close();
		    } else {
		    	NotePlayer player = new NotePlayer(musicString);
		    	player.start();
		    }
		}
	}
	
	public void play() {
		play(false);
	}
	
	public void paint() {
		Iterator<NotePanel> it = notes.iterator();
        while (it.hasNext()) {
        	it.next().repaint();
        }
	}
}
