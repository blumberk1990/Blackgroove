package pl.mgd.blackgroove.utils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import pl.mgd.blackgroove.data.Release;
import pl.mgd.blackgroove.data.VinylRelease;

public class Discogs {

	/*--Fields--*/
	private final String DISCOGS_URL = "https://www.discogs.com";
	private final String DISCOGS_SEARCH_URL = "https://www.discogs.com/search/";
	private final String SEARCH_PARAMETER = "q";
	private final String FINAL_PARAMETER = "type";  
	private final String DIV_NODES_PARAMETER = "div.aside_left.aside_left.aside_more_narrow.with_tabs.aside_off_canvas";   
	private final String DIV_NODES_PARAMETER_2 = "div.card.card_large.float_fix.shortcut_navigable";
	private final String IMAGE_GALLERY = "div.image_gallery";
	private final String TABLE_NODES = "div.section.tracklist";
	private final String TABLE_ROW_NODES = "tr.tracklist_track.track";
	private final String TRACK_SEITE = "td.tracklist_track_pos";
	private final String TRACK_TITLE = "td.track.tracklist_track_title";
	private final String TRACK_DURATION = "td.tracklist_track_duration";
	
	private Release release;
	private String imageUrl;
	private String genre;
	private String style;
	private ObservableList<Track> trackList;
	
	/*--Javabeans--*/
	public Release getRelease() {
		return release;
	}

