package pl.anowak.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import pl.anowak.model.auth.ApiType;

@Table(name = "h5_uzytkownik_api", uniqueConstraints = @UniqueConstraint(columnNames = {"externalClientId","apiType"}))
@Entity
public class ApiUser extends User {
	
	private String externalClientId;

	@Enumerated(EnumType.STRING)
	private ApiType apiType;

	public String getExternalClientId() {
		return externalClientId;
	}

	public void setExternalClientId(String externalClientId) {
		this.externalClientId = externalClientId;
	}

	public ApiType getApiType() {
		return apiType;
	}

	public void setApiType(ApiType apiType) {
		this.apiType = apiType;
	}
	
	
	
}
