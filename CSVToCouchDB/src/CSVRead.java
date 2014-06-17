import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONObject;
import org.json.JSONArray;

import au.com.bytecode.opencsv.CSVReader;

public class CSVRead {

	@SuppressWarnings("resource")
	public static void main(String[] args) throws IOException{
 
		File folder = new File("G:\\myfolder\\Csv\\");
		File[] listOfFiles = folder.listFiles();		
		ArrayList<String> header = new ArrayList<String>();
		
		JSONObject final_onj = new JSONObject();
	 	for (int k = 0; k < listOfFiles.length; k++)
	 	{
			JSONArray arry = new JSONArray();
	 		String csvFilename = listOfFiles[k].getName();
 			CSVReader csvReader = new CSVReader(new FileReader(listOfFiles[k]));
			String[] rows = null;
			boolean initial = true;
			
			while ((rows = csvReader.readNext()) != null) {
				
				JSONObject record = new JSONObject();
				if (initial) {
					for (String headers : rows)
						header.add(headers);

					initial = false;
				} else {
					int a = 0;
					for (String values : rows) {
						try {
							record.put(header.get(a), values);
							a++;
						} catch (Exception e) {

							e.printStackTrace();
						}
					}
					arry.put(record);
				}
			} 	
 			final_onj.put("docs", arry);
 			InsertionToCouch m=new InsertionToCouch();
 		  	m.insertCouch(final_onj.toString(),csvFilename);
   		 }
	}

}
