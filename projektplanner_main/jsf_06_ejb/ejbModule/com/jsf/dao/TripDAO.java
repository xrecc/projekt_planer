package com.jsf.dao;

import java.util.List;
import java.util.Map;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

import com.jsf.entities.Trip;

//DAO - Data Access Object for Person entity
//Designed to serve as an interface between higher layers of application and data.
//Implemented as stateless Enterprise Java bean - server side code that can be invoked even remotely.

@Stateless
public class TripDAO {
	private final static String UNIT_NAME = "entitiesgenerate";

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
		em.remove(em.merge(trip));
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

	public List<Trip> getList(Map<String, Object> searchParams) {
		List<Trip> list = null;

		// 1. Build query string with parameters
		String select = "select t ";
		String from = "from Trip t ";
		String where = "";
		String orderby = "order by t.title asc";

		// search for name
		String name = (String) searchParams.get("title");
		if (name != null) {
			if (where.isEmpty()) {
				where = "where ";
			} else {
				where += "and ";
			}
			where += "t.title like :title ";
		}
		
		// ... other parameters ... 

		// 2. Create query object
		Query query = em.createQuery(select + from + where + orderby);

		// 3. Set configured parameters
		if (name != null) {
			query.setParameter("title", name+"%");
		}

		// ... other parameters ... 

		// 4. Execute query and retrieve list of Person objects
		try {
			list = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

}
