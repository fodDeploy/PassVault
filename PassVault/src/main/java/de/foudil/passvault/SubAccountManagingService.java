package de.foudil.passvault;

import java.util.ArrayList;
import java.util.Date;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;


/*
 * This Service class is meant to manage the subaccounts (i.e. the information about the accounts in which the passwords are used).
 * functions: 
 * 1- Add new instance   | 2- Delete a subaccount instance 
 * 3- Request all existing instances (without showing the password) 
 * 4- Change the Email of the subaccount |5- Change the password of the subaccount 
 * 6- Request the password of a specific instance 
 */

@Service
public class SubAccountManagingService {
	
		Configuration cfg;
		ServiceRegistry rg;
		Configuration cfg_signing;
		ServiceRegistry rg_signing;
		
	//The EncryptionHelper class serves the encryption functionality of the App.
		EncryptionHelper encryptH= new EncryptionHelper();
		public SubAccountManagingService() {
			//Configuration of the database connection to the table containing the subaccounts.
			cfg= new Configuration().configure().addAnnotatedClass(SubAccount.class);
			rg= new StandardServiceRegistryBuilder().applySettings(cfg.getProperties()).build();
			
			//Configuration of the database connection to the table containing the main accounts' information.
			//for the purpose of checking the validity of the log-in information.
			cfg_signing= new Configuration().configure().addAnnotatedClass(MainAccount.class);
			rg_signing= new StandardServiceRegistryBuilder().applySettings(cfg_signing.getProperties()).build();
		}
		//creating an instance of a subaccount
		public int add_subaAcount(SubAccountRequest requestSubAccount) {
			//creating and opening the working session for subaccount management
			SessionFactory sf= cfg.buildSessionFactory(rg); 
			Session session = sf.openSession();
			//creating and opening the working session for login-authentication.
			SessionFactory sf_signing= cfg_signing.buildSessionFactory(rg_signing); 
			Session session_signing = sf_signing.openSession();
			
			//signed holds the value of 0 in case the email does not exist, -1 in case only the password is incorrect and the main account's id otherwise.
			int signed= new SigningHelper(session_signing,requestSubAccount.getMain_email(),requestSubAccount.getMain_password()).signIn();
			if(signed==0) {
				return 404;
			}
			if(signed==-1) {
				return 500;
			}
			else {
				//checking if an instance of the to be created subaccount already exists
				if ( new SubAccountFinder(session,signed).findSubAccount(requestSubAccount.getSub_email(),requestSubAccount.getSub_at())!=404) {
					return 404;
				}
				else {
					
					//creating and saving the subaccount
					SubAccount pass= new SubAccount();
					pass.setAccount_at(requestSubAccount.getSub_at());
					pass.setAccount_email(requestSubAccount.getSub_email());
					pass.setAccount_password(encryptH.encrypt(requestSubAccount.getSub_password()));
					pass.setMainAccount_id(signed);
					pass.setC_timeStamp(Long.toString(new Date().getTime()));
					
					Transaction tx=session.beginTransaction();
					session.save(pass);
					tx.commit();
					session.close();
					return 200;
				}
				
			}
		}
		//Changing the password of an existing subaccount
		public int update_password(SubAccountRequest requestSubAccount) {
			//creating and opening the working session for subaccount management
			SessionFactory sf= cfg.buildSessionFactory(rg);
			Session session = sf.openSession();
			//creating and opening the working session for login-authentication.
			SessionFactory sf_signing= cfg_signing.buildSessionFactory(rg_signing);
			Session session_signing = sf_signing.openSession();
			
			//signed holds the value of 0 in case the email does not exist, -1 in case only the password is incorrect and the main account's id otherwise.
			int signed= new SigningHelper(session_signing,requestSubAccount.getMain_email(),requestSubAccount.getMain_password()).signIn();
			if (signed==0){
				return 404;
			}
			
			else if (signed==-1) {
				return 500;
			}
			else {
				//requesting the id of the targeted subaccount
				SubAccountFinder finder= new SubAccountFinder(session, signed);
				int sub_id=finder.findSubAccount(requestSubAccount.getSub_email(), requestSubAccount.getSub_at());
				//in case it doesn't exist it returns 404 (no subaccount has an id of 404)
				if(sub_id==404) {
					return 404;
				}
				else {
				//updating the password
				SubAccount subAccount=session.get(SubAccount.class, sub_id);
				subAccount.setAccount_password(encryptH.encrypt(requestSubAccount.getSub_password_new().trim()));
				subAccount.setU_timeStamp(Long.toString(new Date().getTime()));
				Transaction trx=session.beginTransaction();
				session.update(subAccount);
				trx.commit();
				session.close();
				
				
				return 200;
				
				}
			}
			
			
		}
		//Changing the email of an existing subaccount
		public int update_email(SubAccountRequest requestSubAccount) {
			//creating and opening the working session for subaccount management
			SessionFactory sf= cfg.buildSessionFactory(rg);
			Session session = sf.openSession();
			
			//creating and opening the working session for login-authentication.
			SessionFactory sf_signing= cfg_signing.buildSessionFactory(rg_signing);
			Session session_signing = sf_signing.openSession();
			
			//signed holds the value of 0 in case the email does not exist, -1 in case only the password is incorrect and the main account's id otherwise.
			int signed= new SigningHelper(session_signing,requestSubAccount.getMain_email(),requestSubAccount.getMain_password()).signIn();

			
			if (signed==0){
				return 404;
			}
			
			else if (signed==-1) {
				return 500;
			}
			else {
				//requesting the id of the targeted subaccount
				SubAccountFinder finder= new SubAccountFinder(session, signed);
				int sub_id=finder.findSubAccount(requestSubAccount.getSub_email(), requestSubAccount.getSub_at());
				
				//in case it doesn't exist it returns 404 (no subaccount has an id of 404)
				if(sub_id==404) {
					return 404;
				}
				else {
					
					//updating the email
				SubAccount subAccount=session.get(SubAccount.class, sub_id);
				subAccount.setAccount_email(requestSubAccount.getSub_email_new());
				subAccount.setU_timeStamp(Long.toString(new Date().getTime()));
				Transaction trx=session.beginTransaction();
				session.update(subAccount);
				trx.commit();
				session.close();
				
				
				return 200;
				}
				
			}
			
			
		}
		
