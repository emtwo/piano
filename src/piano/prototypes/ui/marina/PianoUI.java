package piano.prototypes.ui.marina;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import piano.prototypes.ui.buttons.marina.StartingButton;

public class PianoUI extends Drawing {
	
  final static float dash1[] = {10.0f};
	
	BufferedImage bgImage, main_image, hover_play, hover_practice;
	Drawing play, practice;
	
	ArrayList<StartingButton> buttons = new ArrayList<StartingButton>();

	public PianoUI(JFrame parentFrame) throws IOException {
		super();

		this.play = new PlayUI(parentFrame, this);
		this.practice = new PracticeUI(parentFrame);
		
		// Import piano image.
		File f  = new File("resources/piano2.jpg");
		File f1 = new File("resources/hover_play.jpg");
		File f2 = new File("resources/hover_practice.jpg");

    bgImage        = ImageIO.read(f);
    hover_play     = ImageIO.read(f1);
    hover_practice = ImageIO.read(f2);
    main_image     = bgImage;
		
		buttons.add(new StartingButton("Play", 600, 250, 150, 60, this, hover_play, play, parentFrame));
		buttons.add(new StartingButton("Practice", 600, 470, 150, 60, this, hover_practice, practice, parentFrame));
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		// Clear screen.
		g.setColor(Color.white);
	  g.fillRect(0, 0, getWidth(), getHeight());
		
		// draw entire component grey
    g.setColor(Color.lightGray);
    g.fillRect(0, 0, getWidth(), getHeight());

    // Write title.
		g.setColor(Color.BLACK);
		g.setFont(Fonts.big);
		g.drawString("Piano", 20, 150);
		g.drawString("Like a", 20, 200);
		g.drawString("Pro", 20, 250);
		
		// Write "by Mastodon".
		g.setFont(Fonts.italic);
		g.drawString("By: Mastodon", 20, 300);
    
		// Draw piano image
    g.drawImage(bgImage, 250, 0, getWidth(), getHeight(), 0, 0, bgImage.getWidth(), bgImage.getHeight(), null);
    for (StartingButton button : buttons) {
    	button.paintComponent(g);
    }
	}

	public void mouseClicked(MouseEvent e) {
	    for (StartingButton button : buttons) {
	    	button.setMouseClicked(e.getX(), e.getY());
	    }
	}

	public void setBGImage(BufferedImage image) {
		if (image == null) {
			bgImage = main_image;
			return;
		}
		
		bgImage = image;
	}
	
	@Override
	public void mouseMoved(MouseEvent e) {
		for (StartingButton button : buttons) {
			button.computeMouseEntered(e.getX(), e.getY());
			button.computeMouseExited(e.getX(), e.getY());
		}
		this.repaint();
	}
}
