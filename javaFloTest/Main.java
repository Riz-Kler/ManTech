import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.put;

import static spark.Spark.staticFileLocation;


import freemarker.template.Configuration;
import freemarker.template.Version;


import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

//import com.mysql.jdbc.PreparedStatement;


public class Main
{
    public static void main(String[] args) throws IOException {
    	
   	
//    	ArrayList<Event> test = new ArrayList<Event>();
//    	test = searchByCategory("Library");
//    	displayList(test);

    	
    	
        final Configuration config = configureFreemarker(new Version(2,3,23)); // get Freemarker configuration

        staticFileLocation("/public"); //css files and other public resources are in .../main/resources/public
   //     Event event = new Event();

        
        
        get("/login", (request, response) ->
        		render(config,"login.ftl")
        		
        		);
        
        get("/regist", (request, response) ->
			render(config,"regist.ftl")
		);
        
        get("/regist/2", (request, response) ->
			render(config,"regist2.ftl")
		);
        
        get("/regist/3", (request, response) ->
			render(config,"regist3.ftl")
        		);
        
        get("/profile", (request, response) ->
			render(config,"profile.ftl")
		);
       

        get("/event/new", (request, response) ->
                render(config, "newEventTest.ftl")
                );
  
        // Create (as in CRUD) from request parameters set when a user submits the form above
        post("/event", (request, response) -> {
            

            // Create the entry in the database
            // Use the values of fields in the form the user filled in 
        	
        	Event newEvent = new Event(request.queryParams("eventname"), request.queryParams("description"), request.queryParams("eventdate"), request.queryParams("eventtime"), request.queryParams("picurl"), request.queryParams("organizor"), request.queryParams("artist"), request.queryParams("category"));
            newEvent.addEvent();

            // show the event's information
            response.redirect( "/event/" + newEvent.getName());
            return null;
        } );

        // show all the events we have in the database
        get("/event", (request, response) ->
                render(config, "eventDisplay.ftl", toMap("event", allEvents()) ) );

        // Show the information for one person (as in Read in CRUD)
        get("/event/:name", (request, response) ->
                render( config, "eventDetails.ftl",
                        toMap("event", findByName(request.params(":name")))));

        // form to edit the information on a person who is already in the database
        get("/event/:name/edit", (request, response) ->
                render( config, "eventUpdate.ftl",
                        toMap("event", findByName(request.params(":name")))));

        // Update (as in CRUD), based on information supplied via the form above
        //   note how peopleFormUpdate.ftl (used above) specifies the create is done using
        //   an HTTP post to /people/:id
        
        // WONT DO THE UPDATE WHEREAS IT WORKS FINE OUT OF SPARK
        put("/event/:name", (request, response) -> {
        	findByName(request.params(":name")).updtEvent("'"+request.queryParams("name")+"'", request.queryParams("description"), request.queryParams("date"), request.queryParams("time"), request.queryParams("pictures"), request.queryParams("creator"), request.queryParams("artist"), request.queryParams("category"));
            // show the person's information using an existing route
            //return render(config, "peopleShow.ftl",
            //        toMap("person", people.findOne(new ObjectId(request.params(":id")))));
            response.redirect("/event/" + request.params(":name"));
            return null;
        });

        // Delete (as in CRUD)
        get("/event/:name/delete", (request, response) -> {
            findByName(request.params(":name")).deleteEvent();
            return null;
        });
        
        get("/search", (request, response) ->
                	render(config, "eventSearch.ftl")
        );
       
        post("/search", (request, response) -> {
            
        	if (!(request.queryParams("category").isEmpty()) && request.queryParams("location").isEmpty() && request.queryParams("date").isEmpty()){
        		
        		response.redirect("/search/cat/" + request.queryParams("category"));
        		
            	return null;
        	}
        	
        	if (request.queryParams("category").isEmpty() && !(request.queryParams("location").isEmpty()) && request.queryParams("date").isEmpty()){
        		
        		response.redirect("/search/loc/" + request.queryParams("location"));
        		
            	return null;
        	}

        	if (request.queryParams("category").isEmpty() && request.queryParams("location").isEmpty() && !(request.queryParams("date").isEmpty())){
        		
        		response.redirect("/search/date/" + request.queryParams("date"));
        		
            	return null;
        	}
        	
       	return null;
        
        } );
        
        get("/search/cat/:cat", (request, response) -> 
        	
        	render(config,"eventDisplay.ftl",toMap("event",searchByCategory(request.params(":cat"))))
        		);
        get("/search/loc/:loc", (request, response) -> 
    	
    	render(config,"eventDisplay.ftl",toMap("event",searchByLocation(request.params(":loc"))))
    		);
        get("/search/date/:date", (request, response) -> 
    	
    	render(config,"eventDisplay.ftl",toMap("event",searchByDate(request.params(":date"))))
    		);
        

  

    }

