package pl.mgd.blackgroove.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import pl.mgd.blackgroove.data.Release;
import pl.mgd.blackgroove.data.ReleaseLibrary;
import pl.mgd.blackgroove.data.VinylRelease;
import pl.mgd.blackgroove.data.VinylSize;
import pl.mgd.blackgroove.utils.AboutWindow;
import pl.mgd.blackgroove.utils.ContentSwapping;
import pl.mgd.blackgroove.utils.DeleteConfirmationBoxWindow;
import pl.mgd.blackgroove.utils.Discogs;
import pl.mgd.blackgroove.utils.ReleaseWindow;
import pl.mgd.blackgroove.utils.UpdateDigitalSourceWindow;

public class MainPaneController implements Initializable {

	/*--Fields--*/
	@FXML
	private BorderPane contentSwappingBorderPane;
	
	@FXML
	private ReleaseLibraryPaneController releaseLibraryPaneController;
	
	@FXML
	private LibraryNavigationPaneController libraryNavigationPaneController;
	
	@FXML
	private ProgramNavigationPaneController programNavigationPaneController;
	
	private ContentSwapping contentSwapping;
	private ReleaseWindow releaseWindow;
	private AboutWindow aboutWindow;
	
	private UpdateDigitalSourceWindow updateDigitalSourceWindow;
	private DeleteConfirmationBoxWindow deleteConfirmationBoxWindow;
	private ReleaseLibrary releaseLibrary;

	/*--Javabeans--*/
	
	/*--Constructors--*/
	public MainPaneController() {
		try {
			releaseWindow = new ReleaseWindow();
			aboutWindow = new AboutWindow();
			deleteConfirmationBoxWindow = new DeleteConfirmationBoxWindow();
			updateDigitalSourceWindow = new UpdateDigitalSourceWindow();			
			releaseLibrary = new ReleaseLibrary();				
		} catch (IOException e) {
			e.printStackTrace();
		}
			
	}
	
