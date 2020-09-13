package com.bop;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import com.bop.utils.PolicyUtils;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

// com.bop.AuthorizerLambda::handleRequest

public class AuthorizerLambda implements RequestStreamHandler {
	
	PolicyUtils policyUtils = new PolicyUtils();

	public void handleRequest(InputStream input, OutputStream output, Context context) throws IOException {
		
		LambdaLogger logger = context.getLogger();
		logger.log("Inside AuthorizerLambda....");
		
		try {
			
			BufferedReader bfr = new BufferedReader(new InputStreamReader(input));
			logger.log("read input stream successfully!");
			
			Gson gson = new Gson();
			JsonParser parser = new JsonParser();
			JsonObject lambdaEvent = (JsonObject) parser.parse(bfr);
			logger.log("parsed input stream successfully! lambdaEvent="+lambdaEvent);

			JsonElement headersElements = lambdaEvent.get("headers");
			JsonObject headers = headersElements.getAsJsonObject();
			logger.log("parsed headers successfully! headers="+headers);

			JsonElement tokenElement = headers.get("x-auth-bop");
			String token = gson.fromJson(tokenElement, String.class);
			logger.log("Token deserialized from Integration Request is, token="+token);
			
			JsonObject responseJson = null;
			policyUtils.setContext(context);
			if(token!=null && token.contains("prasath")) {
				logger.log("Bank of Prazy Authorization succeeded for token : "+token);
				responseJson = policyUtils.getPolicy("allow-policy.json");
			}
			else {
				logger.log("Bank of Prazy Authorization failed for token : "+token);
				responseJson = policyUtils.getPolicy("deny-policy.json");
			}
			
		    OutputStreamWriter writer = new OutputStreamWriter(output, "UTF-8");
		    writer.write(responseJson.toString());
		    writer.close();
			
		}
		catch(Exception ex) {
			logger.log("Exception thrown in AuthorizerLambda! Messsage="+ex.getMessage());
		}
		
		logger.log("Exit AuthorizerLambda!");
	}


}
