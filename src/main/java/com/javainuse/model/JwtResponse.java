package com.javainuse.model;

import java.io.Serializable;

public class JwtResponse implements Serializable {

	private static final long serialVersionUID = -8091879091924046844L;
	private final String jwttoken;
	private final DAOUser userData;

	public JwtResponse(String jwttoken, DAOUser userData) {
		this.jwttoken = jwttoken;
		this.userData = userData;
	}

	public DAOUser getUserData() {return userData; }

	public String getToken() {
		return this.jwttoken;
	}
}