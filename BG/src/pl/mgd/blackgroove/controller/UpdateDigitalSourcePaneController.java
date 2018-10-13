package pl.mgd.blackgroove.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class UpdateDigitalSourcePaneController implements Initializable {

	/*--Fields--*/
    @FXML
    private TextField urlDigitalLinkTextField;

    @FXML
    private TextField passwordDigitalLinkTextField;
    
    @FXML
    private Button commitButton;

	/*--Javabeans--*/
    public TextField getUrlDigitalLinkTextField() {
    	return this.urlDigitalLinkTextField;
    }
    
    public String getUrlDigitalLinkText() {
    	return this.urlDigitalLinkTextField.getText();
    }
    
    public void setUrlDigitalLinkTextField(String urlLink) {
    	urlDigitalLinkTextField.setText(urlLink);
    }
    
    public TextField getPasswordDigitalLinkTextField() {
    	return this.passwordDigitalLinkTextField;
    }
    
    public String getPasswordDigitalLinkText() {
    	return this.passwordDigitalLinkTextField.getText();
    }
    
    public void setPasswordDigitalLink(String passwordDigitalLink) {
    	passwordDigitalLinkTextField.setText(passwordDigitalLink);
    }
    
    public Button getCommitButton() {
    	return commitButton;
    }
	
	/*--Methods--*/	
	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}
	

	
}
