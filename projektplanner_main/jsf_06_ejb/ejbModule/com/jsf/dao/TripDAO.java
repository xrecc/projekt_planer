package com.jsf.dao;

import java.util.List;
import java.util.Map;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;

import com.jsf.entities.Trip;
import com.jsf.entities.User;

//DAO - Data Access Object for Person entity
//Designed to serve as an interface between higher layers of application and data.
//Implemented as stateless Enterprise Java bean - server side code that can be invoked even remotely.

@Stateless
public class TripDAO {
	private final static String UNIT_NAME = "entitiesgenerate";
	
	
	@Inject
	private UserDAO userDAO;

	// Dependency injection (no setter method is needed)
	@PersistenceContext(unitName = UNIT_NAME)
	protected EntityManager em;

	public void create(Trip trip) {
		em.persist(trip);
	}

	public Trip merge(Trip trip) {
		return em.merge(trip);
	}

	public void remove(Trip trip) {
		em.createNativeQuery("SET FOREIGN_KEY_CHECKS=0").executeUpdate();
		em.remove(em.merge(trip));
		em.createNativeQuery("SET FOREIGN_KEY_CHECKS=1").executeUpdate();
	}

	public Trip find(Object tripsId) {
		return em.find(Trip.class, tripsId);
	}

	public List<Trip> getFullList() {
		List<Trip> list = null;

		Query query = em.createQuery("select t from Trip t");

		try {
			list = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}
	public List<Trip> getListTripsEdit(String email) {
		List<Trip> list = null;

		Query query = em.createQuery("select t from Trip t where t.user.email =:email");
		query.setParameter("email", email);

		try {
			list = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}
	

	public List<Trip> getList(String email, int offset, int pageSize) {
		List<Trip> list = null;
		

		Query query = em.createQuery("select t from Trip t where t.user.email =:email order by t.title asc");
		query.setParameter("email", email);
        query.setFirstResult(offset); 
        query.setMaxResults(pageSize); 
        
        list = query.getResultList();
        return list;
	}
    public long getTripsUsersTotalCount(String email) {
    	long count = 0;
        TypedQuery<Long> query = em.createQuery("select count(t) from Trip t where t.user.email =:email", Long.class);
        query.setParameter("email", email);

        count = (long) query.getSingleResult();
        return count;
    }

	public Trip getTripsByTitle(String selectedOption) {
		
	
		TypedQuery<Trip> query = em.createQuery("select t.tripsId t from Trip t WHERE t.title =:title", Trip.class);
		query.setParameter("title", selectedOption);
			

		return (Trip) query.getSingleResult();
	}

	public List<Trip> getTrips(int offset, int pageSize, Map<String, Object> searchParams) {
    	List<Trip> list = null;
    	
    	String select = "select t ";
    	String from = "from Trip t ";
    	String where = "";
    	
    	String name = (String) searchParams.get("name");
    	if (name != null) {
    		if (where.isEmpty()) {
    			where = "where ";
    		}else {
    			where += "and ";
    		}
    		where += "t.title like :name ";
    	}
    	Query query = em.createQuery(select + from + where);
		query.setFirstResult(offset); 
        query.setMaxResults(pageSize); 
    	
    	if(name !=null) {
    		query.setParameter("name", name+"%");

    	}
//	public List<Trip> getTrips(int offset, int pageSize) {
//    	List<Trip> list = null;
//    	
//    	
//        TypedQuery<Trip> query = em.createQuery("SELECT distinct t FROM Trip t", Trip.class);
//        query.setFirstResult(offset); 
//        query.setMaxResults(pageSize); 
        
        list = query.getResultList();
        return list;
    }
    public long getTripsTotalCount(Map<String, Object> searchParams) {
    	long count = 0;
        //TypedQuery<Long> query = em.createQuery("select count(t) from Trip t", Long.class);
    	String select = "select count(t) ";
    	String from = "from Trip t ";
    	String where = "";
    	
    	String name = (String) searchParams.get("name");
    	if (name != null) {
    		if (where.isEmpty()) {
    			where = "where ";
    		}else {
    			where += "and ";
    		}
    		where += "t.title like :name ";
    	}
    	Query query = em.createQuery(select + from + where);
    	
    	if(name !=null) {
    		query.setParameter("name", name+"%");
    	}
        count = (long) query.getSingleResult();
        return count;
    }
    
    public void saveTrip(Trip trip) {
    	em.persist(trip);
    }
    
    public Trip getTripById(Integer tripId) {
    	return em.find(Trip.class, tripId);
    }


}
