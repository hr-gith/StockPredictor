package models;

/**
 * Created by hamideh on 24/02/2017.
 */
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import com.opencsv.CSVReader;

public class Stock {
	private String name ;
	private Double price ;
	private TreeMap<LocalDate,Double> priceHistory ;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public TreeMap<LocalDate, Double> getPriceHistory() {
		return priceHistory;
	}

	public void setPriceHistory(TreeMap<LocalDate, Double> priceHistory) {
		this.priceHistory = priceHistory;
	}

	public Stock()	{
		name = "Stock Name";
		priceHistory = getStockFromCsv("Sample data.csv");
	}

	public TreeMap<LocalDate,Double> getStockFromCsv(String fileName){

		//TO Store Key as date and value as price
		TreeMap<LocalDate,Double> stockKeyValuePair= new TreeMap<LocalDate,Double>();
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


