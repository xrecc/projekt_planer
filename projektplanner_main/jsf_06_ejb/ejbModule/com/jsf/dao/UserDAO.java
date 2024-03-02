package com.jsf.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;

import com.jsf.entities.User;

//DAO - Data Access Object for Person entity
//Designed to serve as an interface between higher layers of application and data.
//Implemented as stateless Enterprise Java bean - server side code that can be invoked even remotely.

@Stateless
public class UserDAO {
	private final static String UNIT_NAME = "entitiesgenerate";

	// Dependency injection (no setter method is needed)
	@PersistenceContext(unitName = UNIT_NAME)
	protected EntityManager em;

	public void create(User user) {
		em.persist(user);
	}

	public User merge(User user) {
		return em.merge(user);
	}

	public void remove(User user) {
		em.remove(em.merge(user));
	}

	public User find(Object userId) {
		return em.find(User.class, userId);
	}
	

	public List<User> getFullList() {
		List<User> list = null;

		Query query = em.createQuery("select u from User u");

		try {
			list = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	public List<User> getList(Map<String, Object> searchParams) {
		List<User> login = null;

		// 1. Build query string with parameters
		String select = "select u ";
		String from = "from User u ";
		String where = "";
		String orderby = "order by u.name asc";

		// search for name
		String name = (String) searchParams.get("name");
		if (name != null) {
			if (where.isEmpty()) {
				where = "where ";
			} else {
				where += "and ";
			}
			where += "u.name like :name ";
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
			login = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return login;
	}
//	public List<User> getPassword(Map<String, Object> searchParams) {
//		List<User> password = null;
//
//		// 1. Build query string with parameters
//		String select = "select u ";
//		String from = "from User u ";
//		String where = "";
//		String orderby = "order by u.surname asc";
//
//		// search for name
//		String surname = (String) searchParams.get("surname");
//		if (surname != null) {
//			if (where.isEmpty()) {
//				where = "where ";
//			} else {
//				where += "and ";
//			}
//			where += "u.name like :name or u.surname like :surname or u.email like :email or u.phonenumber like :phonenumber ";
//		}
//		
//		// ... other parameters ... 
//
//		// 2. Create query object
//		Query query = em.createQuery(select + from + where + orderby);
//
//		// 3. Set configured parameters
//		if (surname != null) {
//			query.setParameter("surname", surname+"%");
//		}
//
//		// ... other parameters ... 
//
//		// 4. Execute query and retrieve list of Person objects
//		try {
//			password = query.getResultList();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		return password;
//	}
public User getUserFromDatabase(String login, String pass) {
		try {
			TypedQuery<User> query = em.createQuery("SELECT u FROM User u WHERE u.name = :name AND u.surname = :surname", User.class);
			
			query.setParameter("name", login);
			query.setParameter("surname", pass);
			
			User user = query.getSingleResult();
			
			return user;
			
		} catch (Exception e) {
			System.out.println("Nieprawidłowe hasło lub login");
		}
		return null;
	}

	// simulate retrieving roles of a User from DB
	public List<String> getUserRolesFromDatabase(User user) {
		
		ArrayList<String> roles = new ArrayList<String>();
		
		if (user.getRole().equals("0")) {
			roles.add("user");
		}
		if (user.getRole().equals("1")) {
			roles.add("admin");
		}
		
		return roles;
	}
}
