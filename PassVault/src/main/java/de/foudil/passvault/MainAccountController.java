package de.foudil.passvault;



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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.util.JSONPObject;


@Controller
@RestController
public class MainAccountController {

	@Autowired
	MainAccountManagingService main_service;

	
	
	@PostMapping("main_account/sign_up")
	public int sign_up(@RequestBody MainAccountRequest requestAccount) {
		int result =main_service.sign_up(requestAccount);
		return result;
	}
	
	@DeleteMapping("main_account/delete")
	public int delete(@RequestBody MainAccountRequest requestAccount) {
		int result= main_service.delete(requestAccount);
			
		return result;
		}
	
	@PostMapping("main_account/update_email")
	public int update_email(@RequestBody MainAccountRequest requestAccount) {
		return main_service.update_email(requestAccount);
		
	}
	
	@PostMapping("main_account/update_password")
	public int update_password(@RequestBody MainAccountRequest requestAccount) {
		return main_service.update_password(requestAccount);
		
		
	}
	
	
	
	@PostMapping("main_account/sign_in")
	public int sign_in(@RequestBody MainAccountRequest requestAccount) {
		return main_service.sign_in(requestAccount);
		
	}

	
	@GetMapping("/generatePassword")
	public String sgeneratePassword() {
		return main_service.sgeneratePassword();
	}

}
