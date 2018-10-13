package pl.mgd.blackgroove.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;

public class LibraryNavigationPaneController implements Initializable {

	/*--Fields--*/
	@FXML
    private ImageView logoImageView;

    @FXML
    private Button informationButton;

    @FXML
    private Label LibraryLabel;

    @FXML
    private ListView<String> libraryNavigationListView;

    private ObservableList<String> libraryNavigationItems;
    
	public static final String ALL_ALBUM = "All albums";
	public static final String VINYL_RELEASE = "Vinyl albums";
	public static final String DIGITAL_RELEASE = "Digital albums";
    
    @FXML
    private Button openNewReleaseWindowButton;
    
    /*--Javabeans--*/
    public Button getOpenNewReleaseWindowButton() {
		return this.openNewReleaseWindowButton;	
    }
    
    public Button getInformationButton() {
		return this.informationButton;	
    }
    
    public ListView<String> getLibraryNavigationListView() {
    	return libraryNavigationListView;
    }
    
    /*--Methods--*/
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		libraryNavigationItems = FXCollections.observableArrayList();
		configurationLibraryNavigationListView();
		
	}

	private void configurationLibraryNavigationListView() {
		libraryNavigationItems.addAll(ALL_ALBUM, VINYL_RELEASE, DIGITAL_RELEASE);
		libraryNavigationListView.setItems(libraryNavigationItems);
	}
	
}
