package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class Controller {
    @FXML
    private Label lbResult;

    @FXML
    public void clickPredictBtn(ActionEvent e){
        lbResult.setText("hi");
    }
}
