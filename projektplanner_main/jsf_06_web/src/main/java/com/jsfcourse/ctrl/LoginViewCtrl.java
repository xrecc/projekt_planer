package com.jsfcourse.ctrl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.faces.context.Flash;
import jakarta.inject.Inject;
import jakarta.inject.Named;


import com.jsf.dao.UserDAO;
import com.jsf.entities.User;

@Named
@RequestScoped
public class LoginViewCtrl {
     
    private String name;
    private String surname;
    
	@Inject
	ExternalContext extcontext;
	
	@Inject
	Flash flash;
	
	@EJB
	UserDAO userDAO;
 
    public String getName() {
        return name;
    }
 
    public void setName(String name) {
        this.name = name;
    }
 
    public String getSurname() {
        return surname;
    }
 
    public void setSurname(String surname) {
        this.surname = surname;
    }
    
	public List<User> getLogin(){
		List<User> login = null;
		
		//1. Prepare search params
		Map<String,Object> searchParams = new HashMap<String, Object>();
		
		if (name != null && name.length() > 0){
			searchParams.put("name", name);
			if (surname.equals(name)) {
				ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
	        	try {
	        		externalContext.redirect("index.xhtml"); 
	        	} catch (IOException e) {
	        		e.printStackTrace(); 
	        	}
	        	 
	    }else {
	    	FacesContext.getCurrentInstance().addMessage(null,
	                new FacesMessage("Błędny login i hasło"));
	    }
	    	return null;
	    }
		return login;
			}
		

	
//	public List<User> getPassword(){
//		List<User> password = null;
//		
//		//1. Prepare search params
//		Map<String,Object> searchParams = new HashMap<String, Object>();
//		
//		if (surname != null && surname.length() > 0){
//			searchParams.put("surname", surname);
//		}
//		
//		//2. Get list
//		password = userDAO.getPassword(searchParams);
//		
//		return password;
//	}
 
//    public String save() {
//    	if().equals(name) && .equals(surname)) {
//    		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
//        	try {
//        		externalContext.redirect("index.xhtml"); 
//        	} catch (IOException e) {
//        		e.printStackTrace(); 
//        	}
//        	 
//    }else {
//    	FacesContext.getCurrentInstance().addMessage(null,
//                new FacesMessage("Błędny login i hasło"));
//    }
//    	return null;
//    }
}