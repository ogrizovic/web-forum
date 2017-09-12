package webprog.forum.auth;

import java.io.IOException;
import java.util.List;
import java.util.StringTokenizer;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.Provider;

import org.glassfish.jersey.internal.util.Base64;

import webprog.forum.model.User;
import webprog.forum.service.UserService;

//@Provider
public class SecurityFilter  implements ContainerRequestFilter {

	private static final String AUTH_HEADER_KEY = "Authorization";
	private static final String AUTH_HEADER_PREFIX = "Basic ";
	private UserService service = new UserService();
	
	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
//		List<String> authHeader = requestContext.getHeaders().get(AUTH_HEADER_KEY);
//		if (authHeader.size() > 0) {
//			String authToken = authHeader.get(0);
//			authToken = authToken.replaceFirst(AUTH_HEADER_PREFIX, "");
//			String decodedString = Base64.decodeAsString(authToken);
//			StringTokenizer tokenizer = new StringTokenizer(decodedString, ":");
//			String username = tokenizer.nextToken();
//			String password = tokenizer.nextToken();
//			
//			User tmp = service.getOneById(username);
//			if (tmp != null) {
//				if (password.equals(tmp.getPassword())) {
//					return;
//				}
//			}
//			
//		}
//		Response unauthorizedStatus = Response.status(Status.UNAUTHORIZED)
//				.entity("Unauthorized access.")
//				.build();
//		
//		requestContext.abortWith(unauthorizedStatus);
	}

	
}
