package com.jsfcourse.ctrl;


import java.util.List;
//import java.util.Map;

import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.enterprise.context.RequestScoped;
import jakarta.ejb.EJB;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.faces.context.Flash;

import com.jsf.dao.UserDAO;
import com.jsf.entities.User;
import java.util.logging.Logger;

@Named
@RequestScoped
public class UsersListCtrl {
	private static final String PAGE_USER_EDIT = "/userEditGET?faces-redirect=true";
	private static final String PAGE_STAY_AT_THE_SAME = null;

	
	private String name;
		
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


	public List<User> getFullList(){
		return userDAO.getFullList();
	}


	public String newUser(){
		User user = new User();
		
			
		
		flash.put("user", user);
		
		return PAGE_USER_EDIT;
	}

	public String editUser(User user){
		
		
		FacesContext facesContext = FacesContext.getCurrentInstance();
	    Flash flash = facesContext.getExternalContext().getFlash();
	    
		flash.put("user", user);
		return PAGE_USER_EDIT;
	}

	public String deleteUser(User user){
		userDAO.remove(user);
		return PAGE_STAY_AT_THE_SAME;
	}
	
	public String redirectEditUserGET() {
		return PAGE_USER_EDIT;
	}

}
