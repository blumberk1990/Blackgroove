package pl.mgd.blackgroove.data;

import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import pl.mgd.blackgroove.database.SQLiteReleaseLibrary;

public class ReleaseLibrary {

	/*--Fields--*/
	private SQLiteReleaseLibrary sqlReleaseLibrary;
	private ObservableList<Release> releaseLibraryObservableList;
	
	
	/*--Javabeans--*/
	public ObservableList<Release> getReleaseLibraryObservableList() {
		return releaseLibraryObservableList;
	}
	
	/*--Contructors--*/
	public ReleaseLibrary() {
		releaseLibraryObservableList = FXCollections.observableArrayList();
		sqlReleaseLibrary = new SQLiteReleaseLibrary();
	}
	
	/*--Methods--*/	
	public void addReleaseAndUpdateDatabase(Release release) {
		try {
			System.out.println("Wstawiam takiego Vinyl (ReleaseLibrary):" + release);
			sqlReleaseLibrary.insertRelease(release);
			release.setId(sqlReleaseLibrary.getMaxIdValue());
			releaseLibraryObservableList.add(release);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				sqlReleaseLibrary.closeDbConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public void deleteReleaseAndUpdateDatabase(int id) {
		try {
			sqlReleaseLibrary.deleteSelectedRow(id);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				sqlReleaseLibrary.closeDbConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		releaseLibraryObservableList.removeIf(release -> release.getId() == id);
		
	}
	
	public void updateReleaseAndUpdateDatabase(Release release) {

		try {
			sqlReleaseLibrary.updateSelectedRow(release);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				sqlReleaseLibrary.closeDbConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		int indexOf = releaseLibraryObservableList.indexOf(releaseLibraryObservableList.stream().filter(object -> object.getId() == release.getId()).findFirst().get());
		releaseLibraryObservableList.set(indexOf, release);
	}
	
	public void initializeReleaseLibrary() {		
		try {		
			ResultSet rs = sqlReleaseLibrary.displayAllReleaseLibrary();
			while(rs.next()) {
				Release vr = new VinylRelease(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getInt(6), rs.getString(7), rs.getString(8), rs.getString(9));
				releaseLibraryObservableList.add(vr);
			}	
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				sqlReleaseLibrary.closeDbConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public ObservableList<Release> getDigitalVersionList() {
		ObservableList<Release> digitalReleaseList = FXCollections.observableArrayList();
		releaseLibraryObservableList.stream().filter(object -> object.getFormat().contains("Vinyl + Digital")).forEach(digitalReleaseList::add);
		return digitalReleaseList;
	}
	
	public ObservableList<Release> getVinylVersionList() {
		ObservableList<Release> vinylReleaseList = FXCollections.observableArrayList();
		releaseLibraryObservableList.stream().filter(object -> object.getFormat().equals("Vinyl")).forEach(vinylReleaseList::add);
		return vinylReleaseList;
	}
	
	public ObservableList<Release> getSearchQueryList(String searchQuery) {
		ObservableList<Release> searchQueryList = FXCollections.observableArrayList();
		releaseLibraryObservableList.stream().filter(object -> object.toString().toLowerCase().contains(searchQuery.toLowerCase())).forEach(searchQueryList::add);
		return searchQueryList;
	}
	
}
