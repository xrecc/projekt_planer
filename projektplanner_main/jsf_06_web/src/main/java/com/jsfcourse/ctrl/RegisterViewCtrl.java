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
public class RegisterViewCtrl {
	private static final long serialVersionUID = 1L;

	private static final String PAGE_LOGIN = "/loginview.xhtml";
	private static final String PAGE_STAY_AT_THE_SAME = null;
	
	
	@Inject
	ExternalContext extcontext;
	
	@Inject
	Flash flash;
	
	@EJB
	UserDAO userDAO;
	
	private User user;
	
	public RegisterViewCtrl() {
		user = new User();
		user.setRole("0");
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String newUser() {
		userDAO.create(user);
		return PAGE_LOGIN;
	}
	
}