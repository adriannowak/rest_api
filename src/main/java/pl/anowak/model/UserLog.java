package pl.anowak.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name="h5_uzytkownik_aktywnosc_zaloguj")
@XmlRootElement
public class UserLog extends UserAct {

	private String authorizationToken;
	private String accessKey;
	
	public String getAuthorizationToken() {
		return authorizationToken;
	}
	public void setAuthorizationToken(String authorizationToken) {
		this.authorizationToken = authorizationToken;
	}
	public String getAccessKey() {
		return accessKey;
	}
	public void setAccessKey(String accessKey) {
		this.accessKey = accessKey;
	}
	
	
}
