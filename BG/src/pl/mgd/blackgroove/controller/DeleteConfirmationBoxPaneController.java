package pl.mgd.blackgroove.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class DeleteConfirmationBoxPaneController  implements Initializable {

	/*--Fields--*/
    @FXML
    private VBox confirmationBoxVbox;

    @FXML
    private Label questionLabel;

    @FXML
    private Button yesButton;

    @FXML
    private Button noButton;
    

	/*--Javabeans--*/
	public Button getYesButton() {
		return yesButton;
	}
	public Button getNoButton() {
		return noButton;
	}
	
	/*--Methods--*/
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}

}
