package com.jsf.register;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import com.jsf.dao.UserDAO;

@Named
@RequestScoped
public class Register {
	private static final String PAGE_LOGIN = "/loginview.xhtml";
	private static final String PAGE_REGISTER = "/pages/registerview.xhtml";

	private String login;
	private String pass;

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

//	@Inject
//	UserDAO userDAO;

	public String doRegister() {
		return PAGE_REGISTER;
	}

	
	
}