package com.amadeus.hackathon.interfaces;

import java.io.IOException;

import com.amadeus.hackathon.framework.json.JSONArray;
import com.amadeus.hackathon.framework.json.JSONObject;

/**
 * defines the contract to get list of events
 * @author ssinha
 *
 */
public interface EventApiInterface {
	
	/**
	 * get list of all the events around the globe for the provided input
	 * @param input {@link JSONObject} input parameters
	 * @return {@link JSONArray} list of events
	 * @throws IOException
	 */
	public JSONArray getAllEvents(JSONObject input) throws IOException;
	
	/**
	 * returns the URL used to get events
	 * @return {@link String} URL
	 */
	public String getEventApiURL();
}
