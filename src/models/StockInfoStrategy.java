package models;

import java.time.LocalDate;
import java.util.TreeMap;

/**
 *@author shivaraj, Sushma 
 */
public interface StockInfoStrategy {
    // public TreeMap<LocalDate,Double> getData(String name, String symbol);
    public TreeMap<LocalDate,Double> getData( String symbol,LocalDate startDate,LocalDate endDate);
}
