package piano.ui.buttons;

import java.awt.*;

import piano.ui.Fonts;

import javax.swing.*;

public class HelpButton extends Button {

	private JTextArea helpTextArea = new JTextArea();

	public HelpButton(String buttonText, String helpText, int x, int y, int width, int height, Container container) {
		super(buttonText, x, y, width, height);
		super.setDiff(3);
		super.setFont(Fonts.italic_small_bold);

		container.add(this);
		container.add(helpTextArea);
		helpTextArea.setFont(Fonts.italic_small_bold);
		helpTextArea.setText(helpText);
		helpTextArea.setEditable(false);
		helpTextArea.setLineWrap(true);
		helpTextArea.setWrapStyleWord(true);
		helpTextArea.setBackground(Color.WHITE);
		helpTextArea.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.BLACK),
				BorderFactory.createEmptyBorder(0, 4, 4, 0)));
		helpTextArea.setBounds(x -180, y + 35, 200, 150);
		helpTextArea.setVisible(false);

	}

	@Override
	public void paintComponent(Graphics g) {
        ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		g.setFont(font);

		FontMetrics metrics = g.getFontMetrics(font);
		int hgt = metrics.getHeight();
		int adv = metrics.stringWidth(text);

		if (overButton) {
			helpTextArea.setVisible(true);

			g.setColor(Color.CYAN);
			g.fillRoundRect(x, y, width, height, 8, 8);
			g.setColor(Color.BLACK);
			g.drawString(text, x + (width - adv) / 2, y + (height - hgt) / 2 + hgt - diff);
		} else {
			helpTextArea.setVisible(false);

			g.setColor(Color.BLUE);
			g.fillRoundRect(x, y, width, height, 8, 8);

			g.setColor(Color.WHITE);
			g.drawString(text, x + (width - adv) / 2, y + (height - hgt) / 2 + hgt - diff);
		}

        g.setColor(Color.BLACK);
        g.drawRoundRect(x, y, width, height, 8, 8);
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
