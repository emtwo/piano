package piano.engine;

import javax.swing.*;

import piano.ui.Drawing;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.util.Vector;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class ScorePanel extends Drawing {
	private JFrame frame;
	private String name;
	private int page;
	private Vector<BufferedImage> images;
	private BufferedImage currImage;
	private int[] currChords;
    private Vector<ScheduledFuture> futures = new Vector<ScheduledFuture>();

    private MockAdapterParser mock;
    private PianoAdapterParser piano;
    private PracticeParserListener practiceParserListener;
    private ExamParserListener examParserListener;
    private ExamTest examTest;

	private Action leftAction, rightAction, nextRightNoteAction, nextLeftNoteAction, startPlayAction;

    public Runnable playNextTogetherNote, playNextRightNote, playNextLeftNote, nextPage, finishPlaying;
    //public Runnable highlightNextTogetherNote, highlightNextRightNote, highlightNextLeftNote;

    private ScheduledExecutorService scheduler;

	public Score S;

    private boolean mute = false;

	public ScorePanel(JFrame frame, String name) {
		this.frame = frame;
		this.name = name;
		this.page = 1;

        NotePanel.setFrame(frame);



        Score.ParseScore(name);
        S = Score.Load(name);

		//read in all the images
		images = new Vector<BufferedImage>();
        for (int i = 0; i < S.pages; ++i) {
            images.add(S.getImage(i));
        }
        setBounds(0, 0, S.imageWidth, S.imageHeight);

        currChords = new int[S.staves];

        //add chords to frame
        for (Vector<Chord> chords : S.allChords) {
            for (Chord chord : chords) {
                for (NotePanel note : chord.notes) {
                    this.frame.add(note);
                }
            }
        }

        //listen for keystrokes
        leftAction = new AbstractAction() {
			public void actionPerformed(ActionEvent arg0) {
                setPage(page - 1);
			}
        };

        rightAction = new AbstractAction() {
			public void actionPerformed(ActionEvent arg0) {
                setPage(page + 1);
			}
        };

        nextRightNoteAction = new AbstractAction() {
			public void actionPerformed(ActionEvent arg0) {
				nextNote(0);
			}
        };

        nextLeftNoteAction = new AbstractAction() {
			public void actionPerformed(ActionEvent arg0) {
				nextNote(1);
			}
        };

        startPlayAction = new AbstractAction() {
			public void actionPerformed(ActionEvent arg0) {
				play(false);
			}
        };

        playNextTogetherNote = new Runnable() {
            public void run() {
                nextTogetherNote();
            }
        };

        playNextRightNote = new Runnable() {
            public void run() {
                nextNote(0);
            }
        };

        playNextLeftNote = new Runnable() {
            public void run() {
                nextNote(1);
            }
        };

        nextPage = new Runnable() {
            public void run() {
                setPage(page + 1);
            }
        };

        finishPlaying = new Runnable() {
            public void run() {
                refresh();
                examParserListener.finish();
            }
        };

        /*highlightNextTogetherNote = new Runnable() {
            public void run() {
                nextTogetherNote(false);
            }
        };

        highlightNextRightNote = new Runnable() {
            public void run() {
                nextNote(0, false);
            }
        };

        highlightNextLeftNote = new Runnable() {
            public void run() {
                nextNote(1, false);
            }
        };*/

        setPage(page);

        scheduler = Executors.newScheduledThreadPool(1);

        mock = new MockAdapterParser(this.getInputMap(), this.getActionMap());
        piano = new PianoAdapterParser();
        practiceParserListener = new PracticeParserListener(S.combinedChords);
        mock.addParserListener(practiceParserListener);
        piano.addParserListener(practiceParserListener);

        examParserListener = new ExamParserListener(this);
        examTest = new ExamTest(examParserListener);
        // mock.addParserListener(examParserListener);

        this.getInputMap().put(KeyStroke.getKeyStroke("LEFT"), "left");
        this.getInputMap().put(KeyStroke.getKeyStroke("RIGHT"), "right");
        this.getInputMap().put(KeyStroke.getKeyStroke("SPACE"), "space");
        this.getInputMap().put(KeyStroke.getKeyStroke("L"), "l");
        this.getInputMap().put(KeyStroke.getKeyStroke("P"), "p");
        this.getActionMap().put("left", leftAction);
        this.getActionMap().put("right", rightAction);
        this.getActionMap().put("space", nextRightNoteAction);
        this.getActionMap().put("l", nextLeftNoteAction);
        this.getActionMap().put("p", startPlayAction);

        //examParserListener.start();
        examTest.basicPerfect();

	}

    public void setPage(int newpage) {
        if (newpage >= 1 && newpage <= S.pages) {
            page = newpage;
        }
        currImage = images.get(page - 1);
        repaint();
        repaintActiveNotes();
    }
    public void nextNote(int layer) {
        // play and highlight the next right hand note
        if (currChords[layer] > 0) {
            Chord lastChord = S.allChords[layer].get(currChords[layer] - 1);
            lastChord.setActive(false);
            lastChord.paint();
        }

        if (currChords[layer] < S.allChords[layer].size()) {
            Chord chord = S.allChords[layer].get(currChords[layer]);
            chord.setActive(true);
            chord.paint();
            if (!mute) {
                chord.play();
            }
            ++currChords[layer];
        }
    }

    public void nextTogetherNote() {
        for (int layer = 0; layer < S.staves; ++layer) {
            if (currChords[layer] > 0) {
                Chord lastChord = S.allChords[layer].get(currChords[layer] - 1);
                lastChord.setActive(false);
                lastChord.paint();
            }

            if (currChords[layer] < S.allChords[layer].size()) {
                Chord chord = S.allChords[layer].get(currChords[layer]);
                chord.setActive(true);
                chord.paint();
                ++currChords[layer];
            }
        }

        //System.out.println(S.allChords[0].get(currChords[0] - 1).musicString + ": " + S.allChords[0].get(currChords[0] - 1).getMillisTime());

        if (!mute) {
            NotePlayer.play(S.allChords[0].get(currChords[0] - 1).musicString + "+" + S.allChords[1].get(currChords[1] - 1).musicString);
        }
    }

	public void play(boolean mute) {
        refresh();

        this.mute = mute;

        long startTime = 100L;
        long[] time = {0L, 0L};
        int cNote[] = {0, 0};
        int cPage = 1;

        // schedules all chords to be played
        if (S.staves > 1) {
            while (cNote[0] < S.allChords[0].size() && cNote[1] < S.allChords[1].size()) {
                for (int layer = 0; layer < S.staves; ++layer) {
                    time[layer] = S.allChords[layer].get(cNote[layer]).getMillisTime();
                }

                if (time[0] == time[1]) {
                    futures.add(scheduler.schedule(playNextTogetherNote, startTime + time[0], TimeUnit.MILLISECONDS));
                    if (S.allChords[0].get(cNote[0]).getPage() > cPage) {
                        futures.add(scheduler.schedule(nextPage, startTime + time[0], TimeUnit.MILLISECONDS));
                        ++cPage;
                    }
                    for (int layer = 0; layer < S.staves; ++layer) {
                        ++(cNote[layer]);
                    }
                } else if (time[0] < time[1]) {
                    futures.add(scheduler.schedule(playNextRightNote, startTime + time[0], TimeUnit.MILLISECONDS));
                    ++(cNote[0]);
                } else {
                    futures.add(scheduler.schedule(playNextLeftNote, startTime + time[1], TimeUnit.MILLISECONDS));
                    ++(cNote[1]);
                }
            }
        }

        // schedule last notes if one hand finishes before the other or if there is only one hand
        for (int layer = 0; layer < S.staves; ++layer) {
            while (cNote[layer] < S.allChords[layer].size()) {
                time[layer] = S.allChords[layer].get(cNote[layer]).getMillisTime();
                Runnable r = (layer == 0) ? playNextRightNote : playNextLeftNote;
                futures.add(scheduler.schedule(r, startTime + time[layer], TimeUnit.MILLISECONDS));
                ++(cNote[layer]);
            }
        }

        //set callback for when playback finishes
        futures.add(scheduler.schedule(finishPlaying, startTime + S.allChords[0].lastElement().getMillisTime() + S.allChords[0].lastElement().getMillisDuration(), TimeUnit.MILLISECONDS));
	}

    public void refresh() {

        //clear futures
        for (ScheduledFuture f : futures) {
            f.cancel(true);
        }
        futures.clear();

        //reset currNote state
        for (int layer = 0; layer < S.staves; ++layer) {
            currChords[layer] = 0;
        }

        //set all notes to inactive
        for (Vector<Chord> chords : S.allChords) {
            for (Chord chord : chords) {
                chord.setActive(false);
            }
        }

        //reset page
        setPage(1);
    }

    public void finishExam() {
        repaintGhostNotes();
    }

    private void repaintGhostNotes() {
        for (NotePanel note : S.combinedNotes) {
            if (note.page == page) {
                note.paintGhosts();
            }
        }
    }

	private void repaintActiveNotes() {
        for (Vector<Chord> chords : S.allChords) {
            for (Chord chord : chords) {
                if (chord.active) {
                    chord.paint();
                }
            }
        }
	}

    public void paintComponent(Graphics g) {
        frame.setSize(currImage.getWidth(), currImage.getHeight());
        g.drawImage(currImage, 0, 0, null);
    }
}
