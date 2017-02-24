package models;

/**
 * Created by hamideh on 24/02/2017.
 */
import java.util.ArrayDeque;
import java.util.ArrayList;

public class MovingAverage {
    public ArrayList<Pair> numberSeries;
    public ArrayList<Pair> avgSeries;
    public int indicator; //window size

    //getters and setters
    public ArrayList<Pair> getNumberSeries() {
        return numberSeries;
    }

    public void setNumberSeries(ArrayList<Pair> numberSeries) {
        this.numberSeries = numberSeries;
    }

    public int getIndicator() {
        return indicator;
    }

    public void setIndicator(int indicator) {
        this.indicator = indicator;
    }

    public ArrayList<Pair> getAvgSeries() {
        return avgSeries;
    }

    //constructor
    public MovingAverage(ArrayList<Pair> numberSeries, int indicator) {
        this.numberSeries = numberSeries;
        this.indicator = indicator;
        this.avgSeries = new ArrayList<Pair>();
        calculateMovingAverage();
    }

    /**
     * calculates moving average of numberSeries and set the result in avgSeries
     * @param ArrayDeque<Double> windowSeries
     * @return double average
     */
    public void calculateMovingAverage(){
        ArrayDeque<Double> windowSeries = new ArrayDeque<Double>(indicator);

        for (int i = 0 ; i < numberSeries.size() ; i++){
            if (i < indicator)
                windowSeries.add(numberSeries.get(i).getValue());
            else{
                windowSeries.removeFirst();
                windowSeries.offerLast(numberSeries.get(i).getValue());
            }
            if (i >= (indicator-1)){
                avgSeries.add(new Pair(numberSeries.get(i).getKey() , calculateAverage(windowSeries)));
            }
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
