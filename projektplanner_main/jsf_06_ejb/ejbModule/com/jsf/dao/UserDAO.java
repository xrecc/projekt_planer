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
		em.createNativeQuery("SET FOREIGN_KEY_CHECKS=0").executeUpdate();
		em.remove(em.merge(user));
		em.createNativeQuery("SET FOREIGN_KEY_CHECKS=1").executeUpdate();
	}

	public User find(Object usersId) {
		return em.find(User.class, usersId);
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
	public boolean userEx(String email) {

		Query query = em.createQuery("SELECT COUNT(u) FROM User u WHERE u.email = :email", User.class);
		
		query.setParameter("email", email);

		
		Long count = (Long) query.getSingleResult();
		

		return count > 0;
	}


public User getUserFromDatabase(String email, String pass) {
		try {
			TypedQuery<User> query = em.createQuery("SELECT u FROM User u WHERE u.email = :email AND u.surname = :surname", User.class);
			
			query.setParameter("email", email);
			query.setParameter("surname", pass);
			
			User user = query.getSingleResult();
			
			return user;
			
		} catch (Exception e) {
			System.out.println("Nieprawidłowe hasło lub email");
		}
		return null;
	}

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
    public List<User> getUsers(int offset, int pageSize, Map<String, Object> searchParams) {
    	List<User> list = null;
    	
    	String select = "select u ";
    	String from = "from User u ";
    	String where = "";
    	
    	String name = (String) searchParams.get("name");
    	if (name != null) {
    		if (where.isEmpty()) {
    			where = "where ";
    		}else {
    			where += "and ";
    		}
    		where += "u.name like :name or u.email like :name ";
    	}
    	Query query = em.createQuery(select + from + where);
		query.setFirstResult(offset); 
        query.setMaxResults(pageSize); 
        
    	if(name !=null) {
    		query.setParameter("name", name+"%");

    	}
//    public List<User> getUsers(int offset, int pageSize) {
//    	List<User> list = null;
//    	
//      TypedQuery<User> query = em.createQuery("SELECT u FROM User u", User.class);
//    	
//    		query.setFirstResult(offset); 
//            query.setMaxResults(pageSize); 
//
//    
        list = query.getResultList();
        return list;
    }
    public long getUserTotalCount(Map<String, Object> searchParams) {
    	long count = 0;
       // TypedQuery<Long> query = em.createQuery("select count(u) from User u", Long.class);
    	String select = "select count(u) ";
    	String from = "from User u ";
    	String where = "";
    	
    	String name = (String) searchParams.get("name");
    	if (name != null) {
    		if (where.isEmpty()) {
    			where = "where ";
    		}else {
    			where += "and ";
    		}
    		where += "u.name like :name or u.email like :name ";
    	}
    	Query query = em.createQuery(select + from + where);
    	if(name !=null) {
    		query.setParameter("name", name+"%");
    	}
        count = (long) query.getSingleResult();
        return count;
    }
}
