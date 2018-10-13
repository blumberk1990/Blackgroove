package pl.mgd.blackgroove.utils;

import java.io.IOException;

import javafx.scene.control.Button;
import pl.mgd.blackgroove.controller.UpdateDigitalSourcePaneController;
import pl.mgd.blackgroove.data.Release;
import pl.mgd.blackgroove.data.VinylRelease;


public class UpdateDigitalSourceWindow {

	/*--Fields--*/
	private final String WINDOW_FXML_PATH = "/pl/mgd/blackgroove/view/UpdateDigitalSourcePane.fxml";
	private final String WINDOW_TITLE = "Update Digital Source";

	private NewWindowGenerator newUpdateDigitalSourceWindow;
	private UpdateDigitalSourcePaneController updateDigitalSourcePaneController;
	private boolean confirmationValue;
	
	/*--Javabeans--*/
	public boolean getConfirmationValue() {
		return confirmationValue;
	}
	public void setConfirmationValue(boolean confirmationValue) {
		this.confirmationValue = confirmationValue;
	}
	
	/*--Constructors--*/	
	public UpdateDigitalSourceWindow() throws IOException {
		newUpdateDigitalSourceWindow = new NewWindowGenerator();
		newUpdateDigitalSourceWindow.createStage(WINDOW_TITLE, WINDOW_FXML_PATH);
		updateDigitalSourcePaneController = newUpdateDigitalSourceWindow.getFxmlLoader().getController();
	}

	/*--Methods--*/	
	private void show(Release actualRelease) {
		commitButtonConfiguration();
		readActualObjectField(actualRelease);
		newUpdateDigitalSourceWindow.show();	
	}
	
	public boolean showAndReturnConfirmation(Release actualRelease) {
		show(actualRelease);
		return getConfirmationValue();
	}
	
	public void readActualObjectField(Release actualRelease) {
		updateDigitalSourcePaneController.setUrlDigitalLinkTextField(((VinylRelease)actualRelease).getDigitalVersionLink());
		updateDigitalSourcePaneController.setPasswordDigitalLink(((VinylRelease)actualRelease).getDigitalPassword());
	}
	
	public void commitButtonConfiguration() {
		Button commitButton = updateDigitalSourcePaneController.getCommitButton();
		commitButton.setOnAction(event -> {
			System.out.println("Wywo³anie przycisku");
			setConfirmationValue(true);
			newUpdateDigitalSourceWindow.close();
		});
	}
	
	public Release createUpdateRelease(Release editRelease) {
		((VinylRelease)editRelease).setDigitalVersionLink(updateDigitalSourcePaneController.getUrlDigitalLinkText());
		((VinylRelease)editRelease).setDigitalPassword(updateDigitalSourcePaneController.getPasswordDigitalLinkText());
		return editRelease;
	}
}
