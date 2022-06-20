package de.foudil.passvault;

import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="passwords")
public class SubAccount {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private int main_account_id;
	private String account_email;
	private String account_password;
	private String account_at;
	private String c_timeStamp;
	private String u_timeStamp;

	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getMainAccount_id() {
		return main_account_id;
	}
	public void setMainAccount_id(int mainAccount_id) {
		this.main_account_id = mainAccount_id;
	}
	public String getAccount_email() {
		return account_email;
	}
	public void setAccount_email(String account_email) {
		this.account_email = account_email;
	}
	public String getAccount_password() {
		return account_password;
	}
	public void setAccount_password(String account_password) {
		this.account_password = account_password;
	}
	public String getAccount_at() {
		return account_at;
	}
	public void setAccount_at(String account_at) {
		this.account_at = account_at;
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
