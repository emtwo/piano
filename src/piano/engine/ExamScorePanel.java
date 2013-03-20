package piano.engine;

import piano.ui.ExamLegend;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;

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
                prevPage();
            }
        };

        rightAction = new AbstractAction() {
            public void actionPerformed(ActionEvent arg0) {
                nextPage();
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

    public void nextPage() {
        setPage(page + 1);
        repaint();
        if (finished) {
            repaintGhostNotes();
        }
    }

    public void prevPage() {
        setPage(page - 1);
        repaint();
        if (finished) {
            repaintGhostNotes();
        }
    }

    protected void mouseClickedNoButton(MouseEvent e) {
        if (!finished) {
            finish();
        } else if(SwingUtilities.isLeftMouseButton(e)) {
            nextPage();
        } else if (SwingUtilities.isRightMouseButton(e)) {
            prevPage();
        }
    }

    @Override
    String getHelpText() {
        return "Play the song as if you were performing for an exam. Press escape or click when you are finished playing.";
    }

    public void refresh() {
        clearGhostNotes();
        examParserListener.stop();
        piano.removeParserListener(examParserListener);
        super.refresh();
    }

    public void finish() {
        super.finish();
        examParserListener.finish();
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
        removeGhostNotes();
        for (NotePanel note : S.combinedAllNotes) {
            note.clearGhostNotes();
        }
    }

    public void removeGhostNotes() {
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
