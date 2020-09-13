package com.bop.beans;

import java.io.Serializable;

public class Token implements Serializable {
	
	private static final long serialVersionUID = 1731033366215898352L;
	
	String token;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	@Override
	public String toString() {
		return "Token [token=" + token + "]";
	}

}
