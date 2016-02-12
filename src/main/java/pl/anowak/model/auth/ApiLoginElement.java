package pl.anowak.model.auth;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ApiLoginElement {
	
	protected ApiType apiType = ApiType.Facebook;
	
	@NotNull
	private String accessToken;
	
	public ApiType getApiType() {
		return apiType;
	}
	public void setApiType(ApiType apiType) {
		this.apiType = apiType;
	}
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	
}
