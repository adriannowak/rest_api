package pl.anowak.service;

import java.util.Optional;

import javax.inject.Inject;
import javax.validation.Validator;

import pl.anowak.model.auth.ApiLoginElement;
import pl.anowak.model.auth.AuthAccessElement;

public abstract class AuthServiceIface {

    @Inject
    private Validator validator;

    
	private Optional<UserDetails> getUserDetails(ApiLoginElement authLogin) {
		if(validator.validate(authLogin).isEmpty()) {
			String accessToken = authLogin.getAccessToken();
			return Optional.ofNullable(getUserDetails(accessToken));
		}
		return Optional.empty();
	}
	
	protected abstract UserDetails getUserDetails(String accessToken);






	public AuthAccessElement getAuthLogin(AuthAccessElement element) {
		return null;
		
	}
}
