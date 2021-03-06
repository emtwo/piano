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
import piano.ui.AdvancedPitchTrainingUI;
import piano.ui.KeyboardParserListener.Colour;

public class TestAdvancedPitchTrainingLogic extends AdvancedPitchTrainingUI {

  private void assertCorrectAttributeValues() {
    // Assert correct sizing for chords.
    assertEquals(1, nextNotesList.size());
    assertEquals(1, chord.size());
    assertNotNull(playString);

    // Assert correct colour for chord
    if (Utils.isSharp(chord.get(0).getValue())) {
      assertEquals(Colour.BLACK, nextNotesList.get(0).colour);
    } else {
      assertEquals(Colour.WHITE, nextNotesList.get(0).colour);
    }

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
  public void testBlackKeys() {
    boolean hasAtLeast1BlackKey = false;

    // Loop until we see a black key.
    while (!hasAtLeast1BlackKey) {
      this.getNewPlayString();
      assertEquals(1, chord.size());
      if (Utils.isSharp(chord.get(0).getValue())) {
        hasAtLeast1BlackKey = true;
      }
    }

    assertTrue(hasAtLeast1BlackKey);
  }

  @After
  public void cleanup() {
    nextNotesList = null;
    chord = null;
    playString = null;
  }
}
