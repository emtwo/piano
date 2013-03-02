package piano.engine;

import piano.ui.Drawing;
import piano.ui.JFrameStack;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Vector;

public abstract class BaseScorePanel extends Drawing {
    protected String name;
    protected int page;
    protected Vector<BufferedImage> images;
    protected BufferedImage currImage;

    public Score S;

    public BaseScorePanel(String name) {
        this.name = name;
        this.page = 1;

        S = Score.Load(name);

        //read in all the images
        images = new Vector<BufferedImage>();
        for (int i = 0; i < S.pages; ++i) {
            images.add(S.getImage(i));
        }
        setBounds(0, 0, S.imageWidth, S.imageHeight);

        setPage(page);

    }

    public void setPage(int newpage) {
        if (newpage >= 1 && newpage <= S.pages) {
            page = newpage;
        }
        currImage = images.get(page - 1);
        repaint();
        repaintActiveNotes();
    }

    private void repaintActiveNotes() {
        for (Vector<Chord> chords : S.allChords) {
            for (Chord chord : chords) {
                chord.paint();
            }
        }
    }

    public void paintComponent(Graphics g) {
        JFrameStack.getFrame().setSize(currImage.getWidth(), currImage.getHeight());
        g.drawImage(currImage, 0, 0, null);
    }

    @Override
    public void switchToView() {
        setFocusable(true);
        requestFocusInWindow();
    }
}
