package ch.uzh.ifi.seal.soprafs19.entity;

import ch.uzh.ifi.seal.soprafs19.constant.UserStatus;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

//import date object class;
import java.util.Date;


@Entity
public class User implements Serializable {
	

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;

	@Column
	private String date_birth;
	
	@Column(nullable = false) 
	private String name;
	
	@Column(nullable = false, unique = true) 
	private String username;

	//adding the entity password to the User class
	@Column(nullable = false)
	private String password;
	
	@Column(nullable = false, unique = true) 
	private String token;

	//added creation date variable
	@Column
	private String creation_date;

	@Column(nullable = false)
	private UserStatus status;

	public Long getId() {
		return id;
	}

	public void setId(String id) {
		long long_id=Long.parseLong(id);
		this.id = long_id;
	}

	public void setDate_birth(String birth){this.date_birth=birth;}

	public String getDate_birth(){return (this.date_birth);}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	//added the setPassword function (for the repository to set the password?)
	public void setPassword(String password) {
		this.password = password;
	}

	//added the getPassword function (for the repository to get the password?)
	public String getPassword(){return password;}

	//added the get Creation date function to get the creation date;
	public String getCreation_date() {
		return creation_date;
	}

	public void setCreation_date(String creation_date) {
		this.creation_date = creation_date;
	}

	public String getUsername() { return username; }

	public void setUsername(String username) {
		this.username = username;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public UserStatus getStatus() {
		return status;
	}

	public void setStatus(UserStatus status) {
		this.status = status;
	}

	@Override
	public boolean equals(Object o) {
		if (o == this) return true;
		if (!(o instanceof User)) {
			return false;
		}
		User user = (User) o;
		return this.getId().equals(user.getId());
	}
}
