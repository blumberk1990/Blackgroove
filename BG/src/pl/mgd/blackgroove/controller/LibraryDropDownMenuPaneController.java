package pl.mgd.blackgroove.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.fxml.Initializable;

public class LibraryDropDownMenuPaneController implements Initializable {

	/*--Fields--*/
    @FXML
    private ListView<String> libraryDropDownMenuListView;
  
    private ObservableList<String> dropDownMenuItems;
	public static final String SHOW_DETAILS = "Show details";
	public static final String EDIT_DIGITAL_SOURCE = "Edit digital source";
	public static final String DELETE = "Delete";
    
	/*--Javabeans--*/
	public ListView<String> getlibraryDropDownMenuListView() {
		return libraryDropDownMenuListView;
	}
	
	/*--Methods--*/
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		dropDownMenuItems = FXCollections.observableArrayList();
		configurationLibraryDropDownMenuListView();		
	}
	
	private void configurationLibraryDropDownMenuListView() {
		dropDownMenuItems.addAll(SHOW_DETAILS, EDIT_DIGITAL_SOURCE, DELETE);
		libraryDropDownMenuListView.setItems(dropDownMenuItems);
	}
	
}
