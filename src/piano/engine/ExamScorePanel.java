package piano.engine;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class ExamScorePanel extends BaseScorePanel {

    private ExamParserListener examParserListener;
    private ExamTest examTest;

    private Action leftAction, rightAction;

    public boolean finished = false;

    public ExamScorePanel(String name) {
        super(name);
        clearGhostNotes();
        //listen for keystrokes
        leftAction = new AbstractAction() {
            public void actionPerformed(ActionEvent arg0) {
                if (finished) {
                    setPage(page - 1);
                    repaint();
                    repaintGhostNotes();
                }
            }
        };

        rightAction = new AbstractAction() {
            public void actionPerformed(ActionEvent arg0) {
                if (finished) {
                    setPage(page + 1);
                    repaint();
                    repaintGhostNotes();
                }
            }
        };

        this.getInputMap().put(KeyStroke.getKeyStroke("LEFT"), "left");
        this.getInputMap().put(KeyStroke.getKeyStroke("RIGHT"), "right");
        this.getActionMap().put("left", leftAction);
        this.getActionMap().put("right", rightAction);

        examParserListener = new ExamParserListener(this);
        piano.addParserListener(examParserListener);
        examTest = new ExamTest(this, examParserListener);

        examParserListener.start();
        //examTest.basicPerfect();
    }

    @Override
    String getHelpText() {
        return "Play the song as if you were performing for an exam. Follow along with the red notes and see how you did when the song is finished";
    }

    public void refresh() {
        clearGhostNotes();
        examParserListener.stop();
        super.refresh();
    }

    public void finish() {
        super.finish();
        examParserListener.finish();
        refresh();
        for (NotePanel note : S.combinedAllNotes) {
            for (NotePanel ghost : note.ghostNotes) {
                this.add(ghost);
            }
        }
        validate();
        finished = true;
        repaintGhostNotes();

    }

    public void clearGhostNotes() {
        for (NotePanel note : S.combinedAllNotes) {
            for (NotePanel ghost : note.ghostNotes) {
                this.remove(ghost);
            }
        }
        validate();
    }

    private void repaintGhostNotes() {
        for (NotePanel note : S.combinedAllNotes) {
            if (note.page == page) {
                note.paintGhosts();
            } else {
                note.disableGhosts();
            }
        }
    }
}
