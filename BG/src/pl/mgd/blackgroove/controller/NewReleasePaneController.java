package pl.mgd.blackgroove.controller;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.util.converter.IntegerStringConverter;
import pl.mgd.blackgroove.data.VinylSize;

public class NewReleasePaneController implements Initializable {

	/*--Fields--*/
	@FXML
    private Label newReleaseWindowLabel;

    @FXML
    private Label artistLabel;

    @FXML
    private TextField artistTextField;

    @FXML
    private Label albumTitleLabel;

    @FXML
    private TextField albumTitleTextField;

    @FXML
    private Label labelLabel;

    @FXML
    private TextField labelTextField;

    @FXML
    private Label releaseDataLabel;

    @FXML
    private TextField releaseDataTextField;

    @FXML
    private Label sizeLabel;

    @FXML
    private ComboBox<String> sizeComboBox;

    @FXML
    private Label digitalVersionLinkLabel;

    @FXML
    private TextField digitalVersionLinkTextField;

    @FXML
    private Label digitalPasswordLabel;

    @FXML
    private TextField digitalPasswordTextField;

    @FXML
    private Button addNewReleaseButton;
    
    @FXML
    private Label informationLabel;
    
    /*--Javabeans--*/
	public TextField getArtistTextField() {
		return artistTextField;
	}
	
	public String getArtistText() {
		return artistTextField.getText();
	}

	public void setArtistTextField(TextField artistTextField) {
		this.artistTextField = artistTextField;
	}
	
	public TextField getAlbumTitleTextField() {
		return albumTitleTextField;
	}
	
	public String getAlbumTitleText() {
		return albumTitleTextField.getText();
	}

	public void setAlbumTitleTextField(TextField albumTitleTextField) {
		this.albumTitleTextField = albumTitleTextField;
	}
	
	public TextField getLabelTextField() {
		return labelTextField;
	}
	
	public String getLabelText() {
		return labelTextField.getText();
	}

	public void setLabelTextField(TextField labelTextField) {
		this.labelTextField = labelTextField;
	}
	
	public Integer getReleaseData() {
		return new Integer(getReleaseDataText());
	}
	
	public TextField getReleaseDataTextField() {
		return releaseDataTextField;
	}
	
	public String getReleaseDataText() {
		return releaseDataTextField.getText();
	}

	public void setReleaseDataTextField(TextField releaseDataTextField) {
		this.releaseDataTextField = releaseDataTextField;
	}
	
	public String getSize() {
		return sizeComboBox.getValue();
	}
	
	public VinylSize getVinylSize() {
		return VinylSize.createVinylSizeFromString(this.getSize());
	}
	
	public ComboBox<String> getSizeComboBox() {
		return sizeComboBox;
	}

	public void setSizeComboBox(ComboBox<String> sizeComboBox) {
		this.sizeComboBox = sizeComboBox;
	}
	
	public TextField getDigitalVersionLinkTextField() {
		return digitalVersionLinkTextField;
	}
	
	public String getDigitalVersionLinkText() {
		return digitalVersionLinkTextField.getText();
	}

	public void setDigitalVersionLinkTextField(TextField digitalVersionLinkTextField) {
		this.digitalVersionLinkTextField = digitalVersionLinkTextField;
	}

	public TextField getDigitalPasswordTextField() {
		return digitalPasswordTextField;
	}
	
	public String getDigitalPasswordText() {
		return digitalPasswordTextField.getText();
	}

	public void setDigitalPasswordTextField(TextField digitalPasswordTextField) {
		this.digitalPasswordTextField = digitalPasswordTextField;
	}
	
    public Button getAddNewReleaseButton() {
		return addNewReleaseButton;
	}

	public void setAddNewReleaseButton(Button addNewReleaseButton) {
		this.addNewReleaseButton = addNewReleaseButton;
	}
	
	public Label getInformationLabel() {
		return informationLabel;
	} 

	public void setInformationLabel(String informationLabel) {
		this.informationLabel.setText(informationLabel);
	}
	
	/*--Methods--*/
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		sizeComboBoxConfiguration();
		releaseDataTextFilter();
		addNewReleaseButtonEnable();
		configurationInformationLabel();
	}
	
	private void sizeComboBoxConfiguration() {
		ObservableList<String> sizeList = FXCollections.observableArrayList();
		for(VinylSize v : VinylSize.values()) {
			sizeList.add(v.getDescription());
		}
		sizeComboBox.setItems(sizeList);
		sizeComboBox.setValue(VinylSize.values()[0].toString());
	}
    
	private void releaseDataTextFilter() {
		TextFormatter<Integer> formatter = new TextFormatter<>(
				new IntegerStringConverter(),
				1957,
				c -> Pattern.matches("\\d*", c.getText()) ? c : null);
		releaseDataTextField.setTextFormatter(formatter);
		releaseDataTextField.clear();
	}
	
	private void addNewReleaseButtonEnable() {		
		addNewReleaseButton.disableProperty().set(true);
		addNewReleaseButton.disableProperty().bind(Bindings.isEmpty(artistTextField.textProperty())
				.or(Bindings.isEmpty(albumTitleTextField.textProperty())));
	}
	
	private void configurationInformationLabel() {
		this.setInformationLabel("*Artist and Title are required.");
		
		this.artistTextField.textProperty().addListener((observable, oldValue, newValue) -> {
		if(getArtistText().isEmpty() && getAlbumTitleText().isEmpty()) {
			this.setInformationLabel("*Artist and Title are required.");
		} else if(getArtistText().isEmpty() && !getAlbumTitleText().isEmpty()) {
			this.setInformationLabel("*Artist is required.");
		} else if(!getArtistText().isEmpty() && getAlbumTitleText().isEmpty()) {
			this.setInformationLabel("*Title is required.");
		} else if(!getArtistText().isEmpty() && !getAlbumTitleText().isEmpty()) {
			this.setInformationLabel("");
		}
		});
		
		this.albumTitleTextField.textProperty().addListener((observable, oldValue, newValue) -> {
		if(getArtistText().isEmpty() && getAlbumTitleText().isEmpty()) {
			this.setInformationLabel("*Artist and Title are required.");
		} else if(getArtistText().isEmpty() && !getAlbumTitleText().isEmpty()) {
			this.setInformationLabel("*Artist is required.");
		} else if(!getArtistText().isEmpty() && getAlbumTitleText().isEmpty()) {
			this.setInformationLabel("*Title is required.");
		} else if(!getArtistText().isEmpty() && !getAlbumTitleText().isEmpty()) {
			this.setInformationLabel("");
		}
		});
	}
	
}
