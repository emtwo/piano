package piano.engine;

import org.jfugue.elements.Note;

import java.util.HashMap;

public class ExamTest {
    ExamParserListener listener;
    ExamScorePanel panel;
    HashMap<String, Byte> noteHash = new HashMap<String, Byte>();
    ExamTest (ExamScorePanel panel, ExamParserListener listener) {
        this.panel = panel;
        this.listener = listener;
        for (int i = 0; i <= 10; ++i) {
            noteHash.put("C" + i, (byte) (i * 12));
            noteHash.put("C#" + i, (byte) (i * 12 + 1));
            noteHash.put("Db" + i, (byte) (i * 12 + 1));
            noteHash.put("D" + i, (byte) (i * 12 + 2));
            noteHash.put("D#" + i, (byte) (i * 12 + 3));
            noteHash.put("Eb" + i, (byte) (i * 12 + 3));
            noteHash.put("E" + i, (byte) (i * 12 + 4));
            noteHash.put("F" + i, (byte) (i * 12 + 5));
            noteHash.put("F#" + i, (byte) (i * 12 + 6));
            noteHash.put("Gb" + i, (byte) (i * 12 + 6));
            noteHash.put("G" + i, (byte) (i * 12 + 7));
            noteHash.put("G#" + i, (byte) (i * 12 + 8));
            noteHash.put("Ab" + i, (byte) (i * 12 + 8));
            noteHash.put("A" + i, (byte) (i * 12 + 9));
            noteHash.put("A#" + i, (byte) (i * 12 + 10));
            noteHash.put("Bb" + i, (byte) (i * 12 + 10));
            noteHash.put("B" + i, (byte) (i * 12 + 11));
        }
    }

    void fireNoteEvent(String note, long time) {
        listener.noteEvent(new Note(noteHash.get(note)), time);
    }

    void basicPerfect() {
        fireNoteEvent("E5", 0);
        fireNoteEvent("D5", 937);
        fireNoteEvent("C5", 1875);
        fireNoteEvent("D5", 2812);
        fireNoteEvent("E5", 4687);
        fireNoteEvent("E5", 5625);
        fireNoteEvent("G5", 5625);
        fireNoteEvent("C5", 5625);
        if (panel != null) {
            panel.finish();
        }
    }
}
