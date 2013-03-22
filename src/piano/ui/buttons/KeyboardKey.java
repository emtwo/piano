package piano.ui.buttons;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import java.awt.RenderingHints;
import java.awt.Shape;

import org.jdesktop.swingx.*;
import org.jdesktop.swingx.image.GaussianBlurFilter;
import org.jdesktop.swingx.painter.*;
import org.jdesktop.swingx.painter.effects.ShadowPathEffect;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import piano.ui.ButtonClickCallback;
import piano.ui.Drawing;

public class KeyboardKey extends Button {

	private boolean isBlack;
    private Color hoverColor = Color.LIGHT_GRAY;
	public int id;

	public KeyboardKey(String text, int x, int y, int width, int height, boolean isBlack, int id) {
		super(text, x, y, width, height);
		this.isBlack = isBlack;
		this.id = id;
	}

	public KeyboardKey(String text, int x, int y, int width, int height,
			boolean isBlack, Drawing parent, ButtonClickCallback callback, int id) {
		super(text, x, y, width, height, parent, callback);
		this.isBlack = isBlack;
		this.id = id;
	}

	public void paintComponent(Graphics2D g2) {
    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setFont(font);

		FontMetrics metrics = g2.getFontMetrics(font);
		int hgt = metrics.getHeight();
		int adv = metrics.stringWidth(text);

		if (isBlack) {
			if (overButton) {
				g2.setColor(hoverColor);
			}
			if (g2.getColor() == Color.black) {
			  GradientPaint gp = new GradientPaint(x, y, new Color(80, 80, 80), x + width, y + height, Color.black);
	      g2.setPaint(gp);
			}
			g2.fillRoundRect(x, y, width, height, 5, 5);
			return;
		}
		if (overButton) {
			g2.setColor(hoverColor);
		}
		if (g2.getColor() == Color.WHITE) {
		  GradientPaint gp = new GradientPaint(x, y, Color.white, x + width, y + height, new Color(240, 240, 240));
	    g2.setPaint(gp);
		}
		g2.fillRoundRect(x, y, width, height, 5, 5);

		g2.setColor(Color.BLACK);
		g2.drawRoundRect(x, y, width, height, 5, 5);
		g2.drawString(text, x + (width - adv) - 20, y + (height - hgt) / 2 + hgt - diff);
	}

	public int getId() {
		return id;
	}

    public void setHoverColor(Color c) {
        hoverColor = c;
    }

	@Override
	public void hoverOver() {
		// TODO: fill rectangle as grey
	}

	@Override
	public void hoverOut() {
		// TODO: fill rectangle as white
	}
}
