package com.jsf.dao;

import java.util.List;
//import java.util.Map;
import java.util.Map;

import jakarta.ejb.Stateless;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;

import com.jsf.entities.Attraction;


//DAO - Data Access Object for Person entity
//Designed to serve as an interface between higher layers of application and data.
//Implemented as stateless Enterprise Java bean - server side code that can be invoked even remotely.

@Stateless
public class AttractionDAO {
	private final static String UNIT_NAME = "entitiesgenerate";

	// Dependency injection (no setter method is needed)
	@PersistenceContext(unitName = UNIT_NAME)
	protected EntityManager em;

	public void create(Attraction attraction) {
		em.persist(attraction);
	}

	public Attraction merge(Attraction attraction) {
		return em.merge(attraction);
	}

	public void remove(Attraction attraction) {
		em.remove(em.merge(attraction));
	}

	public Attraction find(Object attractionsId) {
		return em.find(Attraction.class, attractionsId);
	}

	public List<Attraction> getFullList() {
		List<Attraction> list = null;

		Query query = em.createQuery("select a from Attraction a");

		try {
			list = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}
	
	public List<Attraction> findByTripID(Integer tripID){
		Query query = em.createQuery("select a from Attraction a WHERE a.trip.tripsId = :tripID");
		query.setParameter("tripID", tripID);
		return query.getResultList();
	}


	public List<Attraction> getAttractions(int offset, int pageSize, Map<String, Object> searchParams) {
    	List<Attraction> list = null;
    	
    	String select = "select a ";
    	String from = "from Attraction a ";
    	String where = "";
    	
    	String name = (String) searchParams.get("name");
    	if (name != null) {
    		if (where.isEmpty()) {
    			where = "where ";
    		}else {
    			where += "and ";
    		}
    		where += "a.name like :name or a.city like :name or a.country like :name ";
    	}
    	Query query = em.createQuery(select + from + where);
		query.setFirstResult(offset); 
        query.setMaxResults(pageSize); 
    	
    	if(name !=null) {
    		query.setParameter("name", name+"%");

    	}
    	
//        TypedQuery<Attraction> query = em.createQuery("SELECT a FROM Attraction a", Attraction.class);
//        query.setFirstResult(offset); 
//        query.setMaxResults(pageSize); 
        
        list = query.getResultList();
        return list;
    }
	public List<Attraction> getAttractionsTrips(int offset, int pageSize, List<Attraction> listat) {
    	List<Attraction> list = null;
    	   	
    	
        TypedQuery<Attraction> query = em.createQuery("SELECT a FROM Attraction a WHERE a.trip.tripsId = :tripId", Attraction.class);
        query.setParameter("tripId", listat);
        query.setFirstResult(offset); 
        query.setMaxResults(pageSize); 
        
        list = query.getResultList();
        return list;
    }
    public long getAttractionsTotalCount(Map<String, Object> searchParams) {
    	long count = 0;
       // TypedQuery<Long> query = em.createQuery("select count(a) from Attraction a", Long.class);
       	String select = "select count(a) ";
    	String from = "from Attraction a ";
    	String where = "";
    	
    	String name = (String) searchParams.get("name");
    	if (name != null) {
    		if (where.isEmpty()) {
    			where = "where ";
    		}else {
    			where += "and ";
    		}
    		where += "a.name like :name or a.city like :name or a.country like :name ";
    	}
    	Query query = em.createQuery(select + from + where);
    	
    	if(name !=null) {
    		query.setParameter("name", name+"%");
    	}
        count = (long) query.getSingleResult();
        return count;
    }
    

}
