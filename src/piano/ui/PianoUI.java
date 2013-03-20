package piano.ui;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import javax.imageio.ImageIO;

import piano.ui.buttons.ButtonType;

public class PianoUI extends Drawing implements ButtonClickCallback {

  final static float dash1[] = {10.0f};

	Drawing play, practice;

	private NotesToPlayData data;
	private KeyboardView keyboard;
    private Image bg_image;

	public PianoUI() throws IOException {
		super();
		NotesToPlayData data = new NotesToPlayData();
		data.minKey = 60;
		data.maxKey = 71;
		data.numOctaves = 1;

        bg_image = ImageIO.read(new File("data/music_background.jpg"));
		HashMap<Integer, String> menuData = new HashMap<Integer, String>();
		menuData.put(64, "Play");
		menuData.put(67, "Ear Training");
        HashMap<Integer, Color> colorData = new HashMap<Integer, Color>();
        colorData.put(64, Color.RED);
        colorData.put(67, Color.ORANGE);
		keyboard = new KeyboardView(this, menuData, data);
        keyboard.addColorData(colorData);
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		// Clear screen.
		g.setColor(Color.white);
	  g.fillRect(0, 0, getWidth(), getHeight());

        // draw background image
        g.drawImage(bg_image, 0, 0, null);

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
	public void informButtonClicked(ButtonType buttonType, int buttonId) {
		if (buttonId == 64) {
			JFrameStack.pushPanel(new PlayUI());
		} else {
			JFrameStack.pushPanel(new EarTrainingUI());
		}
	}
}
