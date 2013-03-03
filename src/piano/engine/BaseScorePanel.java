package piano.engine;

import piano.ui.JFrameStack;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Vector;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public abstract class BaseScorePanel extends JPanel {
    protected String name;
    protected int page = 1;
    protected Vector<BufferedImage> images;
    protected BufferedImage currImage;
    protected PianoAdapterParser piano = PianoAdapterParser.instance();
    protected boolean mute = true;
    protected Runnable playNextTogetherNote, playNextRightNote, playNextLeftNote, nextPage, finishPlaying;
    protected ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    protected int[] currChords;
    protected Vector<ScheduledFuture> futures = new Vector<ScheduledFuture>();


    public Score S;

    public BaseScorePanel(String name) {
        super(null);

        this.setOpaque(true);
        this.name = name;

        S = Score.Load(name);

        setBounds(0, 0, S.imageWidth, S.imageHeight);
        setPreferredSize(new Dimension(S.imageWidth, S.imageHeight));

        JFrameStack.getFrame().getContentPane().add(this, 1);
        for (NotePanel note : S.combinedAllNotes) {
            this.add(note);
        }
        validate();

        //read in all the images
        images = new Vector<BufferedImage>();
        for (int i = 0; i < S.pages; ++i) {
            images.add(S.getImage(i));
        }

        currChords = new int[S.staves];

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
                repaint();
            }
        };

        finishPlaying = new Runnable() {
            public void run() {
                finish();
            }
        };

        setPage(page);
        repaint();

    }

    protected void setPage(int newPage) {
        if (newPage >= 1 && newPage <= S.pages) {
            page = newPage;
        }
        currImage = images.get(page - 1);
    }

    protected void finish() {}

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

    protected void repaintActiveNotes() {
        for (Vector<Chord> chords : S.allChords) {
            for (Chord chord : chords) {
                chord.paint();
            }
        }
    }

    public void play() {
        refresh();

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
        repaint();
    }


    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(currImage, 0, 0, null);
    }

}