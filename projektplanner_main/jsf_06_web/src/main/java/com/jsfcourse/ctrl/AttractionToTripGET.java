package com.jsfcourse.ctrl;

import java.io.IOException;
import java.io.Serializable;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;

import jakarta.inject.Inject;
import jakarta.inject.Named;
//import jakarta.enterprise.context.RequestScoped;
import jakarta.ejb.EJB;
//import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
//import jakarta.faces.context.Flash;
import jakarta.faces.view.ViewScoped;
import jakarta.faces.application.FacesMessage;

import com.jsf.dao.AttractionDAO;
//import com.jsf.dao.UserDAO;
import com.jsf.entities.Attraction;


@Named
@ViewScoped
public class AttractionToTripGET implements Serializable {
	private static final long serialVersionUID = 1L;

	private static final String PAGE_ATTRACTION_LIST = "/pages/trippages/tripview?faces-redirect=true";
	private static final String PAGE_STAY_AT_THE_SAME = null;

	private Attraction attraction = new Attraction();
	private Attraction loaded = null;
	
	@EJB
	AttractionDAO attractionDAO;

	@Inject
	FacesContext context;
	
	public Attraction getAttraction() {
		return attraction;
	}
	public void setAttraction(Attraction attraction) {
		this.attraction = attraction;
	}

	
	public void onLoad() throws IOException {
		if (!context.isPostback()) {
			if (!context.isValidationFailed() && attraction.getAttractionsId() != null) {
				loaded = attractionDAO.find(attraction.getAttractionsId());
			}
			if (loaded != null) {
				attraction = loaded;
			} else {
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Błędne użycie systemu", null));
				 if (!context.isPostback()) { 
				 context.getExternalContext().redirect("/planer/pages/trippages/tripview.xhtml");
				 context.responseComplete();
				 }
			}
		}

	}

	public String saveData() {

		if (loaded == null) {
			return PAGE_STAY_AT_THE_SAME;
		}
		try {
			if (attraction.getAttractionsId() == null) {
			
				attractionDAO.create(attraction);
			} else {

				attractionDAO.merge(attraction);
			}
		} catch (Exception e) {
			e.printStackTrace();
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "wystąpił błąd podczas zapisu", null));
			return PAGE_STAY_AT_THE_SAME;
		}

		return PAGE_ATTRACTION_LIST;
	}
	public String cancelEdit() {
		return PAGE_ATTRACTION_LIST;
	}

}
