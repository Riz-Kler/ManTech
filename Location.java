import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Location {
	String locationName;
	String address;
	String neighbourhood;
	
	// JDBC driver name and database URL
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	static final String DB_URL = "jdbc:mysql://localhost/Mantech";

	//  Database credentials
	static final String USER = "root";
	static final String PASS = "s8E3oz8]";
	
	public Location() {
		locationName = "";
		address = "";
		neighbourhood = "";
	}
	
	public Location(String name, String a, String n) {
		locationName = name;
		address = a;
		neighbourhood = n;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getNeighbourhood() {
		return neighbourhood;
	}

	public void setNeighbourhood(String neighbourhood) {
		this.neighbourhood = neighbourhood;
	}
	
	public void addLocation(){
		
		String locName = this.getLocationName();
		String locAddr = this.getAddress();
		String locNei = this.getNeighbourhood();
		
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
		    sql = "INSERT INTO Location(id,name,address,neighbourhood) VALUES (10,'"+locName+"',"+"'"+locAddr+"',"+"'"+locNei+"')";  //see what alaa did with the id
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
