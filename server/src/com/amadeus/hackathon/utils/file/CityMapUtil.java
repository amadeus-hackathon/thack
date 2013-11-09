package com.amadeus.hackathon.utils.file;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.amadeus.hackathon.constants.IHackathonConstants;
import com.amadeus.hackathon.framework.json.JSONArray;
import com.amadeus.hackathon.framework.json.JSONObject;
import com.amadeus.hackathon.interfaces.CityMapInterface;

/**
 * read the list of cities with coordinates and other useful information
 * @author ssinha
 *
 */
public final class CityMapUtil implements CityMapInterface {
	
	private JSONArray cities;
	
	@Override
	public JSONArray getAllCities() {
		
		if (cities == null || cities.length() == 0) {
		
			// create an array to store info
			cities = new JSONArray();
			
			// read the cities data from csv file
			BufferedReader br = new BufferedReader(new InputStreamReader(CityMapUtil.class.getClassLoader().getResourceAsStream("data/cities.csv")));
			
			try {
				// read content line by line
				String line = br.readLine();
				while (line != null) {
				
					String[] tokens = line.split("\\^");
					
					if (tokens[5] != null && tokens[6] != null 
							&& !tokens[5].equalsIgnoreCase("NULL")  
							&& !tokens[6].equalsIgnoreCase("NULL")) {
					
						// create a city object
						JSONObject city = new JSONObject();
						city.put(IHackathonConstants.CITY_CODE, tokens[0]);
						city.put(IHackathonConstants.CITY_NAME, tokens[1]);
						city.put(IHackathonConstants.CITY_LATITUDE, Double.parseDouble(tokens[5])/Math.pow(10, 5));						
						city.put(IHackathonConstants.CITY_LONGITUDE, Double.parseDouble(tokens[6])/Math.pow(10, 5));
												
						// add city
						cities.put(city);
					}
					
					// read next line
					line = br.readLine();
				}
				
			} catch (IOException e) {
				// TODO log exception using log4j
			}
		}
		
		return cities;
	}
	
	@Override
	public JSONObject getNearestCity(double lat, double lng) {
		double distance = -1;
		JSONObject response = null;
		
		JSONArray cities = this.getAllCities();
		for (int i = 0; i < cities.length(); i++) {
			if (cities.get(i) instanceof JSONObject) {
				
				JSONObject city = cities.getJSONObject(i);
				
				// calculate distance
				double aptLat = city.getDouble("lat");
				double aptLng = city.getDouble("lng");
				
				// use formula distance = ((x2-x1)^2 + (y2-y1)^2)^(1/2)
				double latDiff = aptLat - lat;
				double lngDiff = aptLng - lng;
				double cityDist = Math.sqrt((latDiff * latDiff) + (lngDiff * lngDiff));
				if (distance == -1 || distance > cityDist) {
					response = city;
					distance = cityDist;
				}
			}
		}
		
		return response;
	}
}