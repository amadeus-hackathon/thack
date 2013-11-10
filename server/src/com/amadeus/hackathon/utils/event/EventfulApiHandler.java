package com.amadeus.hackathon.utils.event;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import com.amadeus.hackathon.constants.IHackathonConstants;
import com.amadeus.hackathon.framework.json.JSONArray;
import com.amadeus.hackathon.framework.json.JSONException;
import com.amadeus.hackathon.framework.json.JSONObject;
import com.amadeus.hackathon.interfaces.EventApiInterface;

/**
 * implementation to get events
 * @author ssinha
 *
 */
public class EventfulApiHandler implements EventApiInterface {
	
	private String eventApiURL;
	
	public EventfulApiHandler() {
		this.eventApiURL = "http://api.eventful.com/json/events/search?app_key=8FNgK29dwTr5KQ7m&page_size=100";
	}
	
	@Override
	public JSONArray getAllEvents(JSONObject input) throws IOException {
		
		// create a URL for establishing connection
		StringBuilder urlParams = new StringBuilder();
		for (Object obj: input.keySet()) {
			// if a string is passed as both key and value then only proceed
			// these key and values will be used to create URL parameters which can only be String
			if (obj instanceof String 
					&& input.get((String)obj) instanceof String 
					&& !input.getString((String)obj).equals("app_key") 
					&& !input.getString((String)obj).equals("page_size")) {

				urlParams.append("&");
				urlParams.append(URLEncoder.encode((String)obj, "UTF-8"));
				urlParams.append("=");
				urlParams.append(URLEncoder.encode(input.getString((String)obj), "UTF-8"));
			}
		}
		
		URL url = new URL(this.getEventApiURL() + urlParams.toString());
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
				
				// convert string to JSON
				try {
					
					// parse JSON
					JSONObject response = new JSONObject(responseData.toString());
					if (response.get(IHackathonConstants.LIST_EVENTS) instanceof JSONObject 
							&& response.getJSONObject(IHackathonConstants.LIST_EVENTS).get(IHackathonConstants.EVENT_KEY) instanceof JSONArray) {
						
						JSONArray events = new JSONArray();
						for (int i = 0; i < response.getJSONObject(IHackathonConstants.LIST_EVENTS).getJSONArray(IHackathonConstants.EVENT_KEY).length(); i++) {
							if (response.getJSONObject(IHackathonConstants.LIST_EVENTS).getJSONArray(IHackathonConstants.EVENT_KEY).get(i) instanceof JSONObject) {
								events.put(this.getProcessedEvent(response.getJSONObject(IHackathonConstants.LIST_EVENTS).getJSONArray(IHackathonConstants.EVENT_KEY).getJSONObject(i)));
							}
						}
						
						return events;
					}
				} catch (JSONException e) {
					// log error for log4j
				}
				
				break;
			default:
				break;
		}
		
		// no events found
		return new JSONArray();
	}

	@Override
	public String getEventApiURL() {
		return this.eventApiURL;
	}
	
	/**
	 * process an event and send only the required data back
	 * @param event {@link JSONObject} event information
	 * @return {@link JSONObject} processed event information
	 */
	private JSONObject getProcessedEvent(JSONObject event) {
		
		JSONObject processed = new JSONObject();
		processed.put(IHackathonConstants.EVENT_CITY_NAME, event.get(IHackathonConstants.EVENT_CITY_NAME));
		processed.put(IHackathonConstants.EVENT_NAME, event.get(IHackathonConstants.EVENT_TITLE));
		processed.put(IHackathonConstants.REGION_NAME, event.get(IHackathonConstants.REGION_NAME));
		processed.put(IHackathonConstants.COUNTRY_NAME, event.get(IHackathonConstants.COUNTRY_NAME));
		
		// set event longitude
		if (event.get(IHackathonConstants.EVENT_LONGITUDE) instanceof String) {
			try {
				processed.put(IHackathonConstants.CITY_LONGITUDE, Double.parseDouble(event.getString(IHackathonConstants.EVENT_LONGITUDE)));
			} catch (NumberFormatException e) {
				// log error using log4j
			}
		}
		
		// set event latitude
		if (event.get(IHackathonConstants.EVENT_LATITUDE) instanceof String) {
			try {
				processed.put(IHackathonConstants.CITY_LATITUDE, Double.parseDouble(event.getString(IHackathonConstants.EVENT_LATITUDE)));
			} catch (NumberFormatException e) {
				// log error using log4j
			}
		}
		
		// send description
		if (event.get(IHackathonConstants.EVENT_DESCRIPTION) instanceof String) {
			try {
				processed.put(IHackathonConstants.EVENT_DESCRIPTION, URLEncoder.encode(event.getString(IHackathonConstants.EVENT_DESCRIPTION),"UTF-8"));
			} catch (JSONException | UnsupportedEncodingException e) {
				// log exception using log4j
			}
		}
		
		if (event.get(IHackathonConstants.EVENT_IMAGE) instanceof JSONObject 
				&& event.getJSONObject(IHackathonConstants.EVENT_IMAGE).get(IHackathonConstants.IMAGE_MEDIUM) instanceof JSONObject 
				&& event.getJSONObject(IHackathonConstants.EVENT_IMAGE).getJSONObject(IHackathonConstants.IMAGE_MEDIUM).get(IHackathonConstants.IMAGE_URL) instanceof String) {
			processed.put(IHackathonConstants.EVENT_IMAGE, event.getJSONObject(IHackathonConstants.EVENT_IMAGE).getJSONObject(IHackathonConstants.IMAGE_MEDIUM).get(IHackathonConstants.IMAGE_URL));
		}
		
		return processed;
	}

}
