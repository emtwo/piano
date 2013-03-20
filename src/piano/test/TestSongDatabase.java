package piano.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Test;

import piano.repository.Song;
import piano.repository.SongDatabase;

public class TestSongDatabase {

	@Test
	public void testStoreAndFetch() throws SQLException {
		SongDatabase db = new SongDatabase();
		db.wipeDatabase();

		// Create strings to be inserted into the song database.
		final String title     = "Fur Elise";
		final String subtitle  = "Subtitle";
		final String composer  = "Beethoven";
		final String style     = "Classical";
		final String date      = "Some Date";
		final String copyright = "1990";
		final String name      = "id";

		// Store and fetch from the song database.
		db.store(title, subtitle, composer, style, date, copyright, name);
		ResultSet rs = db.fetchAllSongs();

		// Test at least 1 row exists.
		assertTrue(rs.next());

		// Test that the row is the one we added.
		assertEquals(title, rs.getString(SongDatabase.COL_TITLE));
		assertEquals(subtitle, rs.getString(SongDatabase.COL_SUBTITLE));
		assertEquals(composer, rs.getString(SongDatabase.COL_COMPOSER));
		assertEquals(style, rs.getString(SongDatabase.COL_STYLE));
		assertEquals(date, rs.getString(SongDatabase.COL_DATE));
		assertEquals(copyright, rs.getString(SongDatabase.COL_COPYRIGHT));
		assertEquals(name, rs.getString(SongDatabase.COL_NAME));

		// Test there are no more rows.
		assertTrue(!rs.next());

		db.closeConnection();
	}
}
