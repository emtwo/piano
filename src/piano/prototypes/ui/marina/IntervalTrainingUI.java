package piano.prototypes.ui.marina;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;

import piano.prototypes.ui.buttons.marina.HelpButton;
import piano.prototypes.ui.buttons.marina.MainMenuButton;

public class IntervalTrainingUI extends Drawing implements KeyPressedCallback {

	private static final String HELP_TEXT = "Observe the first note and listen to the second note. Play the note you think the second one is. Green means correct, red means incorrect.";
	private static final String TITLE = "Interval Training";

	private KeyboardView keyboard;
	private MainMenuButton mainMenu;
	private HelpButton helpButton;
	private boolean stopPainting = false;

	public IntervalTrainingUI(JFrame parentFrame, Drawing parent) throws IOException {
		super();
		keyboard = new KeyboardView(this, false, 60, 71, this, null, parentFrame);
		mainMenu = new MainMenuButton("< Ear Training Menu", 5, 5, 150, 20, this, parent, parentFrame);
		helpButton = new HelpButton("?", HELP_TEXT, 775, 5, 20, 20);
	}

	@Override
	public void switchToView(JFrame parentFrame) {
		super.switchToView(parentFrame);
		keyboard.switchToView(parentFrame);
		stopPainting = false;
	}

	public void paintComponent(Graphics g) {
		if (stopPainting) {
			return;
		}
		super.paintComponent(g);
		// Clear screen.
		g.setColor(Color.white);
		g.fillRect(0, 0, getWidth(), getHeight());

		// Set font and colour
		g.setColor(Color.BLACK);
		g.setFont(Fonts.italic);

		// Write title.
		FontMetrics metrics = g.getFontMetrics(Fonts.italic);
		int adv = metrics.stringWidth(TITLE);
		g.drawString(TITLE, getWidth()/2 - adv/2, 23);
		g.drawLine(0, 30, getWidth(), 30);

		keyboard.setDimensions(getWidth(), getHeight());
		keyboard.paintComponent(g);
		mainMenu.paintComponent(g);
		helpButton.paintComponent(g);
	}

	@Override
	public void informKeyPressed(String keyPressed) {
		repaint();
		keyboard.informKeyPressed(keyPressed);
	}

	public void mouseClicked(MouseEvent e) {
		this.repaint();
		if (mainMenu.setMouseClicked(e.getX(), e.getY())) {
			informExitLoop();
			return;
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		mainMenu.computeMouseEntered(e.getX(), e.getY());
		mainMenu.computeMouseExited(e.getX(), e.getY());

		helpButton.computeMouseEntered(e.getX(), e.getY());
		helpButton.computeMouseExited(e.getX(), e.getY());
		this.repaint();
	}

	@Override
	public void clearKeys() {
		repaint();
	}

	@Override
	public void informExitLoop() {
		stopPainting = true;
		keyboard.informExitLoop();
	}
}