package com.amadeus.hackathon.servlet;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Collection;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.amadeus.hackathon.framework.json.JSONObject;
import com.amadeus.hackathon.utils.scrapping.ClearTripScrappingUtil;

/**
 * servlet used to return list of packages based on input criteria
 * @author ssinha
 *
 */
public class GetClearTripPackages extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		this.doPost(request, response);
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) {
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
		
		ClearTripScrappingUtil scrapper = new ClearTripScrappingUtil();
		try {
			JSONObject result = scrapper.getPackages(input);
			
			BufferedWriter bw  = null;
			boolean closeAttempted = false;
			
			try {
				// create a output stream writer
				bw = new BufferedWriter(new OutputStreamWriter(response.getOutputStream()));
				
				// write to stream
				bw.write(result.toString());
				
				// set boolean flag
				// this indicates that an attempt is made to close this stream
				closeAttempted = true;
				bw.close();
			} catch (IOException e) {
				
				// TODO log error using log4j
			} finally {
				if (bw != null && !closeAttempted) {
					try {
						bw.close();
					} catch (IOException e) {
						// TODO use log4j to log this error
					}
				}
			}
		} catch (IOException e) {
			// log error using log4j
		}
	}
}
