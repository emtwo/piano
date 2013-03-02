package piano.engine;

import org.jfugue.elements.Note;

import piano.ui.Drawing;
import piano.ui.JFrameStack;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;
import java.util.Vector;

public class NotePanel extends Drawing implements Comparable<NotePanel>, Serializable {

	public int lyLine, lyNumber;
	public double x, y;
	public int page;
	public boolean active = false;
	public long time, millisTime, millisDuration;
    public boolean isRest = false;
    public boolean isGhost = false, correct;
    public int restType;
    public int tempo;
    public boolean isTie = false;
    public Score score;

   /* public boolean hasAccidental = false;
    public double accidentalX, accidentalY;
    private Font accidentalFont;
    private int accidentalWidth, accidentalHeight;
    private String accidentalString;       */

	private Font font;
	private Note note = null;
    private String noteString;
    private Vector<NotePanel> ghostNotes = new Vector<NotePanel>();
    private NotePanel matchedGhost = null;
    private int noteWidth;

    private static final int resolution = 384;
    private static final double noteHeight = 1.0, halfNoteHeight = 0.5;

    public NotePanel() {
        JFrameStack.getFrame().add(this);
	}

	public NotePanel(Note note) {
        this();
		setNote(note);
	}

    public NotePanel setScore(Score score) {
        this.score = score;
        setBounds(0, 0, score.imageWidth, score.imageHeight);
        if (score.resolution != resolution) {
            System.err.println("Score is not using default resolution");
        }
        return this;
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

	public NotePanel setGonville(Font font) {
		this.font = font;

        FontMetrics fontMetrics = getFontMetrics(font);
        noteWidth = fontMetrics.stringWidth(noteString);

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

    public NotePanel setMillisTime(long time) {
        this.millisTime = time;
        return this;
    }

    public NotePanel setTie(boolean isTie) {
        this.isTie = true;
        note = new Note((byte) 0);
        note.setAttackVelocity((byte) 0);
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

    public NotePanel setGhost(boolean isGhost) {
        this.isGhost = isGhost;
        return this;
    }

    public NotePanel setCorrect(boolean correct) {
        if (!this.isGhost) {
            System.err.println("Attempting to set non-ghost note as correct/incorrect");
            return this;
        }
        this.correct = correct;
        if (correct) {
            setGlyph("noteheads.s2");
        } else {
            setGlyph("noteheads.s2cross");
        }
        return this;
    }

    public NotePanel addGhostNote(NotePanel ghost, NotePanel referenceNote) {
        double ghostX, ghostY;
        if (referenceNote == null) {
            //note is played after the last note on a layer
            //TODO calculate this better
            ghostX = x + (double) (ghost.getMillisTime() - getMillisTime()) / getMillisDuration() * 100;
        } else if (this.getMillisTime() <= ghost.getMillisTime()) {
            ghostX = x + (double) (ghost.getMillisTime() - getMillisTime()) / getMillisDuration() * (referenceNote.x - x);
        } else {
            ghostX = x - (double) (getMillisTime() - ghost.getMillisTime()) / referenceNote.getMillisDuration() * (x - referenceNote.x);
        }

        if (getValue().equals(ghost.getValue())) {
            ghostY = y;
            // if there is another matched note, then only match the closest one
            if (matchedGhost == null) {
                ghost.setCorrect(true);
                matchedGhost = ghost;
            } else {
                if (Math.abs(matchedGhost.getMillisTime() - getMillisTime()) <= Math.abs(ghost.getMillisTime() - getMillisTime())) {
                    ghost.setCorrect(false);
                } else {
                    ghost.setCorrect(true);
                    matchedGhost.setCorrect(false);
                    matchedGhost = ghost;
                }
            }
        } else {
            ghost.setCorrect(false);
            // difference in line of the two notes
            int lineDifference = 0;
            int step = ghost.getValue() > getValue() ? 1 : -1;
            for (int v = getValue() + step; v != ghost.getValue() + step; v += step) {
                int t = v % 12;
                // skip black keys
                if (t != 1 && t != 3 && t != 6 && t != 8 && t != 10) {
                    lineDifference += step;
                }
            }
            ghostY = y - lineDifference * halfNoteHeight;
        }

        ghost.setGonville(font);
        ghost.setPage(page);
        ghost.setCoordinates(ghostX, ghostY);
        JFrameStack.getFrame().add(ghost);
        this.ghostNotes.add(ghost);
        return this;
    }

    public void clearGhostNotes() {
        for (NotePanel ghost : ghostNotes) {
            JFrameStack.getFrame().remove(ghost);
        }
        this.matchedGhost = null;
        this.ghostNotes.clear();
    }

    public NotePanel getMatchedGhost() {
        return matchedGhost;
    }

    public void paintGhosts() {
        for (NotePanel ghost : ghostNotes) {
            ghost.active = true;
            ghost.repaint();
        }
        if (matchedGhost == null) {
            active = true;
            repaint();
        }
        //TODO most ghost note painting logic
    }


    public Note getNote() {
        return note;
    }

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
        return (int) Math.round(score.scaleWidth * x);
    }

    private int absoluteY() {
        return (int) Math.round(score.scaleHeight * y);
    }

    private int absoluteX(double x) {
        return (int) Math.round(score.scaleWidth * x);
    }

    private int absoluteY(double y) {
        return (int) Math.round(score.scaleHeight * y);
    }

	public void paintComponent(Graphics g) {
		if (active) {
            if (isGhost && correct) {
                g.setColor(Color.GREEN);
            } else {
			    g.setColor(Color.RED);
            }

            Font oldFont = g.getFont();
			g.setFont(font);
			g.drawString(noteString, absoluteX(), absoluteY());
            g.setFont(oldFont);

            /*if (hasAccidental) {
                System.out.p rintln("yay");
                g.setFont(accidentalFont);
                g.drawString(accidentalString, absoluteX(accidentalX), absoluteY(accidentalY));
            } */
		}
	}

	public void repaint() {
        if (score != null) {
		    setOpaque(active);
            repaint(absoluteX(), absoluteY(y - halfNoteHeight), noteWidth, absoluteY(noteHeight));
        }
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
