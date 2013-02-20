package score;

import java.util.Vector;

public class Chord {
	public Vector<NotePanel> notes = new Vector<NotePanel>();
	public boolean active = false;

    //private NotePlayer player = new NotePlayer();
    public String musicString = "";
	
	public Chord() {}
	
	public Chord(NotePanel note) {
		addNote(note);
	}

    public Chord(Chord chord) {
        addChord(chord);
    }

    public Chord(Chord c1, Chord c2) {
        for (NotePanel note : c1.notes) {
            addNote(note);
        }
        for (NotePanel note : c2.notes) {
            addNote(note);
        }
    }
	
	public void addNote(NotePanel note) {
        if (!notes.isEmpty() && note.getTime() != notes.get(0).getTime()) {
            System.err.println("Attempting to add note to chord with different start time");
            return;
        }
		notes.add(note);
        if (!musicString.isEmpty()) {
            musicString = musicString.concat("+");
        }
        musicString = musicString.concat(note.getMusicString());
	}

    public void addChord(Chord chord) {
        for (NotePanel note : chord.notes) {
            addNote(note);
        }
    }
	
	public Chord setActive(boolean active) {
        for (NotePanel note : notes) {
            note.active = active;
        }
        return this;
	}

    public boolean contains(NotePanel note) {
        for (NotePanel n : notes) {
            if (n.getValue().equals(note.getValue())) {
                return true;
            }
        }
        return false;
    }
	
	public int size() {
		return notes.size();
	}

    public long getDuration() {
        long duration = 0;
        for (NotePanel note : notes) {
            if (note.getDuration() > duration) {
                duration = note.getDuration();
            }
        }
        return duration;
    }

    public long getMillisDuration() {
        long duration = 0;
        for (NotePanel note : notes) {
            if (note.getMillisDuration() > duration) {
                duration = note.getMillisDuration();
            }
        }
        return duration;
    }

    public long getTime() {
        return notes.get(0).getTime();
    }

    public long getMillisTime() {
        return notes.get(0).getMillisTime();
    }

    public int getPage() {
        return notes.get(0).page;
    }

    public boolean isRest() {
        return (notes.size() == 1 && notes.get(0).isRest);
    }
	
	public void play() {
        NotePlayer.play(musicString);
	}
	
	public void paint() {
        for (NotePanel note : notes) {
            note.repaint();
        }
	}
}
