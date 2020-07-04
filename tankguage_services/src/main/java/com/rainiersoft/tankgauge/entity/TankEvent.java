package com.rainiersoft.tankgauge.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="TK_EVENT")
public class TankEvent
{
	@Id
	@Column(name="Event_ID")
	int eventId;
	
	@Column(name="Tank_ID")
	int tankId;
	
	@Column(name="Event_Description")
	String eventDescription;
	
	@Column(name="User")
	String user;
	
	@Column(name="User_ID")
	int userId;
	
	@Column(name="Last_Updated")
	Date lastUpdated;
	
	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getEventId() {
		return eventId;
	}

	public void setEventId(int eventId) {
		this.eventId = eventId;
	}

	public int getTankId() {
		return tankId;
	}

	public void setTankId(int tankId) {
		this.tankId = tankId;
	}

	public String getEventDescription() {
		return eventDescription;
	}

	public void setEventDescription(String eventDescription) {
		this.eventDescription = eventDescription;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public Date getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}


}
