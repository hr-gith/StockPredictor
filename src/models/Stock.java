package models;

/**
 * Created by hamideh on 24/02/2017.
 */
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TreeMap;
import com.opencsv.CSVReader;

public class Stock {
	private String name ;
	private Double price ;
	private TreeMap<Date,Double> priceHistory ;

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

	public TreeMap<Date, Double> getPriceHistory() {
		return priceHistory;
	}

	public void setPriceHistory(TreeMap<Date, Double> priceHistory) {
		this.priceHistory = priceHistory;
	}

	public Stock()	{
		name = "Stock Name";
		priceHistory = getStockFromCsv("Sample data.csv");
		/* // fake data for test
		//2.0 4.0 6.0 2.0 2.0 2.0 2.0 3.0
		//3.5 3.5 3.0 2.0 2.25
		priceHistory = new TreeMap<>();
		priceHistory.put(new GregorianCalendar(2016, 3, 1).getTime(), 2.0 );
		priceHistory.put(new GregorianCalendar(2016, 3, 2).getTime(), 4.0 );
		priceHistory.put(new GregorianCalendar(2016, 3, 4).getTime(), 6.0 );
		priceHistory.put(new GregorianCalendar(2016, 3, 6).getTime(), 2.0 );
		priceHistory.put(new GregorianCalendar(2016, 3, 11).getTime(), 2.0 );
		priceHistory.put(new GregorianCalendar(2016, 3, 19).getTime(), 2.0 );
		priceHistory.put(new GregorianCalendar(2016, 3, 20).getTime(), 2.0 );
		priceHistory.put(new GregorianCalendar(2016, 3, 21).getTime(), 3.0 );
		priceHistory.put(new GregorianCalendar(2016, 3, 26).getTime(), 7.0 );*/
	}

	public TreeMap<Date,Double> getStockFromCsv(String fileName){

		//TO Store Key as date and value as price
		TreeMap<Date,Double> stockKeyValuePair= new TreeMap<Date,Double>();
		try {
			CSVReader reader = new CSVReader(new FileReader(fileName));
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd");
			String []newLine = reader.readNext();
			Date date;
			while ((newLine=reader.readNext())!=null) {
				try {
					date = (Date)formatter.parse(newLine[0]);
			
					//Add Date and Price to tree map
			    	stockKeyValuePair.put(date, Double.parseDouble(newLine[4]));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			  }
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	 	return  stockKeyValuePair;
    }
}


