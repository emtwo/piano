package piano.engine;

import org.jfugue.ParserListener;
import org.jfugue.elements.*;
import org.jfugue.parsers.MidiParser;

import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import java.awt.*;
import java.io.*;
import java.util.*;

public class ScoreParser implements ParserListener {

    public String name;
    public double scale;
    public int imageHeight = 842, imageWidth = 595;
    public Vector<Chord> chords[];
    public int staves = 1;
    public int resolution;
    public int pages;
    public Vector<String> imageNames = new Vector<String>();
    public LilyHeader lilyHeader;
    public double outputUnits, outputScale;
    public double staffLineHeight;

    private Vector<NotePanel> notes[];
    private int staffLine;
    private Vector<ArrayList<Integer>> ties = new Vector<ArrayList<Integer>>();
    private Iterator<NotePanel> currNotes[];
    private Vector<String> fontInfo = new Vector<String>();
    private HashMap<String, Font> fonts = new HashMap<String, Font>();
    private int tempo;
    private int layer = 0;
    private long time = 0L;
    private Vector<Vector<Vector<Double>>> staffLines = new Vector<Vector<Vector<Double>>>(); //get(x).get(y) are the stafflines for staff x on page y

    public static boolean hardInstall = false;

    public ScoreParser(String name) {
        this.name = name;

        if (hardInstall) {
            invokeLilyPond();
        }
        parseImages();
        parseLilyPond();
        parsePostScript();
        parseMidi();
    }

