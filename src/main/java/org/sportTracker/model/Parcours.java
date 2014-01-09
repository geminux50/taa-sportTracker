package org.sportTracker.model;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Parcours {

	private String ptDepart;
	private Collection<GPSPoint> gPSPoints;
	private Workout workout;
	private int id;

	public Parcours() {
	}

	public String getPtDepart() {
		return this.ptDepart;
	}

	public void setPtDepart(String ptDepart) {
		this.ptDepart = ptDepart;
	}

	@OneToOne(mappedBy = "parcours")
	public Workout getWorkout() {
		return this.workout;
	}

	public void setWorkout(Workout workout) {
		if (this.workout == null) {
			this.workout = new Workout();
		}
		this.workout = workout;
	}
	
	@Id
	@GeneratedValue
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	// ------------------------- GESTION DU GPSPOINT ---------------------------

	/**
	 * Getter de la collection de GPSPoints
	 * 
	 * @return
	 */
	@OneToMany(mappedBy = "parcours")
	public Collection<GPSPoint> getGPSPoints() {
		return this.gPSPoints;
	}

	/**
	 * Setter de la collection de GPSPoints
	 * 
	 * @param workouts
	 */
	public void setGPSPoints(Collection<GPSPoint> gPSPoint) {
		if (this.gPSPoints == null) {
			this.gPSPoints = new ArrayList<GPSPoint>();
		}

		this.gPSPoints = gPSPoint;
	}

	/**
	 * Ajout d'un GPSPoint à la collection
	 * 
	 * @param workout
	 */
	public boolean addGPSPoint(GPSPoint gPSPoint) {
		if (this.gPSPoints == null) {
			this.gPSPoints = new ArrayList<GPSPoint>();
		}

		return this.gPSPoints.add(gPSPoint);
	}

	/**
	 * Suppression d'un GPSPoint de la collection
	 * 
	 * @param workout
	 */
	public boolean delGPSPoint(GPSPoint gPSPoint) {
		if (this.gPSPoints == null) {
			this.gPSPoints = new ArrayList<GPSPoint>();
		}

		return this.gPSPoints.remove(gPSPoint);
	}

	// --------------------- FIN GESTION DU GPSPOINT ---------------------------

}
