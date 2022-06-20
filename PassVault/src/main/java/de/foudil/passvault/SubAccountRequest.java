package de.foudil.passvault;

public class SubAccountRequest {
	
	String main_email;
	String main_password;
	
	String sub_email;
	String sub_password;
	
	String sub_email_new;
	public String getSub_email_new() {
		return sub_email_new;
	}
	public void setSub_email_new(String sub_email_new) {
		this.sub_email_new = sub_email_new;
	}
	String sub_password_new;
	public String getSub_password_new() {
		return sub_password_new;
	}
	public void setSub_password_new(String sub_password_new) {
		this.sub_password_new = sub_password_new;
	}
	String sub_at;
	public String getMain_email() {
		return main_email;
	}
	public void setMain_email(String email) {
		this.main_email = email;
	}
	public String getMain_password() {
		return main_password;
	}
	public void setMain_password(String password) {
		this.main_password = password;
	}
	public String getSub_email() {
		return sub_email;
	}
	public void setSub_email(String add_email) {
		this.sub_email = add_email;
	}
	public String getSub_password() {
		return sub_password;
	}
	public void setSub_password(String add_password) {
		this.sub_password = add_password;
	}
	public String getSub_at() {
		return sub_at;
	}
	public void setSub_at(String add_at) {
		this.sub_at = add_at;
	}
	
	

}
