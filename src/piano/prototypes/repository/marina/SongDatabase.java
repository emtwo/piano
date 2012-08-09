package piano.prototypes.repository.marina;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SongDatabase {
	private static final String DB_NAME    = "songs.db";
	private static final String SONG_TABLE = "songs";
	
	public static final String COL_TITLE      = "title";
	public static final String COL_AUTHOR     = "composer";
	public static final String COL_MIDI_PATH  = "midi_path";
	public static final String COL_IMAGE_PATH = "image_path";
	public static final String COL_CATEGORY   = "genre";
	
	private Connection connection;
	
	public SongDatabase() {
		openConnection();
		createTable();
	}
	
	private void openConnection() {	
		try {
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection("jdbc:sqlite:" + DB_NAME);
		} catch (Exception e) {
			System.out.println("Unable to open connection.");
		}
	}
	
	private void createTable() {
		try {
			Statement stat = connection.createStatement();
			stat.executeUpdate("CREATE TABLE IF NOT EXISTS " + SONG_TABLE + " ("
					+ COL_TITLE + ", "
					+ COL_AUTHOR + ", "
					+ COL_MIDI_PATH + ", "
					+ COL_IMAGE_PATH + ", "
					+ COL_CATEGORY + ", "
					+ "PRIMARY KEY (" + COL_TITLE + ", " + COL_AUTHOR + "));");
		} catch (SQLException e) {
			System.out.println("Unable to create table");
		}
	}
	
	public void store(String title, String author, String midi, String image, String category) {
		try {
			// Delete existing row.
			PreparedStatement prep1 = connection.prepareStatement(
					"DELETE FROM " + SONG_TABLE + " WHERE " 
					+ COL_TITLE + " = ?" + " AND "
		 			+ COL_AUTHOR + " = ?;");
			
			prep1.setString(1, title);
		  prep1.setString(2, author);
		  prep1.addBatch();
		  prep1.executeBatch();

		  // Create new row.
			PreparedStatement prep2 = connection.prepareStatement(
					"INSERT INTO " + SONG_TABLE + " VALUES (?, ?, ?, ?, ?);");

			prep2.setString(1, title);
		  prep2.setString(2, author);
		  prep2.setString(3, midi);
		  prep2.setString(4, image);
		  prep2.setString(5, category);
		  prep2.addBatch();
		  prep2.executeBatch();
		} catch (SQLException e) {
			System.out.println("Unable to store song.");
			e.printStackTrace();
		}
	}

	public ResultSet fetchByCriterion(String column, String criterion) {
		try {
			Statement stat = connection.createStatement();
			return stat.executeQuery("select * from " + SONG_TABLE + " WHERE " + column + " = '" + criterion +
					"' ORDER BY " + COL_TITLE + ";");
		} catch (SQLException e) {
			System.out.println("Unable to fetch all songs.");
			return null;
		}
	}
	
	public ResultSet fetchAllOfColumn(String column) {
		try {
			Statement stat = connection.createStatement();
			return stat.executeQuery("select distinct " + column + " from " + SONG_TABLE + " ORDER BY " + column + ";");
		} catch (SQLException e) {
			System.out.println("Unable to fetch all songs.");
			return null;
		}
	}
	
	public ResultSet fetchAllSongs() {
		try {
			Statement stat = connection.createStatement();
			return stat.executeQuery("select * from " + SONG_TABLE + " ORDER BY " + COL_TITLE + ";");
		} catch (SQLException e) {
			System.out.println("Unable to fetch all songs.");
			return null;
		}
	}
	
	public void wipeDatabase() {
		try {
			Statement stat = connection.createStatement();
	    stat.executeUpdate("DROP TABLE IF EXISTS " + SONG_TABLE + ";");
	    createTable();
		} catch (SQLException e) {
			System.out.println("Unable to wipe database.");
		}
	}
	
	public void closeConnection() {
		try {
			connection.close();
		} catch (SQLException e) {
			System.out.println("Unable to close connection.");
		}
	}
}
