package com.amadeus.hackathon.servlet;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.amadeus.hackathon.constants.IHackathonConstants;
import com.amadeus.hackathon.framework.json.JSONArray;
import com.amadeus.hackathon.framework.json.JSONObject;
import com.amadeus.hackathon.interfaces.CityMapInterface;
import com.amadeus.hackathon.interfaces.EventApiInterface;
import com.amadeus.hackathon.utils.event.EventbriteApiHandler;
import com.amadeus.hackathon.utils.event.EventfulApiHandler;
import com.amadeus.hackathon.utils.file.CityMapUtil;

/**
 * servlet class to process request to get list of events
 * @author ssinha
 *
 */
public class GetTopEventsServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		this.doPost(request, response);
	}
	
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) {
		
		int api = 0;
		double lat = 0;
		double lng = 0;
		int maxCities = 3;
		int maxEvents = 5;
		JSONArray errors = new JSONArray();
		
		// check for mandatory parameters
		if (request.getParameter(IHackathonConstants.API_TYPE) != null) {
			try {
				api = Integer.parseInt(request.getParameter(IHackathonConstants.API_TYPE));
			} catch (NumberFormatException e) {
				// TODO log exception using log4j
			}
		}
		
		// check for mandatory parameters
		if (request.getParameter(IHackathonConstants.MAX_CITIES) != null) {
			try {
				maxCities = Integer.parseInt(request.getParameter(IHackathonConstants.MAX_CITIES));
			} catch (NumberFormatException e) {
				// TODO log exception using log4j
			}
		}
		
		// check for mandatory parameters
		if (request.getParameter(IHackathonConstants.MAX_EVENTS) != null) {
			try {
				maxCities = Integer.parseInt(request.getParameter(IHackathonConstants.MAX_EVENTS));
			} catch (NumberFormatException e) {
				// TODO log exception using log4j
			}
		}
		
		// check for mandatory parameters
		if (request.getParameter(IHackathonConstants.POSITION_LATITUDE) != null) {
			try {
				lat = Double.parseDouble(request.getParameter(IHackathonConstants.POSITION_LATITUDE));
			} catch (NumberFormatException e) {
				errors.put(this.createMessage("1001", "Please provide a valid value for latitude", "E", null));	
				// TODO log exception using log4j
			}
		} else {
			errors.put(this.createMessage("1002", "Please provide a value for latitude", "E", null));
		}
		
		// check for mandatory parameters
		if (request.getParameter(IHackathonConstants.POSITION_LONGITUDE) != null) {
			try {
				lng = Double.parseDouble(request.getParameter(IHackathonConstants.POSITION_LONGITUDE));
			} catch (NumberFormatException e) {
				errors.put(this.createMessage("1003", "Please provide a valid value for longitude", "E", null));
				// TODO log exception using log4j
			}
		} else {
			errors.put(this.createMessage("1004", "Please provide a value for longitude", "E", null));
		}
		
		if (errors.length() == 0) {
			
			// utility classes
			CityMapInterface cityUtil = new CityMapUtil();
			EventApiInterface eventUtil = null;
			
			switch(api) {
				case 1:
					eventUtil = new EventbriteApiHandler();
					break;
				case 0:
				default:
					eventUtil = new EventfulApiHandler();
					break;
			}
			
			// process only when no error
			Map<String, JSONObject> topEvents = new HashMap<String, JSONObject>();
			
			// set current city information
			JSONObject currentCity = cityUtil.getNearestCity(lat, lng);
			topEvents.put(IHackathonConstants.EVENTS_CURRENT_CITY, currentCity);
			
			// create input object for fetching events
			JSONObject input = new JSONObject();
			for (Object key: request.getParameterMap().keySet()) {
				if (key instanceof String) {
					if (request.getParameterMap().get(key) instanceof String) {
						input.put((String)key, request.getParameterMap().get(key));
					} else if (request.getParameterMap().get(key) instanceof Collection<?>) {
						input.put((String)key, ((Collection<?>)request.getParameterMap().get(key)).toArray()[0]);
					} else if (request.getParameterMap().get(key) instanceof Object[]) {
						input.put((String)key, ((Object[])request.getParameterMap().get(key))[0]);
					}
				}
			}
			
			// create deals JSON
			Map<String, JSONObject> deals = new HashMap<String, JSONObject>();
			
			// get list of events
			JSONArray events = null;
			try {
				events = eventUtil.getAllEvents(input);
			} catch (IOException e) {
				// log the error using log4j
			}
			
			for (int i = 0; events != null && i < events.length(); i++) {
				
				// if maximum cities parsed
				if (deals.keySet().size() == maxCities) {
					break;
				}
				
				if (events.get(i) instanceof JSONObject) {
					
					// get reference
					JSONObject event = (JSONObject)events.get(i);
					
					// if city name is a string and not same as current city
					if (event.get(IHackathonConstants.EVENT_CITY_NAME) instanceof String 
							&& !event.getString(IHackathonConstants.EVENT_CITY_NAME).equals(currentCity.getString(IHackathonConstants.CITY_NAME))) {
						
						String cityName = event.getString(IHackathonConstants.EVENT_CITY_NAME);
						if (cityName != null && !cityName.trim().equals("")) {
							if (!deals.containsKey(cityName)) {
								
								JSONObject city = new JSONObject();
								
								// add city information
								city.put(IHackathonConstants.LIST_EVENTS, new JSONArray().put(event));
								city.put(IHackathonConstants.EVENT_DEALS_AIRPORT, cityUtil.getNearestCity(event.getDouble(IHackathonConstants.CITY_LATITUDE), event.getDouble(IHackathonConstants.CITY_LONGITUDE)));
								
								// add it to deals
								deals.put(cityName, city);
							} else {
								
								// add event only if events less than max
								if (deals.get(cityName).get(IHackathonConstants.LIST_EVENTS) instanceof JSONArray 
										&& deals.get(cityName).getJSONArray(IHackathonConstants.LIST_EVENTS).length() < maxEvents) {
	
									deals.get(cityName).getJSONArray(IHackathonConstants.LIST_EVENTS).put(event);
									
									if (!(deals.get(cityName).get(IHackathonConstants.EVENT_DEALS_AIRPORT) instanceof JSONObject)) {
										deals.get(cityName).put(IHackathonConstants.EVENT_DEALS_AIRPORT, cityUtil.getNearestCity(event.getDouble(IHackathonConstants.CITY_LATITUDE), event.getDouble(IHackathonConstants.CITY_LONGITUDE)));
									}
								}
								
							}
						}
					}
				}
			}
			
			// add deals to events
			topEvents.put(IHackathonConstants.EVENTS_DEALS, new JSONObject(deals));
			
			// write response to output stream
			JSONObject output = new JSONObject(topEvents);
			this.writeToOutputStream(response, output.toString());
			
		} else {
			// send error JSON to caller
			this.writeToOutputStream(response, new JSONObject().put("message", errors).toString());
		}
		
	}
	
	/**
	 * writes the response to output stream
	 * @param response {@link HttpServletResponse}
	 * @param data {@link String} data to be send in output stream
	 * @return {@link Boolean} <code>true</code> if success
	 */
	private boolean writeToOutputStream(HttpServletResponse response, String data) {
		
		BufferedWriter bw  = null;
		boolean closeAttempted = false;
		
		try {
			// create a output stream writer
			bw = new BufferedWriter(new OutputStreamWriter(response.getOutputStream()));
			
			// write to stream
			bw.write(data);
			
			// set boolean flag
			// this indicates that an attempt is made to close this stream
			closeAttempted = true;
			bw.close();
		} catch (IOException e) {
			
			// TODO log error using log4j
			
			return false;
		} finally {
			if (bw != null && !closeAttempted) {
				try {
					bw.close();
				} catch (IOException e) {
					// TODO use log4j to log this error
				}
			}
		}
		
		return true;
	}
	
	/**
	 * creates a message Object
	 * @param number {@link String} message number
	 * @param message {@link String} error message
	 * @param type {@link String} type of message. e.g. ERROR - 'E', WARNING - 'W', INFORMATION - 'I'
	 * @param suberrors {@link JSONArray} list of messages associated with this message
	 * @return {@link JSONObject} message object
	 */
	private JSONObject createMessage(String number, String message, String type, JSONArray subMessages) {
		JSONObject json = new JSONObject();
		json.put(IHackathonConstants.MESSAGE_NUMBER, number);
		json.put(IHackathonConstants.MESSAGE_DESCRIPTION, message);
		json.put(IHackathonConstants.MESSAGE_TYPE, type);
		json.put(IHackathonConstants.SUB_MESSAGES, subMessages);
		
		return json;
	}
}
