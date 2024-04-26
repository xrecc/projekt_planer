package com.jsfcourse.ctrl;

import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.faces.context.Flash;
import jakarta.faces.simplesecurity.RemoteClient;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.time.LocalDate;
import java.util.Calendar;
//import jakarta.ws.rs.GET;
//
//import java.time.LocalDate;
import java.util.Date;
import java.util.logging.Logger;

import com.jsf.dao.TripDAO;
import com.jsf.dao.UserDAO;
import com.jsf.entities.Trip;
import com.jsf.entities.User;



@Named
@RequestScoped
public class TripCreationViewCtrl{
	
	private static final String PAGE_ADD_ATTRACTIONS = "/pages/trippages/tripview?faces-redirect=true";
	private static final String PAGE_STAY_AT_THE_SAME = null;

	private static final Logger LOGGER = Logger.getLogger(TripCreationViewCtrl.class.getName());
	@Inject
	ExternalContext extcontext;
	
	@Inject
	FacesContext context;
	
	@Inject
	Flash flash;
	
	@EJB
	TripDAO tripDAO;
	UserDAO userDAO;
	
	private User user = new User();
	private Trip trip = new Trip();
//	Date today = new Date();

	
	public User getUser() {
		return user;
	}
	
	public Trip getTrip() {
		return trip;
	}
	public void setTrip(Trip trip) {
		this.trip = trip;
	}

//	public Date dateToday() {
//		Date today = new Date();
//		return today;
//	}
//	
//	public Date getDateToday() {
//		return today;
//	}
//	public void getDateToday(Date today) {
//		this.today = today;
//	}

	public String newTrip() {
		
		//LOGGER.info(today.toString());
		
		Date datastart = trip.getTimestart();
		//LOGGER.info(datastart.toString());
		Date dataend = trip.getTimeend();
		//LOGGER.info(dataend.toString());
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		Date today = calendar.getTime();
	
		
		if ((datastart.compareTo(dataend) < 0 || datastart.compareTo(dataend) == 0) && (datastart.compareTo(today) > 0 || datastart.compareTo(today) == 0)){
			
			HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
    		HttpSession session = request.getSession();
    		
            RemoteClient<User> client = RemoteClient.load(session);

            if(client != null && client.getDetails() != null ) {
                Integer id = client.getDetails().getUsersId();
                user.setUsersId(id);			        			
    			trip.setUser(user);
    			tripDAO.create(trip);
            }
			
			
			
		} else {
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Data końcowa musi być większa od początkowej", null));
			return PAGE_STAY_AT_THE_SAME;
		}
		return PAGE_ADD_ATTRACTIONS;
	}
	
	public String goToTrip() {
		return PAGE_ADD_ATTRACTIONS;
	}
	
	

}
