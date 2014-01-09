package org.sportTracker.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@Entity
// @XmlRootElement
@JsonIgnoreProperties({ "friends", "workouts", "tokenKey" })
@Table(name = "TUSER")
public class User {

	private String firstname;
	private String lastname;
	private String password;
	private Date birthDate;
	private double weight;
	private GenderEnum gender;
	private String pseudo;
	private String fbAcct;
	private String twAcct;
	private String mail;
	private String avatar;
	private String group;
	private Collection<Workout> workouts;
	private Collection<User> friends;
	private int id;
	private String tokenKey;

	public User() {
		this.friends = new ArrayList<User>();
		this.workouts = new ArrayList<Workout>();
	}

	public User(String name, String surname, String password, Date birthDate,
			float weight, GenderEnum gender, String pseudo, String cmptFB,
			String cmptTW, String mailAdr, String avatar, String group) {

		this.firstname = name;
		this.friends = new ArrayList<User>();
		this.workouts = new ArrayList<Workout>();
		this.lastname = surname;
		this.password = password;
		this.birthDate = birthDate;
		this.weight = weight;
		this.gender = gender;
		this.pseudo = pseudo;
		this.fbAcct = cmptFB;
		this.twAcct = cmptTW;
		this.mail = mailAdr;
		this.avatar = avatar;
		this.group = group;
	}

	public String getFirstname() {
		return this.firstname;
	}

	public String getLastname() {
		return this.lastname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Temporal(TemporalType.DATE)
	public Date getBirthDate() {
		return this.birthDate;
	}

	public double getWeight() {
		return this.weight;
	}

	@Enumerated(EnumType.STRING)
	public GenderEnum getGender() {
		return this.gender;
	}

	public String getPseudo() {
		return this.pseudo;
	}

	public String getFbAcct() {
		return this.fbAcct;
	}

	public String getTwAcct() {
		return this.twAcct;
	}

	public String getMail() {
		return this.mail;
	}

	public String getAvatar() {
		return this.avatar;
	}

	@Column(name = "CGROUP")
	public String getGroup() {
		return this.group;
	}

	@Id
	@GeneratedValue
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public void setBirthDate(Date myBirthDate) {
		this.birthDate = myBirthDate;
	}

	public void setWeight(double myWeight) {
		this.weight = myWeight;
	}

	public void setGender(GenderEnum myGender) {
		this.gender = myGender;
	}

	public void setPseudo(String myPseudo) {
		this.pseudo = myPseudo;
	}

	public void setFbAcct(String myCompteFaceBook) {
		this.fbAcct = myCompteFaceBook;
	}

	public void setTwAcct(String myCompteTwitter) {
		this.twAcct = myCompteTwitter;
	}

	public void setMail(String myMailAdress) {
		this.mail = myMailAdress;
	}

	public void setAvatar(String myAvatar) {
		this.avatar = myAvatar;
	}

	public void setGroup(String myGroup) {
		this.group = myGroup;
	}

	// ---------------------------- GESTION DES AMIS ---------------------------

	/**
	 * Récupérer la liste d'amis d'un utilisateur
	 * 
	 * @return amis
	 */

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	@JoinTable(name = "FRIENDSHIP")
	public Collection<User> getFriends() {
		return this.friends;
	}

	/**
	 * Définit la liste d'amis d'un utilisateur
	 * 
	 * @param friends
	 */
	public void setFriends(Collection<User> friends) {
		if (this.friends == null) {
			this.friends = new ArrayList<User>();
		}

		this.friends = friends;
	}

	/**
	 * Ajout d'un ami au set d'amis existant
	 * 
	 * @param friend
	 */
	public void addFriend(User friend) {
		this.friends.add(friend);
	}

	public boolean delFriend(User friend) {
		return this.friends.remove(friend);
	}

	// ------------------------ FIN GESTION DES AMIS ---------------------------

	// ------------------------- GESTION DU WORKOUT ----------------------------

	/**
	 * Getter de la collection de Workouts
	 * 
	 * @return
	 */
	@ManyToMany(mappedBy = "users")
	public Collection<Workout> getWorkouts() {
		return this.workouts;
	}

	/**
	 * Setter de la collection de Workouts
	 * 
	 * @param workouts
	 */
	public void setWorkouts(Collection<Workout> workouts) {
		if (this.workouts == null) {
			this.workouts = new ArrayList<Workout>();
		}

		this.workouts = workouts;
	}

	/**
	 * Ajout d'un Workout à la collection
	 * 
	 * @param workout
	 */
	public boolean addWorkout(Workout workout) {
		if (this.workouts == null) {
			this.workouts = new ArrayList<Workout>();
		}

		return this.workouts.add(workout);
	}

	/**
	 * Suppression d'un Workout de la collection
	 * 
	 * @param workout
	 */
	public boolean delWorkout(Workout workout) {
		if (this.workouts == null) {
			this.workouts = new ArrayList<Workout>();
		}

		return this.workouts.remove(workout);
	}

	// --------------------- FIN GESTION DU WORKOUT ----------------------------

	// ----------------------- GESTION SECURITE --------------------------------
	public String getTokenKey() {
		return tokenKey;
	}

	public void setTokenKey(String token_key) {
		this.tokenKey = token_key;
	}

	// --------------------- FIN GESTION SECURITE ------------------------------
	//
	// @Transient
	// public List<String> getBasicInfos() {
	// List<String> basicInfos = new ArrayList<>();
	// basicInfos.add(this.getName());
	// basicInfos.add(this.getSurname());
	// basicInfos.add(this.getPseudo());
	// basicInfos.add(this.getGender().toString());
	// basicInfos.add(this.getTwAcct());
	// basicInfos.add(this.getFbAcct());
	// basicInfos.add(this.getGroup());
	// return basicInfos;
	// }
}
