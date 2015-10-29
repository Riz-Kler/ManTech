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
import java.util.HashMap;

import com.mysql.jdbc.PreparedStatement;


public class Main
{
    public static void main(String[] args) throws IOException {
    	
//    	Event newEvent = new Event();
//    	newEvent.setName("namet");
//    	newEvent.setDescription("descrt");
//    	newEvent.setDate("datet");
//    	newEvent.setTime("timet");
//    	newEvent.setPicURL("picurlt");
//    	newEvent.setEventCreator("creatort");
//    	newEvent.setEventArtist("artistt");
//    	newEvent.setEventCategory("categoryt");
//      newEvent.addEvent();
//  
//      System.out.println(findByName("namet").getClass());
//    	findByName("namet").updtEvent("namet", "description4", "never", "time", "picurl", "eventCreator", "eventArtist", "eventCategory");
    	
    	
 	    	

        final Configuration config = configureFreemarker(new Version(2,3,23)); // get Freemarker configuration

        staticFileLocation("/public"); // css files and other public resources are in .../main/resources/public
        Event event = new Event();

        get("/event/new", (request, response) ->
                render(config, "newEvent.ftl")
                );
        
        // Create (as in CRUD) from request parameters set when a user submits the form above
        // note how peopleFormNew.ftl specifies the create is done using an HTTP post to /people
        post("/event", (request, response) -> {
            

            // Create the entry in the database, using 'people' the database adaptor
            // Use the values of fields in the form the user filled in            
        	Event newEvent = new Event(request.queryParams("name"), request.queryParams("description"), request.queryParams("date"), request.queryParams("time"), request.queryParams("picurl"), request.queryParams("creator"), request.queryParams("artist"), request.queryParams("category"));
            newEvent.addEvent();

            // show the person's information
            // note, if we simply said return render(config,"peopleShow.ftl", <map of data for new person>)
            // we would see the right information, but it would display at url '/people' which is
            // already used to show all people. So instead we redirect to the route to show a
            // persons info as below, and the page at '/people/<id>' displays, using the correct URL
            response.redirect( "/event/" + newEvent.getName());
            return null;
        } );

        // show all the people we have in the database
        get("/event", (request, response) ->
                render(config, "eventDisplay.ftl", toMap("event", event) ) );

        // Show the information for one person (as in Read in CRUD)
        get("/event/:name", (request, response) ->
                render( config, "eventDisplay.ftl",
                        toMap("event", findByName(request.params(":name")))));

        // form to edit the information on a person who is already in the database
        get("/event/:name/edit", (request, response) ->
                render( config, "eventUpdate.ftl",
                        toMap("event", findByName(request.params(":name")))));

        // Update (as in CRUD), based on information supplied via the form above
        //   note how peopleFormUpdate.ftl (used above) specifies the create is done using
        //   an HTTP post to /people/:id
        post("/event/:name", (request, response) -> {
        	findByName(request.params(":name")).updtEvent(request.queryParams("name"), request.queryParams("description"), request.queryParams("date"), request.queryParams("time"), request.queryParams("pictures"), request.queryParams("creator"), request.queryParams("artist"), request.queryParams("category"));
            // show the person's information using an existing route
            //return render(config, "peopleShow.ftl",
            //        toMap("person", people.findOne(new ObjectId(request.params(":id")))));
            response.redirect("/event/" + request.params(":name"));
            return null;
        });
/*
        // Delete (as in CRUD)
        get("/people/:id/delete", (request, response) -> {
            people.delete(new ObjectId(request.params(":id")));
            response.redirect("/people");
            return null;
        });



        // end CRUD for people
        // -------------------------
        // remains of spikes

        get("/ping", (request, response) -> "pong\n");
        get( "/hello", (request, response) -> render(config, "hello.ftl", toMap("name", "Shaderach")) );  // ** ', null' removed

        get( "/hello",                         // HTTP request type and URL
                (request, response) ->            // Java 8 lambda expression
                        // provides call-back code
                        render(config, "helloWorld.ftl")); // call-back code to run
        // when request received

        get( "/hello/:name",
                (request, response) ->
                        render( config, "hello.ftl",
                                toMap("name", request.params(":name"))) );

//      get( "/params", (request, response) -> render(config, "params.ftl", toMap("name", toMap("first", "mark", toMap("second", "van", null)), null)) );
        get( "/params", (request, response) -> render(config, "peopleIndex.ftl", toMap("people", people.all()))); // ** ', null' removed
*/
 
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
            config.setDirectoryForTemplateLoading(new File("src/main/resources")); //set the templates directory
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
		         String creator = rs.getString(7);
		         String artist = rs.getString(8);
		         String category = rs.getString(9);
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
	
}