package pl.mgd.blackgroove.data;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class DigitalRelease extends Release {

	/*--Fields--*/
	private StringProperty digitalVersionLink;
	private StringProperty digitalPassword;
	
	/*--Javabeans--*/
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
	public DigitalRelease() {
		super();
		digitalVersionLink = new SimpleStringProperty();
		digitalPassword = new SimpleStringProperty();
	}
	
	public DigitalRelease(Release release) {
		super(release);
		digitalVersionLink = new SimpleStringProperty(((DigitalRelease) release).getDigitalVersionLink());
		digitalPassword = new SimpleStringProperty(((DigitalRelease) release).getDigitalPassword());
		setFormat(release.getFormat());
	}
	
	public DigitalRelease(int id, String artist, String album, String label, String format, Integer releaseData, String digitalVersionLink, String digitalPassword) {
		super(id, artist, album, label, format, releaseData);
		this.digitalVersionLink = new SimpleStringProperty(digitalVersionLink);
		this.digitalPassword = new SimpleStringProperty(digitalPassword);
	}
	
	/*--Methods--*/	
	@Override
	public String toString() {
		StringBuilder releaseTextBuilder = new StringBuilder();
		releaseTextBuilder.append(super.toString().replaceAll("version.", "version, "));
		releaseTextBuilder.append(", ");
		releaseTextBuilder.append(this.getDigitalVersionLink());
		releaseTextBuilder.append(", ");
		releaseTextBuilder.append(this.getDigitalPassword());
		return releaseTextBuilder.toString();
	}
	
}
