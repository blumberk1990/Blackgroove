package pl.mgd.blackgroove.data;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Release {

	/*--Fields--*/
	private IntegerProperty id;
	private StringProperty artist;
	private StringProperty album;
	private StringProperty label;
	private StringProperty format;
	private IntegerProperty releaseData;
	
	/*--Javabeans--*/
	public void setId(int id) {
		this.id.set(id);
	}
	
	public int getId( ) {
		return id.get();
	}
	
	public IntegerProperty idProperty() {
		return id;
	}
	
	public void setArtist(String artistName) {
		this.artist.setValue(artistName);
	}
	
	public String getArtist( ) {
		return artist.get();
	}
	
	public StringProperty artistProperty() {
		return artist;
	}
	
	public void setAlbum(String album) {
		this.album.setValue(album);
	}
	
	public String getAlbum() {
		return album.get();
	}
	
	public StringProperty albumProperty() {
		return album;
	}
	
	public void setLabel(String label) {
		this.label.setValue(label);
	}
	
	public String getLabel() {
		return label.get();
	}
	
	public StringProperty labelProperty() {
		return label;
	}
	
	public void setFormat(String format) {
		this.format.setValue(format);
	}
	
	public String getFormat() {
		return format.get();
	}
	
	public StringProperty formatProperty() {
		return format;
	}
	
	public void setReleaseData(Integer releaseData) {
		this.releaseData.setValue(releaseData);
	}
	
	public void setReleaseData(String releaseData) {
		if(!releaseData.equals(new String())) {
			this.releaseData.setValue(new Integer(releaseData));
		}
	}
	
	public int getReleaseData() {
		return releaseData.get();
	}
	
	public IntegerProperty releaseDataProperty() {
		return releaseData;
	}

	/*--Contructors--*/
	public Release() {
		id = new SimpleIntegerProperty();
		artist = new SimpleStringProperty();
		album = new SimpleStringProperty();
		label = new SimpleStringProperty();
		format = new SimpleStringProperty();
		releaseData = new SimpleIntegerProperty();
	}
	
	public Release(Release release) {
		this.id = new SimpleIntegerProperty(release.getId());
		this.artist = new SimpleStringProperty(release.getArtist());
		this.album = new SimpleStringProperty(release.getAlbum());
		this.label = new SimpleStringProperty(release.getLabel());
		this.format = new SimpleStringProperty();
		this.releaseData = new SimpleIntegerProperty(release.getReleaseData());
	}
	
	public Release(int id, String artist, String album, String label, String format, int releaseData) {
		this.id = new SimpleIntegerProperty(id);
		this.artist = new SimpleStringProperty(artist);
		this.album = new SimpleStringProperty(album);
		this.label = new SimpleStringProperty(label);
		this.format = new SimpleStringProperty(format);
		this.releaseData = new SimpleIntegerProperty(releaseData);
	}

	/*--Methods--*/
	@Override
	public String toString() {
		StringBuilder releaseTextBuilder = new StringBuilder();
		releaseTextBuilder.append("ID: ");
		releaseTextBuilder.append(this.getId());
		releaseTextBuilder.append(": ");
		releaseTextBuilder.append(this.getArtist());
		releaseTextBuilder.append(" - ");
		releaseTextBuilder.append(this.getAlbum());
		releaseTextBuilder.append(", ");
		releaseTextBuilder.append(this.getLabel());
		releaseTextBuilder.append(", ");
		releaseTextBuilder.append(this.getReleaseData());
		releaseTextBuilder.append(", ");
		releaseTextBuilder.append(this.getFormat());
		releaseTextBuilder.append(" version.");
		return releaseTextBuilder.toString();
	}

}
