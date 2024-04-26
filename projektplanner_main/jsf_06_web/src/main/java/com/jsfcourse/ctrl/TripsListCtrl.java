package com.jsfcourse.ctrl;

import java.util.List;
//import java.util.Map;

import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.enterprise.context.RequestScoped;
import jakarta.ejb.EJB;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.faces.context.Flash;
import jakarta.faces.simplesecurity.RemoteClient;

import com.jsf.dao.AttractionDAO;
import com.jsf.dao.TripDAO;
import com.jsf.entities.Attraction;
import com.jsf.entities.Trip;
//import com.jsf.entities.User;
import com.jsf.entities.User;

@Named
@RequestScoped
public class TripsListCtrl {
	private static final String PAGE_TRIP_EDIT = "tripEditGET?faces-redirect=true";
//	private static final String PAGE_TRIP_EDIT = "/tripEditGET.xhtml";
	private static final String PAGE_STAY_AT_THE_SAME = null;

		private String title;


	
	@Inject
	ExternalContext extcontext;
	
	@Inject
	Flash flash;
	

	
	@EJB
	TripDAO tripDAO;
	@EJB
	AttractionDAO attractionDAO;
	
	private List<Trip> userTrips;
		
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<Trip> getFullList(){
		return tripDAO.getFullList();
	}

	public List<Trip> getList(){
		List<Trip> list = null;
		
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		HttpSession session = request.getSession();
		
		RemoteClient<User> client = RemoteClient.load(session);
		
		String email = client.getDetails().getEmail();
		
		
		list = tripDAO.getListTripsEdit(email);
		
		return list;
	}
	


	    public List<Trip> getUserTrips() {
	        return userTrips;
	    }

	    
	    public List<Attraction> getTestItems() {
	        return attractionDAO.getFullList();
	    }

	    public List<Trip> getItemsToAdd() {
	        return tripDAO.getFullList();
	    }

		public String newTrip(){
			Trip trip = new Trip();
			
				
			
			flash.put("trip", trip);
			
			return PAGE_TRIP_EDIT;
		}

		public String editTrip(Trip trip){
			
			
			FacesContext facesContext = FacesContext.getCurrentInstance();
		    Flash flash = facesContext.getExternalContext().getFlash();
		    
			flash.put("trip", trip);
			return PAGE_TRIP_EDIT;
		}

		public String deleteTrip(Trip trip){
			tripDAO.remove(trip);
			return PAGE_STAY_AT_THE_SAME;
		}
		
		public String redirectEditTripGET() {
			return PAGE_TRIP_EDIT;
		}
	    
	    
	    
	    

}
