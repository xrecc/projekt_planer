package com.jsf.login;

import java.util.List;

import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.simplesecurity.RemoteClient;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import com.jsf.dao.TripDAO;
import com.jsf.dao.UserDAO;
import com.jsf.entities.User;


@Named
@RequestScoped
public class Login {
	private static final String PAGE_MAIN = "/pages/trippages/index.xhtml";
	private static final String PAGE_LOGIN = "/pages/loginview.xhtml";
	private static final String PAGE_REGISTER = "/pages/registerview.xhtml";
	private static final String PAGE_STAY_AT_THE_SAME = null;


	private String pass;
	private String email;

	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	@Inject
	UserDAO userDAO;
	TripDAO tripDAO;

	public String doLogin() throws Exception {
		FacesContext ctx = FacesContext.getCurrentInstance();


		User user = userDAO.getUserFromDatabase(email, pass);
		

		if (user == null) {
			ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Niepoprawny email lub hasło", null));
			return PAGE_STAY_AT_THE_SAME;
		}
		

		
		RemoteClient<User> client = new RemoteClient<User>(); 
		client.setDetails(user);
		

		
		List<String> roles = userDAO.getUserRolesFromDatabase(user); 
		
		if (roles != null) { 
			for (String role: roles) {
				client.getRoles().add(role);
			}
		}
		
	
		HttpServletRequest request = (HttpServletRequest) ctx.getExternalContext().getRequest();
		client.store(request);

		return PAGE_MAIN;
	}
	
	
	public String doLogout(){
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
				.getExternalContext().getSession(true);

		session.invalidate();
		return PAGE_LOGIN;
	}
	
}