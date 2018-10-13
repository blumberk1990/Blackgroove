package pl.mgd.blackgroove.utils;

import java.util.function.Function;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

public class ActionButtonTableCell<S> extends TableCell<S, Button> {

	/*--Fields--*/
	private final Button actionButton;
	
	/*--Javabeans--*/
	public Button getActionButton() {
		return this.actionButton;
	}
	
	/*--Contructors--*/
	private ActionButtonTableCell(String label, Function<S, S> function) {
        this.actionButton = new Button(label);
        this.actionButton.setOnAction((ActionEvent e) -> {
            function.apply(getCurrentItem());
        });
        this.actionButton.setMaxWidth(Double.MAX_VALUE);
	}
	
	/*--Methods--*/
	public S getCurrentItem() {
        return (S) getTableView().getItems().get(getIndex());
    }
	
	public static <S> Callback<TableColumn<S, Button>, TableCell<S, Button>> forTableColumn(String label, Function< S, S> function) {
        return param -> new ActionButtonTableCell<>(label, function);
    }
	

	@Override
    public void updateItem(Button item, boolean empty) {
        super.updateItem(item, empty);

        if (empty) {
            setGraphic(null);
        } else {                
            setGraphic(actionButton);
        }
    }
	
}
