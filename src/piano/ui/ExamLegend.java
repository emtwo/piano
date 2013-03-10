package piano.ui;

import piano.engine.Glyph;
import piano.engine.NotePanel;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;

public class ExamLegend extends JPanel {

    public static final int width = 250, height = 150;
    public static final int gradientLength = 160, gradientHeight = 10;
    public static final int margin = 20;
    public static final String fontName = "data/fonts/ttf/emmentaler-20.ttf";


    private Font font;

    public ExamLegend(int x, int y) {
        setBounds(x, y, width, height);
        setBorder(BorderFactory.createLineBorder(Color.black));
        setBackground(Color.white);

        loadFont();
    }

    private void loadFont() {
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(new File(fontName)));
            font = font.deriveFont(20.0f);

        } catch (Exception e) {
            System.err.println("Error loading font: ");
            e.printStackTrace();
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(Color.white);
        g.fillRect(0, 0, width, height);

        for (int i = 0; i < gradientLength; ++i) {
            float scale = (float) (i) / gradientLength;

            float hsb[] = Color.RGBtoHSB(NotePanel.startingColor.getRed(), NotePanel.startingColor.getGreen(), NotePanel.startingColor.getBlue(), null);
            float hue = hsb[0] * (NotePanel.HSBmin[0] + (NotePanel.HSBmax[0] - NotePanel.HSBmin[0]) * scale);
            float saturation = NotePanel.HSBmin[1] + (NotePanel.HSBmax[1] - NotePanel.HSBmin[1]) * scale;
            float brightness = NotePanel.HSBmin[2] + (NotePanel.HSBmax[2] - NotePanel.HSBmin[2]) * scale;

            Color c = Color.getHSBColor(hue, saturation, brightness);
            g.setColor(c);
            int x = (width - gradientLength) / 2 + i;
            g.drawLine(x, margin, x, margin + gradientHeight);

        }
        g.setColor(Color.black);
        g.setFont(font);
        g.drawString("pp", 13, margin + 9);
        g.drawString("ff", width - 27, margin + 9);

        g.setColor(Color.red);
        g.setFont(font);
        g.drawString(((Character)Glyph.getChar(Glyph.getGlyph("noteheads.s2"))).toString(), margin, 3 * margin + gradientHeight);
        g.drawString(((Character)Glyph.getChar(Glyph.getGlyph("noteheads.s1"))).toString(), 2 * margin, 3 * margin + gradientHeight);
        g.drawString(((Character)Glyph.getChar(Glyph.getGlyph("noteheads.s0"))).toString(), 3 * margin, 3 * margin + gradientHeight);


        g.setFont(Fonts.italic_small);
        g.setColor(Color.black);
        g.drawString("Missed notes", 4 * margin , 3 * margin + gradientHeight + 5);

        g.setColor(Color.red);
        g.setFont(font);
        g.drawString(((Character)Glyph.getChar(Glyph.getGlyph("noteheads.s2cross"))).toString(), margin, 5 * margin + gradientHeight);
        g.setFont(Fonts.italic_small);
        g.setColor(Color.black);
        g.drawString("Incorrect note", 4 * margin , 5 * margin + gradientHeight + 5);



    }

}
