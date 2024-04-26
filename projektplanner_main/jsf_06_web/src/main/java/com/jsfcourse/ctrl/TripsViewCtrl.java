package com.jsfcourse.ctrl;

import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.enterprise.context.RequestScoped;
import jakarta.ejb.EJB;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.faces.context.Flash;

import java.io.IOException;

import com.jsf.dao.AttractionDAO;
import com.jsf.dao.TripDAO;
import com.jsf.entities.Attraction;
import com.jsf.entities.Trip;

@Named
@RequestScoped
public class TripsViewCtrl {
	//private static final String PAGE_PERSON_EDIT = "personEdit?faces-redirect=true";
	//private static final String PAGE_STAY_AT_THE_SAME = null;

	private String title;

		
	@Inject
	ExternalContext extcontext;
	
	@Inject
	Flash flash;
	
	@EJB
	TripDAO tripDAO;
	
	@EJB
	AttractionDAO attractionDAO;
	
	@Inject
	FacesContext context;
		
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}




}
