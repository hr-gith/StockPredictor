package models;

/**
 * Created by hamideh on 24/02/2017.
 */
import java.util.LinkedList;

public class Predictor {
    public LinkedList<MovingAverage> MAList;

    //getters and setters
    public LinkedList<MovingAverage> getMAList() {
        return MAList;
    }

    public void setMAList(LinkedList<MovingAverage> mAList) {
        MAList = mAList;
    }

    //constructor
    public Predictor(LinkedList<MovingAverage> mAList) {
        MAList = mAList;
    }

    /**
     * short term has the most priority
     * @return MovingAverage minMA
     */
    public MovingAverage getPriority(){
        if (!MAList.isEmpty()){
            MovingAverage minMA = MAList.get(0);
            for (int i=0 ; i < MAList.size() ; i++)
                if (minMA.getIndicator() > MAList.get(i).getIndicator())
                    minMA = MAList.get(i);
            return minMA;
        }
        return null;
    }
}
