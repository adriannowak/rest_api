package pl.anowak.service;

import java.util.Optional;
import java.util.UUID;

import javax.inject.Inject;

import com.restfb.types.User;

import pl.anowak.data.MemberRepository;
import pl.anowak.model.ApiUser;
import pl.anowak.model.auth.ApiLoginElement;
import pl.anowak.model.auth.AuthAccessElement;

public class AuthService {

	@Inject 
	FbService fb;
	
	@Inject 
	MemberRepository mr;
	
	@Inject 
	AuthTokenService authToken;
	
	@Inject 
	MemberRegistration mrr;
	
	public Boolean isAuthorized(AuthAccessElement accessElement) {
		return true;
	}
	
	public Optional<AuthAccessElement> login(ApiLoginElement authElement) {
		Optional<User> user = fb.getUser(authElement);
		ApiUser api = null;

		if(user.isPresent()) {
			User externalUser = user.get();
			System.err.println("Użytkownik z serwisu:"+externalUser);
			if(mr.userExistsByExternalClientId(externalUser.getId())) {
				System.err.println("ISTNIEJE!");
				api = mr.getUserByExternalCLientId(externalUser.getId());
			}
			else {
				System.err.println("NIE ISTNIEJE!");

				api = fb.createUser(externalUser);
				try {
					mrr.register(api);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			

		}
		if(api != null) {
			return Optional.of(authToken.access(api));

		}
		
		return Optional.empty();

//		AuthAccessElement auth = new AuthAccessElement();
//		auth.setAuthToken(UUID.randomUUID().toString());
//		fb.test(authElement);
//		return Optional.ofNullable(auth);
	}
}
