package models;

import com.opencsv.CSVReader;

import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.TreeMap;

/**
 * Created by hamideh on 28/03/2017.
 */
public class CSVStockInfoStrategy implements StockInfoStrategy {

    public TreeMap<LocalDate,Double> getData(String name, String symbol){
        //TO Store Key as date and value as price
        TreeMap<LocalDate,Double> stockKeyValuePair= new TreeMap<LocalDate,Double>();

        //TO-DO change to name
        //String fileName = name+".csv";
        String fileName = "Sample data.csv";

        try {
            CSVReader reader = new CSVReader(new FileReader(fileName));

            String[] newLine = reader.readNext();
            LocalDate date;
            while ((newLine = reader.readNext())!=null) {
                date = LocalDate.parse(newLine[0].trim());
                //Add Date and Price to tree map
                stockKeyValuePair.put(date, Double.parseDouble(newLine[4]));
            }
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        return  stockKeyValuePair;
    }
}
