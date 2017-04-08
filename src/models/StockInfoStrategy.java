package models;

import java.time.LocalDate;
import java.util.TreeMap;

/**
 * Created by hamideh on 28/03/2017.
 */
public interface StockInfoStrategy {
    // public TreeMap<LocalDate,Double> getData(String name, String symbol);
    public TreeMap<LocalDate,Double> getData( String symbol,LocalDate startDate,LocalDate endDate);
}
