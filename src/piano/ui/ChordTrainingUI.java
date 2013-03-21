package piano.ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Arrays;

import org.jfugue.elements.Note;

import piano.ui.buttons.HelpButton;
import piano.ui.buttons.MainMenuButton;

public class ChordTrainingUI extends AbstractEarTraining {

  private static final String HELP_TEXT = "Listen to the note and play the note you think it is. Green means correct, red means incorrect.";
  private ArrayList<ArrayList<Integer>> chordDifferences = new ArrayList<ArrayList<Integer>>();

  public ChordTrainingUI() {
    super();
    TITLE = "Chord Training";
    data = new NotesToPlayData();
    data.minKey = 48;
    data.maxKey = 83;
    data.numOctaves = 3;
    data.useBlackKeys = true;

    keyboard = new KeyboardView(this, data);
    mainMenu = new MainMenuButton("< Ear Training Menu", 5, 5, 150, 20);
    helpButton = new HelpButton("?", HELP_TEXT, WIDTH - 25, 5, 20, 20, this);

    chordDifferences.add(new ArrayList<Integer> (Arrays.asList(2)));
    chordDifferences.add(new ArrayList<Integer> (Arrays.asList(3)));
    chordDifferences.add(new ArrayList<Integer> (Arrays.asList(4)));
    chordDifferences.add(new ArrayList<Integer> (Arrays.asList(5)));
    chordDifferences.add(new ArrayList<Integer> (Arrays.asList(7)));
    chordDifferences.add(new ArrayList<Integer> (Arrays.asList(9)));
    chordDifferences.add(new ArrayList<Integer> (Arrays.asList(2, 7)));
    chordDifferences.add(new ArrayList<Integer> (Arrays.asList(3, 7)));
    chordDifferences.add(new ArrayList<Integer> (Arrays.asList(4, 7)));
    chordDifferences.add(new ArrayList<Integer> (Arrays.asList(3, 6)));
    chordDifferences.add(new ArrayList<Integer> (Arrays.asList(4, 8)));
    chordDifferences.add(new ArrayList<Integer> (Arrays.asList(5, 8)));
    chordDifferences.add(new ArrayList<Integer> (Arrays.asList(5, 9)));
  }

  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    // Middle C
    g.setFont(Fonts.italic);
    g.setColor(Color.RED);
    g.drawString(MIDDLE_C, 465, 790);
    g.drawLine(507, 765, 507, 753);
  }

  private ArrayList<Integer> chooseRandomChord() {
    return chordDifferences.get(((int)(Math.random() * (chordDifferences.size()))));
  }

  protected int getRootNote() {
    int rootNote = ((int)(Math.random() * 3));
    switch (rootNote) {
      case 0:
        rootNote = 48;
        break;
      case 1:
        rootNote = 60;
        break;
      case 2:
        rootNote = 72;
        break;
    }
    return rootNote;
  }

  @Override
  public String getNewPlayString() {
    nextNotesList = new ArrayList<ChordToColourMap>();
    int rootNote = getRootNote();
    Note note = new Note((byte) rootNote);
    ArrayList<Integer> chordDifferences = chooseRandomChord();

    // Create chord to be played and add it to the map.
    ArrayList<Note> notes = new ArrayList<Note>();
    chord = new ArrayList<Note>();
    notes.add(note);
    chord.add(note);
    playString = note.getMusicString() + "+";
    for (int i = 0; i < chordDifferences.size(); i++) {
      Integer chordDiff = chordDifferences.get(i);
      Note nextNote = new Note((byte) (rootNote + chordDiff));
      notes.add(nextNote);
      chord.add(nextNote);
      playString += (nextNote.getMusicString());
      if (i != chordDifferences.size() - 1) {
        playString += "+";
      }
    }
    ChordToColourMap map1 = new ChordToColourMap(notes, null);
    nextNotesList.add(map1);
    return playString;
  }
}