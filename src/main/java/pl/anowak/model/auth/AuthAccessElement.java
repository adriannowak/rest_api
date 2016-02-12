package pl.anowak.model.auth;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import pl.anowak.service.UserDetails;

@XmlRootElement
public class AuthAccessElement implements Serializable {

	public AuthAccessElement(String token) {
		this.authToken = token;
	}
	
    @XmlElement(nillable=true)
	private String authToken;
	
    
	public String getAuthToken() {
		return authToken;
	}
	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}
	
	
}