    private static void exec(String command) {
        try {
            System.out.println("Invoking: " + command);
            Process p = Runtime.getRuntime().exec(command);

            //get standard output
            BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            while ((line = input.readLine()) != null) {
                System.out.println(line);
            }
            input.close();

            //get error output
            input = new BufferedReader(new InputStreamReader(p.getErrorStream()));
            while ((line = input.readLine()) != null) {
                System.out.println(line);
            }
            input.close();

            p.waitFor();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void invokeLilyPond() {
        exec("convert-ly -e data/ly/" + name + ".ly");
        exec("lilypond --png -dresolution=72 --ps --output=data/out/" + name + " data/ly/" + name + ".ly");
    }

    private void parseImages() {
        String fileLocation = "data/out/" + name + ".png";
        File file = new File(fileLocation);
        if (file.exists()) {
            imageNames.add(fileLocation);
        } else {
            int p = 1;
            fileLocation = "data/out/" + name + "-page" + p + ".png";
            file = new File(fileLocation);
            while (file.exists()) {
                imageNames.add(fileLocation);
                ++p;
                fileLocation = "data/out/" + name + "-page" + p + ".png";
                file = new File(fileLocation);
            }
        }
        if (imageNames.isEmpty()) {
            System.err.println("Lilypond failed to produce any files");
        }
        pages = imageNames.size();
    }

    private void parseLilyPond() {
        lilyHeader = new LilyHeader(name);
        try {
            BufferedReader LYIn = new BufferedReader(new FileReader("data/ly/" + name + ".ly"));
            int line = 1;
            while (LYIn.ready()) {
                String S = LYIn.readLine();

                //TODO: detect new staff location better
                if ((S.contains("\\new Staff") && (S.contains("down")) || S.contains("lower")) || S.contains("NEW STAFF")) {
                    staffLine = line;
                    staves = 2;
                }

                for (int i = 0; i < S.length(); ++i) {
                    if (S.charAt(i) == '~') {
                        ArrayList<Integer> t = new ArrayList<Integer>();
                        t.add(line);
                        t.add(i);
                        ties.add(t);
                    }
                }

                ++line;
            }

            LYIn.close();
        } catch (Exception e) {
            System.err.println("Parsing the score from Lilypond's output has failed. Error: ");
            e.printStackTrace();
        }

        notes = (Vector<NotePanel>[]) new Vector[staves];
        chords = (Vector<Chord>[]) new Vector[staves];
        currNotes = (Iterator<NotePanel>[]) new Iterator[staves];
        for (int i = 0; i < staves; ++i) {
            notes[i] = new Vector<NotePanel>();
            chords[i] = new Vector<Chord>();
            currNotes[i] = notes[i].iterator();
        }
    }

    private void parsePostScript() {
        try {
            BufferedReader PSIn = new BufferedReader(new FileReader("data/out/" + name + ".ps"));

            //parse header
            while (PSIn.ready()) {
                String S = PSIn.readLine().trim();
                if (S.contains("EndSetup")) {
                    break;
                } else {
                    if (S.startsWith("/magfont")) {
                        fontInfo.add(S);
                    } else if (S.startsWith("/lily-output-units")) {
                        outputUnits = Float.parseFloat(S.split(" ")[1]);
                        for (String T : fontInfo) {
                            addFont(T.split(" ")[0].substring(1), T.split(" ")[2].substring(1), (float) outputUnits * Float.parseFloat(T.split(" ")[3]));
                        }
                    } else if (S.startsWith("/output-scale")) {
                        outputScale = Float.parseFloat(S.split(" ")[1]);
                    } else if (S.startsWith("/staff-height")) {
                        staffLineHeight = Float.parseFloat(S.split(" ")[1]) / 4;
                    }
                }
            }
            scale = outputUnits * outputScale;

            for (int layer = 0; layer < staves; ++layer) {
                Vector<Vector<Double>> staff = new Vector<Vector<Double>>();
                for (int page = 0; page < pages; ++page) {
                    staff.add(new Vector<Double>());
                }
                staffLines.add(staff);
            }
            Vector<Double> cStaffs = new Vector<Double>();

            String prevLine = "";
            int currPage = 1;

            //parse the rest of the file
            while (PSIn.ready()) {
                String S = PSIn.readLine().trim();
                if (S.contains("noteheads") || S.contains("rests")) {
                    // extract coordinate, glyph, and font information
                    String T[] = S.split(" ");
                    NotePanel N = new NotePanel();
                    N.setCoordinates(Double.parseDouble(T[0]), -Double.parseDouble(T[1]))
                            .setGlyph(T[4].substring(1))
                            .setGonville(fonts.get(T[3]));
                    if (S.contains("rests")) {
                        T = S.substring(S.lastIndexOf("rests")).split("[. ]");
                        N.setRest(Integer.parseInt(T[1]));
                    }

                    T = prevLine.substring(prevLine.lastIndexOf(this.name + ".ly")).split(":");
                    N.setLine(Integer.parseInt(T[1]), Integer.parseInt(T[2]))
                            .setPage(currPage);

                    if(N.lyLine < staffLine || staffLine == 0) {
                        notes[0].add(N);
                    } else {
                        notes[1].add(N);
                    }
                } else if (S.contains("accidentals")) {
                    //String T[] = S.split(" ");
                    //lastNote.setAccidentals(Double.parseDouble(T[0]), -Double.parseDouble(T[1]), T[4].substring(1), T[3]);
                } else if (S.startsWith("%%Page:")) {
                    currPage = Integer.parseInt(S.split(" ")[1]);
                }

                if (S.contains("draw_line")) {
                    cStaffs.add(-Double.parseDouble(S.split(" ")[1]));
                    if (cStaffs.size() >= 5 * staves) {
                        //found a full staff
                        Collections.sort(cStaffs);
                        staffLines.get(0).get(currPage - 1).add(cStaffs.get(4) + staffLineHeight);
                        if (staves > 1) {
                            staffLines.get(1).get(currPage - 1).add(cStaffs.get(5) - staffLineHeight);
                        }
                        cStaffs.clear();
                    }
                } else if (!S.contains("draw_round_box")) {
                    cStaffs.clear();
                }

                if (S.contains(this.name + ".ly")) {
                    prevLine = S;
                }
            }

            //sort the notes we found from the PS by where they occurred in the .ly
            for (int i = 0; i < staves; ++i) {
                Collections.sort(notes[i]);
            }

            //find tied notes
            for (int i = 0; i < staves; ++i) {
                int cTie = 0;
                int cNote = 0;

                while (cTie < ties.size() && cNote < notes[i].size() - 1) {
                    ArrayList<Integer> tie = ties.get(cTie);
                    NotePanel prevNote = notes[i].get(cNote);
                    NotePanel nextNote = notes[i].get(cNote + 1);
                    if (pairComp(tie.get(0), tie.get(1), prevNote.lyLine, prevNote.lyNumber) != -1 &&
                            pairComp(tie.get(0), tie.get(1), nextNote.lyLine, nextNote.lyNumber) != 1) {
                        // tie is located between these notes
                        nextNote.setTie(true);
                        ++cNote;
                        ++cTie;
                    } else if (pairComp(tie.get(0), tie.get(1), prevNote.lyLine, prevNote.lyNumber) != -1) {
                        ++cNote;
                    } else {
                        ++cTie;
                    }
                }
            }

            PSIn.close();
        } catch (Exception e) {
            System.err.println("Parsing the score from Lilypond's output has failed. Error: ");
            e.printStackTrace();
        }
    }

    private void addFont(String fontKey, String fontName, float scale) {
        try {
            fontName = fontName.toLowerCase();

            FileInputStream stream = new FileInputStream("data/fonts/ttf/" + fontName + ".ttf");

            Font f = Font.createFont(Font.TRUETYPE_FONT, stream);
            f = f.deriveFont(scale);

            fonts.put(fontKey, f);
            stream.close();

            /*GraphicsEnvironment ge = GraphicsEnvironment
                    .getLocalGraphicsEnvironment();

            ge.registerFont(f);*/

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void parseMidi() {
        try {
            //Allow for alternate file extensions of midi files.
            File midiFile = new File("data/out/" + name + ".midi");
            File altFile = new File("data/out/" + name + ".mid");

            if (!midiFile.exists() && altFile.exists()) {
                midiFile = altFile;
            }

            //parse midi
            Sequence sequence = MidiSystem.getSequence(midiFile);
            resolution = sequence.getResolution();

            for (int i = 0; i < staves; ++i) {
                currNotes[i] = notes[i].iterator();
            }

            MidiParser parser = new MidiParser();
            parser.addParserListener(this);
            parser.parse(sequence);

            // initialize any rests trailing at the end
            for (int i = 0; i < staves; ++i) {
                Vector<Chord> currChords = chords[i];
                Iterator<NotePanel> currNote = currNotes[i];
                long tempTime = 0;
                for (int j = currChords.size() - 1; j >=0; --j) {
                    if (!currChords.get(j).isTie()) {
                        tempTime = currChords.get(j).getTime() + currChords.get(j).getDuration();
                        break;
                    }
                }
                while (currNote.hasNext()) {
                    NotePanel notePanel = currNote.next();
                    if (notePanel.getNote() != null) {
                        notePanel.setTime(tempTime)
                                .setTempo(tempo);

                        tempTime += notePanel.getDuration();
                        Chord chord = new Chord(notePanel);
                        currChords.add(chord);
                    }
                }
            }

            //attach staff lines
            for (int layer = 0; layer < staves; ++layer) {
                for (Chord chord : chords[layer]) {
                    for (NotePanel note : chord.notes) {
                        double staffLine = 0.0;
                        for (double line : staffLines.get(layer).get(note.page - 1)) {
                            if (staffLine < 0.0001 || Math.abs(staffLine - note.y) > Math.abs(line - note.y)) {
                                staffLine = line;
                            }
                        }
                        note.setStaffLine(staffLine);
                    }
                }
            }

        } catch (Exception e) {
            System.err.println("Parsing the score from Lilypond's output has failed. Error: ");
            e.printStackTrace();
        }
    }

    // ParserListener methods
    public void noteEvent(Note note) {
        if (layer >= staves) {
            return;
        }
        //System.out.println(note.getMusicString() + " " + note.getMillisDuration() + " " + note.getDecimalDuration());
        Vector<Chord> currChords = chords[layer];
        Iterator<NotePanel> currNote = currNotes[layer];

        if (!currNote.hasNext()) {
            System.err.println("Received noteEvent, but no PostScript notes are left");
            return;
        }

        if (note.getMillisDuration() > 0) {
            NotePanel notePanel = currNote.next();
            // time the last chord ended
            long tempTime = 0;
            for (int i = currChords.size() - 1; i >=0; --i) {
                if (!currChords.get(i).isTie()) {
                    tempTime = currChords.get(i).getTime() + currChords.get(i).getDuration();
                    break;
                }
            }

            if (notePanel.isTie) {
                Chord chord = new Chord();
                //for each note in the last chord, set the next note as a tied note
                for (int i = 0; i < currChords.lastElement().size(); ++i) {
                    notePanel.setTie(true)
                             .setTime(Math.min(tempTime, time - 1))
                             .setTempo(tempo);
                    chord.addNote(notePanel);
                    notePanel = currNote.next();
                }
                currChords.add(chord);
            }


            while (notePanel.isRest) {
                notePanel.setTime(Math.min(tempTime, time - 1)) //hack, in case the rest should be trimmed
                        .setTempo(tempo);
                tempTime += notePanel.getDuration();
                Chord chord = new Chord(notePanel);
                currChords.add(chord);
                //System.out.println("REST: " + notePanel.getMusicString() + " " + notePanel.getDuration());
                notePanel = currNote.next();
            }

            notePanel.setNote(note)
                    .setTime(time)
                    .setTempo(tempo);
            if (currChords.isEmpty() || currChords.lastElement().getTime() != time) {
                Chord chord = new Chord(notePanel);
                currChords.add(chord);
            } else {
                currChords.lastElement().addNote(notePanel);
            }
        }
    }

    public void sequentialNoteEvent(Note note) {
        noteEvent(note);
        System.out.println("sequential");
    }

    public void parallelNoteEvent(Note note) {
        noteEvent(note);
        System.out.println("parallel");
    }

    public void layerEvent(Layer arg0) {
        //++layer;
        System.out.println("layer");
    }

    public void channelPressureEvent(ChannelPressure arg0) {
        System.out.println("pressure");
    }

    public void controllerEvent(Controller arg0) {
        //System.out.println("controller");
    }

    public void instrumentEvent(Instrument arg0) {
        System.out.println("instr");
    }

    public void keySignatureEvent(KeySignature arg0) {
        System.out.println("key");
    }

    public void systemExclusiveEvent(SystemExclusive systemExclusive) {
        System.out.println("system exclusive");
    }

    public void measureEvent(Measure arg0) {
        System.out.println("measure");
    }

    public void pitchBendEvent(PitchBend arg0) {
        System.out.println("bend");

    }

    public void polyphonicPressureEvent(PolyphonicPressure arg0) {
        System.out.println("poly");
    }

    public void tempoEvent(Tempo arg0) {
        //System.out.println("Tempo: " + arg0.getTempo());
        tempo = arg0.getTempo();
    }

    public void timeEvent(Time arg0) {
        //System.out.println("time " + arg0.getTime());
        time = arg0.getTime();

    }

    public void voiceEvent(Voice arg0) {
        layer = arg0.getVoice();
    }

    private static int pairComp(int n1, int n2, int m1, int m2) {
        if (n1 < m1) {
            return -1;
        } else if (n1 > m1) {
            return 1;
        } else if (n2 < m2) {
            return -1;
        } else if (n2 > m2) {
            return 1;
        }
        return 0;
    }
}
