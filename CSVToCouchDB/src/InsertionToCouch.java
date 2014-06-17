import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

 
 public class InsertionToCouch {

	public void insertCouch(String cellArray,String TableName) throws IOException
	{
		System.out.println(TableName.toUpperCase() +" DATA INSERTION BEGINS ");
		
 		URL puturl = new URL("http://localhost:5984/mydb/_bulk_docs");
		HttpURLConnection conn = (HttpURLConnection) puturl.openConnection();
		conn.setDoOutput(true);
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-Type","application/json");		 
		String input =cellArray;	
 		OutputStream os = conn.getOutputStream();
		os.write(input.getBytes());
		os.flush();
		if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
			throw new RuntimeException(
					"Failed : HTTP error code : "
							+ conn.getResponseCode());
		} else {
	 		System.out.println("RESPONSE CODE IS :  "+conn.getResponseCode());
			System.out.println(TableName.toUpperCase()+" DATA INSERTION ENDS ");
			
			System.out.println("***********************************************************");
		}
		conn.disconnect();
 
	 
	}
}
