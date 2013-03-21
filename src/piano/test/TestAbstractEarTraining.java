package piano.test;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Test;

import piano.ui.AbstractEarTraining;

public class TestAbstractEarTraining extends AbstractEarTraining {

  @Test
  public void testInformKeyValid() {
    assertEquals(0, score);
    assertEquals(1, roundNum);
    assertEquals(0, streakCount);

    this.informKeyValid(true);

    assertEquals(2, roundNum);
    assertEquals(1, score);
    assertEquals(1, streakCount);

    this.informKeyValid(true);

    assertEquals(2, score);
    assertEquals(3, roundNum);
    assertEquals(2, streakCount);

    this.informKeyValid(false);

    assertEquals(4, roundNum);
    assertEquals(2, score);
    assertEquals(0, streakCount);
  }

  @After
  public void clearData() {
    score = 0;
    streakCount = 0;
    roundNum = 0;
  }
}
