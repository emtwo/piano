package piano.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import piano.engine.NotePanel;
import piano.engine.Score;
import piano.engine.ScoreParser;

public class TestScoreParser {
    @Test
    public void TestBasicParsing() {
        boolean oldInstallValue = ScoreParser.hardInstall;

        Score score = Score.ParseScore("londonbridge");

        assertEquals(score.staves, 2);

        assertEquals(score.combinedAllNotes.size(), 62);
        assertEquals(score.combinedAllChords.size(), 29);
        assertEquals(score.combinedNotes.size(), 50);
        assertEquals(score.combinedChords.size(), 25);
        assertEquals(score.allChords[0].size(), 24);
        assertEquals(score.allChords[1].size(), 29);

        NotePanel firstNoteRightHand = score.allChords[0].get(0).notes.get(0);
        NotePanel firstNoteLeftHand = score.allChords[1].get(0).notes.get(0);

        assertEquals(firstNoteRightHand.y, firstNoteRightHand.staffLine - score.staffLineHeight * 2, 0.0001);
        assertEquals(firstNoteLeftHand.y, firstNoteLeftHand.staffLine + score.staffLineHeight * 3.5, 0.0001);

        assertEquals(firstNoteRightHand.getValue().intValue(), 67);
        assertEquals(firstNoteLeftHand.getValue().intValue(), 48);


        ScoreParser.hardInstall = oldInstallValue;
    }

    @Test
    public void TestTiedNotes() {
        boolean oldInstallValue = ScoreParser.hardInstall;

        Score score = Score.ParseScore("row_your_boat");

        assertEquals(score.combinedAllNotes.size(), 120);
        assertEquals(score.combinedAllChords.size(), 69);
        assertEquals(score.combinedNotes.size(), 82);
        assertEquals(score.combinedChords.size(), 56);
        assertEquals(score.allChords[0].size(), 60);
        assertEquals(score.allChords[1].size(), 37);

        int tiedNotes = 0;
        for (NotePanel note : score.combinedAllNotes) {
            if (note.isTie) {
                tiedNotes++;
            }
        }

        assertEquals(tiedNotes, 22);


        ScoreParser.hardInstall = oldInstallValue;
    }

}
