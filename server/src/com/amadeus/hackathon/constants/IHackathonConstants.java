package com.amadeus.hackathon.constants;

/**
 * contain constants declaration
 * @author ssinha
 *
 */
public interface IHackathonConstants {
	
	/**
	 * request parameter to specify max number of cities
	 */
	public String MAX_CITIES = "max_cities";
	
	/**
	 * request parameter to specify which API to use,
	 * 0 - Eventful
	 * 1 - Eventbrite
	 */
	public String API_TYPE = "api_type";
	
	/**
	 * request parameter to specify max events which will be returned for each city
	 */
	public String MAX_EVENTS = "max_events";
	
	/**
	 * request parameter to pass current latitude
	 */
	public String POSITION_LATITUDE = "lat";
	
	/**
	 * request parameter to pass current longitude
	 */
	public String POSITION_LONGITUDE = "lng";
	
	/**
	 * JSON constant for message number key
	 */
	public String MESSAGE_NUMBER = "msg_number";
	
	/**
	 * JSON constant for message type key
	 */
	public String MESSAGE_TYPE = "msg_type";
	
	/**
	 * JSON constant for message description key
	 */
	public String MESSAGE_DESCRIPTION = "server_msg";
	
	/**
	 * JSON constant for sub messages key
	 */
	public String SUB_MESSAGES = "sub_messages";
	
	/**
	 * JSON constant for city code
	 */
	public String CITY_CODE = "code";
	
	/**
	 * JSON constant for city name
	 */
	public String CITY_NAME = "name";
	
	/**
	 * JSON constant for event API city name key
	 */
	public String EVENT_CITY_NAME = "city_name";
	
	/**
	 * JSON constant for event API city key
	 */
	public String EVENT_CITY = "city";
	
	/**
	 * JSON constant for latitude
	 */
	public String CITY_LATITUDE = "lat";
	
	/**
	 * JSON constant for longitude
	 */
	public String CITY_LONGITUDE = "lng";
	
	/**
	 * JSON constant for current city key
	 */
	public String EVENTS_CURRENT_CITY = "city";
	
	/**
	 * JSON constant for showing cities with events
	 */
	public String EVENTS_DEALS = "deals";
	
	/**
	 * JSON constant for event name key
	 */
	public String EVENT_NAME = "event_name";
	
	/**
	 * JSON constant for region name key
	 */
	public String REGION_NAME = "region_name";
	
	/**
	 * JSON constant for country name key
	 */
	public String COUNTRY_NAME = "country_name";
	
	/**
	 * JSON constant for country key
	 */
	public String COUNTRY = "country";
	
	/**
	 * JSON constant for event description key
	 */
	public String EVENT_DESCRIPTION = "description";
	
	/**
	 * JSON constant for event image key
	 */
	public String EVENT_IMAGE = "image";
	
	/**
	 * JSON constant for TYPE of EVENT IMAGE key
	 */
	public String IMAGE_MEDIUM = "medium";
	
	/**
	 * JSON constant for IMAGE URL key
	 */
	public String IMAGE_URL = "url";
	
	/**
	 * JSON constant for latitude of an event
	 */
	public String EVENT_LATITUDE = "latitude";
	
	/**
	 * JSON constant for longitude of an event
	 */
	public String EVENT_LONGITUDE = "longitude";
	
	/**
	 * JSON constant for event title
	 */
	public String EVENT_TITLE = "title";
	
	/**
	 * JSON constant for airport key
	 */
	public String EVENT_DEALS_AIRPORT = "airport";
	
	/**
	 * JSON constant to denote list of events
	 */
	public String LIST_EVENTS = "events";
	
	/**
	 * JSON constant for event
	 */
	public String EVENT_KEY = "event";
	
	/**
	 * JSON constant for event logo
	 */
	public String EVENT_LOGO = "logo";
	
	/**
	 * JSON constant for event venue
	 */
	public String EVENT_VENUE = "venue";
}
