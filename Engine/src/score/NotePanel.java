package score;

import java.awt.Graphics;
import java.awt.Color;

import javax.swing.JPanel;

import org.jfugue.Note;
import org.jfugue.Player;

public class NotePanel extends JPanel implements Comparable<NotePanel> {
	
	public int lyLine, lyNumber;
	public double x, y;
	public int page;
	public Chord chord;
	
	private Note note; 
	
	private static int noteHeight = 6, noteWidth = 8, offX = 0, offY = 2;
	private static double paperWidth, paperHeight;
	private static int imageWidth, imageHeight;
	
	public NotePanel() {
		this.setBounds(0, 0, 1000, 1000);
		setOpaque(false);
	}
	
	public void setNote(Note note) {
		this.note = note;
	}
	
	public void setRest(int rest) {
		note = new Note((byte) 0);
		note.setRest(true);
		note.setDecimalDuration(1/Math.pow(2, rest));
	}
	
	public boolean isRest() {
		if (note != null && note.isRest()) {
			return true;
		} else {
			return false;
		}
	}
	
	public static void setPaper(double width, double height) {
		paperWidth = width;
		paperHeight = height;
	}
	
	public static void setImage(int width, int height) {
		imageWidth = width;
		imageHeight = height;
	}
	
	public void setChord(Chord chord) {
		this.chord = chord;
	}
	
	public void setCoordinates(double x, double y) {
		this.x = x;
		this.y = y;

//		this.setBounds((int) (x / paperWidth * imageWidth - noteWidth/2), (int) (y / paperHeight * imageHeight - noteHeight/2), noteWidth, noteHeight);
	}
	
	public void setLine(int lyLine, int lyNumber) {
		this.lyLine = lyLine;
		this.lyNumber = lyNumber;
	}
	
	public void setPage(int page) {
		this.page = page;
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
	
	public void play() {
		//NotePlayer player = new NotePlayer(note.getMusicString());
	}
	
	public String getMusicString() {
		if (note.isRest()) {
			return "R/" + note.getDecimalDuration();
		} else {
			return note.getMusicString();
		}
	}
	
	public void paintComponent(Graphics g) {
		//super.paintComponent(g);
		if (chord.active) {
			g.setColor(Color.RED);
			g.fillOval((int) (x / paperWidth * imageWidth - offX), (int) (y / paperHeight * imageHeight - offY), noteWidth, noteHeight);
			//g.fillOval((int) (x / paperWidth * imageWidth), (int) (y / paperHeight * imageHeight), 5, 5);
		}
	}
	
	public void repaint(){
		super.repaint((int) (x / paperWidth * imageWidth - offX), (int) (y / paperHeight * imageHeight - offY), noteWidth, noteHeight);
		repaint((int) (x / paperWidth * imageWidth - offX), (int) (y / paperHeight * imageHeight - offY), noteWidth, noteHeight);
	}
	

}
