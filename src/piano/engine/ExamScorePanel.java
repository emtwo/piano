package piano.engine;

import piano.ui.ExamLegend;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class ExamScorePanel extends BaseScorePanel {

    private ExamParserListener examParserListener;
    private ExamTest examTest;

    private Action leftAction, rightAction, escapeAction;

    private ExamLegend legend;

    public boolean finished = false;

    public ExamScorePanel(String name) {
        super(name);
        clearGhostNotes();

        legend = new ExamLegend(10, menuHeight + 10);
        helpButton.addPanel(legend);

        //listen for keystrokes
        leftAction = new AbstractAction() {
            public void actionPerformed(ActionEvent arg0) {
                setPage(page - 1);
                repaint();
                if (finished) {
                    repaintGhostNotes();
                }
            }
        };

        rightAction = new AbstractAction() {
            public void actionPerformed(ActionEvent arg0) {
                setPage(page + 1);
                repaint();
                if (finished) {
                    repaintGhostNotes();
                }
            }
        };

        escapeAction = new AbstractAction() {
            public void actionPerformed(ActionEvent arg0) {
                finish();
            }
        };

        this.getInputMap().put(KeyStroke.getKeyStroke("LEFT"), "left");
        this.getInputMap().put(KeyStroke.getKeyStroke("RIGHT"), "right");
        this.getInputMap().put(KeyStroke.getKeyStroke("ESCAPE"), "escape");
        this.getActionMap().put("left", leftAction);
        this.getActionMap().put("right", rightAction);
        this.getActionMap().put("escape", escapeAction);

        examParserListener = new ExamParserListener(this);
        piano.addParserListener(examParserListener);
        examTest = new ExamTest(this, examParserListener);

        examParserListener.start();
        //examTest.basicPerfect();
    }

    @Override
    String getHelpText() {
        return "Play the song as if you were performing for an exam. Press escape when you are finished playing.";
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
