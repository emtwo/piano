package piano.ui.buttons;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.List;

import piano.repository.Song;
import piano.ui.Fonts;

public class ReplayButton extends Button {

  public ReplayButton(int x, int y, int width, int height) {
    super("Replay", x, y, width, height);
    super.setDiff(4);
    super.setFont(Fonts.italic_small);
  }

  @Override
  public void hoverOver() {
  }

  @Override
  public void hoverOut() {
  }

  @Override
  public boolean setMouseClicked(int x, int y) {
    if (!overButton) {
      return false;
    }

    return true;
  }

  public void paintComponent(Graphics gc) {
    ((Graphics2D)gc).setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
    ((Graphics2D) gc).setStroke(new BasicStroke(1));
    gc.setFont(font);

    FontMetrics metrics = gc.getFontMetrics(font);
    int hgt = metrics.getHeight();
    int adv = metrics.stringWidth(text);

    if (overButton) {
      gc.setColor(Color.ORANGE);
      gc.fillRect(x, y, width, height);
      gc.setColor(Color.BLACK);
      gc.drawRoundRect(x, y, width, height, 10, 10);
      gc.drawString(text, x + (width - adv) / 2, y + (height - hgt) / 2 + hgt - diff);
      return;
    }

    gc.setColor(new Color(34, 139, 34));
    gc.fillRect(x, y, width, height);

    gc.setColor(Color.BLACK);
    gc.drawRoundRect(x, y, width, height, 10, 10);
    gc.drawString(text, x + (width - adv) / 2, y + (height - hgt) / 2 + hgt - diff);
  }
}
