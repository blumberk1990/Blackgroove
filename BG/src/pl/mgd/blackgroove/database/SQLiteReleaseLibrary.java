package pl.mgd.blackgroove.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import pl.mgd.blackgroove.data.Release;
import pl.mgd.blackgroove.data.VinylRelease;

public class SQLiteReleaseLibrary {

	/*--Fields--*/
	final private static String SQL_PREFIX = "jdbc:sqlite:";
	final private static String DRIVERS = "org.sqlite.JDBC";
	final private static String CONN_URL = "/src/pl/mgd/blackgroove/database/BlackGrooveLibrary.sqlite";
	final private static String SQL_QUERY_RELEASE_LIBRARY = "SELECT * FROM ReleaseLibrary";
	final private static String SQL_INSERT_RELEASE_LIBRARY_TABLE = "INSERT INTO ReleaseLibrary(artist, album, label, format, releaseData, size, digitalVersionLink, digitalPassword) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
	final private static String SQL_DELETE_STATEMENT = "DELETE FROM ReleaseLibrary WHERE ID = ?";
	final private static String SQL_TABLE_RELEASE_LIBRARY = "ReleaseLibrary";
	final private static String SQL_LAST_INSERT_ID = "SELECT MAX(ID) FROM ReleaseLibrary";
	
	private static Connection conn;
	
	/*--Javabeans--*/
	
	/*--Constructors--*/
	
	/*--Methods--*/
	private String getDirectoryPath() {
		StringBuilder dirPath = new StringBuilder();
		dirPath.append(SQL_PREFIX);
		dirPath.append(System.getProperty("user.dir"));
		dirPath.append(CONN_URL);
		return dirPath.toString();
	}
	
	private void getConnection() throws ClassNotFoundException, SQLException {
		Class.forName(DRIVERS);
		conn = DriverManager.getConnection(getDirectoryPath());
		System.out.println("Connection to SQLite has been established.");
	}
	
	public void closeDbConnection() throws SQLException {
		conn.close();
		System.out.println("Connection to SQLite has been disestablished.");
	}	
	
	public ResultSet displayAllReleaseLibrary() throws ClassNotFoundException, SQLException {
		if(conn == null) {
			getConnection();
		}
		Statement state = conn.createStatement();
		ResultSet result = state.executeQuery(SQL_QUERY_RELEASE_LIBRARY);
		return result;
	}

	public void insertRelease(Release release) throws ClassNotFoundException, SQLException {
		String sql = SQL_INSERT_RELEASE_LIBRARY_TABLE;
		if(conn.isClosed() || conn == null) {
			getConnection();
		}
		System.out.println("Wydanie dodawane do bazy: " + (VinylRelease)release);
		PreparedStatement prepState = conn.prepareStatement(sql);
		prepState.setString(1, release.getArtist());
		prepState.setString(2, release.getAlbum());
		prepState.setString(3, release.getLabel());
		prepState.setString(4, release.getFormat());
		prepState.setInt(5, release.getReleaseData());
		prepState.setString(6, ((VinylRelease) release).getVinylSize().toString());
		prepState.setString(7, ((VinylRelease) release).getDigitalVersionLink());
		prepState.setString(8, ((VinylRelease) release).getDigitalPassword());
		//Here should be cheked Autoincrement ID number of last Insert
		prepState.executeUpdate();
	}

	/*
	 * When this method is called, selected row will be overwrited
	 * with new value of the selected cell, and commited to SQLLite DB
	 */
	public void updateSelectedRow(Release release) throws ClassNotFoundException, SQLException {
		String sql = "UPDATE " + SQL_TABLE_RELEASE_LIBRARY + " SET artist = ?, "
				+ "album = ?, "
				+ "label = ?, "
				+ "format = ?, "
				+ "releaseData = ?, "
				+ "size = ?, "
				+ "digitalVersionLink = ?, "
				+ "digitalPassword = ? "
				+ "WHERE ID = ?";
		if(conn.isClosed() || conn == null) {
			getConnection();
		}		
		PreparedStatement prepState = conn.prepareStatement(sql);
		prepState.setString(1, release.getArtist());
		prepState.setString(2, release.getAlbum());
		prepState.setString(3, release.getLabel());
		prepState.setString(4, release.getFormat());
		prepState.setInt(5, release.getReleaseData());
		prepState.setString(6, ((VinylRelease)release).getVinylSize().getDescription());
		prepState.setString(7, ((VinylRelease)release).getDigitalVersionLink());
		prepState.setString(8, ((VinylRelease)release).getDigitalPassword());
		prepState.setInt(9, release.getId());
		prepState.executeUpdate();
	}

	/*
	 * When this method is called, selected row is deleted from SQLLite DB
	 */
	public void deleteSelectedRow(int id) throws ClassNotFoundException, SQLException {
		if(conn.isClosed() || conn == null) {
			getConnection();
		}
		PreparedStatement state = conn.prepareStatement(SQL_DELETE_STATEMENT);
		state.setInt(1, id);
		state.executeUpdate();
	}

	/*
	 * When this method is called, selected row is deleted from SQLLite DB
	 */

	public int getMaxIdValue() throws ClassNotFoundException, SQLException {
		int maxID = 0;
		if(conn.isClosed() || conn == null) {
			getConnection();
		}
		Statement state = conn.createStatement();
		state.execute(SQL_LAST_INSERT_ID);
		ResultSet rs = state.getResultSet();
		if(rs.next()) {
			maxID = rs.getInt(1);
		}
		return maxID;
	}
}
