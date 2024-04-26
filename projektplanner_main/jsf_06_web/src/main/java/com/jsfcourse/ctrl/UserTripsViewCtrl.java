package com.jsfcourse.ctrl;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;

import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.enterprise.context.RequestScoped;
import jakarta.ejb.EJB;
//import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.faces.context.Flash;
//import jakarta.faces.simplesecurity.RemoteClient;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpSession;


import com.jsf.dao.TripDAO;
import com.jsf.dao.UserDAO;
import com.jsf.entities.Trip;
import com.jsf.entities.User;

@Named
@RequestScoped
public class UserTripsViewCtrl implements Serializable{
	//private static final String PAGE_PERSON_EDIT = "personEdit?faces-redirect=true";
	//private static final String PAGE_STAY_AT_THE_SAME = null;

	private static final long serialVersionUID = 1L;

	
		private String title;


	    User user = new User();

	
	@Inject
	ExternalContext extcontext;
	
	@Inject
	FacesContext context;
	
	@Inject
	Flash flash;
	
	@EJB
	TripDAO tripDAO;
	@EJB
	UserDAO userDAO;
	
	public User getUser() {
		return user;
	}
	public void setUser(User user){
		this.user = user;
	}
		
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}



	public List<Trip> getList(){
		List<Trip> list = null;
	
		
		return list;
	}


}
