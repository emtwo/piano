package piano.prototypes.ui.buttons.marina;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Frame;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;

import piano.prototypes.ui.marina.Fonts;
import piano.prototypes.ui.marina.SubView;
import piano.prototypes.ui.marina.View;

public abstract class Button extends JPanel {

	public String text;
	protected SubView parent;
	protected boolean bold = false;
	protected int x, y, width, height;
	
	protected View nextView;
	protected JFrame parentFrame;
	public boolean overButton = false;
	
	protected Font font = Fonts.italic_big;
	protected int diff = 10;
	
	/*
	private static final int ROLL_OVER_BG   = Color.LIGHT_GRAY;
	private static final int ROLL_OVER_FONT = Color.LIGHT_GRA;
	private static final int ROLL_OVER_BG = Color.LIGHT_GRAY;
	private static final int ROLL_OVER_BG = Color.LIGHT_GRAY;
	private static final int ROLL_OVER_BG = Color.LIGHT_GRAY;
	private static final int ROLL_OVER_BG = Color.LIGHT_GRAY;
	*/
	public abstract void hoverOver();
	public abstract void hoverOut();
	
	public Button(String text, int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public Button(String text, int x, int y, int width, int height,
			SubView parent, View nextView, JFrame parentFrame) {
		super();
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.text = text;
		this.parent = parent;
		this.nextView = nextView;
		this.parentFrame = parentFrame;
		
		this.addMouseMotionListener(parent);
		addMouseListener(parent);
	}

	protected void overButton() {
		overButton = true;
		Frame.getFrames()[0].setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	}
	
	protected void outButton() {
		overButton = false;
		Frame.getFrames()[0].setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
	}
	
	public void computeMouseEntered(int x, int y) {
		// If we were over the button, it's not possible to re-enter it.
		if (overButton) {
			return;
		}
				
		boolean entered = x > this.x && x < (this.x + this.width) && y > this.y && y < (this.y + this.height);
		if (entered) {
			hoverOver();
			overButton();
		}
	}

	public void computeMouseExited(int x, int y) {
		// If we weren't over the button, it's not possible to exit it.
		if (!overButton) {
			return;
		}
			
		boolean exited = x < this.x || x > (this.x + this.width) || y < this.y || y > (this.y + this.height);
		if (exited) {
			hoverOut();
			outButton();
		}
	}

	public boolean setMouseClicked(int x, int y) {
		if (!overButton || nextView == null) {
			return false;
		}

		hoverOut();
		outButton();
		nextView.switchToView(parentFrame);
		return true;
	}
	
	public void setFont(Font f) {
		this.font = f;
	}
	
	public void setDiff(int diff) {
		this.diff = diff;
	}
	
	public void paintComponent(Graphics gc) {
		gc.setFont(font);
		
		FontMetrics metrics = gc.getFontMetrics(font);
		int hgt = metrics.getHeight();
		int adv = metrics.stringWidth(text);
		
		if (overButton) {
			gc.setColor(Color.BLACK);
			gc.drawRoundRect(x, y, width, height, 10, 10);
			gc.drawString(text, x + (width - adv) / 2, y + (height - hgt) / 2 + hgt - diff);
			return;
		}
		
		gc.setColor(Color.BLACK);
		gc.fillRoundRect(x, y, width, height, 10, 10);
		
		gc.setColor(Color.WHITE);
		gc.drawString(text, x + (width - adv) / 2, y + (height - hgt) / 2 + hgt - diff);
	}
	
}
