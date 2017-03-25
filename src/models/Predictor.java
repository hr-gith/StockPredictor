package models;

/**
 * Created by hamideh on 24/02/2017.
 */
import javafx.scene.chart.*;

import java.awt.geom.Line2D;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

public class Predictor {
    private Stock stock;
    private ArrayList<Integer> indicators;
    private LinkedList<MovingAverage> movingAverageList;
    private LineChart<String, Number> movingAverageChart;

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

    private void setMovingAverageList(){
        movingAverageList = new LinkedList<>();
        for (int indicator : indicators)
            movingAverageList.add(new MovingAverage(stock.getPriceHistory(), indicator));
    }

    //constructor
    public Predictor(Stock stock, ArrayList<Integer> indicators) {
        this.stock = stock;
        this.indicators = indicators;
    }

    /**
     * short term has the most priority
     * @return MovingAverage minMA
     */
    private MovingAverage getPriority(){
        if (!movingAverageList.isEmpty()){
            MovingAverage minMA = movingAverageList.get(0);
            for (int i=0 ; i < movingAverageList.size() ; i++)
                if (minMA.getIndicator() > movingAverageList.get(i).getIndicator())
                    minMA = movingAverageList.get(i);
            return minMA;
        }
        return null;
    }

    private LocalDate findLastIntersection(MovingAverage baseMA, MovingAverage otherMA){
        if (baseMA.getAvgSeries().isEmpty() || otherMA.getAvgSeries().isEmpty())
            return null;
        LocalDate currentDate = baseMA.getAvgSeries().lastKey();
        LocalDate nextDate = baseMA.getAvgSeries().lowerKey(currentDate);

        while (nextDate != null){
            if (Line2D.linesIntersect(0.0, baseMA.getAvgSeries().get(currentDate),
                    1.0, baseMA.getAvgSeries().get(nextDate),
                    0.0, otherMA.getAvgSeries().get(currentDate),
                    1.0, otherMA.getAvgSeries().get(nextDate))){
                //Intersection found
                return nextDate;
            }
            currentDate = nextDate;
            nextDate = baseMA.getAvgSeries().lowerKey(currentDate);
        }
        return null;
    }

    public String predict(){
        if (indicators.size() > 1) {
            setMovingAverageList();
            String advice = "";
            MovingAverage priorityMA = getPriority();
            MovingAverage otherMA;
            LocalDate predictionPoint = null;
            for (int i = 0; i < movingAverageList.size(); i++) {
                if (movingAverageList.get(i) != priorityMA) {
                    otherMA = movingAverageList.get(i);
                    LocalDate intersectionDate = findLastIntersection(priorityMA, otherMA);
                    if (predictionPoint == null || (intersectionDate != null && predictionPoint.compareTo(intersectionDate) == 1))
                        predictionPoint = intersectionDate;
                }
            }
            //if there is not any intersection
            if (predictionPoint == null) {
                advice = "Buy";
                return advice;
            }
            //predict by checking if the MA with the most priority is going up or down
            Double currentPrice;
            Double nextPrice;
            do {
                if (predictionPoint == priorityMA.getAvgSeries().lastKey()) {
                    advice = "Buy";
                } else {
                    currentPrice = priorityMA.getAvgSeries().get(predictionPoint);
                    nextPrice = priorityMA.getAvgSeries().higherEntry(predictionPoint).getValue();
                    if (currentPrice < nextPrice) {
                        advice = "Buy";
                    } else if (currentPrice > nextPrice) {
                        advice = "Sell";
                    } else {
                        predictionPoint = priorityMA.getAvgSeries().higherKey(predictionPoint);
                        if (predictionPoint == null)
                            advice = "Buy";
                    }
                }
            } while (advice == "");
            return advice;
        }else
            return "More indicators needed to predict.";
    }

    public LineChart<String, Number> getMovingAverageChart(LocalDate from, LocalDate to ){
        if (indicators.size() > 0)
            setMovingAverageList();

        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Date");
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("price");
        yAxis.setAutoRanging(true);
        yAxis.setForceZeroInRange(false);
        movingAverageChart = new LineChart<>(xAxis, yAxis);
        movingAverageChart.setTitle("Moving average chart");

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy:mm:dd");
        for (MovingAverage ma:movingAverageList){
            XYChart.Series<String, Number> newSeries = new XYChart.Series<>();
            newSeries.setName(Integer.toString(ma.getIndicator()));
            SortedMap<LocalDate , Double> subAvgSeries = ma.getAvgSeries().subMap(from, to);
            int month, day, year;
            for (Map.Entry<LocalDate , Double> entry : subAvgSeries.entrySet()) {
                month = entry.getKey().getMonth().getValue();
                day = entry.getKey().getDayOfMonth();
                year = entry.getKey().getYear();
                String strDate = Integer.toString(year) + "-" + Integer.toString(month) + "-" + Integer.toString(day);

                newSeries.getData().add(new XYChart.Data<>(strDate, entry.getValue()));
            }
            movingAverageChart.getData().add(newSeries);
        }
        movingAverageChart.setCreateSymbols(false);
        return movingAverageChart;
    }
}
