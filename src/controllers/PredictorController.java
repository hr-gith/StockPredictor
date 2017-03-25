package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.*;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import models.Predictor;
import models.Stock;
import org.controlsfx.control.CheckComboBox;

import java.net.URL;
import java.time.LocalDate;
import java.util.*;

public class PredictorController implements Initializable{
    public Stock currentStock;
    public Predictor predictor;
    public String errorMessage;
    public LocalDate dateFrom;
    public LocalDate dateTo;
    public ObservableList<Integer> indicators ;
    public ObservableList<Integer> selectedIndicators;


    @FXML
    private DatePicker datePickerFrom;
    @FXML
    private DatePicker datePickerTo;
    @FXML
    private GridPane gpResult;
    @FXML
    private Label lbErrorMsg;
    @FXML
    private CheckComboBox<Integer> Chcb_indicators;
    @FXML
    private ComboBox<String> Cb_stocks;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        currentStock = new Stock();
        predictor = new Predictor(currentStock, new ArrayList<>());
        errorMessage = "";

        indicators = FXCollections.observableArrayList();
        selectedIndicators = FXCollections.observableArrayList();
        indicators.addAll(20,50,100,200);
        Chcb_indicators.getItems().addAll(indicators);
        Chcb_indicators.getCheckModel().getCheckedItems().addListener(new ListChangeListener() {
            public void onChanged(ListChangeListener.Change c) {
                selectedIndicators = Chcb_indicators.getCheckModel().getCheckedItems();
                updatePrediction();
            }
        });
    }

    public void refreshPredictorPage(){
        gpResult.getChildren().clear();
        lbErrorMsg.setText("");
        errorMessage = "";
    }

    public boolean setAndValidateIndicators(){
        int nbIndicators = selectedIndicators.size();
        if (selectedIndicators.isEmpty() || nbIndicators == 1){
            errorMessage += "Error: Please select at least two indicators.\r\n";
            return false;
        }

        predictor.getIndicators().clear();
        for(int i = 0 ; i < nbIndicators ; i++){
            predictor.getIndicators().add(selectedIndicators.get(i));
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
    public void onChangeStockCb(ActionEvent e){
        updatePrediction();
    }

    public void updatePrediction(){
        refreshPredictorPage();
        if (!setAndValidateIndicators() || !setAndValidateDateRange() || currentStock == null){
            lbErrorMsg.setText(errorMessage);
            return;
        }
        LineChart<String, Number> maChart = predictor.getMovingAverageChart(dateFrom, dateTo);
        if (maChart != null) {
            gpResult.setConstraints(maChart, 0, 0);
            gpResult.getChildren().add(maChart);
            //TO-Do getAdvice should be created
            String advice = predictor.predict();
            Label lbAdvice = new Label("  Advice: " + advice);
            gpResult.setConstraints(lbAdvice, 0, 2);
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
