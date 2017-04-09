package models;

/**
 * Created by Shivaraj, Sushma on 09/03/2017.
 */
import java.time.LocalDate;
import java.util.*;

public class Stock {
	private String name ;
	private String symbol;
	private Double price ;
	private TreeMap<LocalDate,Double> priceHistory ;
	private StockInfoStrategy strategy;
	private LocalDate startDate;
	private LocalDate endDate;

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
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

	public void setStrategy(StockInfoStrategy strategy){
		this.strategy = strategy;
	}
	/*
	public Stock(String name, String symbol, StockInfoStrategy strategy)	{
		this.name = name;
		this.symbol = symbol;
		this.strategy = strategy;
		priceHistory = strategy.getData(name, symbol);
	} */

	public Stock(String symbol, StockInfoStrategy strategy,LocalDate startDate,LocalDate endDate)	{

		this.symbol = symbol;
		this.strategy = strategy;
		this.startDate=startDate;
		this.endDate =endDate;
		priceHistory = strategy.getData(symbol,startDate,endDate);
	}
}


