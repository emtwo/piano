package piano.test;

import static org.junit.Assert.assertEquals;

import java.sql.SQLException;
import java.util.List;

import org.junit.Test;

import piano.repository.Song;
import piano.repository.SongDatabaseAccessor;

public class TestSongDatabaseAccessor {

	@Test
	public void testAddSongFetchAllSongs() throws SQLException {
		SongDatabaseAccessor accessor = SongDatabaseAccessor.getDatabaseAccessor();
		accessor.wipe();

		// Create strings to be used to create Song.
    final String title     = "Fur Elise";
    final String subtitle  = "Subtitle";
    final String composer  = "Beethoven";
    final String style     = "Classical";
    final String date      = "Some Date";
    final String copyright = "1990";
    final String name      = "id";

		// Create & add song to database.
		Song song = new Song(title, subtitle, composer, style, date, copyright, name);
		accessor.addSong(song);

		List<Song> songsInDB = accessor.getAllSongs();

		// Test the item we added is correctly the only one in the database.
		assertEquals(1, songsInDB.size());
		assertEquals(title,    songsInDB.get(0).title);
		assertEquals(subtitle, songsInDB.get(0).subtitle);
		assertEquals(composer, songsInDB.get(0).composer);
		assertEquals(style,    songsInDB.get(0).style);
		assertEquals(date,     songsInDB.get(0).date);

		accessor.close();
	}
}
