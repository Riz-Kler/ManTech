//import java.util.Date;
import java.sql.*;

public class Event {
	String name;
	String description;
	String date;
	String time; //actually: Date date = new Date(); System.out.println(date.toString()); returns date AND time
	String picURL;
	String eventCreator;
	String eventArtist;
	String eventCategory;
	
	// JDBC driver name and database URL
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	static final String DB_URL = "jdbc:mysql://localhost/Mantech";
	
	//  Database credentials
	static final String USER = "root";
	static final String PASS = "s8E3oz8]";
	
	public Event() {
		name = "";
		description = "";
		date = "";
		time = date.toString();
		picURL = "";
		eventCreator = "";
		eventArtist = "";
		eventCategory = "";
	}
	
	public Event(Event evt){
		this.name= evt.name;
		this.description= evt.description;
		this.date= evt.date;
		this.time= evt.time;
		this.picURL= evt.picURL;
		this.eventCreator= evt.eventCategory;
		this.eventArtist= evt.eventArtist;
		this.eventCategory= evt.eventCategory;
	}
	
	public Event(String name, String description, String date, String time, String picurl, String eventCreator, String eventArtist, String eventCategory) {
		this.name = name;
		this.description = description;
		this.date = date;
		this.time = time;
		this.picURL = picurl;
		this.eventCreator = eventCreator;
		this.eventArtist = eventArtist;
		this.eventCategory = eventCategory;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getEventCreator() {
		return eventCreator;
	}

	public void setEventCreator(String eventCreator) {
		this.eventCreator = eventCreator;
	}

	public String getEventArtist() {
		return eventArtist;
	}

	public void setEventArtist(String eventArtist) {
		this.eventArtist = eventArtist;
	}

	public String getEventCategory() {
		return eventCategory;
	}

	public void setEventCategory(String eventCategory) {
		this.eventCategory = eventCategory;
	}

	public String getPicURL() {
		return picURL;
	}

	public void setPicURL(String picURL) {
		this.picURL = picURL;
	}
	

	public void displayEvent(){
		   Connection conn = null;
		   Statement stmt = null;
		   try{
		      //STEP 2: Register JDBC driver
		      Class.forName("com.mysql.jdbc.Driver");

		      //STEP 3: Open a connection
		      System.out.println("Connecting to database...");
		      conn = DriverManager.getConnection(DB_URL,USER,PASS);

		      //STEP 4: Execute a query
		      System.out.println("Creating statement...");
		      stmt = conn.createStatement();
		      String sql;
		      sql = "SELECT * FROM Event";
		      ResultSet rs = stmt.executeQuery(sql);

		      //STEP 5: Extract data from result set
		      while(rs.next()){
		         //Retrieve by column name
		         String name = rs.getString(2);
		         String descr = rs.getString(3);
		         String date = rs.getString(4);
		         String time = rs.getString("time");
		         String picurl = rs.getString("picurl");
		         String creator = rs.getString("creator");
		         String artist = rs.getString("artist");
		         String category = rs.getString("category");

		         //Display values
		         System.out.println("Name: " + name);
		         System.out.println("Description: " + descr);
		         System.out.println("Date: " + date);
		         System.out.println("Time: " + time);
		         System.out.println("Creator: " + creator);
		         System.out.println("Artist: " + artist);
		         System.out.println("Category: " + category);
		      }
		      //STEP 6: Clean-up environment
		      rs.close();
		      stmt.close();
		      conn.close();
		   }catch(SQLException se){
		      //Handle errors for JDBC
		      se.printStackTrace();
		   }catch(Exception e){
		      //Handle errors for Class.forName
		      e.printStackTrace();
		   }finally{
		      //finally block used to close resources
		      try{
		         if(stmt!=null)
		            stmt.close();
		      }catch(SQLException se2){
		      }// nothing we can do
		      try{
		         if(conn!=null)
		            conn.close();
		      }catch(SQLException se){
		         se.printStackTrace();
		      }//end finally try
		   }//end try
		   System.out.println("Goodbye!");
		}
	
    
	public void addEvent() {
		
		String name = this.getName();
		String descr = this.getDescription();
		String date = this.getDate();
		String time = this.getTime();
		String picurl = this.getPicURL();
		String eventCreator = this.getEventCreator();
		String eventArtist = this.getEventArtist();
		String eventCategory = this.getEventCategory();
		
		Connection conn = null;
		Statement stmt = null;
		try{
		    //STEP 2: Register JDBC driver
		    Class.forName("com.mysql.jdbc.Driver");

		    //STEP 3: Open a connection
		    //System.out.println("Connecting to database...");
		    conn = DriverManager.getConnection(DB_URL,USER,PASS);

		    //STEP 4: Execute a query
		    stmt = conn.createStatement();
		    String sql;
		    sql = "INSERT INTO Event(name,description,date,time,pictures,eventcreator,eventartist,eventcategory) VALUES ('"+name+"', '"+descr+"', '"+date+"', '"+time+"', '"+picurl+"', '"+eventCreator+"', '"+eventArtist+"', '"+eventCategory+"')"; 
		    stmt.executeUpdate(sql);
		    
		    //STEP 6: Clean-up environment
		    stmt.close();
		    conn.close();
		 }catch(SQLException se){
		    //Handle errors for JDBC
		    se.printStackTrace();
		 }catch(Exception e){
		    //Handle errors for Class.forName
		    e.printStackTrace();
		 }finally{
		    //finally block used to close resources
		    try{
		       if(stmt!=null)
		          stmt.close();
		    }catch(SQLException se2){
		    }// nothing we can do
		    try{
		       if(conn!=null)
		          conn.close();
		    }catch(SQLException se){
		       se.printStackTrace();
		    }//end finally try
		 }//end try
	}
public void updtEvent(String name, String description, String date, String time, String picurl, String eventCreator, String eventArtist, String eventCategory) {
		
		String evtname = this.getName();
		Connection conn = null;
		Statement stmt = null;
		try{
		    //STEP 2: Register JDBC driver
		    Class.forName("com.mysql.jdbc.Driver");

		    //STEP 3: Open a connection
		    //System.out.println("Connecting to database...");
		    conn = DriverManager.getConnection(DB_URL,USER,PASS);

		    //STEP 4: Execute a query
		    stmt = conn.createStatement();
		    String sql;
		    sql = "UPDATE Event SET name='"+name+"', description='"+description+"', date='"+date+"', time='"+time+"', pictures='"+picurl+"', eventCreator='"+eventCreator+"', eventArtist='"+eventArtist+"', eventCategory='"+eventCategory+"' WHERE name='"+evtname+"'";
		    stmt.executeUpdate(sql);
		    
		    //STEP 6: Clean-up environment
		    stmt.close();
		    conn.close();
		 }catch(SQLException se){
		    //Handle errors for JDBC
		    se.printStackTrace();
		 }catch(Exception e){
		    //Handle errors for Class.forName
		    e.printStackTrace();
		 }finally{
		    //finally block used to close resources
		    try{
		       if(stmt!=null)
		          stmt.close();
		    }catch(SQLException se2){
		    }// nothing we can do
		    try{
		       if(conn!=null)
		          conn.close();
		    }catch(SQLException se){
		       se.printStackTrace();
		    }//end finally try
		 }//end try
	}

public void deleteEvent() {
	
	String name = this.getName();

	
	Connection conn = null;
	Statement stmt = null;
	try{
	    //STEP 2: Register JDBC driver
	    Class.forName("com.mysql.jdbc.Driver");

	    //STEP 3: Open a connection
	    //System.out.println("Connecting to database...");
	    conn = DriverManager.getConnection(DB_URL,USER,PASS);

	    //STEP 4: Execute a query
	    stmt = conn.createStatement();
	    String sql;
	    sql = "DELETE FROM Event WHERE name LIKE"+"'"+name+"'";
	    stmt.executeUpdate(sql);
	    
	    //STEP 6: Clean-up environment
	    stmt.close();
	    conn.close();
	 }catch(SQLException se){
	    //Handle errors for JDBC
	    se.printStackTrace();
	 }catch(Exception e){
	    //Handle errors for Class.forName
	    e.printStackTrace();
	 }finally{
	    //finally block used to close resources
	    try{
	       if(stmt!=null)
	          stmt.close();
	    }catch(SQLException se2){
	    }// nothing we can do
	    try{
	       if(conn!=null)
	          conn.close();
	    }catch(SQLException se){
	       se.printStackTrace();
	    }//end finally try
	 }//end try
}
}
