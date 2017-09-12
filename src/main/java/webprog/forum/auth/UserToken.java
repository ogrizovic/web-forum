package webprog.forum.auth;

import webprog.forum.model.IDInterface;

public class UserToken implements IDInterface {

	private String username;
	private String token;
	
	public UserToken() {
	}
	
	public UserToken(String token, String username) {
		super();
		this.username = username;
		this.token = token;
	}



	@Override
	public String getId() {
		return token;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
	
	
}
