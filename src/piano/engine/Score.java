package piano.engine;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;

public class Score implements Serializable {

    public String name;
    public int imageHeight, imageWidth;
    public double scale = 1.0;
    public Vector<Chord> allChords[], chords[];
    public Vector<Chord> combinedChords, combinedAllChords;
    public Vector<NotePanel> combinedNotes, combinedAllNotes;
    public Vector<String> imageNames;
    public int staves;
    public int pages;
    public int resolution;
    public LilyHeader lilyHeader;
    public double staffLineHeight;

    public static Score ParseScore(String name) {
        Score S = new Score();
        ScoreParser P = new ScoreParser(name);

        S.name = P.name;
        S.imageWidth = P.imageWidth;
        S.imageHeight = P.imageHeight;
        S.resolution = P.resolution;
        S.pages = P.pages;
        S.staves = P.staves;
        S.allChords = P.chords;
        S.imageNames = P.imageNames;
        S.lilyHeader = P.lilyHeader;
        S.scale = P.scale;
        S.staffLineHeight = P.staffLineHeight;
        S.init();

        S.save();

        return S;
    }

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

    public static Score Load(String name) {
        try {
            String filePath = "data/out/" + name + ".dat";
            System.out.println("Loading score from " + filePath);
            FileInputStream f = new FileInputStream(filePath);
            ObjectInputStream s = new ObjectInputStream(f);
            return (Score) s.readObject();
        } catch (InvalidClassException e) {
            System.err.println("Loading score has failed, run again with install = true");
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Loading score has failed. Error: ");
            e.printStackTrace();
        }
        return null;
    }

    public BufferedImage getImage(int n) {
        if (n >= pages) {
            System.err.println("Attempted to get image number " + n + " when there are only " + pages);
            return null;
        }
        String imageName = imageNames.get(n);
        try {
            BufferedImage image = ImageIO.read(new File(imageName));
            if (image.getWidth() != imageWidth || image.getHeight() != imageHeight) {
                System.err.println("Size of score images imageName is not default.");
            }
            return image;
        } catch (IOException e) {
            System.err.println("Could not read file " + imageName);
            e.printStackTrace();
        }
        return null;
    }

    private Score() {}

    private void init() {

        chords = (Vector<Chord>[]) new Vector[staves];
        combinedChords = new Vector<Chord>();
        combinedAllChords = new Vector<Chord>();
        combinedNotes = new Vector<NotePanel>();
        combinedAllNotes = new Vector<NotePanel>();

        for (int layer = 0; layer < staves; ++layer) {
            chords[layer] = new Vector<Chord>();
            for (Chord chord : allChords[layer]) {
                if (!chord.isRestOrTie()) {
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
                        if (!allChords[layer].get(cChord[layer]).isRestOrTie()) {
                            chord.addChord(allChords[layer].get(cChord[layer]));
                        }
                        allChord.addChord(allChords[layer].get(cChord[layer]));
                        ++(cChord[layer]);
                    }
                } else if (time[0] < time[1]) {
                    if (!allChords[0].get(cChord[0]).isRestOrTie()) {
                        chord.addChord(allChords[0].get(cChord[0]));
                    }
                    allChord.addChord(allChords[0].get(cChord[0]));
                    ++(cChord[0]);
                } else {
                    if (!allChords[1].get(cChord[1]).isRestOrTie()) {
                        chord.addChord(allChords[1].get(cChord[1]));
                    }
                    allChord.addChord(allChords[1].get(cChord[1]));
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
                if (!allChords[layer].get(cChord[layer]).isRestOrTie()) {
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
                note.setScore(this);
            }
        }
	}


}
