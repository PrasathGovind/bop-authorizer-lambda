package com.bop.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class PolicyUtils {
	
	Context context;
	
	ClassLoader classLoader = getClass().getClassLoader();
	
	public JsonObject getPolicy(String fileName) {
		
		LambdaLogger logger = context.getLogger();
		logger.log("Inside getPolicy....");
		
		JsonObject allowPolicy = null;
		try {
        	
        	InputStream inputStream = classLoader.getResourceAsStream(fileName);
        	BufferedReader bfr = new BufferedReader(new InputStreamReader(inputStream));
        	logger.log("Successfully read inputstream!");
        	
        	JsonParser parser = new JsonParser();
        	allowPolicy = (JsonObject) parser.parse(bfr);
        	logger.log("Successfully parsed policy document "+fileName);
        	
        } catch (Exception e) {
        	logger.log("Exception thrown in getPolicy. Message="+e.getMessage());
        }
		
		logger.log("Exit getPolicy!");
		return allowPolicy;
	}

	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}

}
