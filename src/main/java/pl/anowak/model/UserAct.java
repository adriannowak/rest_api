package pl.anowak.model;

import java.util.Date;

import javax.annotation.Generated;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;

@Entity(name="h5_uzytkownik_aktywnosc")
@Inheritance(strategy=InheritanceType.JOINED)

public class UserAct {
  
	@Id
    @GeneratedValue
	private Long id;
	
    private String ipAddress;
    
    private Date date;
    
    @ManyToOne
    @JoinColumn(name="user_id",referencedColumnName="id")
    private User user;
    
    
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}

    
    
}
