package piano.engine;

public class ExamScorePanel extends BaseScorePanel {

    private ExamParserListener examParserListener;
    private ExamTest examTest;

    public ExamScorePanel(String name) {
        super(name);
        clearGhostNotes();

        examParserListener = new ExamParserListener(this);
        piano.addParserListener(examParserListener);
        examTest = new ExamTest(this, examParserListener);

        examParserListener.start();
        //examTest.basicPerfect();
    }

    public void finish() {
        super.finish();
        examParserListener.finish();
        finishExam();
    }

    public void refresh() {
        super.refresh();
        clearGhostNotes();

    }

    public void clearGhostNotes() {
        for (NotePanel note : S.combinedAllNotes) {
            for (NotePanel ghost : note.ghostNotes) {
                this.remove(ghost);
            }
        }
    }

    private void finishExam() {
        for (NotePanel note : S.combinedAllNotes) {
            for (NotePanel ghost : note.ghostNotes) {
                this.add(ghost);
            }
        }
        repaintGhostNotes();
    }

    private void repaintGhostNotes() {
        for (NotePanel note : S.combinedNotes) {
            if (note.page == page) {
                note.paintGhosts();
            }
        }
    }
}
