package piano.ui.buttons;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;

import piano.ui.Fonts;

public class ScrollButton extends Button {

  public ScrollButton(String text, int x, int y, int width, int height) {
    super(text, x, y, width, height);
    super.setDiff(4);
  }

  @Override
  public void hoverOver() {}

  @Override
  public void hoverOut() {}

  public void paintComponent(Graphics gc) {
    ((Graphics2D) gc).setStroke(new BasicStroke(1));
    gc.setFont(Fonts.italic);

    FontMetrics metrics = gc.getFontMetrics(Fonts.italic);
    int hgt = metrics.getHeight();
    int adv = metrics.stringWidth(text);

    if (overButton) {
      gc.setColor(Color.ORANGE);
      gc.fillRect(x, y, width, height);
      gc.setColor(Color.BLACK);
      gc.drawRect(x, y, width, height);
      gc.drawString(text, x + (width - adv) / 2, y + (height - hgt) / 2 + hgt - diff);
      return;
    }

    gc.setColor(Color.BLACK);
    gc.fillRect(x, y, width, height);

    gc.setColor(Color.WHITE);
    gc.drawRect(x, y, width, height);
    gc.drawString(text, x + (width - adv) / 2, y + (height - hgt) / 2 + hgt - diff);
  }
}
