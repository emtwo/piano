package piano.prototypes.marina;

import java.io.File;
import java.io.IOException;

import javax.swing.JComponent;
import javax.swing.JFrame;

import piano.prototypes.repository.marina.SongDatabaseAccessor;
import piano.prototypes.ui.marina.PianoUI;

public class Piano {
	
	SongDatabaseAccessor accessor;
	
	public Piano() throws IOException {
		populateDatabase();		// Note: this is some pre-processing that should be done as an "install"
    
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
    
    PianoUI rootUI = new PianoUI(frame);
    rootUI.switchToView(frame);
  }
	
	public static void main(String[] args) throws IOException {
		new Piano();
	}
}
