package databean;

import org.genericdao.PrimaryKey;

@PrimaryKey("userName")
public class EmployeeBean {
	private String userName;
	private int  hashedPassword;
	private String lastName;
	private String firstName;
	
	public EmployeeBean() {
		
	}
	public EmployeeBean(String lastName, String firstName, String password, String userName) {
		//this.email = email;
		this.lastName = lastName;
		this.firstName = firstName;
		this.hashedPassword = Integer.parseInt(password);
		this.userName = userName;
	}
	public int getHashedPassword() 		{return hashedPassword;}
	public String getLastName() 		{return lastName;}
	public String getFirstName() 		{return firstName;}
	public String getUserName() 		{return userName;}
	
	public void setHashedPassword(int hashedPassword) 	{this.hashedPassword = hashedPassword;}
	public void setLastName(String s) 	{lastName = s;}
	public void setFirstName(String s)  {firstName = s;}
	public void setUserName(String s) 	{userName = s;}
	
	
}
