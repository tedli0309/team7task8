/**
 *  Author : 	Zebangli 
 *  AndrewId :  zebangl
 *  Date   :	2016.12.5
 *  Course :	08672
 */
package databean;

import org.genericdao.PrimaryKey;

@PrimaryKey("favoriteId")
public class FavoriteBean {
	private int favoriteId;
	private int userId;
	private String url;
	private String comment;
	private int click;
	
	public FavoriteBean(){}
	public FavoriteBean(int userId, String url, String comment, int click) {
		this.userId = userId;
		this.url = url;
		this.comment = comment;
		this.click = click;
	}
	
	public int getFavoriteId() 			{return favoriteId;}
	public int getUserId() 				{return userId;}
	public String getUrl()  			{return url;}
	public String getComment() 			{return comment;}
	public int getClick() 				{return click;}
	
	public void setFavoriteId(int i) 	{favoriteId = i;}
	public void setUserId(int i)		{userId = i;}
	public void setUrl(String s)		{url = s;}
	public void setComment(String s)	{comment = s;}
	public void setClick(int i)			{click = i;}
}