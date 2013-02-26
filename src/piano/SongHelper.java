package piano;

import piano.repository.Song;

public class SongHelper {
	public static Song getSong1() {
		// Create strings to be used to create Song.
		final String midiPath  = "music/midi/Sometimes.mid";
		final String imagePath = "music/images/britney_spears.jpg";
		final String title     = Song.getSongTitle(midiPath);
		final String author    = "Britney Spears";
		final String category  = "Pop";

		// Create & add song to database.
		return new Song(midiPath, imagePath, title, author, category);
	}

	public static Song getSong2() {
		// Create strings to be used to create Song.
		final String midiPath  = "music/midi/Baby_One_More_Time.mid";
		final String imagePath = "music/images/britney_spears.jpg";
		final String title     = Song.getSongTitle(midiPath);
		final String author    = "Britney Spears";
		final String category  = "Pop";

		// Create & add song to database.
		return new Song(midiPath, imagePath, title, author, category);
	}

	public static Song getSong3() {
		// Create strings to be used to create Song.
		final String midiPath  = "music/midi/Everybody.mid";
		final String imagePath = "music/images/backstreet_boys.jpg";
		final String title     = Song.getSongTitle(midiPath);
		final String author    = "Backstreet Boys";
		final String category  = "Pop";

		// Create & add song to database.
		return new Song(midiPath, imagePath, title, author, category);
	}

	public static Song getSong4() {
		// Create strings to be used to create Song.
		final String midiPath  = "music/midi/From_The_Bottom_Of_My_Broken_Heart.mid";
		final String imagePath = "music/images/britney_spears.jpg";
		final String title     = Song.getSongTitle(midiPath);
		final String author    = "Britney Spears";
		final String category  = "Pop";

		// Create & add song to database.
		return new Song(midiPath, imagePath, title, author, category);
	}

	public static Song getSong5() {
		// Create strings to be used to create Song.
		final String midiPath  = "music/midi/I_Want_It_That_Way.mid";
		final String imagePath = "music/images/backstreet_boys.jpg";
		final String title     = Song.getSongTitle(midiPath);
		final String author    = "Backstreet Boys";
		final String category  = "Pop";

		// Create & add song to database.
		return new Song(midiPath, imagePath, title, author, category);
	}

	public static Song getSong6() {
		// Create strings to be used to create Song.
		final String midiPath  = "music/midi/Larger_Than_Life.mid";
		final String imagePath = "music/images/backstreet_boys.jpg";
		final String title     = Song.getSongTitle(midiPath);
		final String author    = "Backstreet Boys";
		final String category  = "Pop";

		// Create & add song to database.
		return new Song(midiPath, imagePath, title, author, category);
	}

	public static Song getSong7() {
		// Create strings to be used to create Song.
		final String midiPath  = "music/midi/My_Name_Is.mid";
		final String imagePath = "music/images/eminem.jpg";
		final String title     = Song.getSongTitle(midiPath);
		final String author    = "Eminem";
		final String category  = "Rap";

		// Create & add song to database.
		return new Song(midiPath, imagePath, title, author, category);
	}

	public static Song getSong8() {
		// Create strings to be used to create Song.
		final String midiPath  = "music/midi/Shape_Of_My_Heart.mid";
		final String imagePath = "music/images/backstreet_boys.jpg";
		final String title     = Song.getSongTitle(midiPath);
		final String author    = "Backstreet Boys";
		final String category  = "Pop";

		// Create & add song to database.
		return new Song(midiPath, imagePath, title, author, category);
	}

	public static Song getSong9() {
		// Create strings to be used to create Song.
		final String midiPath  = "music/midi/Show_Me_The_Meaning.mid";
		final String imagePath = "music/images/backstreet_boys.jpg";
		final String title     = Song.getSongTitle(midiPath);
		final String author    = "Backstreet Boys";
		final String category  = "Pop";

		// Create & add song to database.
		return new Song(midiPath, imagePath, title, author, category);
	}

	public static Song getSong10() {
		// Create strings to be used to create Song.
		final String midiPath  = "music/midi/The_Real_Slim_Shady.mid";
		final String imagePath = "music/images/eminem.jpg";
		final String title     = Song.getSongTitle(midiPath);
		final String author    = "Eminem";
		final String category  = "Rap";

		// Create & add song to database.
		return new Song(midiPath, imagePath, title, author, category);
	}

	public static Song getSong11() {
		// Create strings to be used to create Song.
		final String midiPath  = "music/midi/The_Way_I_Am.mid";
		final String imagePath = "music/images/eminem.jpg";
		final String title     = Song.getSongTitle(midiPath);
		final String author    = "Eminem";
		final String category  = "Rap";

		// Create & add song to database.
		return new Song(midiPath, imagePath, title, author, category);
	}

	public static Song getSong12() {
		// Create strings to be used to create Song.
		final String midiPath  = "music/midi/You_Drive_Me_Crazy.mid";
		final String imagePath = "music/images/britney_spears.jpg";
		final String title     = Song.getSongTitle(midiPath);
		final String author    = "Britney Spears";
		final String category  = "Pop";

		// Create & add song to database.
		return new Song(midiPath, imagePath, title, author, category);
	}

	public static Song getSong13() {
		// Create strings to be used to create Song.
		final String midiPath  = "music/midi/Dont_Stop_Believin.mid";
		final String imagePath = "music/images/journey.jpg";
		final String title     = Song.getSongTitle(midiPath);
		final String author    = "Journey";
		final String category  = "Soft Rock";

		// Create & add song to database.
		return new Song(midiPath, imagePath, title, author, category);
	}
}
