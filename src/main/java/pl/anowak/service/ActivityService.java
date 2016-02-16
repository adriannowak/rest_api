package pl.anowak.service;

import java.sql.Date;

import javax.inject.Inject;

import pl.anowak.data.UserActRepository;
import pl.anowak.model.User;
import pl.anowak.model.UserLog;

public class ActivityService {

	@Inject
	UserActRepository userActRepository;
	
	
	public void userLogged(User user,String addressIp, String accessKey, String authorizationToken) {
		UserLog log = new UserLog();
		log.setAuthorizationToken(authorizationToken);
		log.setAccessKey(accessKey);
		log.setIpAddress(addressIp);
		log.setUser(user);
		log.setDate(new Date(0));
		userActRepository.persist(log);
	}
}
