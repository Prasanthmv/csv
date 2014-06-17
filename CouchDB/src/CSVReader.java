 
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

 import org.codehaus.jettison.json.JSONObject;

 
public class CSVReader {

	public static void main(String[] args) {
		int count = 1;
		String ID;
		PushtoElasticsearch pushtoElasticsearch = new PushtoElasticsearch();
		// Input file which needs to be parsed
		//String fileToParse = args[0];
		// String fileToParse = "C:\\Users\\Jamsheer T\\Desktop\\csv\\prof.csv";
		BufferedReader fileReader = null;
		boolean initial = true;
		ArrayList<String> header = new ArrayList<String>();
		JSONObject record = new JSONObject();

		// Delimiter used in CSV file
		final String DELIMITER = ",";
		try {
			String line = "";
			int i = 0;
			// Create the file reader
			fileReader = new BufferedReader(new FileReader("G:\\Child.xls"));

			// Read the file line by line
			while ((line = fileReader.readLine()) != null) {

				i = 0;
				String[] tokens = line.split(DELIMITER);
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
				ID = (String) record.get(header.get(0));
				/*
				 * if (ID.startsWith("\"")) { ID = ID.substring(1); } if
				 * (ID.endsWith("\"")) { ID = ID.substring(0, ID.length() - 1);
				 * }
				 */

				System.out.println(record);
				System.out.println("Saved Record   ----------:" + count);
				System.out.println();
				count++;
				//pushtoElasticsearch.pushToElastic(ID, record.toString(),args[1]);

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				fileReader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
