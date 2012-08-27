package piano.prototypes.ui.marina;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JFrame;

import piano.prototypes.ui.buttons.marina.BackButton;
import piano.prototypes.ui.buttons.marina.MainMenuButton;
import piano.prototypes.ui.buttons.marina.SimpleButton;

public class PlayUI extends Drawing {
	
	ArrayList<SimpleButton> buttons = new ArrayList<SimpleButton>();
	
	SubView currentSubView, allView, genreView, composerView;
	Drawing menuView;
	MainMenuButton mainMenu;
	int width = 800;

	public PlayUI(JFrame parentFrame, Drawing mainMenuView) throws IOException {
		super();

		buttons.add(new SimpleButton("All", 0, 100, width/3, 60, this, null, parentFrame));
		buttons.add(new SimpleButton("Genre", width/3, 100, width/3, 60, this, null, parentFrame));
		buttons.add(new SimpleButton("Composer", width/3 * 2, 100, width/3, 60, this, null, parentFrame));
		buttons.get(0).setChosen(true);
		
		menuView = mainMenuView;
		mainMenu = new MainMenuButton("Main Menu", width/2 - width/12, width - 60, width/6, 25, this, menuView, parentFrame);
		
		allView = new AllSubView(parentFrame);
		genreView = new GenreSubView(parentFrame);
		composerView = new ComposerSubView(parentFrame);
		
		currentSubView = allView;
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		// Clear screen.
		g.setColor(Color.white);
		g.fillRect(0, 0, getWidth(), getHeight());
		
		// Set font and colour
		g.setColor(Color.BLACK);
		g.setFont(Fonts.big);
		
		// Write "Choose a Song".
		String text = "Choose a Song!";
		FontMetrics metrics = g.getFontMetrics(Fonts.big);
		int adv = metrics.stringWidth(text);
		g.drawString(text, getWidth()/2 - adv/2, 65);
		
		g.drawLine(0, 85, getWidth(), 85);
		g.drawLine(0, 10, getWidth(), 10);
		
		for (SimpleButton button : buttons) {
			button.paintComponent(g);
		}
		
		mainMenu.paintComponent(g);
		
		// Choose based on tab what to display.
		currentSubView.paintComponent(g);
	}

	public void mouseClicked(MouseEvent e) {
		currentSubView.mouseClicked(e);
		this.repaint();
		
		if (mainMenu.setMouseClicked(e.getX(), e.getY())) {
			return;
		}
		
		SimpleButton chosenButton = null;
		
		// Find the chosen button.
    for (SimpleButton button : buttons) {
    	if (button.setMouseClicked(e.getX(), e.getY())) {
    		chosenButton = button;
    	}
    }

    if (chosenButton == null) {
    	return;
    }

    // Make all buttons not chosen.
    for (SimpleButton button : buttons) {
    	button.setChosen(false);
    }
    
    // Set our chosen button to chosen.
    chosenButton.setChosen(true);
    setSubView(chosenButton);
    this.repaint();
	}
	
	public void setSubView(SimpleButton chosenButton) {
		if (chosenButton.text.equals("All")) {
			currentSubView = allView;
		} else if (chosenButton.text.equals("Genre")) {
			currentSubView = genreView;
		} else {
			currentSubView = composerView;
		}
	}
	
	@Override
	public void mouseMoved(MouseEvent e) {
		for (SimpleButton button : buttons) {
			button.computeMouseEntered(e.getX(), e.getY());
			button.computeMouseExited(e.getX(), e.getY());
		}
		mainMenu.computeMouseEntered(e.getX(), e.getY());
		mainMenu.computeMouseExited(e.getX(), e.getY());
		
		currentSubView.mouseMoved(e);
		this.repaint();
	}
}
