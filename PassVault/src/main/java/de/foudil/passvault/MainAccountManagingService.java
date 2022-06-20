package de.foudil.passvault;

import java.util.Date;
import java.util.Random;

import org.apache.tomcat.util.json.JSONParser;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Random;
import java.util.stream.Collectors;

import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.util.JSONPObject;


/*
 * This Service class is meant to manage the main accounts (i.e. the information about the accounts with which the user accesses their stored data).
 * functions: 
 * 1- Sign up   | 2- Delete the main account 
 * 3- Validating the log-in information 
 * 4- Change the Email of the main account |5- Change the password of the main account 
 * 6- Auto-generate a password 
 */

@Service
public class MainAccountManagingService {
	
	Configuration cfg;
	ServiceRegistry rg;
	
	//The EncryptionHelper class serves the encryption functionality of the App.
	EncryptionHelper encryptH= new EncryptionHelper();

	public MainAccountManagingService() {
		
		
		//Configuration of the database connection to the table containing the main accounts
		cfg= new Configuration().configure().addAnnotatedClass(MainAccount.class);
		rg= new StandardServiceRegistryBuilder().applySettings(cfg.getProperties()).build();
	}
	
	
	public int sign_up(MainAccountRequest requestAccount) {
		EncryptionHelper encryptH= new EncryptionHelper();
		SessionFactory sf= cfg.buildSessionFactory(rg);
		Session session = sf.openSession();

		//The email is firstly checked, in case it already exists
		EmailChecker checker=new EmailChecker(session,requestAccount.getEmail());
		
		//If the email doesn't exist then it continues
		if(checker.checkEmail()==0) {
			MainAccount account=new MainAccount();
			account.setEmail(requestAccount.getEmail().trim());
			//The password is encrypted then saved
			account.setPassword(encryptH.encrypt(requestAccount.getPassword().trim()));
			account.setC_timeStamp(Long.toString(new Date().getTime()));
			Transaction trx=session.beginTransaction();
			session.save(account);
			trx.commit();
			session.close();
			
			
			return 200;
			
		}
		// if the email already exists simply 500 will be returned
		else {
			return 500;
		}
	}
	
	public int delete(MainAccountRequest requestAccount) {
		SessionFactory sf= cfg.buildSessionFactory(rg);
		Session session = sf.openSession();
		
		//signed holds the value of 0 in case the email does not exist, -1 in case only the password is incorrect and the main account's id otherwise.
		int signed=new SigningHelper (session,requestAccount.getEmail().trim(),requestAccount.getPassword().trim()).signIn();
		
		
		if (signed==0){
			return 404;
		}
		
		else if (signed==-1) {
			return 500;
		}
		else {
			//The account will be deleted if the authentification reaches this stage.
			MainAccount account=session.get(MainAccount.class, signed);
			Transaction trx=session.beginTransaction();
			session.delete(account);
			trx.commit();
			session.close();
			
			
			return 200;
			
		}
		
		
	}
	
	public int update_email(MainAccountRequest requestAccount) {
		SessionFactory sf= cfg.buildSessionFactory(rg);
		Session session = sf.openSession();
		
		//signed holds the value of 0 in case the email does not exist, -1 in case only the password is incorrect and the main account's id otherwise.
		int signed=new SigningHelper (session,requestAccount.getEmail().trim(),requestAccount.getPassword().trim()).signIn();
		
		//Checking whether the email to be changed to already exists for another account
		int newEmail_checked= new EmailChecker(session,requestAccount.getEmail_new().trim()).checkEmail();

		if (signed==0){
			return 404;
		}
		
		else if (signed==-1) {
			return 500;
		}
		else {
			if (newEmail_checked==0) {
			//The account will be updated if the authentification reaches this stage.
			MainAccount account=session.get(MainAccount.class, signed);
			account.setEmail(requestAccount.getEmail_new().trim());
			account.setU_timeStamp(Long.toString(new Date().getTime()));
			Transaction trx=session.beginTransaction();
			session.update(account);
			trx.commit();
			session.close();
			
			
			return 200;
			}
			else {
				return 500;
			}
			
		}
		
		
	}
	
	public int update_password(MainAccountRequest requestAccount) {
		SessionFactory sf= cfg.buildSessionFactory(rg);
		Session session = sf.openSession();
		Transaction tx=session.beginTransaction();
		
		//signed holds the value of 0 in case the email does not exist, -1 in case only the password is incorrect and the main account's id otherwise.
		int signed=new SigningHelper (session,requestAccount.getEmail(),requestAccount.getPassword().trim()).signIn();
		tx.commit();
		if (signed==0){
			return 404;
		}
		
		else if (signed==-1) {
			return 500;
		}
		else {
			//The password will be updated if the authentification reaches this stage.
			MainAccount account=session.get(MainAccount.class, signed);
			account.setPassword(encryptH.encrypt(requestAccount.getPassword_new().trim()));
			account.setU_timeStamp(Long.toString(new Date().getTime()));
			Transaction trx=session.beginTransaction();
			session.update(account);
			trx.commit();
			session.close();
			
			
			return 200;
			
		}
		
		
	}
	
	
	
	public int sign_in(MainAccountRequest requestAccount) {
		SessionFactory sf= cfg.buildSessionFactory(rg);
		Session session = sf.openSession();
		Transaction tx=session.beginTransaction();
		
		
		//signed holds the value of 0 in case the email does not exist, -1 in case only the password is incorrect and the main account's id otherwise.
		int signed=new SigningHelper (session,requestAccount.getEmail().trim(),requestAccount.getPassword().trim()).signIn();
		
		if (signed==0) {
			return 404;
		}
		else if(signed==-1) {
			return 500;
		}
		else {
			return 200;
		}
		
	}

	
	//This function generates a random sequence of 12 characters including numbers, letters and special symbols.
	public String sgeneratePassword() {
		int leftLimit = 48; // numeral '0'
	    int rightLimit = 122; // letter 'z'
	    int targetStringLength = 12;
	    Random random = new Random();

	    String generatedPassword = random.ints(leftLimit, rightLimit + 1)
	      .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
	      .limit(targetStringLength)
	      .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
	      .toString();

		return generatedPassword;
	}

}

