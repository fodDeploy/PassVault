package de.foudil.passvault;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="main_accounts")
public class MainAccount {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String email;
	private String password;
	private String c_timeStamp;
	private String u_timeStamp;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getC_timeStamp() {
		return c_timeStamp;
	}
	public void setC_timeStamp(String c_timeStamp) {
		this.c_timeStamp = c_timeStamp;
	}
	public String getU_timeStamp() {
		return u_timeStamp;
	}
	public void setU_timeStamp(String u_timeStamp) {
		this.u_timeStamp = u_timeStamp;
	}
	
	

}

