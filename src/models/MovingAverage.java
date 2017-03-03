package models;

/**
 * Created by hamideh on 24/02/2017.
 */
import java.util.*;

public class MovingAverage {
    private int indicator; //window size
    private TreeMap <Date, Double> numberSeries;
    private TreeMap <Date, Double> avgSeries;

    //getters and setters
    public TreeMap <Date, Double> getNumberSeries() {
        return numberSeries;
    }

    public void setNumberSeries(TreeMap <Date, Double> numberSeries) {
        this.numberSeries = numberSeries;
    }

    public int getIndicator() {
        return indicator;
    }

    public void setIndicator(int indicator) {
        this.indicator = indicator;
    }

    public TreeMap <Date, Double> getAvgSeries() {
        return avgSeries;
    }

    //constructor
    public MovingAverage(TreeMap <Date, Double> numberSeries, int indicator) {
        this.numberSeries = numberSeries;
        this.indicator = indicator;
        this.avgSeries = new TreeMap<>();
        calculateMovingAverage();
    }

    /**
     * calculates moving average of numberSeries and set the result in avgSeries
     * @param ArrayDeque<Double> windowSeries
     * @return double average
     */
    public void calculateMovingAverage(){
        ArrayDeque<Double> windowSeries = new ArrayDeque<>(indicator);

        int i = 1;
        for (Map.Entry<Date, Double> entry : numberSeries.entrySet()){
            if (i <= indicator)
                windowSeries.add(entry.getValue());
            else{
                windowSeries.removeFirst();
                windowSeries.offerLast(entry.getValue());
            }
            if (i > indicator)
                avgSeries.put(entry.getKey() , calculateAverage(windowSeries));
            i++;
        }
    }

    /**
     * calculates average of numbers in ArrayDeque<Double>
     * @param ArrayDeque<Double> windowSeries
     * @return double average
     */
    private double calculateAverage(ArrayDeque<Double> windowSeries) {
        double avg = 0;
        for (Double i : windowSeries)
            avg += i;
        avg /= windowSeries.size();
        return avg;
    }
}
