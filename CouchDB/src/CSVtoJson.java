 
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.json.JSONArray;

/**
 * 
 * @author Jamsheer T +91 9846716175
 */
public class CSVtoJson {

	public static void main(String[] args) {
		int i;
		String line = "";
		
		
		File folder = new File("G:\\fedifm\\Excels\\");
		File[] listOfFiles = folder.listFiles();
		
		
		BufferedReader fileReader = null;
		boolean initial = true;
		ArrayList<String> header = new ArrayList<String>();
		
		JSONArray arry=new JSONArray();
		JSONArray arry1=new JSONArray();
		try {
			for (int k = 0; k < listOfFiles.length; k++)
			{
				String fileToParse =listOfFiles[k].getName(); //"G:\\fedifm\\Address.csv";
				fileReader = new BufferedReader(new FileReader(fileToParse));
	 			while ((line = fileReader.readLine()) != null) {
					JSONObject record = new JSONObject();
					i = 0;
					String[] tokens = line.split(",");
					if (initial) {

						for (String token : tokens) {
	 						if (token.startsWith("\"")) {
								token = token.substring(1);
							}
							if (token.endsWith("\"")) {
								token = token.substring(0, token.length() - 1);
							}
							
							header.add(token);
						}
						initial = false;
						continue;

					}
	 				// Get all tokens available in line
					for (String token : tokens) {
						
						if (token.startsWith("\"")) {
							token = token.substring(1);
						}
						if (token.endsWith("\"")) {
							token = token.substring(0, token.length() - 1);
						}
						record.put(header.get(i).toString(), token);
						i++;
						if (i == header.size())
							break;

					}
					arry.put(record);
	 			}
				
			}
			
 		 
 			/*FileWriter fw = new FileWriter("G:\\fedifm\\Address.json");
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(arry.toString());
			bw.close();*/
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {

			e.printStackTrace();
		}

	}

}
