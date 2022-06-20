package de.foudil.passvault;

import java.util.ArrayList;

import javax.persistence.Query;

import org.hibernate.Session;


//This class is a helper class. It checks the existence of a given email.//


public class EmailChecker {
	Session session;
	String email;
	
	public EmailChecker( Session session, String email) {
		this.session=session;
		this.email=email;	
		
	}
	
	public int checkEmail() {
		//Requests all matching emails (either 1 or 0 emails would be delivered)
		String sqlQuery="SELECT id FROM main_accounts WHERE email  LIKE '%"+email +"%' ;";
		Query query= session.createNativeQuery(sqlQuery);
		ArrayList<Integer> id= (ArrayList<Integer>) query.getResultList();
		
		//return 0 is when there is no matching email and the id of the (first) email, when there is.
		if (id.size()==0) {
			return 0;
		} else {
			System.out.println(id.get(0));
			return id.get(0);

		}
	}

}
