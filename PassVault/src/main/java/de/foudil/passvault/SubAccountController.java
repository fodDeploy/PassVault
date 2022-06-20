package de.foudil.passvault;

import java.util.ArrayList;
import java.util.Date;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RestController
public class SubAccountController {
	@Autowired
	SubAccountManagingService sub_service;

	
	@PostMapping("sub_account/add")
	public int add_subaAcount(@RequestBody SubAccountRequest requestSubAccount) {
		return sub_service.add_subaAcount(requestSubAccount);
	}
	
	@PostMapping("sub_account/update_password")
	public int update_password(@RequestBody SubAccountRequest requestSubAccount) {
		return sub_service.update_password(requestSubAccount);
		
		
	}
	
	@PostMapping("sub_account/update_email")
	public int update_email(@RequestBody SubAccountRequest requestSubAccount) {
		return sub_service.update_email(requestSubAccount);
		
		
	}
	
	@DeleteMapping("sub_account/delete")
	public int delete(@RequestBody SubAccountRequest requestSubAccount) {
		return sub_service.delete(requestSubAccount);
		
		
	}
	
	@PostMapping("sub_account/get_password")
	public String get_password(@RequestBody SubAccountRequest requestSubAccount) {
		return sub_service.get_password(requestSubAccount);
		
		
	}
	
	@PostMapping("sub_account/get_all")
	public ArrayList<SubAccountResponse> get_all(@RequestBody SubAccountRequest requestSubAccount) {
		
		return sub_service.get_all(requestSubAccount);
	}
	
}
