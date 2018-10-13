package pl.mgd.blackgroove.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import pl.mgd.blackgroove.main.Main;

public class ProgramNavigationPaneController implements Initializable {

	/*--Fields--*/
    @FXML
    private Button previouseContentButton;

    @FXML
    private Button nextContentButton;

    @FXML
    private TextField searchTextField;
    
    @FXML
    private HBox regionHbox;

    @FXML
    private Button minimizeButton;

    @FXML
    private Button maximizeButton;

    @FXML
    private Button closeButton;


    final StageCoordinate stageCoordinate = new StageCoordinate();
    
    /*--Javabeans--*/
	public Button getPreviouseContentButton() {
		return previouseContentButton;
	}

	public void setPreviouseContentButton(Button previouseContentButton) {
		this.previouseContentButton = previouseContentButton;
	}
    
	public Button getNextContentButton() {
		return nextContentButton;
	}

	public void setNextContentButton(Button nextContentButton) {
		this.nextContentButton = nextContentButton;
	}
	
	public TextField getSearchTextField() {
		return searchTextField;
	}
	
    /*--Methods--*/
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		configurationCloseButton();		
		configurationMaximizeButton();
		configurationMinimizeButton();
		configurationStageMoving();

	}
	
	private void configurationCloseButton() {
		closeButton.setOnAction(e -> {
			System.out.println("Program is closed, thanks for checking.");
			Main.getPrimaryStage().close();
		});
		
	}
	
	private void configurationMaximizeButton() {
		maximizeButton.setOnAction(e -> {	
			if(Main.getPrimaryStage().isMaximized()) {
				System.out.println("Bringback previouse window size.");
				Main.getPrimaryStage().setWidth(stageCoordinate.prevWindowWidth);
				Main.getPrimaryStage().setHeight(stageCoordinate.prevWindowHight);
				Main.getPrimaryStage().setX(stageCoordinate.prevWindowPosX);
				Main.getPrimaryStage().setY(stageCoordinate.prevWindowPosY);
				Main.getPrimaryStage().setMaximized(false);
			} else {
				System.out.println("Maximize window size.");
				stageCoordinate.prevWindowWidth = Main.getPrimaryStage().getWidth();
				stageCoordinate.prevWindowHight = Main.getPrimaryStage().getHeight();
				stageCoordinate.prevWindowPosX = Main.getPrimaryStage().getX();
				stageCoordinate.prevWindowPosY = Main.getPrimaryStage().getY();
				Main.getPrimaryStage().setMaximized(true);
			}	
		});
		
	}
	
	private void configurationMinimizeButton() {
		minimizeButton.setOnAction(e -> {
			Main.getPrimaryStage().setIconified(true);
		});
		
	}
	
	private void configurationStageMoving() {	
		
		regionHbox.setOnMousePressed(event -> {
			if(event.getButton().equals(MouseButton.PRIMARY)) {
				stageCoordinate.deltaX = Main.getPrimaryStage().getX() - event.getScreenX();
				stageCoordinate.deltaY = Main.getPrimaryStage().getY() - event.getScreenY();	
			}
		});
		
		regionHbox.setOnMouseDragged(event -> {
			if(event.getButton().equals(MouseButton.PRIMARY)) {
				Main.getPrimaryStage().setX(event.getScreenX() + stageCoordinate.deltaX);
				Main.getPrimaryStage().setY(event.getScreenY() + stageCoordinate.deltaY);	
			}
		});
		
		regionHbox.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
			if(Main.getPrimaryStage().isMaximized()) {
				if(event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
					stageCoordinate.setPrevStageCoordinate();
					Main.getPrimaryStage().setMaximized(false);
				}		
			} else {
				if(event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
					stageCoordinate.getActualStageCoordinate();
					Main.getPrimaryStage().setMaximized(true);					
				}
			}
		});
		
	}
	
	/*--Inner class--*/
	private class StageCoordinate {		
		public double prevWindowWidth;
	    public double prevWindowHight;
	    public double prevWindowPosX;
	    public double prevWindowPosY;
	    public double deltaX;
	    public double deltaY;
	    
	    public void getActualStageCoordinate() {
	    	prevWindowWidth = Main.getPrimaryStage().getWidth();
			prevWindowHight = Main.getPrimaryStage().getHeight();
			prevWindowPosX = Main.getPrimaryStage().getX();
			prevWindowPosY = Main.getPrimaryStage().getY();		
	    }
	    
	    public void setPrevStageCoordinate() {
	    	Main.getPrimaryStage().setWidth(prevWindowWidth);
			Main.getPrimaryStage().setHeight(prevWindowHight);
			Main.getPrimaryStage().setX(prevWindowPosX);
			Main.getPrimaryStage().setY(prevWindowPosY);			
	    }
	    
	}
	
}
