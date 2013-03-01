package piano;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JFrame;

import piano.engine.*;
import piano.repository.SongDatabaseAccessor;
import piano.ui.JFrameStack;
import piano.ui.PianoUI;

public class Piano {

	SongDatabaseAccessor accessor;

	public Piano() throws IOException {

        boolean install = true;

        if (install) {
            loadHeaders();
		    populateDatabase();		// Note: this is some pre-processing that should be done as an "install"
        }

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
                }
            }
        } catch (Exception e) {
            System.err.println("Error loading font: ");
            e.printStackTrace();
        }
    }

    private void loadHeaders() {
        ArrayList<LilyHeader> headers = new ArrayList<LilyHeader>();
        try {
            File lilyFolder = new File("data/ly");
            File[] lilyFiles = lilyFolder.listFiles();
            for (File lilyFile : lilyFiles) {
                if (lilyFile.getName().endsWith(".ly")) {
                    String lilyName = lilyFile.getName().split(".")[0];
                    headers.add(new LilyHeader(lilyName));
                }
            }
        } catch (Exception e) {
            System.err.println("Error loading font: ");
            e.printStackTrace();
        }
    }

	private void populateDatabase() {
		File file = new File("../../music");

		// The music folder has already been initialized.
		if (file.exists() && file.isDirectory()) {
			System.out.println("Music folder already initialized");
			//return;
		}

		System.out.println("Going to initialize database.");

		accessor = SongDatabaseAccessor.getDatabaseAccessor();
		accessor.wipe();

		// Add hard-coded songs (temporary)
		accessor.addSong(SongHelper.getSong1());
		accessor.addSong(SongHelper.getSong2());
		accessor.addSong(SongHelper.getSong3());
		accessor.addSong(SongHelper.getSong4());
		accessor.addSong(SongHelper.getSong5());
		accessor.addSong(SongHelper.getSong6());
		accessor.addSong(SongHelper.getSong7());
		accessor.addSong(SongHelper.getSong8());
		accessor.addSong(SongHelper.getSong9());
		accessor.addSong(SongHelper.getSong10());
		accessor.addSong(SongHelper.getSong11());
		accessor.addSong(SongHelper.getSong12());
		accessor.addSong(SongHelper.getSong13());
	}

  public static void createAndShowGUI() throws IOException {
    //Create and set up the window.
    JFrame frame = new JFrame("Piano Like a Pro");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    JFrameStack.setJFrameStack(frame);

    PianoUI rootUI = new PianoUI();
    JFrameStack.pushPanel(rootUI);
  }

	public static void main(String[] args) throws IOException {
		new Piano();
	}
}
