package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.*;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import models.Predictor;
import models.Stock;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class PredictorController implements Initializable{
    public Stock currentStock;
    public Predictor predictor;
    public String errorMessage;
    public LocalDate dateFrom;
    public LocalDate dateTo;

    @FXML
    private DatePicker datePickerFrom;
    @FXML
    private DatePicker datePickerTo;
    @FXML
    private GridPane gpResult;
    @FXML
    private Label lbErrorMsg;
    @FXML
    private CheckBox chb_20;
    @FXML
    private CheckBox chb_50;
    @FXML
    private CheckBox chb_100;
    @FXML
    private CheckBox chb_200;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        currentStock = new Stock();
        predictor = new Predictor(currentStock, new ArrayList<>());
        errorMessage = "";
    }

    public void refreshPredictorPage(){
        gpResult.getChildren().clear();
        lbErrorMsg.setText("");
        errorMessage = "";
    }

    public boolean setAndValidateIndicators(){
        predictor.getIndicators().clear();
        if (chb_20.isSelected())
            predictor.getIndicators().add(20);
        if (chb_50.isSelected())
            predictor.getIndicators().add(50);
        if (chb_100.isSelected())
            predictor.getIndicators().add(100);
        if (chb_200.isSelected())
            predictor.getIndicators().add(200);
        if (predictor.getIndicators().size() < 2) {
            errorMessage += "Error: Please select at least two indicators.\r\n";
            return false;
        }
        return true;
    }

    public boolean setAndValidateDateRange(){
        if (datePickerFrom.getValue() == null || datePickerTo.getValue() == null){
            errorMessage = "Please select a valid range of dates.\r\n";
            return false;
        }
        dateFrom = datePickerFrom.getValue();
        dateTo = datePickerTo.getValue();

        if ( dateTo.compareTo(dateFrom) <= 0){
            errorMessage = "Please select a valid range of dates.\r\n";
            return false;
        }
        return true;
    }

    @FXML
    public void onClickPredictBtn(ActionEvent e){
        refreshPredictorPage();
        if (!setAndValidateIndicators() || !setAndValidateDateRange() || currentStock == null){
            lbErrorMsg.setText(errorMessage);
            return;
        }
        LineChart<String, Number> maChart = predictor.getMovingAverageChart(dateFrom, dateTo);
        if (maChart != null) {
            GridPane.setConstraints(maChart, 0, 0);
            gpResult.getChildren().add(maChart);
            //TO-Do getAdvice should be created
            String advice = predictor.predict();
            Label lbAdvice = new Label("  Advice: " + advice);
            GridPane.setConstraints(lbAdvice, 0, 2);
            gpResult.getChildren().add(lbAdvice);
        }
    }

    private LineChart<String, Number> createStockChart(){
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Date");
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("price");
        yAxis.setAutoRanging(true);
        yAxis.setForceZeroInRange(false);
        LineChart<String, Number> newChart = new LineChart<>(xAxis, yAxis);
        newChart.setTitle("Stock price chart");

        XYChart.Series<String, Number> newSeries = new XYChart.Series<>();
        newSeries.setName(currentStock.getName() + " price history");
        SortedMap<LocalDate , Double> subPriceHistory = currentStock.getPriceHistory().subMap(dateFrom, dateTo);
        //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-mm-dd");
        int year, month, day;
        for (Map.Entry<LocalDate, Double> entry:subPriceHistory.entrySet()){
            month = entry.getKey().getMonth().getValue();
            day = entry.getKey().getDayOfMonth();
            year = entry.getKey().getYear();
            String strDate = Integer.toString(year) + "-" + Integer.toString(month) + "-" + Integer.toString(day);
            //String str = entry.getKey().format(formatter);
            newSeries.getData().add(new XYChart.Data<>(strDate , entry.getValue()));
        }
        newChart.setCreateSymbols(false);
        newChart.getData().add(newSeries);
        return newChart;
    }

    @FXML
    public void onClickDisplayChartBtn(ActionEvent e){
        refreshPredictorPage();
        if (!setAndValidateDateRange() || currentStock == null){
            lbErrorMsg.setText(errorMessage);
            return;
        }
        LineChart<String, Number> stockChart = createStockChart();
        if (stockChart != null) {
            gpResult.getChildren().add(stockChart);
            lbErrorMsg.setText("");
        }else
            lbErrorMsg.setText("Data not found in this range of dates.");
    }
}
