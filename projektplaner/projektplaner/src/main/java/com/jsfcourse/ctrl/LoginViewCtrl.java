package com.jsfcourse.ctrl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;

@Named
@RequestScoped
public class LoginViewCtrl {
     
    private String firstname;
    private String lastname;
 
    public String getFirstname() {
        return firstname;
    }
 
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }
 
    public String getLastname() {
        return lastname;
    }
 
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
 
    public String save() {
    	if("admin".equals(firstname) && "admin".equals(lastname)) {
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
}