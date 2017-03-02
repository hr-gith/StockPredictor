package models;

/**
 * Created by hamideh on 24/02/2017.
 */
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TreeMap;
import com.opencsv.CSVReader;

public class Stock {
String name ;
Double price ;
TreeMap<Date,Double> priceHistory ;


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


Stock()
{
	priceHistory=getStockFromCsv("C:\\Users\\Sushma\\workspace\\Sample data.csv");
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


