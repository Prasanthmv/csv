import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.json.JSONArray;
import org.json.JSONObject;

public class ReadExcelPOI {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try
        {
			
			//CouchDbClient dbClient = new CouchDbClient("couchdb.properties");
			//Response resp;			
			
            FileInputStream file = new FileInputStream(new File("G:\\FEDiFM-CoreDataSample\\Installation.xlsx"));
            
            Workbook workbook = WorkbookFactory.create( file );
            // Get the first Sheet.
            Sheet sheet = workbook.getSheetAt( 0 );

            
             // Start constructing JSON.
            JSONObject json = new JSONObject();
             // Iterate through the rows.
            JSONArray rows = new JSONArray();
            int i=0;
            ArrayList<Object> headerArray=new ArrayList();
            
            ArrayList<Object> allValuearray=new  ArrayList<>();
             for (Iterator<Row> rowsIT = sheet.rowIterator(); rowsIT.hasNext(); )
             {
                 Row row = rowsIT.next();
                 //JSONObject jRow = new JSONObject();
                 // Iterate through the cells.
                // JSONArray cells = new JSONArray();
                 ArrayList<Object> valueArray=new ArrayList<>();
                 for (Iterator<Cell> cellsIT = row.cellIterator();cellsIT.hasNext(); )
                 {
                     Cell cell = cellsIT.next();
                     Object value="";
                     if(cell.getCellType()==0)
                     {          
                    	 value=Integer.valueOf((int) cell.getNumericCellValue());
                    	// cells.put(cell.getNumericCellValue());
                     }
                     else if(cell.getCellType()==1)
                     {     
                    	 value=cell.getStringCellValue();
                    	// cells.put(cell.getStringCellValue());
                     }
                     
                     if(i==0)
                     {
                    	 headerArray.add(value);
                     }
                     else if(i>0)
                     {
                    	 valueArray.add(value); 
                     }
                      
                 }
                 if(i!=0)
                	 allValuearray.add(valueArray);
          
                 
                // jRow.put( "cell", cells );
                 
                
                 i++;
             }
              // Create the JSON.
             
             for(int j=0;j<allValuearray.size();j++)
             {
            	 ArrayList  eachValue=(ArrayList) allValuearray.get(j);
            	 JSONArray eachCellJsonAry = new JSONArray();               	 
            	 eachCellJsonAry.put(0,"_id:"+eachValue.get(0));
	             for(int k=1;k<headerArray.size();k++)
	             {	
	            	 eachCellJsonAry.put(k,headerArray.get(k)+":"+eachValue.get(k));
	             }
	             JSONObject jRow = new JSONObject();
	             jRow.put( "eachcell",eachCellJsonAry);
	             rows.put( jRow);
              }
             
             json.put( "docs", rows );
             
             try
  			{
            	 
            	System.out.println("*** *****PUT DATA***** ****");
            	URL puturl = new URL("http://localhost:5984/persistance/_bulk_docs");
            	HttpURLConnection conn = (HttpURLConnection) puturl.openConnection();
            	System.out.println(conn);
            	System.out.println("*******************************");
     			conn.setDoOutput(true);
     			conn.setRequestMethod("POST");
     			conn.setRequestProperty("Content-Type", "application/json");
     			
     			String input = json.toString();
     			
     			
     			System.out.println(input);

    			OutputStream os = conn.getOutputStream();
    			System.out.println("before write");
    			os.write(input.getBytes());
    			System.out.println("after");
    			os.flush();
    			System.out.println("Resp Code: "+conn.getResponseCode() );
    		/*	if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
    				throw new RuntimeException("Failed : HTTP error code : "+conn.getResponseCode());
    			}
    			else
    			{
    				System.out.println(conn.getResponseCode());
    				System.out.println("*****DATA INSERTED*****");
    			} */   			
    			System.out.println("****** ***GET DATA***** ****");    			
    			
    			conn.disconnect();
    			
    			/*URL getUrl = new URL("http://localhost:5984/lightcouch-db-test-2/_all_docs?key=1001");
    			HttpURLConnection conn1 = (HttpURLConnection) getUrl.openConnection();
    			conn1.setDoInput(true);
    			conn1.setRequestMethod("GET");
    			conn1.setRequestProperty("Content-Type", "application/json");
     			
     			BufferedReader br = new BufferedReader(new InputStreamReader((conn1.getInputStream())));
     			
     			System.out.println(br.readLine());
     			
     			conn1.disconnect();*/
  			}
  			catch(Exception e)
  			{
  				 e.printStackTrace();
  			}
           
            file.close();
            
         }
		catch(Exception e){
			e.printStackTrace();
		}
	}

}