	public void setRelease(Release release) {
		this.release = release;
	}
	
	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	
	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}
	
	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}
	
	public ObservableList<Track> getTrackList() {
		return trackList;
	}
	public void setTrackList(ObservableList<Track> trackList) {
		this.trackList = trackList;
	}
	
	/*--Constructors--*/
	public Discogs() {
		release = new Release();
		imageUrl = new String();
		trackList = FXCollections.observableArrayList();
	}
	
	public Discogs(Release release) {
		this.release = release;
	}
	
	public Discogs(Discogs discogs) {
		this.release = discogs.release;
		this.imageUrl = discogs.imageUrl;
		this.trackList = discogs.trackList;
	}
	
	/*--Methods--*/
	/**
	 *
	 * Function which prepare all content for details page
	 * @since             			1.0
	 */
	public void search() {
		try {
			this.search(release);
		} catch (URISyntaxException | IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 *
	 * @param release				Release which have to be found
	 * @since             			1.0
	 */
	private void search(Release release) throws URISyntaxException, IOException {
		String searchPageContent = searchPageSource(release);	
		String detailsPageContent = releaseDetailsPageSource(searchPageContent);
		collectDetailsFromPageContent(detailsPageContent);
	}
	
	/**
	 *
	 * @param release				Release which have to be found
	 * @return            			String with search details page content
	 * @since             			1.0
	 */
	private String searchPageSource(Release release) throws URISyntaxException, IOException {
		URI searchUri = generateSearchPageURI(generateQueryfromRelease(release));
		return getUriPageSource(searchUri);
	}

	/**
	 *
	 * @param query	 	  			string from which  have to be generated search URI
	 * @return            			URI to Discogs search page
	 * @throws URISyntaxException
	 * @since             			1.0
	 */
	private URI generateSearchPageURI(String query) throws URISyntaxException {		
		return new URIBuilder(DISCOGS_SEARCH_URL).setParameter(SEARCH_PARAMETER, query)
				.setParameter(FINAL_PARAMETER, "release")
				.build();
	}
	
	/**
	 *
	 * @param release 	  			Release from which is generated pattern for query
	 * @return            			String with query pattern
	 * @since             			1.0
	 */
	private String generateQueryfromRelease(Release release) {		
		return release.getArtist() + " - " + release.getAlbum() + ", " + release.getLabel() + ", " + release.getReleaseData() + ", " + ((VinylRelease) release).getVinylSize().name();
	}
	
	/**
	 *
	 * @param searchUri	  			URI from which a content has to be fetched
	 * @return            			Page content
	 * @since             			1.0
	 */
	private String getUriPageSource(URI searchUri) throws IOException {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet httpget = new HttpGet(searchUri);
		CloseableHttpResponse response = null;
		HttpEntity entity = null;
		String pageContent = null;
		try {
			response = httpClient.execute(httpget);
			entity = response.getEntity();
			pageContent = EntityUtils.toString(entity);
		} catch (ClientProtocolException e) {
           e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}  finally {
           response.close();
           httpClient.close();
       }
		return pageContent;
	}
	
	/**
	 *
	 * @param searchPageContent		content of search page, which is base for fetch data
	 * @return            			String with release details page content
	 * @since             			1.0
	 */
	public String releaseDetailsPageSource(String searchPageContent) throws URISyntaxException, IOException {
		URI detailsPageUri = generateReleaseDetailsPageURI(searchPageContent);
		return getUriPageSource(detailsPageUri);
	}
	
	/**
	 *
	 * @param searchPageContent	 	search page content from which have to be scraped URI to details page
	 * @return            			URI to Discogs search page
	 * @throws URISyntaxException 
	 * @since             			1.0
	 */
	private URI generateReleaseDetailsPageURI(String searchPageContent) throws URISyntaxException {
		Document doc = Jsoup.parse(searchPageContent);
		String releaseIdDetails = doc.select(DIV_NODES_PARAMETER)
									.select(DIV_NODES_PARAMETER_2)
									.select("a[href]").attr("href");
		releaseIdDetails = DISCOGS_URL + releaseIdDetails;
		return new URI(releaseIdDetails);
	}

	/**
	 *
	 * @param detailsPageContent 	details page content from which have to be scraped details page
	 * @since             			1.0
	 */
	private void collectDetailsFromPageContent(String detailsPageContent) {
		Document doc = Jsoup.parse(detailsPageContent);
		setImageUrl(parseImageUrl(doc));
		Elements elements = fetchProfileSection(doc);
		setGenre(parseGenre(elements));
		setStyle(parseStyle(elements));
		setTrackList(parseTrackList(doc));
	}

	/**
	 *
	 * @param doc				 	Document from which a content has to be fetched
	 * @since             			1.0
	 */
	private String parseImageUrl(Document doc) {
		String imageUrlElement = doc.selectFirst(IMAGE_GALLERY)
									.select("[src]")
									.first()
									.attr("abs:src");
		this.setImageUrl(imageUrlElement);
		return imageUrlElement;
	}
	
	/**
	 *
	 * @param elements			 	Elements from which a content has to be fetched
	 * @return            			String with Genre
	 * @since             			1.0
	 */
	private String parseGenre(Elements elements) {
		StringBuilder genre = new StringBuilder();
		for(Element row : elements.select("div[itemprop=genre]").select("a[href]")) {
			genre.append(row.text());
			genre.append(", ");
		}
		return genre.delete(genre.length()-2, genre.length()).toString();
	}
	
	/**
	 *
	 * @param elements			 	Elements from which a content has to be fetched
	 * @return            			String with Style
	 * @since             			1.0
	 */
	private String parseStyle(Elements elements) {
		return elements.get(elements.size()-1).text();
	}

	/**
	 *
	 * @param doc				 	Document from which a content has to be fetched
	 * @return            			Elements with section data
	 * @since             			1.0
	 */
	private Elements fetchProfileSection(Document doc) {
		return doc.select("div.profile").select("div.content");
	}
	
	/**
	 *
	 * @param doc	  				Document from which a content has to be fetched
	 * @return            			ObservableList<Track> of the release
	 * @since             			1.0
	 */
	private ObservableList<Track> parseTrackList(Document doc) {
		Elements trackListTable = doc.select(TABLE_NODES).select(TABLE_ROW_NODES);
		ObservableList<Track> trackList = FXCollections.observableArrayList();
		for(Element row : trackListTable) {
			String seitePosition = row.select(TRACK_SEITE).text();
			String trackTitle = row.select(TRACK_TITLE).text();
			String trackDuration = row.select(TRACK_DURATION).text();
			Discogs.Track track = new Discogs.Track(seitePosition, trackTitle, trackDuration);
			trackList.add(track);
		}
		return trackList;
	}

	
	/*--Inner Class--*/
	public class Track {
		
		/*--Fields--*/
		private StringProperty number;
		private StringProperty title;
		private StringProperty duration;
		
		/*--Javabeans--*/
		public String getNumber() {
			return number.get();
		}
		public StringProperty numberProperty() {
			return number;
		}
		public void setNumber(String number) {
			this.number.setValue(number);
		}
			
		public String getTitle() {
			return title.get();
		}
		public StringProperty titleProperty() {
			return title;
		}
		public void setTitle(String title) {
			this.title.setValue(title);
		}
		
		public String getDuration() {
			return duration.get();
		}
		public StringProperty durationProperty() {
			return duration;
		}
		public void setDuration(String duration) {
			this.duration.setValue(duration);
		}
		
		/*--Constructors--*/
		private Track(String number, String title, String duration) {
			this.number = new SimpleStringProperty(number);
			this.title = new SimpleStringProperty(title);
			this.duration = new SimpleStringProperty(duration);
		}
		
		/*--Methods--*/
		@Override
		public String toString() {
			// TODO Auto-generated method stub
			return this.getNumber() + ", " + this.getTitle() + ", " + this.getDuration();
		}
		
	}
}
