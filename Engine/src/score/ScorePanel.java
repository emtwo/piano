package score;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Iterator;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

public class ScorePanel extends JPanel {
	private JFrame frame;
	private String name;
	//private MidiPlayer midi;
	private int page, npages;
	private Vector<BufferedImage> images;
	private BufferedImage currImage;
	private int currNote = -1, currLeftNote= -1;

	private Action leftAction, rightAction, nextNoteAction, nextLeftNoteAction, playAction; 
	
	private Score S;
	
	public ScorePanel(JFrame frame, String name) {
		this.frame = frame;
		this.name = name;
		this.page = 1;
        //this.midi = new MidiPlayer(name);
		
		NotePlayer player = new NotePlayer(""); //player needs some time to spin up, so invoke it now
		player.start();
        
        LilyPond.invokeLilyPond(this.name);
        this.S = LilyPond.parseScore(this.name);
				
		//read in all the images
		images = new Vector<BufferedImage>();
        try {
        	int p = 1;
        	String fileLocation = "data/out/" + name + "-page" + p + ".png";
        	File file = new File(fileLocation);
        	while (file.exists()) {
        		images.add(ImageIO.read(file));
        		++p;
        		fileLocation = "data/out/" + name + "-page" + p + ".png";
        		file = new File(fileLocation);
        	} 
            currImage = images.elementAt(0);
        	npages = p - 1;
        }
        catch (java.io.IOException e) {
			System.out.println(e.getMessage());
		}
        
        //add chords to frame
        Iterator<Chord> it = S.leftChords.iterator();
        while (it.hasNext()) {
        	Iterator<NotePanel> noteIt = it.next().notes.iterator();
        	while (noteIt.hasNext()) {
        		this.frame.add(noteIt.next());
        	}
        }
        
        it = S.rightChords.iterator();
        while (it.hasNext()) {
        	Iterator<NotePanel> noteIt = it.next().notes.iterator();
        	while (noteIt.hasNext()) {
        		this.frame.add(noteIt.next());
        	}
        }

        //listen for keystrokes
        leftAction = new AbstractAction() {
			public void actionPerformed(ActionEvent arg0) {
				if (page > 1) {
					--page;
					repaint();
					repaintActiveNotes();
				}
			}
        };
        
        rightAction = new AbstractAction() {
			public void actionPerformed(ActionEvent arg0) {
				if (page < npages) {
					++page;
					repaint();
					repaintActiveNotes();
				}
			}
        };
        
        nextNoteAction = new AbstractAction() {
			public void actionPerformed(ActionEvent arg0) {
				nextNote();
			}
        };
        
        nextLeftNoteAction = new AbstractAction() {
			public void actionPerformed(ActionEvent arg0) {
				nextLeftNote();
			}
        };
        
        playAction = new AbstractAction() {
			public void actionPerformed(ActionEvent arg0) {
				play();
			}
        };
        
        this.getInputMap().put(KeyStroke.getKeyStroke("LEFT"), "left");
        this.getInputMap().put(KeyStroke.getKeyStroke("RIGHT"), "right");
        this.getInputMap().put(KeyStroke.getKeyStroke("SPACE"), "space");
        this.getInputMap().put(KeyStroke.getKeyStroke("L"), "l");
        this.getActionMap().put("left", leftAction);
        this.getActionMap().put("right", rightAction);
        this.getActionMap().put("space", nextNoteAction);
        this.getActionMap().put("l", nextLeftNoteAction);
        setBounds(0, 0, currImage.getWidth(), currImage.getHeight());
        repaint();
	}
	
	public void paintComponent(Graphics g) {
		//super.paintComponent(g);
		currImage = images.elementAt(page - 1);
		frame.setSize(currImage.getWidth(), currImage.getHeight());
		NotePanel.setImage(currImage.getWidth(), currImage.getHeight());
		g.drawImage(currImage, 0, 0, null);

		/*Iterator<Note> it = S.Notes.iterator();
        while (it.hasNext()) {
    		g.setColor(Color.RED);
    		Note N = it.next();
    		if (N.Page == 1) {
    			g.fillOval((int) (N.x / S.PaperWidth * this.image.getWidth()), (int) (N.y / S.PaperHeight * this.image.getHeight()), 5, 5);
    		}
    		/*try {
    			Thread.sleep(10);
    		}
    		catch (InterruptedException e) {
    			
    		}
        }*/
	}
	
	public void nextNote() {
		if (currNote >= 0) {
			Chord lastChord = S.rightChords.elementAt(currNote);
			lastChord.setActive(false);
			lastChord.paint();
		}
		++currNote;
		Chord chord = S.rightChords.elementAt(currNote);
		chord.setActive(true);
		chord.paint();
		chord.play();
	}
	
	public void nextLeftNote() {
		if (currLeftNote >= 0) {
			Chord lastChord = S.leftChords.elementAt(currLeftNote);
			lastChord.setActive(false);
			lastChord.paint();
		}
		++currLeftNote;
		Chord chord = S.leftChords.elementAt(currLeftNote);
		chord.setActive(true);
		chord.paint();
		chord.play();
	}
	
	public void play() {
		while (currNote < S.rightChords.size()) {
			nextNote();
		}
	}
	
	private void repaintNotes() {
        Iterator<Chord> it = S.rightChords.iterator();
        while (it.hasNext()) {
        	it.next().paint();
        }
        
        it = S.leftChords.iterator();
        while (it.hasNext()) {
        	it.next().paint();
        }
	}
	
	private void repaintActiveNotes() {
        Iterator<Chord> it = S.rightChords.iterator();
        while (it.hasNext()) {
        	Chord chord = it.next();
        	if (chord.active == true) {
        		it.next().paint();
        	}
        }
        
        it = S.leftChords.iterator();
        while (it.hasNext()) {
        	Chord chord = it.next();
        	if (chord.active == true) {
        		it.next().paint();
        	}
        }
	}
}
