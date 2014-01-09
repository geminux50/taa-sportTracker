package org.sportTracker.model;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 * <!-- begin-user-doc --> <!-- end-user-doc -->
 * 
 * @generated
 */

@Entity
public class Meteo {

	private String weather;
	private String temperature;
	private String humidity;
	private Collection<Workout> workouts;
	private int id;

	public Meteo() {
	}

	@Id
	@GeneratedValue
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getWeather() {
		return this.weather;
	}

	public void setWeather(String myWeather) {
		this.weather = myWeather;
	}

	public String getTemperature() {
		return this.temperature;
	}

	public void setTemperature(String myTemperature) {
		this.temperature = myTemperature;
	}

	public String getHumidity() {
		return this.humidity;
	}

	public void setHumidity(String myHumidity) {
		this.humidity = myHumidity;
	}

	// ------------------------- GESTION DU WORKOUT ----------------------------

	/**
	 * Getter de la collection de Workouts
	 * 
	 * @return
	 */
	@OneToMany(mappedBy = "meteo")
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

}
