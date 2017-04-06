package controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.*;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import models.*;
import org.controlsfx.control.CheckComboBox;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import javafx.scene.control.ListCell;
import javafx.scene.control.cell.ComboBoxListCell;

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
    public HashMap<String, String> stocksDOW;
    public ObservableList<String> stockWatchList ;
    public ObservableList<String> selectedStockWatchList;

    @FXML
    private DatePicker datePickerFrom;
    @FXML
    private DatePicker datePickerTo;
    @FXML
    private GridPane gpResult;
    @FXML
    private Label lbErrorMsg;
    @FXML
    private CheckComboBox<Integer> chCb_indicators;
    @FXML
    private ComboBox<String> cob_stocks;
    @FXML
    private CheckComboBox<String> chCb_StockList;
    @FXML
    private ListView listView;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        errorMessage = "";

        //initializing Indicators
        indicators = FXCollections.observableArrayList();
        indicators.addAll(20,50,100,200);
        chCb_indicators.getItems().addAll(indicators);
        chCb_indicators.getCheckModel().getCheckedItems().addListener(new ListChangeListener() {
            public void onChanged(ListChangeListener.Change c) {
                updatePredictionPage();
            }
        });

        //initializing stock watch list
        stockWatchList = FXCollections.observableArrayList();
        List<String> list = new ArrayList<String>();
        stockWatchList.addAll("Apple","American Express","Boeing","Caterpillar","Cisco Systems","Chevron","Coca-Cola",
                "DuPont","ExxonMobil","General Electric","Goldman Sachs","Home Depot","IBM","Intel","Johnson & Johnson",
                "JPMorgan Chase","McDonald's","3M Company","Merck","Microsoft","Nike","Pfizer","Procter & Gamble","The Travelers",
                "UnitedHealth","United Technologies","Visa","Verizon","Wal-Mart","Walt Disney");

        ObservableList<String> observableList = FXCollections.observableList(list);
        chCb_StockList.getItems().addAll(stockWatchList);
        chCb_StockList.getCheckModel().getCheckedItems().addListener(new ListChangeListener() {
            public void onChanged(ListChangeListener.Change c) {
                updateStockListSection();
            }
        });

        //initializing DOW 30 Stocks
        setDowStocks();
        List<String> stockList= new ArrayList<>();
        String str;
        for (Map.Entry<String, String> entry : stocksDOW.entrySet()) {
            str = String.format("%-10s", entry.getKey());
            stockList.add(str + entry.getValue().trim());
        }
        cob_stocks.getItems().addAll(stockList);
        cob_stocks.setVisibleRowCount(4);
        cob_stocks.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                updatePredictionPage();
            }
        });
    }

    @FXML
    public void onChangeDateRange(ActionEvent e){
        updatePredictionPage();
    }

    public void refreshPredictionPage(){
        gpResult.getChildren().clear();
        lbErrorMsg.setText("");
        errorMessage = "";
    }

    public void setDowStocks(){
        stocksDOW = new HashMap<>();
        stocksDOW.put("Apple" , "AAPL");
        stocksDOW.put("AXP" , "American Express");
        stocksDOW.put("BA" , "Boeing");
        stocksDOW.put("CAT" , "Caterpillar");
        stocksDOW.put("CSCO" , "Cisco Systems");
        stocksDOW.put("CVX" , "Chevron");
        stocksDOW.put("KO" , "Coca-Cola");
        stocksDOW.put("DD" , "DuPont");
        stocksDOW.put("XOM" , "ExxonMobil");
        stocksDOW.put("GE" , "General Electric");
        stocksDOW.put("GS" , "Goldman Sachs");
        stocksDOW.put("HD" , "Home Depot");
        stocksDOW.put("IBM" , "IBM");
        stocksDOW.put("INTC" , "Intel");
        stocksDOW.put("JNJ" , "Johnson & Johnson");
        stocksDOW.put("JPM" , "JPMorgan Chase");
        stocksDOW.put("MCD" , "McDonald's");
        stocksDOW.put("MMM" , "3M Company");
        stocksDOW.put("MRK" , "Merck");
        stocksDOW.put("MSFT" , "Microsoft");
        stocksDOW.put("NKE" , "Nike");
        stocksDOW.put("PFE" , "Pfizer");
        stocksDOW.put("PG" , "Procter & Gamble");
        stocksDOW.put("TRV" , "The Travelers");
        stocksDOW.put("UNH" , "UnitedHealth");
        stocksDOW.put("UTX" , "United Technologies");
        stocksDOW.put("V" , "Visa");
        stocksDOW.put("VZ" , "Verizon");
        stocksDOW.put("WMT" , "Wal-Mart");
        stocksDOW.put("DIS" , "Walt Disney");
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

    public void setCurrentStock(){
        String[] stockInfo = cob_stocks.getSelectionModel().getSelectedItem().split(" +");
        if (stockInfo.length != 0)
            //TO-DO: change to yahoo API strategy
            //currentStock = new Stock(stockInfo[0] , stockInfo[1] , new YahooAPIStockInfoStrategy());
            currentStock = new Stock(stockInfo[0] , stockInfo[1] , new CSVStockInfoStrategy());
        else
            currentStock = null;
    }

    public void updateStockListSection(){
        selectedStockWatchList = chCb_StockList.getCheckModel().getCheckedItems();
        listView.setItems(selectedStockWatchList);
        listView.setCellFactory(ComboBoxListCell.forListView(selectedStockWatchList));

    }

    public void updatePredictionPage(){
        refreshPredictionPage();
        setCurrentStock();
        selectedIndicators = chCb_indicators.getCheckModel().getCheckedItems();
        predictor = new Predictor(currentStock, new ArrayList<>());

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

    public boolean saveToJson(){
        selectedStockWatchList = chCb_StockList.getCheckModel().getCheckedItems();
        AccountsCollection.currentAccount.watchList = new ArrayList<>(selectedStockWatchList);
        AccountsCollection.WriteToJson();
        return true;
    }
}
