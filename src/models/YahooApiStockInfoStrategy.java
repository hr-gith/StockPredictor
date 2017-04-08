package models;

import java.time.LocalDate;
import java.util.TreeMap;

/**
 * Created by hamideh on 28/03/2017.
 */
public class YahooApiStockInfoStrategy implements StockInfoStrategy {

    public TreeMap<LocalDate, Double> getData (String name, String symbol){
        //TO Store Key as date and value as price
        TreeMap<LocalDate,Double> stockKeyValuePair= new TreeMap<LocalDate,Double>();

        //TO-DO: Connecting to API


        return stockKeyValuePair;
    }

}
