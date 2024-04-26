package com.jsf.actions;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;



@Named
@RequestScoped
public class Actions{
	private static final String PAGE_MAIN = "/pages/trippages/index.xhtml";
	private static final String PAGE_LOGIN = "/pages/loginview.xhtml";
	
	public String goHome() {
		
		return PAGE_MAIN;
	}
	public String goLogin() {
		return PAGE_LOGIN;
	}
}