package com.bop;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class AuthorizerLambda implements RequestHandler<String, Context> {

	public Context handleRequest(String input, Context context) {
		
		LambdaLogger logger = context.getLogger();
		
		logger.log("Inside AuthorizerLambda....");
		
		logger.log("Request received, input : "+input);
		
		if(input!=null) {
			logger.log("Successfully Authorized!");
		}
		
		logger.log("Exit AuthorizerLambda.");

		return context;
	}

}
