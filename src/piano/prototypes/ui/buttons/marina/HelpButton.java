package piano.prototypes.ui.buttons.marina;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import piano.prototypes.ui.marina.Fonts;

public class HelpButton extends Button {

	private String helpText;
	
	public HelpButton(String buttonText, String helpText, int x, int y, int width, int height) {
		super(buttonText, x, y, width, height);
		super.setDiff(3);
		super.setFont(Fonts.italic_small_bold);
		this.helpText = helpText;
	}

	@Override
	public void paintComponent(Graphics g) {
		g.setFont(font);

		FontMetrics metrics = g.getFontMetrics(font);
		int hgt = metrics.getHeight();
		int adv = metrics.stringWidth(text);

		g.setColor(Color.BLACK);
		g.drawRoundRect(x, y, width, height, 8, 8);

		if (overButton) {
			g.setColor(Color.CYAN);
			g.fillRoundRect(x, y, width, height, 8, 8);

			g.setColor(Color.BLACK);
			drawString(g, helpText, x - 175, y + 50, 200);
			g.drawString(text, x + (width - adv) / 2, y + (height - hgt) / 2 + hgt - diff);
			return;
		}
		g.setColor(Color.BLUE);
		g.fillRoundRect(x, y, width, height, 8, 8);

		g.setColor(Color.WHITE);
		g.drawString(text, x + (width - adv) / 2, y + (height - hgt) / 2 + hgt - diff);
	}

	public void drawString(Graphics g, String s, int x, int y, int width) {
		// FontMetrics gives us information about the width,
		// height, etc. of the current Graphics object's Font.
		FontMetrics fm = g.getFontMetrics();

		int lineHeight = fm.getHeight();

		int curX = x;
		int curY = y;

		String[] words = s.split(" ");

		for (String word : words) {
			// Find out thw width of the word.
			int wordWidth = fm.stringWidth(word + " ");

			// If text exceeds the width, then move to next line.
			if (curX + wordWidth >= x + width) {
				curY += lineHeight;
				curX = x;
			}

			g.drawString(word, curX, curY);

			// Move over to the right for next word.
			curX += wordWidth;
		}
	}

	@Override
	public void hoverOver() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hoverOut() {
		// TODO Auto-generated method stub
		
	}
}
