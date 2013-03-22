package piano.ui.buttons;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
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
      GradientPaint gp = new GradientPaint(x, y, new Color(34, 139, 34), x, y + height, new Color(34, 200, 34));
      ((Graphics2D) gc).setPaint(gp);
      gc.fillRoundRect(x, y, width, height, 10, 10);
      gc.setColor(Color.BLACK);
      gc.drawRoundRect(x, y, width, height, 10, 10);
      gc.drawString(text, x + (width - adv) / 2, y + (height - hgt) / 2 + hgt - diff);
      return;
    }

    GradientPaint gp = new GradientPaint(x, y, new Color(34, 200, 34), x, y + height, new Color(34, 139, 34));
    ((Graphics2D) gc).setPaint(gp);
    gc.fillRoundRect(x, y, width, height, 10, 10);

    gc.setColor(Color.BLACK);
    gc.drawRoundRect(x, y, width, height, 10, 10);
    gc.drawString(text, x + (width - adv) / 2, y + (height - hgt) / 2 + hgt - diff);
  }
}
