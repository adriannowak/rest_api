package pl.anowak.service;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import pl.anowak.data.MemberRepository;
import pl.anowak.data.UserActRepository;
import pl.anowak.model.ApiUser;
import pl.anowak.model.User;
import pl.anowak.model.auth.ApiLoginElement;
import pl.anowak.model.auth.AuthAccessElement;

@ApplicationScoped

public class AuthTokenService {

	private Map<String,Long> container = new ConcurrentHashMap<>();
	
	@Inject
	MemberRepository repository;
	
	@Inject
	UserActRepository userActRepository;

	
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
	
	public Optional<User> getUser(String token) {
		Long userId = getUserId(token);
		if(userId != null) {
			User user = repository.findById(userId);
			return Optional.ofNullable(user);
			
		}
		return Optional.empty();
	}
	
	public Long getUserId(String token) {
		if(container.containsKey(token)) {
			return container.get(token);
		}
		else {
			Optional<User> user = userActRepository.getByToken(token);
			if(user.isPresent()) {
				return user.get().getId();
			}
			return null;
		}
	}
	
	private String getNextToken() {
		return UUID.randomUUID().toString();
	}
}
