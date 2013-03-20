package piano.test;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Test;

import piano.ui.AbstractEarTraining;

public class TestAbstractEarTraining extends AbstractEarTraining {

  @Test
  public void testRoundComplete() {
    assertEquals(0, roundNum);
    this.roundComplete();
    assertEquals(1, roundNum);
    this.roundComplete();
    assertEquals(2, roundNum);
  }

  @Test
  public void testInformKeyValid() {
    assertEquals(0, score);
    assertEquals(0, streakCount);

    this.informKeyValid(true);

    assertEquals(1, score);
    assertEquals(1, streakCount);

    this.informKeyValid(true);

    assertEquals(2, score);
    assertEquals(2, streakCount);

    this.informKeyValid(false);

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
