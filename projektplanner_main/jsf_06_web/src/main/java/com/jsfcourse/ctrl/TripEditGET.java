package com.jsfcourse.ctrl;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
import java.util.logging.Logger;

import org.primefaces.model.LazyDataModel;

import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.annotation.PostConstruct;
//import jakarta.enterprise.context.RequestScoped;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
//import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
//import jakarta.faces.context.Flash;
import jakarta.faces.view.ViewScoped;
import jakarta.faces.application.FacesMessage;

import com.jsf.dao.AttractionDAO;
import com.jsf.dao.TripDAO;
import com.jsf.entities.Attraction;
//import com.jsf.dao.UserDAO;
import com.jsf.entities.Trip;
//import com.jsf.entities.User;
import com.jsf.entities.User;

@Named
@ViewScoped
public class TripEditGET implements Serializable {
	private static final long serialVersionUID = 1L;

	private static final String PAGE_TRIP_LIST = "/pages/trippages/triplistview?faces-redirect=true";
	private static final String PAGE_USER_TRIPS_LIST = "/pages/trippages/usertripslist?faces-redirect=true";
	private static final String PAGE_STAY_AT_THE_SAME = null;
	
	private static final Logger LOGGER = Logger.getLogger(TripEditGET.class.getName());

	private Trip trip = new Trip();
	private List<Attraction> attractionlist;
	private Trip loaded = null;
	
	@EJB
	TripDAO tripDAO;
	@EJB
	AttractionDAO attractionDAO;

	@Inject
	FacesContext context;
	
	public Trip getTrip() {
		return trip;
	}
	public void setTrip(Trip trip) {
		this.trip = trip;
	}
	public List<Attraction> getAttractionlist(){
		return attractionlist;
	}
	public void setAttractionlist(List<Attraction> attractionlist) {
		this.attractionlist = attractionlist;
	}
	
	public void onLoad() throws IOException {
		if (!context.isPostback()) {
			if (!context.isValidationFailed() && trip.getTripsId() != null) {
				loaded = tripDAO.find(trip.getTripsId());
				
				attractionlist = attractionDAO.findByTripID(loaded.getTripsId());
				
				
			}
			if (loaded != null) {
				trip = loaded;
				
				FacesContext.getCurrentInstance().getExternalContext().getApplicationMap().put("loadedData", loaded);
			} else {
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Błędne użycie systemu", null));
				 if (!context.isPostback()) { 
				 context.getExternalContext().redirect("/planer/pages/trippages/index.xhtml");
				 context.responseComplete();
				 }
			}
		}

	}
	
	public String deleteAttractionFromTrip(Attraction attraction) throws IOException {
	        if (attraction != null) {	            
	            attraction.setTrip(null);
	            attractionDAO.merge(attraction);
	        } else {
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Błędne użycie systemu", null));
				 if (!context.isPostback()) { 
				 context.getExternalContext().redirect("/planer/pages/trippages/triplistview.xhtml");
				 context.responseComplete();
				 }
	        }
		return PAGE_STAY_AT_THE_SAME;
	}

	public String saveData() {
		
		if (loaded == null) {
			return PAGE_STAY_AT_THE_SAME;
		}
		try {
			if (trip.getTripsId() == null) {
							
				tripDAO.create(trip);
			} else {
				
				tripDAO.merge(trip);
			}
		} catch (Exception e) {
			e.printStackTrace();
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "wystąpił błąd podczas zapisu", null));
			return PAGE_STAY_AT_THE_SAME;
		}

		return PAGE_TRIP_LIST;
	}
	public String saveDataUser() {
		
		if (loaded == null) {
			return PAGE_STAY_AT_THE_SAME;
		}
		try {
			if (trip.getTripsId() == null) {
							
				tripDAO.create(trip);
			} else {
				
				tripDAO.merge(trip);
			}
		} catch (Exception e) {
			e.printStackTrace();
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "wystąpił błąd podczas zapisu", null));
			return PAGE_STAY_AT_THE_SAME;
		}

		return PAGE_USER_TRIPS_LIST;
	}
	public String cancelEdit() {
		return PAGE_TRIP_LIST;
	}

}
