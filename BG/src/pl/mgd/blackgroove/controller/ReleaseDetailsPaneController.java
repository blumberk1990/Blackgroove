package pl.mgd.blackgroove.controller;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;

import javafx.fxml.Initializable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import pl.mgd.blackgroove.utils.Discogs;

public class ReleaseDetailsPaneController implements Initializable{

	/*--Fields--*/
	public static final String NUMBER_COLUMN = "Side";
	public static final String TITLE_COLUMN = "Title";
	public static final String DURATION_COLUMN = "Time";
	
    @FXML
    private BorderPane releaseDetailsPane;

    @FXML
    private TableView<Discogs.Track> trackListTableView;

    @FXML
    private ImageView albumCoverImageView;

    @FXML
    private Label artistLabel;

    @FXML
    private Label albumLabel;

    @FXML
    private Label labelLabel;

    @FXML
    private Label yearLabel;

    @FXML
    private Label genreLabel;
    
    @FXML
    private Label styleLabel;

    @FXML
    private ImageView formatIconImageView;

    @FXML
    private Label formatLabel;
    
	/*--Javabeans--*/
    public void setArtistLabel(String artist) {
		this.artistLabel.setText(artist);
	}
	
	public void setAlbumLabel(String album) {
		this.albumLabel.setText(album);
	}
	
	public void setLabelLabel(String label) {
		this.labelLabel.setText(label);
	}
	
	public void setYearLabel(String released) {
		this.yearLabel.setText(released);
	}
	
	public void setGenreLabel(String genre) {
		this.genreLabel.setText(genre);
	}
	
	public void setStyleLabel(String style) {
		this.styleLabel.setText(style);
	}
	
	public void setFormatLabel(String format) {
		this.formatLabel.setText(format);
	}

	/*--Methods--*/
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		configureTrackListTableView();
		
	}
	
	private void configureTrackListTableView() {
		TableColumn<Discogs.Track, String> numberColumn = new TableColumn<Discogs.Track, String>(NUMBER_COLUMN);
		numberColumn.setCellValueFactory(new PropertyValueFactory<>("number"));
		TableColumn<Discogs.Track, String> titleColumn = new TableColumn<Discogs.Track, String>(TITLE_COLUMN);
		titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
		TableColumn<Discogs.Track, String> durationColumn = new TableColumn<Discogs.Track, String>(DURATION_COLUMN);
		durationColumn.setCellValueFactory(new PropertyValueFactory<>("duration"));
		
		trackListTableView.getColumns().add(numberColumn);
		trackListTableView.getColumns().add(titleColumn);
		trackListTableView.getColumns().add(durationColumn);
	}
	
	public void showDetailsContent(Discogs discogs) throws IOException {
		URL imageUrl = new URL(discogs.getImageUrl());
		BufferedImage bufferedImageAlbumCover = ImageIO.read(imageUrl);
		Image imageAlbumCover = SwingFXUtils.toFXImage(bufferedImageAlbumCover, null);
		albumCoverImageView.setImage(imageAlbumCover);
		
		ObservableList<Discogs.Track> trackList = FXCollections.observableArrayList();
		trackList.setAll(discogs.getTrackList());
		this.trackListTableView.setItems(trackList);
		
		this.setArtistLabel(discogs.getRelease().getArtist());
		this.setAlbumLabel(discogs.getRelease().getAlbum());
		this.setLabelLabel(discogs.getRelease().getLabel());
		this.setFormatLabel(discogs.getRelease().getFormat());
		this.setYearLabel(discogs.getRelease().releaseDataProperty().getValue().toString());
		this.setGenreLabel(discogs.getGenre());
		this.setStyleLabel(discogs.getStyle());
	}

}
