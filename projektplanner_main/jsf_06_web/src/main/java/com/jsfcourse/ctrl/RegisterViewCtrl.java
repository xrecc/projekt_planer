package com.jsfcourse.ctrl;

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

	private static final String PAGE_LOGIN = "/pages/loginview?faces-redirect=true";
	private static final String PAGE_STAY = null;
	
	
	@Inject
	ExternalContext extcontext;
	
	@Inject
	Flash flash;
	
	@EJB
	UserDAO userDAO;
	
	private User user = new User();
	private int phonenumber;
	private String email;

	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	public int getPhonenumber() {
		return phonenumber;
	}

	public void setPhonenumber(int phonenumber) {
		this.phonenumber = phonenumber;
	}
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String newUser() {
		
		FacesContext ctx = FacesContext.getCurrentInstance();
		
		if(userDAO.userEx(user.getEmail())) {
			ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Uzytkownik juz istnieje", null));
			return PAGE_STAY;
		}else {
		user.setRole("0");
		
		userDAO.create(user);
		return PAGE_LOGIN;
		}
	}
	
}