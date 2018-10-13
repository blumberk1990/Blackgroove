package pl.mgd.blackgroove.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import pl.mgd.blackgroove.controller.ReleaseDetailsPaneController;
import pl.mgd.blackgroove.controller.ReleaseLibraryPaneController;
import pl.mgd.blackgroove.data.Release;

public class ContentSwapping {

	/*--Fields--*/
	private final String RELEASE_LIBRARY_PANE = "/pl/mgd/blackgroove/view/ReleaseLibraryPane.fxml";
	private final String RELEASE_DETAILS_PANE = "/pl/mgd/blackgroove/view/ReleaseDetailsPane.fxml";
	private BorderPane actualContentPane;
	private List<BorderPane> availibleContentPage;
	private ReleaseLibraryPaneController releaseLibraryPaneController;
	private ReleaseDetailsPaneController releaseDetailsPaneController;
	
	/*--Javabeans--*/
	public ReleaseLibraryPaneController getReleaseLibraryPaneController() {
		return releaseLibraryPaneController;
	}
	
	public void setReleaseLibraryPaneController(Object releaseLibraryPaneController) {
		this.releaseLibraryPaneController = (ReleaseLibraryPaneController) releaseLibraryPaneController;
	}
	
	public ReleaseDetailsPaneController getReleaseDetailsPaneController() {
		return releaseDetailsPaneController;
	}
	
	public void setReleaseDetailsPaneController(Object releaseDetailsPaneController) {
		this.releaseDetailsPaneController = (ReleaseDetailsPaneController) releaseDetailsPaneController;
	}

	/*--Constructors--*/
	public ContentSwapping(BorderPane actualContentPane) {
		this.actualContentPane = actualContentPane;
		availibleContentPage = new ArrayList<>();
		try {
			availibleContentPage();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/*--Methods--*/
	
	/**
	 * Function for initialization available page type in content window.
	 * For initialization have to be called new function contentTypePageLoader with Path of new contentPage *.fxml file.
	 * @throws IOException 
	 */
	private void availibleContentPage() throws IOException {
		availibleContentPage.add(contentTypePageLoader(RELEASE_LIBRARY_PANE));
		availibleContentPage.add(contentTypePageLoader(RELEASE_DETAILS_PANE));

	}
	
	/**
	 * Function for loading page to program structure.
	 * 
	 * @param path			Path to *.fxml file.
	 * @throws IOException 
	 * @return            	Content pane (BorderPane) 
	 */
	private BorderPane contentTypePageLoader(String path) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader();
		BorderPane loadedPage = (BorderPane) fxmlLoader.load(getClass().getResource(path).openStream());
		setContentPageController(path, fxmlLoader.getController());
		return loadedPage;
	}

	/**
	 * Function for setting available page type controller relevant to the path and controller.
	 * Function have to be expanded in the case of adding new Page type.
	 * @param path			Path to *.fxml file.
	 * @param controller	Controller of existing page type, relevant to given path param.
	 */
	private void setContentPageController(String path, Object controller) {
		StringBuilder controllerSelector = new StringBuilder(path);
		controllerSelector.delete(0, controllerSelector.indexOf("/view/") + 6);
		controllerSelector.delete(controllerSelector.length() - 5, controllerSelector.length());
		switch(controllerSelector.toString()) {
			case "ReleaseLibraryPane" :
				setReleaseLibraryPaneController(controller);
				System.out.println("Setting controller in ContentSwapper" + controller);
				break;
			case "ReleaseDetailsPane" :
				setReleaseDetailsPaneController(controller);
				System.out.println("Setting controller in ContentSwapper" + controller);
				break;
		}
		
	}
	
	/**
	 * Function for swapping main content page
	 * @param loadedPage		pane which has to be swapped in place of content page.
	 */
	private void mainContentSwitcher(BorderPane loadedPage) throws IOException {		
		List<Node> parentChildren = ((Pane)this.actualContentPane.getParent()).getChildren();
		parentChildren.set(parentChildren.indexOf(this.actualContentPane), loadedPage);
		this.actualContentPane = (BorderPane) loadedPage;	
		//this.actualContentPane.setPrefWidth(5000);
		this.actualContentPane.setPrefHeight(5000);

	}
	
	public void loadReleaseLibraryContentPane(ObservableList<Release> content) {
		try {
			mainContentSwitcher(availibleContentPage.get(0));
		} catch (IOException e) {
			e.printStackTrace();
		}
		releaseLibraryPaneController.showLibraryContent(content);
	}
	
	public void loadReleaseDetailsContentPane(Discogs discogs) {
		try {
			mainContentSwitcher(availibleContentPage.get(1));
			releaseDetailsPaneController.showDetailsContent(discogs);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
