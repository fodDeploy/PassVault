package de.foudil.passvault;

import java.util.ArrayList;

import javax.persistence.Query;

import org.hibernate.Session;



/*
 * This class takes care of sending filtered and non-filtered requests to the data base
 */
public class SubAccountFinder {
	Session session;
	String email;
	String at;
	int mainAccount_id;
	public SubAccountFinder(Session session,int mainAccount_id) {
		
		//It works on the currently running session of the user, it does not create a new one.
		this.session=session;
		//With the Id of the user's account it keeps track of the requesting user.
		this.mainAccount_id=mainAccount_id;
	}
	
	//This function requests a specific password stored in the database from the email associated with it and the organization it was registered at.
	public int findSubAccount(String email, String at) {
		String sqlQuery="SELECT id FROM passwords WHERE (account_email LIKE '%"+email+"%') AND (account_at LIKE '%"+at+"%') AND (main_account_id = "+mainAccount_id+" );";
		Query query=session.createNativeQuery(sqlQuery);
		ArrayList<Integer> id= (ArrayList<Integer>) query.getResultList();
		if (id.size()==0) {
			return 404;
		} else {
			return id.get(0);
		}
	}
	
	//With this function all sub accounts of the particular user are requested.
	public ArrayList<Integer> findAllSubAccounts() {
		String sqlQuery=String.format("SELECT id FROM passwords WHERE main_account_id = %d ;",mainAccount_id);
		Query query=session.createNativeQuery(sqlQuery);
		ArrayList<Integer> ids= (ArrayList<Integer>) query.getResultList();
		System.out.println(ids.get(0));
		return ids;
	}
	
	//With this function, half of the email will be replaced by "***" for security concerns.
	//example: normal_email@gmail.com ----> normal******@gmail.com
	public String hide_email(String email) {
		
		String hidden_email="";
		String main_part=email.substring(0, email.indexOf('@'));
		String secondary_part=email.substring(email.indexOf('@'));

		
		
		for (int i=0; i<(int) main_part.length()/2;i++) {
			hidden_email+=main_part.charAt(i);
		}
		for (int i=(int) main_part.length()/2; i<main_part.length();i++) {
			hidden_email+='*';
		}
		
		hidden_email+=secondary_part;
		
		return hidden_email;
	}
}
