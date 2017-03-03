package models;

/**
 * Created by hamideh on 24/02/2017.
 */
import java.util.LinkedList;

public class Predictor {
    public Stock stock;
    public LinkedList<MovingAverage> movingAverageList;

    //getters and setters

    //constructor

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
}
