package piano.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SongDatabaseAccessor {

	private static SongDatabaseAccessor accessor;
	private SongDatabase db;

	private SongDatabaseAccessor() {
		db = new SongDatabase();
	}

	public static SongDatabaseAccessor getDatabaseAccessor() {
		if (accessor == null) {
			accessor = new SongDatabaseAccessor();
		}
		return accessor;
	}

	public void addSong(Song song) {
		db.store(song.title, song.subtitle, song.composer, song.style, song.date, song.copyright, song.name);
	}

	public List<Song> getAllSongs() throws SQLException {
		ArrayList<Song> songs = new ArrayList<Song>();
		ResultSet rs = db.fetchAllSongs();
		try {
			while (rs.next()) {
				String title     = rs.getString(SongDatabase.COL_TITLE);
				String subtitle  = rs.getString(SongDatabase.COL_SUBTITLE);
				String composer  = rs.getString(SongDatabase.COL_COMPOSER);
				String style		 = rs.getString(SongDatabase.COL_STYLE);
				String date      = rs.getString(SongDatabase.COL_DATE);
				String copyright = rs.getString(SongDatabase.COL_COPYRIGHT);
				String name      = rs.getString(SongDatabase.COL_NAME);

				Song song = new Song(title, subtitle, composer, style, date, copyright, name);
				songs.add(song);
			}
		} catch (SQLException e) {
			System.out.println("");
		} finally {
			rs.close();
		}
		return songs;
	}

	public List<String> getAllOfColumn(String column) throws SQLException {
		ArrayList<String> columnVals = new ArrayList<String>();
		ResultSet rs = db.fetchAllOfColumn(column);
		try {
			while (rs.next()) {
				String stringInColumn  = rs.getString(column);
				columnVals.add(stringInColumn);
			}
		} catch (SQLException e) {
			System.out.println("");
		} finally {
			rs.close();
		}
		return columnVals;
	}

	public List<Song> getAllByCriterion(String column, String criterion) throws SQLException {
		ArrayList<Song> songs = new ArrayList<Song>();
		ResultSet rs = db.fetchByCriterion(column, criterion);
		try {
			while (rs.next()) {
				String title     = rs.getString(SongDatabase.COL_TITLE);
				String subtitle  = rs.getString(SongDatabase.COL_SUBTITLE);
				String composer  = rs.getString(SongDatabase.COL_COMPOSER);
				String style		 = rs.getString(SongDatabase.COL_STYLE);
				String date      = rs.getString(SongDatabase.COL_DATE);
				String copyright = rs.getString(SongDatabase.COL_COPYRIGHT);
				String name      = rs.getString(SongDatabase.COL_NAME);

				Song song = new Song(title, subtitle, composer, style, date, copyright, name);
				songs.add(song);
			}
		} catch (SQLException e) {
			System.out.println("");
		} finally {
			rs.close();
		}
		return songs;
	}

	public void wipe() {
		db.wipeDatabase();
	}

	public void close() {
		db.closeConnection();
	}
}
