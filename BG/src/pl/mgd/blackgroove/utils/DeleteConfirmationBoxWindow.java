package pl.mgd.blackgroove.utils;

import java.io.IOException;

import javafx.scene.control.Button;
import pl.mgd.blackgroove.controller.DeleteConfirmationBoxPaneController;

public class DeleteConfirmationBoxWindow {
	
	/*--Fields--*/
	private final String WINDOW_FXML_PATH = "/pl/mgd/blackgroove/view/DeleteConfirmationBoxPane.fxml";
	private final String WINDOW_TITLE = "Confirmation Box";
	private boolean confirmationValue;
	private NewWindowGenerator newDeleteConfirmationBoxWindow;
	private DeleteConfirmationBoxPaneController deleteConfirmationBoxPaneController;
	
	/*--Javabeans--*/
	public boolean getConfirmationValue() {
		return confirmationValue;
	}
	public void setConfirmationValue(boolean confirmationValue) {
		this.confirmationValue = confirmationValue;
	}
	
	/*--Constructors--*/
	public DeleteConfirmationBoxWindow() throws IOException {
		newDeleteConfirmationBoxWindow = new NewWindowGenerator();
		newDeleteConfirmationBoxWindow.createStage(WINDOW_TITLE, WINDOW_FXML_PATH);
		deleteConfirmationBoxPaneController = newDeleteConfirmationBoxWindow.getFxmlLoader().getController();
	}
	
	/*--Methods--*/	
	public void show() {
		confirmationButtons();
		newDeleteConfirmationBoxWindow.show();
	}
	
	public Boolean showAndReturnConfirmation() {		
		show();
		return getConfirmationValue();
	}
	
	public void close() {
		newDeleteConfirmationBoxWindow.close();
	}
	
	private void confirmationButtons() {
		Button yesButton = deleteConfirmationBoxPaneController.getYesButton();
		yesButton.setOnAction(event -> {
			setConfirmationValue(true);
			newDeleteConfirmationBoxWindow.close();
			System.out.println("Button yes");
		});
		Button noButton = deleteConfirmationBoxPaneController.getNoButton();
		noButton.setOnAction(event -> {
			setConfirmationValue(false);
			newDeleteConfirmationBoxWindow.close();
			System.out.println("Button no");
		});
	}
	
}
