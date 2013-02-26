package piano.ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JFrame;

import piano.ui.buttons.Button;

public class Box extends Button {
	int x, y, width, height;

	public Box(int x, int y, int width, int height, Drawing parent, Drawing nextView, JFrame parentFrame) {
		super("", x, y, width, height, parent, nextView, parentFrame);
	}

	@Override
	public void hoverOver() {}

	@Override
	public void hoverOut() {}
}
