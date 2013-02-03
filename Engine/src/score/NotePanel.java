package score;

import org.jfugue.elements.Note;

import javax.swing.*;
import java.awt.*;
import java.io.FileInputStream;
import java.util.HashMap;

public class NotePanel extends JPanel implements Comparable<NotePanel> {

	public int lyLine, lyNumber;
	public double x, y;
	public int page;
	public boolean active = false;
	public long time, millisTime, millisDuration;
    public boolean isRest = false;
    public int restType;
    public int tempo;

   /* public boolean hasAccidental = false;
    public double accidentalX, accidentalY;
    private Font accidentalFont;
    private int accidentalWidth, accidentalHeight;
    private String accidentalString;       */

	private Font font;
	private Note note;

    private String noteString;
    private int noteHeight, noteWidth, halfNoteHeight;

	private static double paperWidth, paperHeight;
	private static int imageWidth, imageHeight;

	private static HashMap<String, Font> fonts;
    private static int resolution;

    public static void setPaper(double width, double height) {
        paperWidth = width;
        paperHeight = height;
    }

    public static void setImage(int width, int height) {
        imageWidth = width;
        imageHeight = height;
    }

    public static void setResolution(int resolution) {
        NotePanel.resolution = resolution;
    }

    public static void addFont(String fontKey, String fontName, float scale) {
        try {
            fontName = fontName.toLowerCase();
            if (fonts == null) {
                fonts = new HashMap<String, Font>();
            }

            FileInputStream stream = new FileInputStream("data/fonts/otf/" + fontName + ".otf");

            Font f = Font.createFont(Font.TRUETYPE_FONT, stream);
            f = f.deriveFont(scale);
            fonts.put(fontKey, f);
            stream.close();

            GraphicsEnvironment ge = GraphicsEnvironment
                    .getLocalGraphicsEnvironment();

            ge.registerFont(f);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public NotePanel() {
		this.setBounds(0, 0, 1000, 1000);
		setOpaque(false);
	}

	public NotePanel(Note note) {
		this.setBounds(0, 0, 1000, 1000);
		setOpaque(false);
		setNote(note);
	}

	public NotePanel setNote(Note note) {
		this.note = note;
        return this;
	}

	public NotePanel setRest(int rest) {
		note = new Note((byte) 0);
		note.setRest(true);
        restType = rest;
        return this;
	}

    public NotePanel setTempo(int t) {
        tempo = t;
        if (isRest) {
            note.setDecimalDuration((double) 480 / resolution / Math.pow(2, restType));
        }
        this.millisTime = (long) (1875 * getTime() / tempo);
        this.millisDuration = (long) (1875 * getDuration() / tempo);

        return this;
    }

	public NotePanel setFont(String fontName) {
		this.font = fonts.get(fontName);
        if (font == null) {
            System.err.println("Couldn't find font: " + fontName);
        }

        FontMetrics fontMetrics = getFontMetrics(font);

        noteWidth = fontMetrics.stringWidth(noteString);
        noteHeight = fontMetrics.getHeight() + 1;
        halfNoteHeight = noteHeight / 2;

        return this;
	}

	public NotePanel setCoordinates(double x, double y) {
		this.x = x;
		this.y = y;

//		this.setBounds((int) (x / paperWidth * imageWidth - noteWidth/2), (int) (y / paperHeight * imageHeight - noteHeight/2), noteWidth, noteHeight);
        return this;
	}

	public NotePanel setLine(int lyLine, int lyNumber) {
		this.lyLine = lyLine;
		this.lyNumber = lyNumber;
        return this;
	}

	public NotePanel setPage(int page) {
		this.page = page;
        return this;
	}

    public NotePanel setGlyph(String glyphName) {
        int glyph = Glyph.getGlyph(glyphName);
        if (glyph == -1) {
            System.err.println("unknown glyph: \"" + glyphName + "\"");
        }
        this.noteString = ((Character)(Glyph.getChar(glyph))).toString();

        if (glyphName.startsWith("rests")) {
            this.isRest = true;
        }
        return this;
    }

    public NotePanel setTime(long time) {
        this.time = time;
        return this;
    }

    /*public NotePanel setAccidentals(double x, double y, String glyphName, String fontName) {
        System.out.println("yay?");
        hasAccidental = true;
        accidentalX = x;
        accidentalY = y;

        int glyph = Glyph.getGlyph(glyphName);
        if (glyph == -1) {
            System.err.println("unknown glyph: \"" + glyphName + "\"");
        }
        accidentalString = ((Character)(Glyph.getChar(glyph))).toString();

        accidentalFont = fonts.get(fontName);
        if (font == null) {
            System.err.println("Couldn't find font: " + fontName);
        }

        FontMetrics fontMetrics = getFontMetrics(font);

        accidentalWidth = fontMetrics.stringWidth(accidentalString);
        accidentalHeight = fontMetrics.getHeight() + 1;
        //halfNoteHeight = noteHeight / 2;

        return this;
    }   */

    public long getDuration() {
        //get duration in ticks
        return (long) (note.getDecimalDuration() * resolution);
    }

    public long getMillisDuration() {
        return millisDuration;
    }

    public long getTime() {
        return time;
    }

    public long getMillisTime() {
        return millisTime;
    }

	public String getMusicString() {
	    return note.getMusicString();
	}

    public Byte getValue() {
        return note.getValue();
    }

    public int getTempo() {
        return tempo;
    }

    private int absoluteX() {
        return (int) Math.round(imageWidth / paperWidth * x);
    }

    private int absoluteY() {
        return (int) Math.round(imageHeight / paperHeight * y);
    }

    private int absoluteX(double x) {
        return (int) Math.round(imageWidth / paperWidth * x);
    }

    private int absoluteY(double y) {
        return (int) Math.round(imageHeight / paperHeight * y);
    }

	public void paintComponent(Graphics g) {
		if (active) {
			g.setColor(Color.RED);
			g.setFont(font);
			g.drawString(noteString, absoluteX(), absoluteY());

            /*if (hasAccidental) {
                System.out.println("yay");
                g.setFont(accidentalFont);
                g.drawString(accidentalString, absoluteX(accidentalX), absoluteY(accidentalY));
            } */
		}
	}

	public void repaint() {
        //repaint((int) (x / paperWidth * imageWidth - offX), (int) (y / paperHeight * imageHeight - offY), noteWidth, noteHeight);
		repaint(0, 0, 10000, 10000);

        repaint(absoluteX(), absoluteY() - halfNoteHeight, noteWidth, noteHeight);
        /*if (hasAccidental) {
            repaint(absoluteX(accidentalX), absoluteY(accidentalY), accidentalWidth, accidentalHeight);
        } */
	}

    @Override
    public int compareTo(NotePanel note) {
        if (note.lyLine < this.lyLine) {
            return 1;
        } else if (note.lyLine > this.lyLine) {
            return -1;
        } else if (note.lyNumber < this.lyNumber) {
            return 1;
        } else if (note.lyNumber > this.lyNumber) {
            return -1;
        } else {
            return 0;
        }
    }
}
