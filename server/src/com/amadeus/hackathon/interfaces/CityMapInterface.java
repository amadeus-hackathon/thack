package com.amadeus.hackathon.interfaces;

import com.amadeus.hackathon.framework.json.JSONArray;
import com.amadeus.hackathon.framework.json.JSONObject;

/**
 * contract defined to access city information
 * @author ssinha
 *
 */
public interface CityMapInterface {
	
	/**
	 * get list of all cities with its information
	 * @return {@link JSONArray} list of cities
	 */
	public JSONArray getAllCities();
	
	/**
	 * get the nearest city from the provided lat and lng
	 * @param lat {@link Double} latitude value
	 * @param lng {@link Double} longitude value
	 * @return {@link JSONObject} city information
	 */
	public JSONObject getNearestCity(double lat, double lng);
}
