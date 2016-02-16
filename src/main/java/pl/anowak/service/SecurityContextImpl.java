package pl.anowak.service;

import java.security.Principal;

import javax.ws.rs.core.SecurityContext;

import pl.anowak.model.User;

public class SecurityContextImpl implements SecurityContext {

	private User user;
	
	public SecurityContextImpl(User user) {
			this.user = user;
	}
	
	@Override
	public Principal getUserPrincipal() {
		return new Principal() {

			@Override
			public String getName() {
				return user.getId().toString();
			}
			
		};
	}

	@Override
	public boolean isUserInRole(String role) {
		return true;
	}

	@Override
	public boolean isSecure() {
		return true;
	}

	@Override
	public String getAuthenticationScheme() {
		return SecurityContext.BASIC_AUTH;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	

}
