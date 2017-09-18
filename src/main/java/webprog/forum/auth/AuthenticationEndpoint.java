package webprog.forum.auth;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.google.gson.Gson;

import webprog.forum.model.User;
import webprog.forum.service.UserService;

@Path("/authentication")
public class AuthenticationEndpoint {

	private UserService userService;
	private AuthenticationService authService;
	private User loggedUser;
	
	public AuthenticationEndpoint() {
		this.userService = new UserService();
		this.authService = new AuthenticationService();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response authenticateUser(Credentials credentials) {
		
		try {

            // Authenticate the user using the credentials provided
            authenticate(credentials.getUsername(), credentials.getPassword());

            // Issue a token for the user
            String token = issueToken(credentials.getUsername());
            
            // Return the token on the response
            StringBuilder userToken = new StringBuilder();
            userToken.append(credentials.getUsername());
            userToken.append(" ");
            userToken.append(token);
            userToken.append(" ");
            userToken.append(loggedUser.getUloga());
            return Response.ok(userToken).build();

        } catch (Exception e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }      
    }

    private void authenticate(String username, String password) throws Exception {
        // Authenticate against a database, LDAP, file or whatever
        // Throw an Exception if the credentials are invalid
    	User user = userService.getOneById(username);
    	loggedUser = user;
    	if (user != null) {
    		if (password.equals(user.getPassword())) {
    			return;
    		}
    	}
    	else throw new Exception();
    }

    private String issueToken(String username) {
        // Issue a token (can be a random String persisted to a database or a JWT token)
        // The issued token must be associated to a user
        // Return the issued token
    	Random random = new SecureRandom();
    	String token = new BigInteger(130, random).toString(32);
    	authService.create(new UserToken(token, username));
    	return token;
	}
}
