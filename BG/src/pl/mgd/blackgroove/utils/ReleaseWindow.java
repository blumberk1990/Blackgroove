package pl.mgd.blackgroove.utils;

import java.io.IOException;

import javafx.scene.control.Button;
import pl.mgd.blackgroove.controller.NewReleasePaneController;
import pl.mgd.blackgroove.data.DigitalRelease;
import pl.mgd.blackgroove.data.Release;
import pl.mgd.blackgroove.data.VinylRelease;

public class ReleaseWindow {

	/*--Fields--*/
	private final String FORMAT_VINYL = "Vinyl";
	//private final String DIGITAL_FORMAT = "Digital";
	private final String FORMAT_VINYL_DIGITAL = "Vinyl + Digital";
	
	private final String WINDOW_FXML_PATH = "/pl/mgd/blackgroove/view/NewReleasePane.fxml";
	private final String WINDOW_TITLE = "Add Release";
	private NewWindowGenerator newReleaseWindow;
	private NewReleasePaneController newReleasePaneController;
	private boolean newReleaseButtonPressed;
			
	/*--Javabeans--*/
	public boolean getNewReleaseButtonPressed() {
		return newReleaseButtonPressed;
	}

	public void setNewReleaseButtonPressed(boolean newReleaseButtonPressed) {
		this.newReleaseButtonPressed = newReleaseButtonPressed;
	}
	
	/*--Constructors--*/
	public ReleaseWindow() throws IOException {
		newReleaseWindow = new NewWindowGenerator();
		newReleaseWindow.createStage(WINDOW_TITLE, WINDOW_FXML_PATH);
		newReleasePaneController = newReleaseWindow.getFxmlLoader().getController();
	}
	
	/*--Methods--*/	
	public void show() {
		addNewReleaseButtonHandler();
		newReleaseWindow.show();
	}
	
	public int specifyNewRelease() {
		int specifyTyp;
		if(specifyFormat().equals(FORMAT_VINYL)) {
			specifyTyp = 1;
		} else {
			specifyTyp = 2;
		}
		return specifyTyp;
	}
	
	private String specifyFormat() {
		String format;
		if(newReleasePaneController.getDigitalVersionLinkText().isEmpty() && newReleasePaneController.getDigitalPasswordText().isEmpty()) {
			format = FORMAT_VINYL;
		} else {
			format = FORMAT_VINYL_DIGITAL;
		}
		return format;
	}
	
	public Release readAndCreateVinyl() {
		Release newRelease = new VinylRelease();
		newRelease.setArtist(newReleasePaneController.getArtistText());
		newRelease.setAlbum(newReleasePaneController.getAlbumTitleText());
		newRelease.setLabel(newReleasePaneController.getLabelText());
		newRelease.setReleaseData(newReleasePaneController.getReleaseDataText());
		((VinylRelease)newRelease).setVinylSize(newReleasePaneController.getVinylSize());
		((VinylRelease)newRelease).setDigitalVersionLink(newReleasePaneController.getDigitalVersionLinkText());
		((VinylRelease)newRelease).setDigitalPassword(newReleasePaneController.getDigitalPasswordText());
		newRelease.setFormat(specifyFormat());
		return newRelease;
	}
	
	public Release readAndCreateDigital() {
		Release newRelease = new DigitalRelease();
		newRelease.setArtist(newReleasePaneController.getArtistText());
		newRelease.setAlbum(newReleasePaneController.getAlbumTitleText());
		newRelease.setLabel(newReleasePaneController.getLabelText());
		newRelease.setReleaseData(newReleasePaneController.getReleaseDataText());
		((DigitalRelease)newRelease).setDigitalVersionLink(newReleasePaneController.getDigitalVersionLinkText());
		((DigitalRelease)newRelease).setDigitalPassword(newReleasePaneController.getDigitalPasswordText());
		newRelease.setFormat(specifyFormat());
		return newRelease;
	}
	
	private void addNewReleaseButtonHandler() {
		Button addNewReleaseButton = newReleasePaneController.getAddNewReleaseButton();
		addNewReleaseButton.setOnAction(e -> {
			setNewReleaseButtonPressed(true);
			newReleaseWindow.close();
		});
	}
	
	public void clearField() {
		System.out.println("Cleaning");
		newReleasePaneController.getArtistTextField().clear();
		newReleasePaneController.getAlbumTitleTextField().clear();
		newReleasePaneController.getLabelTextField().clear();
		newReleasePaneController.getReleaseDataTextField().clear();
		newReleasePaneController.getDigitalVersionLinkTextField().clear();
		newReleasePaneController.getDigitalPasswordTextField().clear();
	}
	
}
