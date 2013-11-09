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
public class EventbriteApiHandler implements EventApiInterface {
	
	private String eventApiURL;
	
	public EventbriteApiHandler() {
		this.eventApiURL = "https://www.eventbrite.com/json/event_search?app_key=ETMUQN2LATXAVR3H3J&max=100";
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
					if (response.get(IHackathonConstants.LIST_EVENTS) instanceof JSONArray) {
						
						JSONArray events = new JSONArray();
						for (int i = 1; i < response.getJSONArray(IHackathonConstants.LIST_EVENTS).length(); i++) {
							if (response.getJSONArray(IHackathonConstants.LIST_EVENTS).get(i) instanceof JSONObject 
									&& response.getJSONArray(IHackathonConstants.LIST_EVENTS).getJSONObject(i).get(IHackathonConstants.EVENT_KEY) instanceof JSONObject) {
								events.put(this.getProcessedEvent(response.getJSONArray(IHackathonConstants.LIST_EVENTS).getJSONObject(i).getJSONObject(IHackathonConstants.EVENT_KEY)));
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
		processed.put(IHackathonConstants.EVENT_CITY_NAME, "");
		
		if (event.has(IHackathonConstants.EVENT_TITLE)) {
			processed.put(IHackathonConstants.EVENT_NAME, event.get(IHackathonConstants.EVENT_TITLE));
		}
		
		// get the venue
		if (event.has(IHackathonConstants.EVENT_VENUE) && event.get(IHackathonConstants.EVENT_VENUE) instanceof JSONObject) {
			JSONObject venue = event.getJSONObject(IHackathonConstants.EVENT_VENUE);
			processed.put(IHackathonConstants.REGION_NAME, "");
			
			if (venue.has(IHackathonConstants.COUNTRY)) {
				processed.put(IHackathonConstants.COUNTRY_NAME, venue.get(IHackathonConstants.COUNTRY));
			}
			
			if (venue.has(IHackathonConstants.EVENT_CITY)) {
				processed.put(IHackathonConstants.EVENT_CITY_NAME, venue.get(IHackathonConstants.EVENT_CITY));
			}
			
			// set event longitude
			if (venue.has(IHackathonConstants.EVENT_LONGITUDE) && venue.get(IHackathonConstants.EVENT_LONGITUDE) instanceof Double) {
				processed.put(IHackathonConstants.CITY_LONGITUDE, venue.getDouble(IHackathonConstants.EVENT_LONGITUDE));
			}
			
			// set event longitude
			if (venue.has(IHackathonConstants.EVENT_LATITUDE) && venue.get(IHackathonConstants.EVENT_LATITUDE) instanceof Double) {
				processed.put(IHackathonConstants.CITY_LATITUDE, venue.getDouble(IHackathonConstants.EVENT_LATITUDE));
			}
		}
		
		
		// send description
		if (event.has(IHackathonConstants.EVENT_DESCRIPTION) && event.get(IHackathonConstants.EVENT_DESCRIPTION) instanceof String) {
			try {
				processed.put(IHackathonConstants.EVENT_DESCRIPTION, URLEncoder.encode((String)event.get(IHackathonConstants.EVENT_DESCRIPTION),"UTF-8"));
			} catch (JSONException | UnsupportedEncodingException e) {
				// log exception using log4j
			}
		}
		
		// set the image
		if (event.has(IHackathonConstants.EVENT_LOGO) && event.get(IHackathonConstants.EVENT_LOGO) instanceof String) {
			processed.put(IHackathonConstants.EVENT_IMAGE, event.getString(IHackathonConstants.EVENT_LOGO));
		}
		
		return processed;
	}
}
