package com.jsfcourse.ctrl;

import java.beans.IntrospectionException;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;
import org.primefaces.model.filter.FilterConstraint;
import org.primefaces.util.LocaleUtils;

import com.jsf.dao.AttractionDAO;
import com.jsf.dao.TripDAO;
import com.jsf.dao.UserDAO;
import com.jsf.entities.Attraction;
import com.jsf.entities.Trip;
import com.jsf.entities.User;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.context.FacesContext;
import jakarta.faces.simplesecurity.RemoteClient;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Named
@RequestScoped
public class LazyView implements Serializable {


	private static final long serialVersionUID = 1L;
	
	private List<User> users;
	private List<Attraction> attractions;
	private List<Trip> trips;
	private String name;

	private LazyDataModel<User> lazyUsers;
	private LazyDataModel<Attraction> lazyAttractions;
	private LazyDataModel<Attraction> lazyAttractionsTrips;
	private LazyDataModel<Trip> lazyTrips;
	private LazyDataModel<Trip> lazyTripsUsers;
	

	public LazyDataModel<User> getLazyUsers(){
		return lazyUsers;
	}
	
	public List<User> getUsers(){
		return users;
	}
	
	public LazyDataModel<Attraction> getLazyAttractions(){
		return lazyAttractions;
	}
	public LazyDataModel<Attraction> getLazyAttractionsTrips(){
		return lazyAttractionsTrips;
	}
	
	public List<Attraction> getAttractions(){
		return attractions;
	}
	
	public LazyDataModel<Trip> getLazyTrips(){
		return lazyTrips;
	}
	public LazyDataModel<Trip> getLazyTripsUsers(){
		return lazyTripsUsers;
	}
	
	public List<Trip> getTrips(){
		return trips;
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}  

    @EJB
    UserDAO userDAO;
    @EJB
    AttractionDAO attractionDAO;
    @EJB
    TripDAO tripDAO;
    

    @PostConstruct
    public void init() {
    	users = getUsersList();
    	attractions = getAttractionsList();
    	trips = getTripsList();
        lazyUsers = new LazyDataModel<User>() {
        	
        	private static final long serialVersionUID = 1L;
        	
        	private List<User> users;
        	
        	@Override
            public List<User> load(int first, int pageSize, Map<String, SortMeta> arg2, Map<String, FilterMeta> arg3) {
                
        		Map<String,Object> searchParams = new HashMap<String, Object>();
        		
       		if (name != null && name.length() > 0){
       			searchParams.put("name", name);
        		}

            	users = userDAO.getUsers(first, pageSize, searchParams);
            	

            	int rowCount = (int) userDAO.getUserTotalCount(searchParams);
            	setRowCount(rowCount);
            	
 
            	
                return users;
            }
			@Override
			public int count(Map<String, FilterMeta> arg0) {
				
				return 0;
			}
        };
        lazyAttractions = new LazyDataModel<Attraction>() {
        	
        	private static final long serialVersionUID = 1L;
        	
        	private List<Attraction> attractions;
        	
        	@Override
            public List<Attraction> load(int first, int pageSize, Map<String, SortMeta> arg2, Map<String, FilterMeta> arg3) {
                
        		Map<String,Object> searchParams = new HashMap<String, Object>();
        		
           		if (name != null && name.length() > 0){
           			searchParams.put("name", name);
            		}
        		attractions = attractionDAO.getAttractions(first, pageSize, searchParams);

            	int rowCount = (int) attractionDAO.getAttractionsTotalCount(searchParams);
            	setRowCount(rowCount);

                return attractions;
            }
			@Override
			public int count(Map<String, FilterMeta> filterBy) {
				
				return 0;
			}
        };
        lazyTripsUsers = new LazyDataModel<Trip>() {
        	
        	private static final long serialVersionUID = 1L;
        	
        	private List<Trip> trips;
        	
        	@Override
            public List<Trip> load(int first, int pageSize, Map<String, SortMeta> arg2, Map<String, FilterMeta> arg3) {
               
        		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        		HttpSession session = request.getSession();
        		
                RemoteClient<User> client = RemoteClient.load(session);

                if(client != null && client.getDetails() != null ) {
                    String email = client.getDetails().getEmail();
                    trips = tripDAO.getList(email, first, pageSize);
                    int rowCount = (int) tripDAO.getTripsUsersTotalCount(email);
                	setRowCount(rowCount);
                }

            	

                return trips;
            }
			@Override
			public int count(Map<String, FilterMeta> arg0) {
				
				return 0;
			}
        };
        	lazyTrips = new LazyDataModel<Trip>() {
        	
        	private static final long serialVersionUID = 1L;
        	
        	private List<Trip> trips;
        	
        	@Override
            public List<Trip> load(int first, int pageSize, Map<String, SortMeta> arg2, Map<String, FilterMeta> arg3) {
                

           		Map<String,Object> searchParams = new HashMap<String, Object>();
        		
           		if (name != null && name.length() > 0){
           			searchParams.put("name", name);
            		}
        		trips = tripDAO.getTrips(first, pageSize, searchParams);

            	int rowCount = (int) tripDAO.getTripsTotalCount(searchParams);
            	setRowCount(rowCount);

                return trips;
            }
			@Override
			public int count(Map<String, FilterMeta> arg0) {
				
				return 0;
			}
        };
        
    
    }

    
    public List<User> getUsersList() {
    	return userDAO.getFullList();
    }

    public LazyDataModel<User> getLazyModelU() {
        return lazyUsers;
    }
    public List<Attraction> getAttractionsList() {
    	return attractionDAO.getFullList();
    }

    public LazyDataModel<Attraction> getLazyModelA() {
        return lazyAttractions;
    }
    public List<Trip> getTripsList() {
    	return tripDAO.getFullList();
    }

    public LazyDataModel<Trip> getLazyModelT() {
        return lazyTrips;
    }

}