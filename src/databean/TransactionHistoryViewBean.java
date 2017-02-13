package databean;

//import java.util.Date;

public class TransactionHistoryViewBean {
	private String name;
	private String executeDate;
	private double shares = 0.0;
	private String transactionType;
	private double amount = 0.0;
	private int userId = 0;
	
	public TransactionHistoryViewBean(){
		 
	}
	public String getName()					{return name;}
	public String getExecuteDate()       	{return executeDate;}
	public double getShares()       		{return shares;}
	public String getTransactionType()      {return transactionType;}
	public double getAmount()       		{return amount;}
	public int getUserId()       		    {return userId;}
	
	public void setName(String s)				{name = s;}
	public void setExecuteDate(String da)       {executeDate = da;}
	public void setShares(double d)       		{shares = d;}
	public void setTransactionType(String s)    {transactionType =s;}
	public void setAmount(double d)       		{amount = d;}
	public void setUserId(int i)       		    {userId = i;}
	
	public double toPrice() {
		if (shares == 0.0) return 0.0;
		else return amount / shares;
	}
}
