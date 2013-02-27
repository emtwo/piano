package piano.engine;

import java.io.*;
import java.util.*;

public class Score implements Serializable {

    public String name;
	public double paperHeight = 169.00937007874;
	public double paperWidth = 119.50157480315;
    public Vector<Chord> allChords[], chords[];
    public Vector<Chord> combinedChords = new Vector<Chord>(), combinedAllChords = new Vector<Chord>();
    public Vector<NotePanel> combinedNotes = new Vector<NotePanel>(), combinedAllNotes = new Vector<NotePanel>();
    public int staves = 1;
    public int pages;
    public int resolution;

    public static Score ParseScore(String name) {
        Score S = new Score();
        ScoreParser P = new ScoreParser(name);

        S.name = P.name;
        S.paperWidth = P.paperWidth;
        S.paperHeight = P.paperHeight;
        S.resolution = P.resolution;
        S.pages = P.pages;
        S.staves = P.staves;
        S.allChords = P.chords;
        S.populateCombinedChords();

        S.save();

        return S;

    }

    public static Score Load(String name) {
        try {
            String filePath = "data/out/" + name + ".dat";
            System.out.println("Loading score from " + filePath);
            FileInputStream f = new FileInputStream(filePath);
            ObjectInput s = new ObjectInputStream(f);
            return (Score) s.readObject();
        } catch (Exception e) {
            System.err.println("Loading score has failed. Error: ");
            e.printStackTrace();
        }
        return null;
    }

    private Score() {}

    private void save() {
        try {
            String filePath = "data/out/" + name + ".dat";
            System.out.println("Saving score to " + filePath);
            FileOutputStream f = new FileOutputStream(filePath);
            ObjectOutput s = new ObjectOutputStream(f);
            s.writeObject(this);
            s.flush();
        } catch (Exception e) {
            System.err.println("Saving score has failed. Error: ");
            e.printStackTrace();
        }
    }

    private void populateCombinedChords() {

        chords = (Vector<Chord>[]) new Vector[staves];

        for (int layer = 0; layer < staves; ++layer) {
            for (Chord chord : allChords[layer]) {
                if (!chord.isRest()) {
                    chords[layer] = new Vector<Chord>();
                    chords[layer].add(chord);
                }
            }
        }

        long[] time = {0L, 0L};
        int cChord[] = {0, 0};

        if (staves > 1) {
            while (cChord[0] < allChords[0].size() && cChord[1] < allChords[1].size()) {
                for (int layer = 0; layer < staves; ++layer) {
                    time[layer] = allChords[layer].get(cChord[layer]).getMillisTime();
                }
                Chord chord = new Chord();
                Chord allChord = new Chord();

                if (time[0] == time[1]) {
                    for (int layer = 0; layer < staves; ++layer) {
                        if (!allChords[layer].get(cChord[layer]).isRest()) {
                            chord.addChord(allChords[layer].get(cChord[layer]));
                        }
                        allChord.addChord(allChords[layer].get(cChord[layer]));
                        ++(cChord[layer]);
                    }
                } else if (time[0] < time[1]) {
                    if (!allChords[0].get(cChord[0]).isRest()) {
                        chord.addChord(allChords[0].get(cChord[0]));
                    }
                    allChord.addChord(allChords[0].get(cChord[0]));
                    ++(cChord[0]);
                } else {
                    if (!allChords[1].get(cChord[1]).isRest()) {
                        chord.addChord(allChords[1].get(cChord[1]));
                    }
                    allChord.addChord(allChords[0].get(cChord[0]));
                    ++(cChord[1]);
                }
                if (!chord.notes.isEmpty()) {
                    combinedChords.add(chord);
                }
                combinedAllChords.add(allChord);
            }
        }

        // schedule last notes if one hand finishes before the other or if there was only one hand to begin with
        for (int layer = 0; layer < staves; ++layer) {
            while (cChord[layer] < allChords[layer].size()) {
                time[layer] = allChords[layer].get(cChord[layer]).getMillisTime();
                if (!allChords[layer].get(cChord[layer]).isRest()) {
                    combinedChords.add(new Chord(allChords[layer].get(cChord[layer])));
                }
                combinedAllChords.add(new Chord(allChords[layer].get(cChord[layer])));
                ++(cChord[layer]);
            }
        }

        // populate combined notes
        for (Chord chord : combinedChords) {
            for (NotePanel note : chord.notes) {
                combinedNotes.add(note);
            }
        }
        for (Chord chord : combinedAllChords) {
            for (NotePanel note : chord.notes) {
                combinedAllNotes.add(note);
            }
        }
	}


}
