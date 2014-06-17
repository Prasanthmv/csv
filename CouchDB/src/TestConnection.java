import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.lightcouch.CouchDbClient;
import org.lightcouch.Response;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSerializer;

//Based on http://www.lightcouch.org/lightcouch-guide.html#dependencies

public class TestConnection {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		CouchDbClient dbClient = new CouchDbClient("couchdb.properties"); 	 
		Response resp;
		
		//How to insert JSON already formatted
		
		JsonParser parser = new JsonParser();
		String json_text = "{\"_id\": \"a"+(int)(Math.random()*100)+"\",\"a\": \"A\"}";
		System.out.println(json_text );
		JsonObject o = (JsonObject)parser.parse(json_text );
		
		try
		{
	    resp = dbClient.save(o); 
		}
		catch(org.lightcouch.DocumentConflictException e)
		{
			//if we insert something that already exists
			//we get Exception in thread "main" org.lightcouch.DocumentConflictException: << Status: 409 (Conflict) 
		}
		
		//Prepare JSON
		for(int i=0; i<100; i++)
		{
			 JsonObject  object=new JsonObject ();
			 object.addProperty("name","Amit Kumar");
			 object.addProperty("Max.Marks",new Integer(100));
			 object.addProperty("Min.Marks",new Double(40));
			 object.addProperty("Scored",new Double(66.67));
			 object.addProperty("nickname","Amit");
			 object.addProperty("_id", "whatever"+(int)(Math.random()*100000));
			 object.addProperty("rev", "3");
			
			try
			{
				resp = dbClient.save(object); 
			}
			catch(org.lightcouch.DocumentConflictException e)
			{
				//if we insert something that already exists
				//we get Exception in thread "main" org.lightcouch.DocumentConflictException: << Status: 409 (Conflict) 
			}
		
		}	
		
		dbClient.shutdown();
	
	}

}
	

/*
 * 
 * 	// dbClient.save(foo);   // or save and ignore response 
		// dbClient.batch(foo);  // saves as batch
		//Response resp2 = dbClient.update(object);

		
		/*Map<String, Object> map = new HashMap<String, Object>();
		map.put("_id", "some-id");
		map.put("a-list", Collections.EMPTY_LIST);
		dbClient.save(map);

		JsonObject json = new JsonObject();
		json.addProperty("_id", "some-id-2");
		json.add("an-array", new JsonArray());
		dbClient.save(json); 
*/		


