package piano.prototypes.ui.marina;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

public class StartingButton extends Button {
	
	protected BufferedImage bgImage;
	
	public StartingButton(String text, int x, int y, int width, int height,
			View parent, BufferedImage bgImage, View nextView, JFrame parentFrame) {
		
		super(text, x, y, width, height, parent, nextView, parentFrame);
		this.bgImage = bgImage;
	}

	public void paintComponent(Graphics gc) {
		super.paintComponent(gc);
	}
	
	@Override
	public void hoverOver() {
		((PianoUI)parent).setBGImage(bgImage);
	}
	
	@Override
	public void hoverOut() {
		((PianoUI)parent).setBGImage(null);
	}
}
