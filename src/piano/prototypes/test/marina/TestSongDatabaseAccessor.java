package piano.prototypes.test.marina;

import static org.junit.Assert.assertEquals;

import java.sql.SQLException;
import java.util.List;

import org.junit.Test;

import piano.prototypes.repository.marina.Song;
import piano.prototypes.repository.marina.SongDatabaseAccessor;

public class TestSongDatabaseAccessor {
	@Test
	public void testAddSongFetchAllSongs() throws SQLException {
		SongDatabaseAccessor accessor = SongDatabaseAccessor.getDatabaseAccessor();
		accessor.wipe();
		
		// Create strings to be used to create Song.
		final String midiPath  = "music/midi/Sometimes.mid";
		final String imagePath = "music/images/britney_spears.jpg";
		final String title     = Song.getSongTitle(midiPath);
		final String author    = "Britney Spears";
		final String category  = "Pop";
		
		// Create & add song to database.
		Song song = new Song(midiPath, imagePath, title, author, category);
		accessor.addSong(song);
		
		List<Song> songsInDB = accessor.getAllSongs();

		// Test the item we added is correctly the only one in the database.
		assertEquals(1, songsInDB.size());
		assertEquals(midiPath,  songsInDB.get(0).midiPath);
		assertEquals(imagePath, songsInDB.get(0).imagePath);
		assertEquals(title,     songsInDB.get(0).title);
		assertEquals(author,    songsInDB.get(0).author);
		assertEquals(category,  songsInDB.get(0).category);
		
		accessor.close();
	}
}