	/*--Methods--*/
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		contentSwapping = new ContentSwapping(contentSwappingBorderPane);
		System.out.println(libraryNavigationPaneController);
		System.out.println(programNavigationPaneController);
		releaseLibrary.initializeReleaseLibrary();
		libraryNavigationPaneController.getLibraryNavigationListView().getSelectionModel().select(0);		
		contentSwapping.loadReleaseLibraryContentPane(releaseLibrary.getReleaseLibraryObservableList());	
		configurationNewReleaseWindowButton();
		configurationInformationButton();
		configurationDropDownMenuSelector();
		configurationLibraryNavigation();
		configurationLibraryEditCellMod();
		configurationSearchBar();
		configurationContentPaneSelector();
		
	}

	/*--Configuration New Release Window--*/	
	private void configurationNewReleaseWindowButton() {
		Button openNewReleaseWindowButton = libraryNavigationPaneController.getOpenNewReleaseWindowButton();
		openNewReleaseWindowButton.setOnAction(e -> {
			releaseWindow.show();
			if(releaseWindow.getNewReleaseButtonPressed()) {
				System.out.println(releaseWindow.readAndCreateVinyl());
				releaseLibrary.addReleaseAndUpdateDatabase(releaseWindow.readAndCreateVinyl());
			}
			releaseWindow.clearField();
			/*
			 * ten element poni¿ej trzeba ubraæ w funkcjê
			 */
			libraryNavigationPaneController.getLibraryNavigationListView().getSelectionModel().select(0);
			contentSwapping.loadReleaseLibraryContentPane(releaseLibrary.getReleaseLibraryObservableList());
		});
		
	}
	
	/*--Configuration About Window--*/	
	private void configurationInformationButton() {
		Button informationButton = libraryNavigationPaneController.getInformationButton();
		informationButton.setOnAction(e -> {
			System.out.println("About Blackgroove");
			aboutWindow.show();
		});
	}

	/*--Configuration DropDown Menu functionalty--*/	
	public void configurationDropDownMenuSelector() {
		ObservableValue<Integer> observableValue = contentSwapping.getReleaseLibraryPaneController().getMenuObservableValue();
		observableValue.addListener((arg, oldVal, newVal) -> {
			Release editRelease = contentSwapping.getReleaseLibraryPaneController().getEditRelease();
			System.out.println("DropDownMEnuSelector MainPaneController");
			switch(newVal) {			
			case 1:
				System.out.println("case 1 ");
				break;
			case 2:
				System.out.println("Update digital source: " + editRelease);
				if(updateDigitalSourceWindow.showAndReturnConfirmation(editRelease)) {
					releaseLibrary.updateReleaseAndUpdateDatabase(updateDigitalSourceWindow.createUpdateRelease(editRelease));
					updateDigitalSourceWindow.setConfirmationValue(false);
				}
				break;
			case 3:
				System.out.println("Delete: ");
				if(deleteConfirmationBoxWindow.showAndReturnConfirmation()) {
					 releaseLibrary.deleteReleaseAndUpdateDatabase(editRelease.getId());
					 deleteConfirmationBoxWindow.setConfirmationValue(false); 
				 }
				break;
			default:
				break;
			}
			
			contentSwapping.getReleaseLibraryPaneController().getLibraryDropDownMenuWindowController().getlibraryDropDownMenuListView().getSelectionModel().clearSelection();
			contentSwapping.getReleaseLibraryPaneController().getLibraryDropDownMenuWindow().setMenuValue(0);
		});
	}
	
	/*--Configuration Navigation Library--*/
	public void configurationLibraryNavigation() {	
		contentSwapping.getReleaseLibraryPaneController().getLibrarySelectorLabel().textProperty().bind(libraryNavigationPaneController.getLibraryNavigationListView().getSelectionModel().selectedItemProperty());
		ListView<String> libraryNavigationListView = libraryNavigationPaneController.getLibraryNavigationListView();
		libraryNavigationListView.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
			String selectedModel = libraryNavigationListView.getSelectionModel().getSelectedItem();
				switch(selectedModel) {
				case LibraryNavigationPaneController.ALL_ALBUM:
					System.out.println("All Album");
					contentSwapping.loadReleaseLibraryContentPane(releaseLibrary.getReleaseLibraryObservableList());
					break;
				case LibraryNavigationPaneController.VINYL_RELEASE:
					System.out.println("Vinyl Release");
					contentSwapping.loadReleaseLibraryContentPane(releaseLibrary.getVinylVersionList());
					break;
				case LibraryNavigationPaneController.DIGITAL_RELEASE:
					System.out.println("Digital Release");
					contentSwapping.loadReleaseLibraryContentPane(releaseLibrary.getDigitalVersionList());
					break;
				default:
					break;
				}
			
		});
	}
	
	/*
	 * Settings for editing cell in tableView, and updating DB
	 * according to user input
	 */
	private void configurationLibraryEditCellMod() {
		ObservableList<TableColumn<Release, ?>> ColumnList = contentSwapping.getReleaseLibraryPaneController().getReleaseLibrary().getColumns();
		ColumnList.get(0).setOnEditCommit(event -> {		
			((Release) event.getTableView().getItems().get(event.getTablePosition().getRow())).setArtist((String)event.getNewValue());
			releaseLibrary.updateReleaseAndUpdateDatabase((Release) event.getTableView().getItems().get(event.getTablePosition().getRow()));
		});
		ColumnList.get(1).setOnEditCommit(event -> {
			((Release) event.getTableView().getItems().get(event.getTablePosition().getRow())).setAlbum((String)event.getNewValue());
			releaseLibrary.updateReleaseAndUpdateDatabase((Release) event.getTableView().getItems().get(event.getTablePosition().getRow()));
		});		
		ColumnList.get(2).setOnEditCommit(event -> {
			((Release) event.getTableView().getItems().get(event.getTablePosition().getRow())).setLabel((String) event.getNewValue());
			releaseLibrary.updateReleaseAndUpdateDatabase((Release) event.getTableView().getItems().get(event.getTablePosition().getRow()));
		});	
		ColumnList.get(3).setOnEditCommit(event -> {
			((Release) event.getTableView().getItems().get(event.getTablePosition().getRow())).setReleaseData((Integer) event.getNewValue());
			releaseLibrary.updateReleaseAndUpdateDatabase((Release) event.getTableView().getItems().get(event.getTablePosition().getRow()));
		});	
		ColumnList.get(4).setOnEditCommit(event -> {
			((VinylRelease) event.getTableView().getItems().get(event.getTablePosition().getRow())).setVinylSize((VinylSize) event.getNewValue());
			releaseLibrary.updateReleaseAndUpdateDatabase((VinylRelease) event.getTableView().getItems().get(event.getTablePosition().getRow()));
		});
	}
	
	/*
	 * Searchbar config
	 */
	public void configurationSearchBar() {
		TextField searchTextField = programNavigationPaneController.getSearchTextField();
		searchTextField.textProperty().addListener((arg, oldVal, newVal) -> {		
			System.out.println("Update searchBar: " + newVal);
			contentSwapping.loadReleaseLibraryContentPane(releaseLibrary.getSearchQueryList(newVal));
			//contentSwapping.getReleaseLibraryPaneController().showLibraryContent(releaseLibrary.getSearchQueryList(newVal));
		});
		searchTextField.focusedProperty().addListener((arg, oldVal, newVal) -> {
			System.out.println("focusedProperty: " + newVal);
			if(newVal) {
				libraryNavigationPaneController.getLibraryNavigationListView().getSelectionModel().clearSelection();
			}			
		});
	}
	
	/*--configuration detials pane--*/
	public void configurationContentPaneSelector() {
		TableView<Release> relaseLibraryTableView = contentSwapping.getReleaseLibraryPaneController().getReleaseLibrary();
		relaseLibraryTableView.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
			if(event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
				Discogs discogs = new Discogs(relaseLibraryTableView.getSelectionModel().getSelectedItem());
				discogs.search();
				contentSwapping.loadReleaseDetailsContentPane(discogs);
			}
		});
	}
}
