package piano.prototypes.ui.marina;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.HashMap;

import javax.swing.JFrame;

public class PianoUI extends Drawing implements KeyPressedCallback {
	
  final static float dash1[] = {10.0f};

	Drawing play, practice;
	
	private KeyboardView keyboard;

	public PianoUI(JFrame parentFrame) throws IOException {
		super();

		HashMap<Integer, PianoMenuData> menuData = new HashMap<Integer, PianoMenuData>();
		menuData.put(64, new PianoMenuData("Play", new PlayUI(parentFrame, this)));
		menuData.put(67, new PianoMenuData("Ear Training", new EarTrainingUI(parentFrame, this)));
		keyboard = new KeyboardView(this, true, 60, 71, this, menuData, parentFrame);
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
		keyboard.setDimensions(250, 0, getWidth(), getHeight());
		keyboard.paintComponent(g);
	}

	public void mouseClicked(MouseEvent e) {
		keyboard.mouseClicked(e);
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		keyboard.mouseMoved(e);
		this.repaint();
	}

	@Override
	public void informKeyPressed(String keyPressed) {}

	@Override
	public void clearKeys() {}

	@Override
	public void informExitLoop() {}
}
