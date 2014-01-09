package org.sportTracker.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;

@Entity
public class GPSPoint {

	private double longtitude;
	private double latitude;
	private Date time;
	private Parcours parcours;

	private int id;

	public GPSPoint() {
	}

	public double getLongtitude() {
		return this.longtitude;
	}

	public void setLongtitude(double longitude) {
		this.longtitude = longitude;
	}

	public double getLatitude() {
		return this.latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	@Temporal(javax.persistence.TemporalType.DATE)
	public Date getTime() {
		return this.time;
	}

	public void setTime(Date myTime) {
		this.time = myTime;
	}

	@ManyToOne
	// @JoinColumn(nullable = false)
	public Parcours getParcours() {
		return this.parcours;
	}

	public void setParcours(Parcours parcours) {
		if (this.parcours == null) {
			this.parcours = new Parcours();
		}
		parcours.addGPSPoint(this);

	}

	@Id
	@GeneratedValue
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