		public int delete(SubAccountRequest requestSubAccount) {
			//creating and opening the working session for subaccount management
			SessionFactory sf= cfg.buildSessionFactory(rg);
			Session session = sf.openSession();
			//creating and opening the working session for login-authentication.
			SessionFactory sf_signing= cfg_signing.buildSessionFactory(rg_signing);
			Session session_signing = sf_signing.openSession();
			
			//signed holds the value of 0 in case the email does not exist, -1 in case only the password is incorrect and the main account's id otherwise.
			int signed= new SigningHelper(session_signing,requestSubAccount.getMain_email(),requestSubAccount.getMain_password()).signIn();
			if (signed==0){
				return 404;
			}
			
			else if (signed==-1) {
				return 500;
			}
			else {
				//requesting the id of the targeted subaccount
				SubAccountFinder finder= new SubAccountFinder(session, signed);
				int sub_id=finder.findSubAccount(requestSubAccount.getSub_email(), requestSubAccount.getSub_at());
				
				//in case it doesn't exist it returns 404 (no subaccount has an id of 404)
				if(sub_id==404) {
					return 404;
				}
				else {
				//Deleting the targeted subaccount
				SubAccount subAccount=session.get(SubAccount.class, sub_id);
				Transaction trx=session.beginTransaction();
				session.delete(subAccount);
				trx.commit();
				session.close();
				
				
				return 200;
				
				}
			}
			
			
		}
		
		public String get_password(SubAccountRequest requestSubAccount) {
			//creating and opening the working session for subaccount management
			SessionFactory sf= cfg.buildSessionFactory(rg);
			Session session = sf.openSession();
			//creating and opening the working session for login-authentication.
			SessionFactory sf_signing= cfg_signing.buildSessionFactory(rg_signing);
			Session session_signing = sf_signing.openSession();
			//signed holds the value of 0 in case the email does not exist, -1 in case only the password is incorrect and the main account's id otherwise.
			int signed= new SigningHelper(session_signing,requestSubAccount.getMain_email(),requestSubAccount.getMain_password()).signIn();
			if (signed==0){
				return "email not found";
			}
			
			else if (signed==-1) {
				return "failed login";
			}
			
			else {
				
				SubAccountFinder finder= new SubAccountFinder(session, signed);
				//in case no the requested password doesn't it returns 404 (no subaccount has an id of 404)
				int sub_id=finder.findSubAccount(requestSubAccount.getSub_email(), requestSubAccount.getSub_at());
				if(sub_id==404) {
					return "no sub account found";
				}
				else {
				
				SubAccount subAccount=session.get(SubAccount.class, sub_id);
				
				
				return encryptH.decrypt(subAccount.getAccount_password());
				
				}
			}
			
			
		}
		
			
		//This function is to request all existing subaccounts
		public ArrayList<SubAccountResponse> get_all(@RequestBody SubAccountRequest requestSubAccount) {
			
			
			
			ArrayList<SubAccountResponse> response= new ArrayList<SubAccountResponse>();
			
			//in case of failure this this object will be put in the response list (and only it)
			SubAccountResponse failed = new SubAccountResponse();
			
			SessionFactory sf= cfg.buildSessionFactory(rg);
			Session session = sf.openSession();
			SessionFactory sf_signing= cfg_signing.buildSessionFactory(rg_signing);
			Session session_signing = sf_signing.openSession();
				
			//signed holds the value of 0 in case the email does not exist, -1 in case only the password is incorrect and the main account's id otherwise.
			int signed= new SigningHelper(session_signing,requestSubAccount.getMain_email(),requestSubAccount.getMain_password()).signIn();
			if (signed==0){
				
				failed.setSuccess(0);
				response.add(failed);
			}
			
			else if (signed==-1) {
				failed.setSuccess(0);
				response.add(failed);
			}
			
			else {
				
				SubAccountFinder finder= new SubAccountFinder(session, signed);
				
				ArrayList<Integer> ids= finder.findAllSubAccounts();
				for (Integer id : ids) {
					SubAccount subAccount=session.get(SubAccount.class,id);
					SubAccountResponse sub_response = new SubAccountResponse();
					sub_response.setSuccess(1);
					sub_response.setEmail(finder.hide_email(subAccount.getAccount_email()));
					sub_response.setAt(subAccount.getAccount_at());
					response.add(sub_response);
					
					
					
				}
			}
			return response;
			
		}
		
	}



