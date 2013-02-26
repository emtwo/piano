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
		db.store(song.title, song.author, song.midiPath, song.imagePath, song.category);
	}

	public List<Song> getAllSongs() throws SQLException {
		ArrayList<Song> songs = new ArrayList<Song>();
		ResultSet rs = db.fetchAllSongs();
		try {
			while (rs.next()) {
				String midiPath  = rs.getString(SongDatabase.COL_MIDI_PATH);
				String imagePath = rs.getString(SongDatabase.COL_IMAGE_PATH);
				String title     = rs.getString(SongDatabase.COL_TITLE);
				String author		 = rs.getString(SongDatabase.COL_AUTHOR);
				String category  = rs.getString(SongDatabase.COL_CATEGORY);

				Song song = new Song(midiPath, imagePath, title, author, category);
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
				String midiPath  = rs.getString(SongDatabase.COL_MIDI_PATH);
				String imagePath = rs.getString(SongDatabase.COL_IMAGE_PATH);
				String title     = rs.getString(SongDatabase.COL_TITLE);
				String author		 = rs.getString(SongDatabase.COL_AUTHOR);
				String category  = rs.getString(SongDatabase.COL_CATEGORY);

				Song song = new Song(midiPath, imagePath, title, author, category);
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
