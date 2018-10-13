package pl.mgd.blackgroove.utils;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import pl.mgd.blackgroove.main.Main;

public class NewWindowGenerator {
	
	/*--Fields--*/
	private FXMLLoader fxmlLoader;
	private Parent scene;
	private Stage newStage;
	
	/*--Javabeans--*/
	public FXMLLoader getFxmlLoader() {
		return fxmlLoader;
	}
	
	public Stage getNewStage() {
		return newStage;
	}
	
	/*--Constructors--*/
	public NewWindowGenerator() {
		fxmlLoader = new FXMLLoader();
		newStage = new Stage();
	}
	
	/*--Methods--*/
	public void createStage(String windowTitle, String windowFxmlFile) throws IOException {
		fxmlLoader = new FXMLLoader(getClass().getResource(windowFxmlFile));
		scene = (Parent) fxmlLoader.load();
		newStage.initStyle(StageStyle.UNDECORATED);
		newStage.initModality(Modality.NONE);
		newStage.setResizable(false);
		newStage.setTitle(windowTitle);
		newStage.setScene(new Scene(scene));
		addFocusStageListener();
	}
	
	public void show() {
		newStage.setX(Main.primaryStage.getX() + Main.primaryStage.getWidth()/2-155);
		newStage.setY(Main.primaryStage.getY() + Main.primaryStage.getHeight()/2-138);
		newStage.showAndWait();
	}
	
	public void show(Integer witdth, Integer height) {
		newStage.setX(Main.primaryStage.getX() + Main.primaryStage.getWidth()/2-155);
		newStage.setY(Main.primaryStage.getY() + Main.primaryStage.getHeight()/2-138);
		newStage.showAndWait();
	}

	public void close() {
		newStage.close();
	}
	
	public void addFocusStageListener() {	
		newStage.focusedProperty().addListener((arg, oldVal, newVal) -> {
			if(!newVal) {
				close();
			}
		});
	}
		
}
