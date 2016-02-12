package pl.anowak.service;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import javax.enterprise.context.ApplicationScoped;

import pl.anowak.model.ApiUser;
import pl.anowak.model.auth.ApiLoginElement;
import pl.anowak.model.auth.AuthAccessElement;

@ApplicationScoped

public class AuthTokenService {

	private Map<String,Long> container = new ConcurrentHashMap<>();
	
	public AuthAccessElement access(ApiUser user) {
		String token = getNextToken();
		container.put(token, user.getId());
		return new AuthAccessElement(token);
	}
	
	public Boolean tokenExists(AuthAccessElement accessElement) {
		return tokenExists(accessElement.getAuthToken());
	}
	public Boolean tokenExists(String token) {
		return container.containsKey(token);
	}
	
	public Long getUserId(String token) {
		return container.get(token);
	}

	private String getNextToken() {
		return UUID.randomUUID().toString();
	}
}
