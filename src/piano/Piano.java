package piano;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JFrame;

import piano.engine.*;
import piano.repository.Song;
import piano.repository.SongDatabaseAccessor;
import piano.ui.JFrameStack;
import piano.ui.PianoUI;

public class Piano {

	SongDatabaseAccessor accessor;

	public Piano() throws IOException {
	  boolean softInstall = false;
      boolean hardInstall = true;

	  if (hardInstall) {
      ScoreParser.hardInstall = true;
      renderScores();
    }

	  if (softInstall) {
	    ScoreParser.hardInstall = false;
      renderScores();
	  }

	  populateDatabase();   // Note: this is some pre-processing that should be done as an "install"

    PianoAdapterParser.instance();
    NotePlayer.init(); // this takes some time, so initialize music players
    loadFonts();

    //Schedule a job for the event dispatch thread:
    //creating and showing this application's GUI.
    javax.swing.SwingUtilities.invokeLater(new Runnable() {
    public void run() {
      try {
        createAndShowGUI();
      } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }
    });
	}

    private void loadFonts() {
        try {
            File fontFolder = new File("data/fonts/ttf");
            File[] fontFiles = fontFolder.listFiles();
            for (File fontFile : fontFiles) {
                if (fontFile.getName().endsWith(".ttf")) {
                    Font f = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(fontFile));
					GraphicsEnvironment ge = GraphicsEnvironment
							.getLocalGraphicsEnvironment();

					ge.registerFont(f);
                }
            }

        } catch (Exception e) {
            System.err.println("Error loading font: ");
            e.printStackTrace();
        }
    }

    private ArrayList<LilyHeader> loadHeaders() {
        ArrayList<LilyHeader> headers = new ArrayList<LilyHeader>();
        try {
            File lilyFolder = new File("data/ly");
            File[] lilyFiles = lilyFolder.listFiles();
            for (File lilyFile : lilyFiles) {
                if (lilyFile.getName().endsWith(".ly")) {
                    String lilyName = lilyFile.getName().split("\\.")[0];
                    headers.add(new LilyHeader(lilyName));
                }
            }
        } catch (Exception e) {
            System.err.println("Error loading lilypond: ");
            e.printStackTrace();
        }
        return headers;
    }

    private void renderScores() {
        ArrayList<LilyHeader> songInfo = loadHeaders();
        for (LilyHeader header : songInfo) {
            Score.ParseScore(header.name);
        }
    }

	private void populateDatabase() {
		System.out.println("Going to initialize database.");

		accessor = SongDatabaseAccessor.getDatabaseAccessor();
		accessor.wipe();

		ArrayList<LilyHeader> songInfo = loadHeaders();
		for (LilyHeader header : songInfo) {
		  accessor.addSong(new Song(header.get("title"), header.get("subtitle"),
		      header.get("composer"), header.get("style"), header.get("date"), header.get("copyright"), header.name));
		}
	}

  public static void createAndShowGUI() throws IOException {
    //Create and set up the window.
    JFrame frame = new JFrame("Rhapsody");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    JFrameStack.setJFrameStack(frame);

    PianoUI rootUI = new PianoUI();
    JFrameStack.pushPanel(rootUI);
  }

	public static void main(String[] args) throws IOException {
		new Piano();
	}
}
