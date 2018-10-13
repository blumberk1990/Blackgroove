package pl.mgd.blackgroove.main;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import pl.mgd.blackgroove.utils.UndecoratedResizeMod;
import javafx.scene.Parent;
import javafx.scene.Scene;


public class Main extends Application {
	   public static Stage primaryStage; // static Stage
	   public static Rectangle2D primaryScreenBounds;
	   
	    private void setPrimaryStage(Stage stage) {
	        Main.primaryStage = stage;
	        
	    }

	    static public Stage getPrimaryStage() {
	        return Main.primaryStage;
	        
	    }
	    
	    @Override
		public void start(Stage primaryStage) {
			try {
				//final String appName = "Blackgroove v1.0";
				setUserAgentStylesheet(STYLESHEET_CASPIAN);
				setPrimaryStage(primaryStage);
				FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
		                "/pl/mgd/blackgroove/view/MainPane.fxml"));			
				Parent parent = (Parent) fxmlLoader.load();
		        Scene scene = new Scene(parent);
		        primaryStage.initStyle(StageStyle.UNDECORATED);
				primaryStage.setScene(scene);
				primaryStage.setMinHeight(515);
				primaryStage.setMinWidth(620);
				primaryScreenBounds = Screen.getPrimary().getVisualBounds();
				
				 //set Stage boundaries to visible bounds of the main screen
				primaryStage.setWidth(primaryScreenBounds.getWidth());
				primaryStage.setHeight(primaryScreenBounds.getHeight());
				primaryStage.setWidth(550);
				primaryStage.setHeight(550);
				primaryStage.setX(primaryScreenBounds.getMinX() + primaryScreenBounds.getMaxX()/2 - primaryStage.getWidth()/2);
				primaryStage.setY(primaryScreenBounds.getMinY() + primaryScreenBounds.getMaxY()/2 - primaryStage.getHeight()/2);		
				primaryStage.show();
				UndecoratedResizeMod.addResizeListener(primaryStage);
			} catch(Exception e) {
				e.printStackTrace();
			}
			
		}
		
		public static void main(String[] args) {
			launch(args);
			
		}
}
