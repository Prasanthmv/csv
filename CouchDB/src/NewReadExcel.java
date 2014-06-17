import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONArray;
import org.json.JSONObject;

public class NewReadExcel {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {

			ArrayList<String> files = new ArrayList<String>();

			files.add("Installation.xlsx");
		 /*	files.add("Site.xlsx");
			files.add("Facility.xlsx");
			files.add("Floor.xlsx");
			files.add("Space.xlsx");
			files.add("Component.xlsx"); */

			/*ArrayList<String> identifier = new ArrayList<>();
			identifier.add("InstallationID_");
			identifier.add("SiteID_");
			identifier.add("FacilityID_");
			identifier.add("FloorID_");
			identifier.add("SpaceID_");
			identifier.add("ComponentID_");*/

			ArrayList<String> typeField = new ArrayList<>();
			typeField.add("Installation");
			/*typeField.add("Site");
			typeField.add("Facility");
			typeField.add("Floor");
			typeField.add("Space");
			typeField.add("Component");*/

			/*ArrayList<String> parentFields = new ArrayList<>();
			parentFields.add("No");// no id for install table
			parentFields.add("InstallationID_");// install id for site table
			parentFields.add("SiteID_");// site id for facility table
			parentFields.add("FacilityID_");// facilityid for floor table
			parentFields.add("FloorID_");// FloorID for space table
			parentFields.add("SpaceID_");// SpaceID for component table*/

			for (int a = 0; a < files.size(); a++) {

				System.out.println("Inserting File [" + (a + 1) + "]"+ files.get(a));
				FileInputStream file = new FileInputStream(new File(
						"G:\\FEDiFM-CoreDataSample\\" + files.get(a)));

				// Create Workbook instance holding reference to .xlsx file
				XSSFWorkbook workbook = new XSSFWorkbook(file);

				// Get first/desired sheet from the workbook
				XSSFSheet sheet = workbook.getSheetAt(0);

				int cellSize = sheet.getRow(0).getLastCellNum();
				// Start constructing JSON.

				// Iterate through the rows.

				int i = 0;
				ArrayList<Object> headerArray = new ArrayList<Object>();

				ArrayList<Object> allValuearray = new ArrayList<>();

				for (int rowNum = sheet.getFirstRowNum(); rowNum <= sheet
						.getLastRowNum(); rowNum++) {
					Row r = sheet.getRow(rowNum);
					ArrayList<Object> valueArray = new ArrayList<>();
					for (int cn = 0; cn < cellSize; cn++) {
						// System.out.println("cn "+cn);
						Object value = "";
						Cell cell = r.getCell(cn, Row.RETURN_BLANK_AS_NULL);
						if (cell == null) {
							value = "empty";
						} else {
							if (cell.getCellType() == 0
									|| cell.getCellType() == 3) {
								value = Integer.valueOf((int) cell
										.getNumericCellValue());
							} else if (cell.getCellType() == 1) {
								value = cell.getStringCellValue();
							}
						}
						if (rowNum == 0) {
							headerArray.add(value);
						} else {
							valueArray.add(value);
						}
					}
					if (rowNum != 0)
						allValuearray.add(valueArray);
				}
				// Create the JSON.

				JSONObject d = new JSONObject();
				JSONArray eachCellJsonAry = new JSONArray();
				int n = 1;
				for (int j = 0; j < allValuearray.size(); j++) {
					ArrayList eachValue = (ArrayList) allValuearray.get(j);
					JSONObject valuekeyPair = new JSONObject();
					//valuekeyPair.put("_id",/*identifier.get(a)+ String.valueOf(*/""+eachValue.get(0)/*)*/);
					valuekeyPair.put("docType", typeField.get(a));

					/*if (typeField.get(a).equals("Installation"))
						valuekeyPair.put("parentField", parentFields.get(a));
					else if (typeField.get(a).equals("Site")
							|| typeField.get(a).equals("Facility"))
						valuekeyPair.put("parentField", parentFields.get(a)
								+ String.valueOf(eachValue.get(6)));
					else if (typeField.get(a).equals("Floor")
							|| typeField.get(a).equals("Component"))
						valuekeyPair.put("parentField", parentFields.get(a)
								+ String.valueOf(eachValue.get(8)));
					else if (typeField.get(a).equals("Space"))
						valuekeyPair.put("parentField", parentFields.get(a)
								+ String.valueOf(eachValue.get(10)));*/

					for (int k = 0; k < headerArray.size(); k++) {

						/*if (headerArray.get(k).equals(
								identifier.get(a).split("_")[0])) {
							valuekeyPair.put((String) headerArray.get(k),
									identifier.get(a) + eachValue.get(k));

						} else {*/
							valuekeyPair.put((String) headerArray.get(k), ""
									+ eachValue.get(k));
						//}

					}
					eachCellJsonAry.put(j, valuekeyPair);
					/*if (allValuearray.size() < 1000) {

						URL puturl = new URL(
								"http://localhost:5984/maxpersist/_bulk_docs");
						HttpURLConnection conn = (HttpURLConnection) puturl
								.openConnection();
						conn.setDoOutput(true);
						conn.setRequestMethod("POST");
						conn.setRequestProperty("Content-Type",
								"application/json");

						JSONObject json = new JSONObject();
						json.put("docs", eachCellJsonAry);
						String input = json.toString();
						OutputStream os = conn.getOutputStream();
						os.write(input.getBytes());
						os.flush();
						if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
							throw new RuntimeException(
									"Failed : HTTP error code : "
											+ conn.getResponseCode());
						} else {
							// System.out.println(conn.getResponseCode());
							System.out.println(j + "*****DATA INSERTED*****");
						}
						conn.disconnect();

					} else if (j == n * 1000) {*/
						
					/*}*/
				}

				URL puturl = new URL(
						"http://localhost:5984/fedifm/_bulk_docs");
				HttpURLConnection conn = (HttpURLConnection) puturl
						.openConnection();
				conn.setDoOutput(true);
				conn.setRequestMethod("POST");
				conn.setRequestProperty("Content-Type",
						"application/json");

				JSONObject json = new JSONObject();
				json.put("docs", eachCellJsonAry);
				String input = json.toString();
				
				System.out.println(input);
				OutputStream os = conn.getOutputStream();
				os.write(input.getBytes());
				os.flush();
				if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
					throw new RuntimeException(
							"Failed : HTTP error code : "
									+ conn.getResponseCode());
				} else {
					n++;
					System.out.println(conn.getResponseCode());
					System.out.println( "*****DATA INSERTED*****");
				}
				conn.disconnect();
				file.close();

				System.out.println("Inserting File [" + (a + 1) + "]"
						+ files.get(a) + " Completed..........");
				System.out.println(eachCellJsonAry);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}

	}

}
