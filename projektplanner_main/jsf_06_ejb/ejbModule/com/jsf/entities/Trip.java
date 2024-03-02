package com.jsf.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;


/**
 * The persistent class for the trips database table.
 * 
 */
@Entity
@Table(name="trips")
@NamedQuery(name="Trip.findAll", query="SELECT t FROM Trip t")
public class Trip implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="trips_id")
	private int tripsId;

	@Temporal(TemporalType.DATE)
	@Column(name="created_at")
	private Date createdAt;

	@Temporal(TemporalType.DATE)
	private Date timeend;

	@Temporal(TemporalType.DATE)
	private Date timestart;

	private String title;

	//bi-directional many-to-one association to Attraction
	@OneToMany(mappedBy="trip")
	private List<Attraction> attractions;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="users_users_id")
	private User user;

	public Trip() {
	}

	public int getTripsId() {
		return this.tripsId;
	}

	public void setTripsId(int tripsId) {
		this.tripsId = tripsId;
	}

	public Date getCreatedAt() {
		return this.createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getTimeend() {
		return this.timeend;
	}

	public void setTimeend(Date timeend) {
		this.timeend = timeend;
	}

	public Date getTimestart() {
		return this.timestart;
	}

	public void setTimestart(Date timestart) {
		this.timestart = timestart;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<Attraction> getAttractions() {
		return this.attractions;
	}

	public void setAttractions(List<Attraction> attractions) {
		this.attractions = attractions;
	}

	public Attraction addAttraction(Attraction attraction) {
		getAttractions().add(attraction);
		attraction.setTrip(this);

		return attraction;
	}

	public Attraction removeAttraction(Attraction attraction) {
		getAttractions().remove(attraction);
		attraction.setTrip(null);

		return attraction;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}