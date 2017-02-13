package databean;

import java.util.Date;

import org.genericdao.PrimaryKey;

@PrimaryKey("pricedate,fundId")

public class FundPriceHistoryBean {
	private int fundId;
	private String pricedate;
	private double price;
	
	public FundPriceHistoryBean() {
		
	}
	public FundPriceHistoryBean(int fundId,String pricedate,double price){
		this.fundId = fundId;
		this.pricedate = pricedate;
		this.price = price;
	}
	
	public int getFundId() 			{return fundId;}
	public String getPricedate() 		{return pricedate;}
	public double getPrice() 		{return price;}
	
	public void setFundId(int i)		{fundId = i;}
	public void setPricedate(String d)	{pricedate = d;}
	public void setPrice(double d)  	{price = d;}
}
