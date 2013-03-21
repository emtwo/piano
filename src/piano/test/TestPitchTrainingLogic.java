package piano.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.jfugue.elements.Note;
import org.junit.After;
import org.junit.Test;

import piano.Utils;
import piano.ui.PitchTrainingUI;
import piano.ui.KeyboardParserListener.Colour;

public class TestPitchTrainingLogic extends PitchTrainingUI {

  private void assertCorrectAttributeValues() {
    // Assert correct sizing for chords.
    assertEquals(1, nextNotesList.size());
    assertEquals(1, chord.size());
    assertNotNull(playString);

    // Assert correct colour for chord
    assertEquals(Colour.WHITE, nextNotesList.get(0).colour);

    // Assert note is in the correct range.
    byte key = chord.get(0).getValue();
    assertTrue(key >= data.minKey && key <= data.maxKey);

    // Assert correct chord used in all variables
    assertTrue(Utils.chordsEqual(nextNotesList.get(0).chord, chord));
  }

  @Test
  public void testGetNewPlayString() {
    // Assert clean starting environment.
    assertNull(nextNotesList);
    assertNull(chord);
    assertNull(playString);

    this.getNewPlayString();
    assertCorrectAttributeValues();

    this.getNewPlayString();
    assertCorrectAttributeValues();
  }

  @Test
  public void testEventuallyNewChord() {
    this.getNewPlayString();
    ArrayList<Note> lastChord = chord;

    while (Utils.chordsEqual(lastChord, chord)) {
      this.getNewPlayString();
    }
    assertFalse(Utils.chordsEqual(chord, lastChord)); // Test that a new chord is returned (not always true).
  }

  @Test
  public void testNoBlackKeys() {
    // Get new note 500 times and check that there are no black notes.
    for (int i = 0; i < 500; i++) {
      this.getNewPlayString();
      assertEquals(1, chord.size());
      assertFalse(Utils.isSharp(chord.get(0).getValue()));
    }
  }

  @After
  public void cleanup() {
    nextNotesList = null;
    chord = null;
    playString = null;
  }
}
