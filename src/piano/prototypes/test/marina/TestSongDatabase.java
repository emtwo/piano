package piano.prototypes.test.marina;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Test;

import piano.prototypes.repository.marina.Song;
import piano.prototypes.repository.marina.SongDatabase;

public class TestSongDatabase {
	@Test
	public void testStoreAndFetch() throws SQLException {
		SongDatabase db = new SongDatabase();
		db.wipeDatabase();
		
		// Create strings to be inserted into the song database.
		final String midiPath  = "music/midi/Sometimes.mid";
		final String imagePath = "music/images/britney_spears.jpg";
		final String title     = Song.getSongTitle(midiPath);
		final String author    = "Britney Spears";
		final String category  = "Pop";
		
		// Store and fetch from the song database.
		db.store(title, "Britney Spears", midiPath, imagePath, category);
		ResultSet rs = db.fetchAllSongs();
		
		// Test at least 1 row exists.
		assertTrue(rs.next());
		
		// Test that the row is the one we added.
		assertEquals(midiPath,  rs.getString(SongDatabase.COL_MIDI_PATH));
		assertEquals(imagePath, rs.getString(SongDatabase.COL_IMAGE_PATH));
		assertEquals(title,     rs.getString(SongDatabase.COL_TITLE));
		assertEquals(author,    rs.getString(SongDatabase.COL_AUTHOR));
		assertEquals(category,  rs.getString(SongDatabase.COL_CATEGORY));
		
		// Test there are no more rows.
		assertTrue(!rs.next());
		
		db.closeConnection();
	}
}
