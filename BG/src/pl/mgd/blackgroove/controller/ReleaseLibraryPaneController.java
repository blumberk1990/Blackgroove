package pl.mgd.blackgroove.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.util.converter.IntegerStringConverter;
import pl.mgd.blackgroove.data.Release;
import pl.mgd.blackgroove.data.VinylRelease;
import pl.mgd.blackgroove.data.VinylSize;
import pl.mgd.blackgroove.utils.ActionButtonTableCell;
import pl.mgd.blackgroove.utils.LibraryDropDownMenuWindow;

public class ReleaseLibraryPaneController implements Initializable {

	/*--Fields--*/	
	private final static String ARTIST_COLUMN = "Artist";
	private static final String ALBUM_COLUMN = "Album";
	private static final String LABEL_COLUMN = "Label";
	private static final String RELEASE_DATA_COLUMN = "Year";
	private static final String SIZE_COLUMN = "Size";
	private static final String FORMAT_COLUMN = "Format";
	private static final String ACTION_COLUMN = "Action";
	
    private TableColumn<Release, String> artistColumn;
    private TableColumn<Release, String> albumColumn;
    private TableColumn<Release, String> labelColumn;
    private TableColumn<Release, Integer> releaseColumn;
    private TableColumn<Release, VinylSize> sizeColumn;
    private TableColumn<Release, String> formatColumn;
    private TableColumn<Release, Button> actionColumn;
          
	@FXML
    private BorderPane releaseLibraryPane;

    @FXML
    private TableView<Release> releaseLibrary;

    @FXML
    private ImageView libraryImageView;

    @FXML
    private Label libraryLabel;

    @FXML
    private Label librarySelectorLabel;
    
    private LibraryDropDownMenuWindow libraryDropDownMenuWindow;
    private Release editRelease;
    
    private IntegerProperty menuValue;
    private ObservableValue<Integer> menuObservableValue;
    
	/*--Javabeans--*/
	public LibraryDropDownMenuPaneController getLibraryDropDownMenuWindowController() {
		return libraryDropDownMenuWindow.getLibraryDropDownMenuPaneController();
	}
	
	public LibraryDropDownMenuWindow getLibraryDropDownMenuWindow() {
		return libraryDropDownMenuWindow;
	}

    public Release getEditRelease() {
    	return editRelease;
    }
    
    private void setEditRelease(Release editRelease) {
    	this.editRelease = editRelease;
    }
    
    public ObservableValue<Integer> getMenuObservableValue() {
    	return menuObservableValue;
    }
    
	public TableView<Release> getReleaseLibrary() {
		return releaseLibrary;
	}
	
	public void setLibrarySelctorLabel(String librarySelctorLabel) {
		librarySelectorLabel.setText(librarySelctorLabel);
	}
	
	public Label getLibrarySelectorLabel() {
		return librarySelectorLabel;
	}

	/*--Methods--*/
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		editRelease = new VinylRelease();
		menuValue = new SimpleIntegerProperty();
		menuObservableValue = menuValue.asObject();
		configureLibraryTableView();
		try {
			libraryDropDownMenuWindow = new LibraryDropDownMenuWindow();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void configureLibraryTableView() {
		releaseLibrary.setEditable(true);
		
		artistColumn = new TableColumn<Release, String>(ARTIST_COLUMN);
		artistColumn.setCellValueFactory(cellData -> cellData.getValue().artistProperty());
		artistColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		
		albumColumn = new TableColumn<Release, String>(ALBUM_COLUMN);
		albumColumn.setCellValueFactory(cellData -> cellData.getValue().albumProperty());
		albumColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		
		labelColumn = new TableColumn<Release, String>(LABEL_COLUMN);
		labelColumn.setCellValueFactory(cellData -> cellData.getValue().labelProperty());
		labelColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		
		releaseColumn = new TableColumn<Release, Integer>(RELEASE_DATA_COLUMN);
		releaseColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<Integer>(cellData.getValue().releaseDataProperty().get()));
		releaseColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
		
		ObservableList<VinylSize> vinylSizeList = FXCollections.observableArrayList(VinylSize.values());
		sizeColumn = new TableColumn<Release, VinylSize>(SIZE_COLUMN);
		sizeColumn.setCellValueFactory(cellData -> ((VinylRelease) cellData.getValue()).vinylSizeProperty());		
		sizeColumn.setCellFactory(ComboBoxTableCell.forTableColumn(vinylSizeList));
		
		formatColumn = new TableColumn<Release, String>(FORMAT_COLUMN);
		formatColumn.setCellValueFactory(new PropertyValueFactory<>("format"));
		
		actionColumn = new TableColumn<Release, Button>(ACTION_COLUMN);
		actionColumn.setSortable(false);
		actionColumn.setCellFactory(ActionButtonTableCell.forTableColumn(ACTION_COLUMN, (Release r) -> {	
			menuValue.set(0);
			setEditRelease(r);
			menuValue.set(libraryDropDownMenuWindow.showAndReturnOption());
			return r;
		}));
		
		releaseLibrary.getColumns().add(artistColumn);
		releaseLibrary.getColumns().add(albumColumn);
		releaseLibrary.getColumns().add(labelColumn);
		releaseLibrary.getColumns().add(releaseColumn);
		releaseLibrary.getColumns().add(sizeColumn);
		releaseLibrary.getColumns().add(formatColumn);		
		releaseLibrary.getColumns().add(actionColumn);
	}

	public void showLibraryContent(ObservableList<Release> releaseLibrary) {
		this.releaseLibrary.setItems(releaseLibrary);
		
	}
	
	/*public Release getEditReleaseFromDropDownMenuButton() {
		return libraryDropDownMenuWindow.getEditRelease();
	}*/
		

}
