package org.sportTracker.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Workout {

	private double duration;
	private Date date;
	private Date departureTime;
	private Date finishTime;
	private Sport typeSport;
	private String description;
	private int id;
	private Collection<User> users;
	private Cardio cardio;
	private Parcours parcours;
	private Meteo meteo;

	public Workout() {
		this.users = new ArrayList<User>();
	}

	public void setCardio(Cardio cardio) {
		this.cardio = cardio;
	}

	public double getDuration() {
		return this.duration;
	}

	@Temporal(TemporalType.DATE)
	public Date getDate() {
		return this.date;
	}

	@Temporal(TemporalType.DATE)
	public Date getDepartureTime() {
		return this.departureTime;
	}

	@Temporal(TemporalType.DATE)
	public Date getFinishTime() {
		return this.finishTime;
	}

	@Enumerated(EnumType.STRING)
	public Sport getTypeSport() {
		return this.typeSport;
	}

	public String getDescription() {
		return this.description;
	}

	@Id
	@GeneratedValue
	public int getId() {
		return this.id;
	}

	// ---------------------- GESTION DES USERS --------------------------------

	/**
	 * Getter de la collection de Users
	 * 
	 * @return
	 */
	@ManyToMany
	public Collection<User> getUsers() {
		return this.users;
	}

	/**
	 * Setter de la collection de Users
	 * 
	 * @param user
	 */
	public void setUsers(Collection<User> users) {
		if (this.users == null) {
			this.users = new ArrayList<User>();
		}
		this.users = users;
	}

	/**
	 * Ajout d'un User à la collection
	 * 
	 * @param user
	 */
	public boolean addUser(User user) {
		if (this.users == null) {
			this.users = new ArrayList<User>();
		}

		return this.users.add(user);
	}

	/**
	 * Suppression d'un User de la collection
	 * 
	 * @param user
	 */
	public boolean delUser(User user) {
		if (this.users == null) {
			this.users = new ArrayList<User>();
		}

		return this.users.remove(user);
	}

	// ---------------------- FIN GESTION DES USERS ----------------------------

	@OneToOne
	public Cardio getCardio() {
		return this.cardio;
	}

	@OneToOne
	public Parcours getParcours() {
		return this.parcours;
	}

	@ManyToOne
	public Meteo getMeteo() {
		return this.meteo;
	}

	public void setDuration(double duration) {
		this.duration = duration;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void setDepartureTime(Date departureTime) {
		this.departureTime = departureTime;
	}

	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}

	public void setTypeSport(Sport typeSport) {
		this.typeSport = typeSport;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setId(int myId) {
		this.id = myId;
	}

	public void setParcours(Parcours parcours) {
		this.parcours = parcours;
	}

	public void setMeteo(Meteo meteo) {
		this.setMeteo(meteo);
	}

}
