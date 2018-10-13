package pl.mgd.blackgroove.utils;

import java.io.IOException;

public class AboutWindow {

	/*--Fields--*/
	private final String WINDOW_FXML_PATH = "/pl/mgd/blackgroove/view/AboutPane.fxml";
	private final String WINDOW_TITLE = "About BlackGroove";
	private NewWindowGenerator aboutWindow;
	
	/*--Javabeans--*/
	
	/*--Constructors--*/
	public AboutWindow() throws IOException {
		aboutWindow = new NewWindowGenerator();
		aboutWindow.createStage(WINDOW_TITLE, WINDOW_FXML_PATH);
	}
	
	/*--Methods--*/	
	public void show() {
		aboutWindow.show();
	}
	
}
