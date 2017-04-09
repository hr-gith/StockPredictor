package models;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.util.TreeMap;

/**
 * @author Shivaraj , Sushma
 //Description: This class connects to YahooAPI to fetch the data in real time
 Created on 07/04/2017
 */
public class YahooAPIStockInfoStrategy implements StockInfoStrategy {
    TreeMap<LocalDate,Double> stockKeyValuePair= new TreeMap<LocalDate,Double>();
    public TreeMap<LocalDate, Double> getData (String symbol,LocalDate startDate,LocalDate endDate){
        //TO Store Key as date and value as price

        //stockKeyValuePair=stockData(startDate,endDate,symbol);
        //TO-DO: Connecting to API
        int startDay = startDate.getDayOfMonth()-1;
        int startMonth = startDate.getMonthValue();
        int startYear = startDate.getYear();
        //System.out.println(startyear);


        int endDay = endDate.getDayOfMonth()-1;
        int endMonth = endDate.getMonthValue();
        int endYear = endDate.getYear();
       
        
		String url ="http://ichart.finance.yahoo.com/table.csv?s="+symbol+"&d="+endMonth+"&e="+endDay+"&f="+endYear+"&g=d&a="+startMonth+"&b="+startDay+"&c="+startYear+"&ignore=.csv";

        // Create a URL and open a connection
        
		URL YahooURL;
        try {
            YahooURL = new URL(url);

            HttpURLConnection con = (HttpURLConnection) YahooURL.openConnection();

            // Set the HTTP Request type method to GET (Default: GET)
            con.setRequestMethod("GET");

            // Created a BufferedReader to read the contents of the request.
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;

            in.readLine();
            //Skip Header
            inputLine = in.readLine();


            //Date,Open,High,Low,Close,Volume,Adj Close

            while (inputLine  != null) {

            	String [] splitdata= inputLine.split(",");
            	stockKeyValuePair.put(LocalDate.parse(splitdata[0]), Double.parseDouble(splitdata[4]));
                inputLine =in.readLine();
            }


            // TO CLOSE  CONNECTION!
            in.close();
        }
        catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return stockKeyValuePair;
    }


}
