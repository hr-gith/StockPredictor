package models;

/**
 * Created by hamideh on 24/02/2017.
 */
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

import java.text.SimpleDateFormat;
import java.util.*;

public class Predictor {
    private Stock stock;
    private ArrayList<Integer> indicators;
    private LinkedList<MovingAverage> movingAverageList;

    //getters and setters

    public Stock getStock() {
        return stock;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }

    public ArrayList<Integer> getIndicators() {
        return indicators;
    }

    public void setIndicators(ArrayList<Integer> indicators) {
        this.indicators = indicators;
    }

    public LinkedList<MovingAverage> getMovingAverageList() {
        return movingAverageList;
    }

    public void setMovingAverageList(){
        if (indicators.isEmpty())
            return;
        movingAverageList = new LinkedList<>();
        for (int indicator : indicators)
            movingAverageList.add(new MovingAverage(stock.getPriceHistory(), indicator));
    }

    //constructor
    public Predictor(Stock stock, ArrayList<Integer> indicators) {
        this.stock = stock;
        this.indicators = indicators;
        setMovingAverageList();
    }

    /**
     * short term has the most priority
     * @return MovingAverage minMA
     */
    public MovingAverage getPriority(){
        if (!movingAverageList.isEmpty()){
            MovingAverage minMA = movingAverageList.get(0);
            for (int i=0 ; i < movingAverageList.size() ; i++)
                if (minMA.getIndicator() > movingAverageList.get(i).getIndicator())
                    minMA = movingAverageList.get(i);
            return minMA;
        }
        return null;
    }

    public AreaChart<String, Number> getMovingAverageChart(Date from, Date to ){
        if (indicators.size() > 0 && (movingAverageList == null || movingAverageList.isEmpty())){
            setMovingAverageList();
        }
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Date");
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("price");
        AreaChart<String, Number> newChart = new AreaChart<>(xAxis, yAxis);
        newChart.setTitle("Moving average chart");

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy:mm:dd");
        for (MovingAverage ma:movingAverageList){
            XYChart.Series<String, Number> newSeries = new XYChart.Series<>();
            newSeries.setName(Integer.toString(ma.getIndicator()));
            SortedMap<Date , Double> subAvgSeries = ma.getAvgSeries().subMap(from, to);
            for (Map.Entry<Date , Double> entry : subAvgSeries.entrySet()){
                newSeries.getData().add(new XYChart.Data<>(dateFormat.format(entry.getKey()), entry.getValue()));
            }
            newChart.getData().add(newSeries);
        }
        //newChart.intersects()
        return newChart;
    }
}
