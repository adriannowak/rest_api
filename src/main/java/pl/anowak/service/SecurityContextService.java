package pl.anowak.service;

import java.security.Principal;
import java.util.Optional;

import javax.inject.Inject;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;

import pl.anowak.data.MemberRepository;
import pl.anowak.model.User;

public class SecurityContextService {
	
	@Inject
	AuthTokenService authTokenService;
	
	@Inject
	MemberRepository memberRepository;
	
	public Optional<SecurityContext> createSecurityContext(String token) {
		Optional<User> user = authTokenService.getUser(token);
		if(user.isPresent()) {
			return Optional.<SecurityContext>of(new SecurityContextImpl(user.get()));
		}
		
		return Optional.empty();
	}
	
	public Optional<User> getSecurityContext(SecurityContext securityContext) {
		Principal context = securityContext.getUserPrincipal();
		if(context != null && context.getName() != null) {
			Long userId = Long.parseLong(context.getName());
			return Optional.of(memberRepository.findById(userId));
		}
		return Optional.empty();
	}
	
	
}
