package com.jsfcourse.ctrl;

import java.io.IOException;
//import java.util.HashMap;
import java.util.List;
//import java.util.Map;

import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.enterprise.context.RequestScoped;
import jakarta.ejb.EJB;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.faces.context.Flash;

import com.jsf.dao.AttractionDAO;
import com.jsf.entities.Attraction;
//import com.jsf.entities.User;
import com.jsf.entities.Trip;
import com.jsf.entities.User;

@Named
@RequestScoped
public class AttractionsListCtrl {
	
	private static final String PAGE_ATTRACTION_EDIT = "/attracionEditGET?faces-redirect=true";
	private static final String PAGE_STAY_AT_THE_SAME = null;

	private String name;

		
	@Inject
	ExternalContext extcontext;
	
	@Inject
	Flash flash;
	
	@EJB
	AttractionDAO attractionDAO;
	
	@Inject
	FacesContext context;
	
	private Attraction attraction = new Attraction();
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	public Attraction getAttraction() {
		return attraction;
	}
	public void setAttraction(Attraction attraction) {
		this.attraction = attraction;
	}


	public List<Attraction> getFullList(){
		return attractionDAO.getFullList();
	}

	public String newAttraction(){
		
		attraction.setIsAvailable("1");
		attractionDAO.create(attraction);
		
		return PAGE_ATTRACTION_EDIT;
	}

	public String editAttraction(Attraction attraction){

		FacesContext facesContext = FacesContext.getCurrentInstance();
	    Flash flash = facesContext.getExternalContext().getFlash();
	    
		flash.put("attraction", attraction);
		return PAGE_ATTRACTION_EDIT;
	}

	public String deleteAttraction(Attraction attraction){
		attractionDAO.remove(attraction);
		return PAGE_STAY_AT_THE_SAME;
	}
	
	
	public String redirectEditAttractionGET() {
		return PAGE_ATTRACTION_EDIT;
	}

}
