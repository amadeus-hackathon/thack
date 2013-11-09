package com.amadeus.hackathon.utils.scrapping;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import com.amadeus.hackathon.framework.json.JSONArray;
import com.amadeus.hackathon.framework.json.JSONObject;

/**
 * scrap clear trip page to get list of packages
 * @author ssinha
 *
 */
public class ClearTripScrappingUtil {
	
	private String clearTripURL;
	
	public ClearTripScrappingUtil() {
		this.clearTripURL = "http://www.cleartrip.com/packages/search/results?num_rooms=1";
	}
	
	/**
	 * get list of packages
	 * @param input {@link JSONObject} input parameters
	 * @return {@link JSONObject}
	 * @throws IOException 
	 */
	public JSONObject getPackages(JSONObject input) throws IOException {
		
		int max_packages = 5;
		if (input.has("max_package") && input.get("max_package") instanceof String) {
			try {
				max_packages = Integer.parseInt(input.getString("max_package"));
			} catch (NumberFormatException e) {
				// log error using log4j
			}
		}
		
		// create a URL for establishing connection
		StringBuilder urlParams = new StringBuilder();
		for (Object obj: input.keySet()) {
			// if a string is passed as both key and value then only proceed
			// these key and values will be used to create URL parameters which can only be String
			if (obj instanceof String 
					&& !((String)obj).equals("max_packages")
					&& input.get((String)obj) instanceof String) {

				urlParams.append("&");
				urlParams.append(URLEncoder.encode((String)obj, "UTF-8"));
				urlParams.append("=");
				urlParams.append(URLEncoder.encode(input.getString((String)obj), "UTF-8"));
			}
		}

		URL url = new URL(this.clearTripURL + urlParams.toString());
		HttpURLConnection connection = (HttpURLConnection)url.openConnection();
		connection.setRequestMethod("GET");
		connection.setReadTimeout(600000);
		connection.setConnectTimeout(600000);
		connection.connect();
			
		int status = connection.getResponseCode();
		switch (status) {
			case 200:
			case 201:
				
				// read the input stream if connection response is SUCCESS
				BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				StringBuilder responseData = new StringBuilder();
				
				// read all the contents
				String line = br.readLine();
				while (line != null) {
					responseData.append(line);
					line = br.readLine();
				}
				
				// create the JSONObject to 
				// store combo deal with min price
				JSONObject minPriceCombo = null;
				
				// return list of packages
				JSONArray packages = new JSONArray();
				JSONObject response = new JSONObject(responseData.toString());
				if (response.has("ht") && response.has("rr") && response.has("fl")
						&& response.get("ht") instanceof JSONArray 
						&& response.get("rr") instanceof JSONObject
						&& response.get("fl") instanceof JSONObject) {
					
					JSONObject rr = response.getJSONObject("rr");
					JSONObject fl = response.getJSONObject("fl");
					
					if (fl.has("jsons") && fl.get("jsons") instanceof JSONObject) {
						JSONObject jsons = fl.getJSONObject("jsons");
						if (jsons.has("combo_deals_tabs") && jsons.get("combo_deals_tabs") instanceof JSONArray) {

							for (int i = 0; i < jsons.getJSONArray("combo_deals_tabs").length(); i++) {
								if (jsons.getJSONArray("combo_deals_tabs").get(i) instanceof JSONObject) {
									
									if (minPriceCombo != null) {
										
										JSONObject currCombo = jsons.getJSONArray("combo_deals_tabs").getJSONObject(i);										
										if (currCombo.has("pr") 
												&& currCombo.get("pr") instanceof Double 
												&& currCombo.getDouble("pr") < minPriceCombo.getDouble("pr")) {
											minPriceCombo = currCombo;
										}
										
									} else {
										if (jsons.getJSONArray("combo_deals_tabs").getJSONObject(i).has("pr") 
												&& jsons.getJSONArray("combo_deals_tabs").getJSONObject(i).get("pr") instanceof Double) {
											minPriceCombo= jsons.getJSONArray("combo_deals_tabs").getJSONObject(i);
										}
									}
								}
							}
						}
					}
					
					for (int i =0; i < response.getJSONArray("ht").length(); i++) {
						
						if (packages.length() >= max_packages) {
							break;
						}
						
						if (response.getJSONArray("ht").get(i) instanceof JSONObject) {
							JSONObject ht = response.getJSONArray("ht").getJSONObject(i);
							
							// package is available for hotel
							if (ht.has("id") 
									&& ht.get("id") instanceof String 
									&& rr.has(ht.getString("id")) 
									&& rr.get(ht.getString("id")) instanceof JSONObject) {
								
								// create package information
								packages.put(this.processJSON(ht, rr.getJSONObject(ht.getString("id"))));
							}
						}
					}					
				}
				
				// get image for airline
				if (minPriceCombo.has("al") && minPriceCombo.get("al") instanceof String) {
					minPriceCombo.put("img", "http://www.cleartrip.com/images/air_logos/" + minPriceCombo.getString("al"));
				}	
				
				JSONObject result = new JSONObject();
				result.put("hotels", packages);
				result.put("flight", minPriceCombo);
				
				return result;
			default:
				break;
		}
		
		return null;
	}
	
	/**
	 * process the JSON for hotel and flight to get package information
	 * @param ht {@link JSONObject} hotel JSON data
	 * @param rr {@link JSONObject} package related information
	 * @return {@link JSONObject} packages
	 */
	private JSONObject processJSON(JSONObject ht, JSONObject rr) {
		
		JSONObject pkg = new JSONObject();
		
		// hotel name
		if (ht.has("nm")) {
			pkg.put("htNm", ht.get("nm"));
		}
		
		if (ht.has("id") && ht.has("im") && ht.get("id") instanceof String && ht.get("im") instanceof String) {
			String id = ht.getString("id");
			StringBuilder url = new StringBuilder("http://www.cleartrip.com/places/hotels/");
			url.append(id.substring(0, id.length() - 2));
			url.append("/");
			url.append(id);
			url.append("/");
			url.append(ht.getString("im"));
			
			// set the hotel image
			pkg.put("htImg", url.toString());
		}
		
		if (ht.has("ar")) {
			pkg.put("ar", ht.get("ar"));
		}
		
		// total price
		if (rr.has("pf")) {
			pkg.put("pf", rr.get("pf"));
		}
		
		// total price
		if (rr.has("ps")) {
			pkg.put("ps", rr.get("ps"));
		}
		
		// total price
		if (ht.has("latlng")) {
			pkg.put("latlng", ht.get("latlng"));
		}
		
		return pkg;
	}
}
