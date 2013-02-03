package score;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Vector;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class ScorePanel extends JPanel {
	private JFrame frame;
	private String name;
	private int page, npages;
	private Vector<BufferedImage> images;
	private BufferedImage currImage;
	private int[] currChords;
    private Vector<ScheduledFuture> futures = new Vector<ScheduledFuture>();

    private MockAdapterParser mock;
    private PracticeParserListener practiceParserListener;
    private ExamParserListener examParserListener;

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

        NotePlayer.init(); // this takes some time, so initialize music players
        
        LilyPond.invokeLilyPond(this.name);
        this.S = LilyPond.parseScore(this.name);
				
		//read in all the images
		images = new Vector<BufferedImage>();
        try {
            String fileLocation = "data/out/" + name + ".png";
            File file = new File(fileLocation);
            if (file.exists()) {
                images.add(ImageIO.read(file));
                npages = 1;
            } else {
                int p = 1;
                fileLocation = "data/out/" + name + "-page" + p + ".png";
                file = new File(fileLocation);
                while (file.exists()) {
                    images.add(ImageIO.read(file));
                    ++p;
                    fileLocation = "data/out/" + name + "-page" + p + ".png";
                    file = new File(fileLocation);
                }
                npages = p - 1;
            }
            if (images.isEmpty()) {
                System.err.println("Lilypond failed to produce any files");
            }
            currImage = images.get(0);
        }
        catch (java.io.IOException e) {
			System.out.println(e.getMessage());
		}

        currChords = new int[S.staves];
        
        //add chords to frame
        for (Vector<Chord> chords : S.chords) {
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

        scheduler = Executors.newScheduledThreadPool(1);

        mock = new MockAdapterParser(this.getInputMap(), this.getActionMap());
        //practiceParserListener = new PracticeParserListener(S.combinedChords);
        //mock.addParserListener(practiceParserListener);

        examParserListener = new ExamParserListener(this);
        mock.addParserListener(examParserListener);

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

        setBounds(0, 0, currImage.getWidth(), currImage.getHeight());
        repaint();

	}

    public void setPage(int newpage) {
        if (newpage >= 1 && newpage <= npages) {
            page = newpage;
        }
        repaint();
        repaintActiveNotes();
    }
    public void nextNote(int layer) {
        // play and highlight the next right hand note
        if (currChords[layer] > 0) {
            Chord lastChord = S.chords[layer].get(currChords[layer] - 1);
            lastChord.setActive(false);
            lastChord.paint();
        }

        if (currChords[layer] < S.chords[layer].size()) {
            Chord chord = S.chords[layer].get(currChords[layer]);
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
                Chord lastChord = S.chords[layer].get(currChords[layer] - 1);
                lastChord.setActive(false);
                lastChord.paint();
            }

            if (currChords[layer] < S.chords[layer].size()) {
                Chord chord = S.chords[layer].get(currChords[layer]);
                chord.setActive(true);
                chord.paint();
                ++currChords[layer];
            }
        }

        System.out.println(S.chords[0].get(currChords[0] - 1).musicString + ": " + S.chords[0].get(currChords[0] - 1).getMillisTime());

        if (!mute) {
            NotePlayer.play(S.chords[0].get(currChords[0] - 1).musicString + "+" + S.chords[1].get(currChords[1] - 1).musicString);
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
            while (cNote[0] < S.chords[0].size() && cNote[1] < S.chords[1].size()) {
                for (int layer = 0; layer < S.staves; ++layer) {
                    time[layer] = S.chords[layer].get(cNote[layer]).getMillisTime();
                }

                if (time[0] == time[1]) {
                    futures.add(scheduler.schedule(playNextTogetherNote, startTime + time[0], TimeUnit.MILLISECONDS));
                    if (S.chords[0].get(cNote[0]).getPage() > cPage) {
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
            while (cNote[layer] < S.chords[layer].size()) {
                time[layer] = S.chords[layer].get(cNote[layer]).getMillisTime();
                Runnable r = (layer == 0) ? playNextRightNote : playNextLeftNote;
                futures.add(scheduler.schedule(r, startTime + time[layer], TimeUnit.MILLISECONDS));
                ++(cNote[layer]);
            }
        }

        //set callback for when playback finishes
        futures.add(scheduler.schedule(finishPlaying, startTime + S.chords[0].lastElement().getMillisTime() + S.chords[0].lastElement().getMillisDuration(), TimeUnit.MILLISECONDS));
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
        for (Vector<Chord> chords : S.chords) {
            for (Chord chord : chords) {
                chord.setActive(false);
            }
        }

        //reset page
        setPage(1);
    }

	private void repaintActiveNotes() {
        for (Vector<Chord> chords : S.chords) {
            for (Chord chord : chords) {
                if (chord.active) {
                    chord.paint();
                }
            }
        }
	}

    public void paintComponent(Graphics g) {
        currImage = images.get(page - 1);
        frame.setSize(currImage.getWidth(), currImage.getHeight());
        NotePanel.setImage(currImage.getWidth(), currImage.getHeight());
        g.drawImage(currImage, 0, 0, null);
    }
}