    private static Object render(Configuration config, String view) {
        return render(config, view, null);
    }

    private static Object render(Configuration config, String view, Object viewArgs) {
        StringWriter sw = new StringWriter();
        try {
            config.getTemplate(view).process(viewArgs, sw); //template engine processing
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sw.toString(); //return the rendered HTML
    }

    // Added to allow simplification of method invocations by removing ', null' from call parameters
    static private HashMap toMap(String key, Object obj) {
        return toMap(key, obj, null);
    }

    static private HashMap toMap(String key, Object obj, HashMap map) {
        if (map == null)
            map = new HashMap<>();
        map.put( key, obj );
        return map;
    }

    private static Configuration configureFreemarker(Version version) {
        Configuration config = new Configuration(version);
        try {
            config.setDirectoryForTemplateLoading(new File("src/main/resources/views")); //set the templates directory
        } catch (IOException e) {
            e.printStackTrace();
        }
        return config;
    }
    
	public static Event findByName(String name){
		// JDBC driver name and database URL
		final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
		final String DB_URL = "jdbc:mysql://localhost/Mantech";
		
		//  Database credentials
		final String USER = "root";
		final String PASS = "s8E3oz8]";
		Connection conn = null;
		  Statement stmt = null;
		   try{
		      //STEP 2: Register JDBC driver
		      Class.forName("com.mysql.jdbc.Driver");

		      //STEP 3: Open a connection
		//      System.out.println("Connecting to database...");
		      conn = DriverManager.getConnection(DB_URL,USER,PASS);

		      //STEP 4: Execute a query
		 //     System.out.println("Creating statement...");

		      String sql;
		      sql = "SELECT * FROM Event WHERE name LIKE"+"'"+name+"'";
		      stmt = conn.createStatement();
		      ResultSet rs = stmt.executeQuery(sql);

		      //STEP 5: Extract data from result set
		      while(rs.next()){ //let's assume that name is unique
		         //Retrieve by column name
		         String nam = rs.getString(2);
		         String descr = rs.getString(3);
		         String date = rs.getString(4);
		         String time = rs.getString(5);
		         String picurl = rs.getString(6);
		         int creatorId = rs.getInt(8);
		         String creator = getDBNameUsrFromIdUsr(creatorId);
		         int artistId = rs.getInt(9);
		         String artist = getDBNameArtistFromIdArtist(artistId);
		         int categoryId = rs.getInt(10);
		         String category = getDBNameCatFromIdCat(categoryId);
		         return new Event(nam,descr,date,time,picurl,creator,artist,category);
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
		return null;
		
	}
	
	public static void displayList(ArrayList<Event> eventList){
		for(int i=0; i < eventList.size(); i++){
			System.out.println((eventList.get(i)).getName() + " on " + eventList.get(i).getDate() + " organized by " + eventList.get(i).getEventCreator());
		}
	}
	
	public static int getDBidUsrFromNameUsr(String name){
		// JDBC driver name and database URL
		final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
		final String DB_URL = "jdbc:mysql://localhost/Mantech";
		
		//  Database credentials
		final String USER = "root";
		final String PASS = "s8E3oz8]";
		Connection conn = null;
		Statement stmt = null;
		
		   try{
		      //STEP 2: Register JDBC driver
		      Class.forName("com.mysql.jdbc.Driver");

		      //STEP 3: Open a connection
		//      System.out.println("Connecting to database...");
		      conn = DriverManager.getConnection(DB_URL,USER,PASS);

		      //STEP 4: Execute a query
		 //     System.out.println("Creating statement...");

		      String getIdCat;
		      getIdCat = "SELECT idUser FROM User WHERE name LIKE '"+name+"'";
		     
		      stmt = conn.createStatement();
		      ResultSet rs = stmt.executeQuery(getIdCat);

		      //STEP 5: Extract data from result set
		      while(rs.next()){ //let's assume that name is unique
		         //Retrieve by column name
			         int id = rs.getInt(1);
			         return id;
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
		return -1;
		
	}

	
	public static int getDBidCatFromNameCat(String cat){
		// JDBC driver name and database URL
		final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
		final String DB_URL = "jdbc:mysql://localhost/Mantech";
		
		//  Database credentials
		final String USER = "root";
		final String PASS = "s8E3oz8]";
		Connection conn = null;
		Statement stmt = null;
		
		   try{
		      //STEP 2: Register JDBC driver
		      Class.forName("com.mysql.jdbc.Driver");

		      //STEP 3: Open a connection
		//      System.out.println("Connecting to database...");
		      conn = DriverManager.getConnection(DB_URL,USER,PASS);

		      //STEP 4: Execute a query
		 //     System.out.println("Creating statement...");

		      String getIdCat;
		      getIdCat = "SELECT idCategory FROM Category WHERE name LIKE '"+cat+"'";
		     
		      stmt = conn.createStatement();
		      ResultSet rs = stmt.executeQuery(getIdCat);

		      //STEP 5: Extract data from result set
		      while(rs.next()){ //let's assume that name is unique
		         //Retrieve by column name
			         int id = rs.getInt(1);
			         return id;
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
		return -1;
		
	}

	
	
	public static String getDBNameCatFromIdCat(int id){
		// JDBC driver name and database URL
		final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
		final String DB_URL = "jdbc:mysql://localhost/Mantech";
		
		//  Database credentials
		final String USER = "root";
		final String PASS = "s8E3oz8]";
		Connection conn = null;
		Statement stmt = null;

		
		   try{
		      //STEP 2: Register JDBC driver
		      Class.forName("com.mysql.jdbc.Driver");

		      //STEP 3: Open a connection
		//      System.out.println("Connecting to database...");
		      conn = DriverManager.getConnection(DB_URL,USER,PASS);

		      //STEP 4: Execute a query
		 //     System.out.println("Creating statement...");

		      String getNameCat;
		      getNameCat = "SELECT name FROM Category WHERE idCategory = "+id;
		     
		      stmt = conn.createStatement();
		      ResultSet rs = stmt.executeQuery(getNameCat);

		      //STEP 5: Extract data from result set
		      while(rs.next()){ //let's assume that name is unique
		         //Retrieve by column name
			         String name = rs.getString(1);
			         return name;
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
		return null;
		
	}
	
	
	public static String getDBNameArtistFromIdArtist(int id){
		// JDBC driver name and database URL
		final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
		final String DB_URL = "jdbc:mysql://localhost/Mantech";
		
		//  Database credentials
		final String USER = "root";
		final String PASS = "s8E3oz8]";
		Connection conn = null;
		Statement stmt = null;

		
		   try{
		      //STEP 2: Register JDBC driver
		      Class.forName("com.mysql.jdbc.Driver");

		      //STEP 3: Open a connection
		//      System.out.println("Connecting to database...");
		      conn = DriverManager.getConnection(DB_URL,USER,PASS);

		      //STEP 4: Execute a query
		 //     System.out.println("Creating statement...");

		      String getNameArtist;
		      getNameArtist = "SELECT name FROM Artist WHERE idArtist = "+id;
		     
		      stmt = conn.createStatement();
		      ResultSet rs = stmt.executeQuery(getNameArtist);

		      //STEP 5: Extract data from result set
		      while(rs.next()){ //let's assume that name is unique
		         //Retrieve by column name
			         String name = rs.getString(1);
			         return name;
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
		return null;
		
	}
	public static String getDBNameUsrFromIdUsr(int id){
		// JDBC driver name and database URL
		final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
		final String DB_URL = "jdbc:mysql://localhost/Mantech";
		
		//  Database credentials
		final String USER = "root";
		final String PASS = "s8E3oz8]";
		Connection conn = null;
		Statement stmt = null;

		
		   try{
		      //STEP 2: Register JDBC driver
		      Class.forName("com.mysql.jdbc.Driver");

		      //STEP 3: Open a connection
		//      System.out.println("Connecting to database...");
		      conn = DriverManager.getConnection(DB_URL,USER,PASS);

		      //STEP 4: Execute a query
		 //     System.out.println("Creating statement...");

		      String getNameUsr;
		      getNameUsr = "SELECT name FROM User WHERE idUser = "+id;
		     
		      stmt = conn.createStatement();
		      ResultSet rs = stmt.executeQuery(getNameUsr);

		      //STEP 5: Extract data from result set
		      while(rs.next()){ //let's assume that name is unique
		         //Retrieve by column name
			         String name = rs.getString(1);
			         return name;
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
		return null;
		
	}
	public static boolean verifyLogin(String usrname, String password){
		// JDBC driver name and database URL
		final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
		final String DB_URL = "jdbc:mysql://localhost/Mantech";
		
		//  Database credentials
		final String USER = "root";
		final String PASS = "s8E3oz8]";
		Connection conn = null;
		Statement stmt = null;

		
		   try{
		      //STEP 2: Register JDBC driver
		      Class.forName("com.mysql.jdbc.Driver");

		      //STEP 3: Open a connection
		//      System.out.println("Connecting to database...");
		      conn = DriverManager.getConnection(DB_URL,USER,PASS);

		      //STEP 4: Execute a query
		 //     System.out.println("Creating statement...");

		      String sql;
		      sql = "SELECT * FROM User WHERE nickname LIKE '"+usrname+"' AND password LIKE '"+password+"'";
		     
		      stmt = conn.createStatement();
		      ResultSet rs = stmt.executeQuery(sql);

		      //STEP 5: Extract data from result set
		      if(rs.next()){ 
		    	//STEP 6: Clean-up environment
			      rs.close();
			      stmt.close();
			      conn.close();
		         return true;
		      }
		      else {
		    	//STEP 6: Clean-up environment
			      rs.close();
			      stmt.close();
			      conn.close();
		    	  return false;
		      }
		      
		      
		      
			
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
		return false;
		
	}
	public static ArrayList<Event> searchByCategory(String cat){
		// JDBC driver name and database URL
		final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
		final String DB_URL = "jdbc:mysql://localhost/Mantech";
		
		//  Database credentials
		final String USER = "root";
		final String PASS = "s8E3oz8]";
		Connection conn = null;
		Statement stmt = null;
		ArrayList<Event> eventList = new ArrayList<Event>();
		
		   try{
		      //STEP 2: Register JDBC driver
		      Class.forName("com.mysql.jdbc.Driver");

		      //STEP 3: Open a connection
		//      System.out.println("Connecting to database...");
		      conn = DriverManager.getConnection(DB_URL,USER,PASS);

		      //STEP 4: Execute a query
		 //     System.out.println("Creating statement...");
		      int id = getDBidCatFromNameCat(cat);
		      String sql;
		      
		      sql = "SELECT * FROM Event WHERE eventCategory ="+ id;
		      stmt = conn.createStatement();
		      ResultSet rs = stmt.executeQuery(sql);

		      //STEP 5: Extract data from result set
		      while(rs.next()){ //let's assume that name is unique
		         //Retrieve by column name
		         String name = rs.getString(2);
		         String descr = rs.getString(3);
		         String date = rs.getString(4);
		         String time = rs.getString(5);
		         String picurl = rs.getString(6);
		         String creator = rs.getString(7);
		         String artist = rs.getString(8);
		         String category = rs.getString(9);
		         
		         Event evt = new Event(name,descr,date,time,picurl,creator,artist,category);
		         eventList.add(evt);
		      }
		      
		      
		      //STEP 6: Clean-up environment
		      rs.close();
		      stmt.close();
		      conn.close();
		      return eventList;
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
		return null;
		
	}
	
	public static ArrayList<Event> searchByDate(String date){
		// JDBC driver name and database URL
		final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
		final String DB_URL = "jdbc:mysql://localhost/Mantech";
		
		//  Database credentials
		final String USER = "root";
		final String PASS = "s8E3oz8]";
		Connection conn = null;
		Statement stmt = null;
		ArrayList<Event> eventList = new ArrayList<Event>();
		
		   try{
		      //STEP 2: Register JDBC driver
		      Class.forName("com.mysql.jdbc.Driver");

		      //STEP 3: Open a connection
		//      System.out.println("Connecting to database...");
		      conn = DriverManager.getConnection(DB_URL,USER,PASS);

		      //STEP 4: Execute a query
		 //     System.out.println("Creating statement...");

		      String sql;
		      sql = "SELECT * FROM Event WHERE date LIKE"+"'"+date+"'";
		      stmt = conn.createStatement();
		      ResultSet rs = stmt.executeQuery(sql);

		      //STEP 5: Extract data from result set
		      while(rs.next()){ //let's assume that name is unique
		         //Retrieve by column name
		         String name = rs.getString(2);
		         String descr = rs.getString(3);
		         String datea = rs.getString(4);
		         String time = rs.getString(5);
		         String picurl = rs.getString(6);
		         String creator = rs.getString(7);
		         String artist = rs.getString(8);
		         String category = rs.getString(9);
		         
		         Event evt = new Event(name,descr,datea,time,picurl,creator,artist,category);
		         eventList.add(evt);
		      }
		      
		      
		      //STEP 6: Clean-up environment
		      rs.close();
		      stmt.close();
		      conn.close();
		      return eventList;
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
		return null;
		
	}
	
	public static ArrayList<Event> searchByLocation(String loc){
		// JDBC driver name and database URL
		final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
		final String DB_URL = "jdbc:mysql://localhost/Mantech";
		
		//  Database credentials
		final String USER = "root";
		final String PASS = "s8E3oz8]";
		Connection conn = null;
		Statement stmt = null;
		ArrayList<Event> eventList = new ArrayList<Event>();
		
		   try{
		      //STEP 2: Register JDBC driver
		      Class.forName("com.mysql.jdbc.Driver");

		      //STEP 3: Open a connection
		//      System.out.println("Connecting to database...");
		      conn = DriverManager.getConnection(DB_URL,USER,PASS);

		      //STEP 4: Execute a query
		 //     System.out.println("Creating statement...");

		      String sql;
		      sql = "SELECT * FROM Event WHERE location LIKE"+"'"+loc+"'";
		      stmt = conn.createStatement();
		      ResultSet rs = stmt.executeQuery(sql);

		      //STEP 5: Extract data from result set
		      while(rs.next()){ //let's assume that name is unique
		         //Retrieve by column name
		         String name = rs.getString(2);
		         String descr = rs.getString(3);
		         String date = rs.getString(4);
		         String time = rs.getString(5);
		         String picurl = rs.getString(6);
		         String creator = rs.getString(7);
		         String artist = rs.getString(8);
		         String category = rs.getString(9);
		         
		         Event evt = new Event(name,descr,date,time,picurl,creator,artist,category);
		         eventList.add(evt);
		      }
		      
		      
		      //STEP 6: Clean-up environment
		      rs.close();
		      stmt.close();
		      conn.close();
		      return eventList;
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
		return null;
		
	}
	
	public static ArrayList<Event> allEvents(){
		// JDBC driver name and database URL
		final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
		final String DB_URL = "jdbc:mysql://localhost/Mantech";
		
		//  Database credentials
		final String USER = "root";
		final String PASS = "s8E3oz8]";
		Connection conn = null;
		Statement stmt = null;
		ArrayList<Event> eventList = new ArrayList<Event>();
		
		   try{
		      //STEP 2: Register JDBC driver
		      Class.forName("com.mysql.jdbc.Driver");

		      //STEP 3: Open a connection
		//      System.out.println("Connecting to database...");
		      conn = DriverManager.getConnection(DB_URL,USER,PASS);

		      //STEP 4: Execute a query
		 //     System.out.println("Creating statement...");

		      String sql;
		      sql = "SELECT * FROM Event";
		      stmt = conn.createStatement();
		      ResultSet rs = stmt.executeQuery(sql);

		      //STEP 5: Extract data from result set
		      while(rs.next()){ //let's assume that name is unique
		         //Retrieve by column name
		         String name = rs.getString(2);
		         String descr = rs.getString(3);
		         String date = rs.getString(4);
		         String time = rs.getString(5);
		         String picurl = rs.getString(6);
		         String creator = rs.getString(7);
		         String artist = rs.getString(8);
		         String category = rs.getString(9);
		         
		         Event evt = new Event(name,descr,date,time,picurl,creator,artist,category);
		         eventList.add(evt);
		      }
		      
		      
		      //STEP 6: Clean-up environment
		      rs.close();
		      stmt.close();
		      conn.close();
		      return eventList;
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
		return null;
		
	}
	
}