package pl.mgd.blackgroove.data;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class VinylRelease extends Release {

	/*--Fields--*/
	private ObjectProperty<VinylSize> vinylSize;
	private StringProperty digitalVersionLink;
	private StringProperty digitalPassword;
	
	/*--Javabeans--*/
	public VinylSize getVinylSize() {
		return vinylSize.get();
	}
	
	public void setVinylSize(VinylSize vinylSize) {
		this.vinylSize.set(vinylSize);
	}
	
	public ObjectProperty<VinylSize> vinylSizeProperty() {
		return vinylSize;
	}
	
	public void setDigitalVersionLink(String digitalVersionLink) {
		this.digitalVersionLink.setValue(digitalVersionLink);
	}
	
	public String getDigitalVersionLink( ) {
		return digitalVersionLink.get();
	}
	
	public StringProperty digitalVersionLinkProperty() {
		return digitalVersionLink;
	}
	
	public void setDigitalPassword(String digitalPassword) {
		this.digitalPassword.setValue(digitalPassword);
	}
	
	public String getDigitalPassword( ) {
		return digitalPassword.get();
	}
	
	public StringProperty digitalPasswordProperty() {
		return digitalPassword;
	}

	/*--Contructors--*/
	public VinylRelease() {
		super();
		this.vinylSize = new SimpleObjectProperty<VinylSize>(VinylSize.LP);
		digitalVersionLink = new SimpleStringProperty();
		digitalPassword = new SimpleStringProperty();

	}
	
	public VinylRelease(Release release) {
		super(release);
		this.vinylSize = new SimpleObjectProperty<VinylSize>(VinylSize.LP);
		setFormat(release.getFormat());
		digitalVersionLink = new SimpleStringProperty(((VinylRelease) release).getDigitalVersionLink());
		digitalPassword = new SimpleStringProperty(((VinylRelease) release).getDigitalPassword());
	}
	
	public VinylRelease(int id, String artist, String album, String label, String format, Integer releaseData, String size, String digitalVersionLink, String digitalPassword) {
		super(id, artist, album, label, format, releaseData);
		this.vinylSize = new SimpleObjectProperty<VinylSize>(VinylSize.createVinylSizeFromString(size));
		this.digitalVersionLink = new SimpleStringProperty(digitalVersionLink);
		this.digitalPassword = new SimpleStringProperty(digitalPassword);
	}
	
	/*--Methods--*/	
	@Override
	public String toString() {
		StringBuilder releaseTextBuilder = new StringBuilder();
		releaseTextBuilder.append(super.toString().replaceAll("version.", "version, "));
		releaseTextBuilder.append(this.getVinylSize().toString());
		releaseTextBuilder.append(", ");
		releaseTextBuilder.append(this.getDigitalVersionLink());
		releaseTextBuilder.append(", ");
		releaseTextBuilder.append(this.getDigitalPassword());
		return releaseTextBuilder.toString();
	}
}
