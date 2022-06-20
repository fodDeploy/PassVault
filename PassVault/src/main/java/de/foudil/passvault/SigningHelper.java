package de.foudil.passvault;

import java.util.ArrayList;

import org.hibernate.Session;


//This class' role is to help easily check the validity of the log-in informations from many parts of the app.
public class SigningHelper {
	Session session;
	String email;
	String password;
	EncryptionHelper encrpytH= new EncryptionHelper();

	
	//Need to be initialized with the current session and the log-in informations
	public SigningHelper(Session session, String email,String password) {
		this.session=session;
		this.email=email;
		this.password=password;
		
	}
	public int signIn() {
		EmailChecker emailChecker= new EmailChecker(session, email);
		//check for the existence of the email, then the validity of its password
		int id =emailChecker.checkEmail();
		
		// returns 0 if the Email doesn't exist, -1 if the password is incorrect 
		//and the id of the account if everything is fine.
		if(id ==0) {
			return 0;
		}
		else {
			MainAccount account = (MainAccount) session.get(MainAccount.class, id);
			if (encrpytH.decrypt(account.getPassword().trim()).equals(password.trim())) {
				return id;
			}
			else {
				return -1;
			}
		}
	}

}
