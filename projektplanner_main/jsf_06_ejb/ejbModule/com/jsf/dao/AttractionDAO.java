package com.jsf.dao;

import java.util.List;
import java.util.Map;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

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

	public List<Attraction> getList(Map<String, Object> searchParams) {
		List<Attraction> list = null;

		// 1. Build query string with parameters
		String select = "select a ";
		String from = "from Attraction a ";
		String where = "";
		String orderby = "order by a.country asc";

		// search for name
		String name = (String) searchParams.get("name");
		if (name != null) {
			if (where.isEmpty()) {
				where = "where ";
			} else {
				where += "and ";
			}
			where += "a.name like :name or a.city like :name or a.country like :name ";
		}
		
		// ... other parameters ... 

		// 2. Create query object
		Query query = em.createQuery(select + from + where + orderby);

		// 3. Set configured parameters
		if (name != null) {
			query.setParameter("name", name+"%");
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
	public List<Attraction> getCountryList() {
		List<Attraction> list = null;

		Query query = em.createQuery("select distinct a.country from Attraction a");

		try {
			list = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}
	public List<Attraction> getCityList() {
		List<Attraction> listc = null;

		Query query = em.createQuery("select distinct a.city from Attraction a");

		try {
			listc = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return listc;
	}

}
