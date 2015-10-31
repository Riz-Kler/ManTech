import java.sql.*;

public class Category {
	
String categoryName;
	
	// JDBC driver name and database URL
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	
	public Category() {
		categoryName = "";
	}
	
	public Category(String categoryname) {
		categoryName = categoryname;
	}
	
	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	
	public void addCategory() {
		
		String newCat = this.getCategoryName();
		
		Connection conn = null;
		Statement stmt = null;
		try{
		    //STEP 2: Register JDBC driver
		    Class.forName("com.mysql.jdbc.Driver");

		    //STEP 3: Open a connection
		    //System.out.println("Connecting to database...");
		    conn = DriverManager.getConnection("jdbc:mysql://localhost/ManTech_Events?" + "user=root&password=1Qazxsw2_alaa");

		    //STEP 4: Execute a query
		    stmt = conn.createStatement();
		    String sql;
		    sql = "INSERT INTO Category(name) VALUES ('"+newCat+"')"; 
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
	
	
	public void displayCategory() {
		
		
	}


}
