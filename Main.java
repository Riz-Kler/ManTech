import static spark.Spark.*;

import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.put;

import static spark.Spark.staticFileLocation;


import freemarker.template.Configuration;
import freemarker.template.Version;


import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;

public class Main {

	public static void main(String[] args) {
		
//		Category cat = new Category("foo");
//		cat.addCategory();
		
		
		// TODO Auto-generated method stub
		get("/hello", (req, res) -> "Hello World");
		
		final Configuration config = configureFreemarker(new Version(2,3,23));
    	Category Category = new Category();

		 get("/Category/new", (request, response) ->
         render(config, "NewCategory.ftl") );
	        post("/Category", (request, response) -> {
	        	Category newCategory  = new Category(request.queryParams("name"));
	        	newCategory.addCategory();

	        	response.redirect( "/Category/" + newCategory.getCategoryName());
	        	            return null;
	        	        } ); 
	        
	        get("/Category", (request, response) ->
	    render(config, "categoryDisplay.ftl", toMap("Category",Category) ) );

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
	
	

}
