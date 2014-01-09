package org.sportTracker.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Cardio {

	private String frequency;
	private String workZone;
	private Workout workout;
	private int id;

	public Cardio() {
	}

	public String getFrequency() {
		return this.frequency;
	}

	public String getWorkZone() {
		return this.workZone;
	}

	@OneToOne(mappedBy = "cardio")
	public Workout getWorkout() {
		return this.workout;
	}

	@Id
	@GeneratedValue
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setFrequency(String freq) {
		this.frequency = freq;
	}

	public void setWorkZone(String workzone) {
		this.workZone = workzone;
	}

	public void setWorkout(Workout workout) {
		if (this.workout == null) {
			this.workout = new Workout();
		}
		this.workout = workout;

	}

}
