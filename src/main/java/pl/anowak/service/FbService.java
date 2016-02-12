package pl.anowak.service;

import java.util.Date;
import java.util.Optional;

import javax.inject.Inject;

import pl.anowak.data.MemberRepository;
import pl.anowak.model.ApiUser;
import pl.anowak.model.auth.ApiLoginElement;
import pl.anowak.model.auth.ApiType;

import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Version;
import com.restfb.exception.FacebookException;
import com.restfb.types.User;

public class FbService {

	@Inject
	MemberRegistration mr;
	@Inject
	MemberRepository mrr;

	// public static String getExternalClientId(ApiLoginElement api) {
	// return getUser(api).isPresent();
	// }

	public ApiUser createUser(User api) {
		ApiUser aa = new ApiUser();
		aa.setApiType(ApiType.Facebook);
		aa.setCreatedOn(new Date());
		aa.setFirstName(api.getFirstName());
		aa.setLastName(api.getLastName());
		aa.setExternalClientId(api.getId());
		return aa;
	}

	public Optional<User> getUser(ApiLoginElement api) {
		FacebookClient facebookClient = new DefaultFacebookClient(
				api.getAccessToken(), Version.VERSION_2_5);
		User me = null;
		try {
			me = facebookClient.fetchObject("me", User.class);
		} catch (FacebookException e) {
			System.err.println("Użytkownik nie istnieje!");
			e.printStackTrace();
		}

		return Optional.ofNullable(me);

	}

}
