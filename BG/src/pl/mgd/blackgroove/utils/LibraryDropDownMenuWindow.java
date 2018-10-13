package pl.mgd.blackgroove.utils;

import java.io.IOException;

import javafx.scene.control.ListView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import pl.mgd.blackgroove.controller.LibraryDropDownMenuPaneController;
import pl.mgd.blackgroove.data.Release;
import pl.mgd.blackgroove.data.VinylRelease;

public class LibraryDropDownMenuWindow {

	/*--Fields--*/
	private final String WINDOW_FXML_PATH = "/pl/mgd/blackgroove/view/LibraryDropDownMenuPane.fxml";
	private final String WINDOW_TITLE = "DropDown Menu";
	private NewWindowGenerator newLibraryDropDownMenuWindow;
	private LibraryDropDownMenuPaneController libraryDropDownMenuPaneController;
	private Release editRelease;
	private int menuValue;
	/*--Javabeans--*/
	public LibraryDropDownMenuPaneController getLibraryDropDownMenuPaneController() {
		return libraryDropDownMenuPaneController;
	}
	
	public void setEditRelease(Release editRelease) {
		this.editRelease = editRelease;
	}
	public Release getEditRelease() {
		return editRelease;
	}
	
	public int getMenuValue() {
		return menuValue;
	}
	
	public void setMenuValue(int menuValue) {
		this.menuValue = menuValue;
	}
	
	/*--Constructors--*/
	public LibraryDropDownMenuWindow() throws IOException {
		editRelease = new VinylRelease();
		menuValue = 0;
		newLibraryDropDownMenuWindow = new NewWindowGenerator();
		newLibraryDropDownMenuWindow.createStage(WINDOW_TITLE, WINDOW_FXML_PATH);
		libraryDropDownMenuPaneController = newLibraryDropDownMenuWindow.getFxmlLoader().getController();
	}
	
	/*--Methods--*/	
	public void show(Release editRelease) {
		//dropDownSelectorFeedback();
		setEditRelease(editRelease);
		newLibraryDropDownMenuWindow.show();
	}
	
	public int showAndReturnOption() {
		dropDownSelectorFeedback();
		newLibraryDropDownMenuWindow.show();
		return menuValue;
	}
	
	public void close() {
		newLibraryDropDownMenuWindow.close();
	}
	
	public void dropDownSelectorFeedback() {
		ListView<String> libraryDropDownMenu = libraryDropDownMenuPaneController.getlibraryDropDownMenuListView();
		libraryDropDownMenu.addEventFilter(MouseEvent.MOUSE_RELEASED, event -> {
			menuValue = 0;			
			if(event.getButton().equals(MouseButton.PRIMARY)) {
				System.out.println("Jestem w œrodku");
				/*
				 * Problem y wielokrotnzm wzwolaniem
				 */
				switch(libraryDropDownMenu.getSelectionModel().getSelectedItem()) {			
				case LibraryDropDownMenuPaneController.SHOW_DETAILS:
					menuValue = 1;
					close();
					break;
				case LibraryDropDownMenuPaneController.EDIT_DIGITAL_SOURCE:
					menuValue = 2;
					close();
					break;
				case LibraryDropDownMenuPaneController.DELETE:
					menuValue = 3;
					close();
					break;
				default:
					break;
				}
			}
		});
	}
	
}
