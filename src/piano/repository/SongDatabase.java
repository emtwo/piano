package piano.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SongDatabase {
	private static final String DB_NAME    = "songs.db";
	private static final String SONG_TABLE = "songs";

	public static final String COL_TITLE     = "title";
	public static final String COL_SUBTITLE  = "subtitle";
	public static final String COL_COMPOSER  = "composer";
	public static final String COL_STYLE     = "style";
	public static final String COL_DATE      = "date";
	public static final String COL_COPYRIGHT = "copyright";
	public static final String COL_NAME      = "name";

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
					+ COL_SUBTITLE + ", "
					+ COL_COMPOSER + ", "
					+ COL_STYLE + ", "
					+ COL_DATE + ", "
					+ COL_COPYRIGHT + ", "
					+ COL_NAME + ", "
					+ "PRIMARY KEY (" + COL_TITLE + ", " + COL_COMPOSER + "));");
		} catch (SQLException e) {
			System.out.println("Unable to create table");
		}
	}

	public void store(String title, String subtitle, String composer, String style, String date, String copyright, String name) {
		try {
			// Delete existing row.
			PreparedStatement prep1 = connection.prepareStatement(
					"DELETE FROM " + SONG_TABLE + " WHERE "
					+ COL_TITLE + " = ?" + " AND "
					+ COL_COMPOSER + " = ?;");

			prep1.setString(1, title);
		  prep1.setString(2, composer);
		  prep1.addBatch();
		  prep1.executeBatch();

		  // Create new row.
			PreparedStatement prep2 = connection.prepareStatement(
					"INSERT INTO " + SONG_TABLE + " VALUES (?, ?, ?, ?, ?, ?, ?);");

			prep2.setString(1, title);
		  prep2.setString(2, subtitle);
		  prep2.setString(3, composer);
		  prep2.setString(4, style);
		  prep2.setString(5, date);
		  prep2.setString(6, copyright);
		  prep2.setString(7, name);
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
