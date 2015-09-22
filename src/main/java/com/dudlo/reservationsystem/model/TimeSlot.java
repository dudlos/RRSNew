package com.dudlo.reservationsystem.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "TIMESLOT_TABLE")
public class TimeSlot implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long timeSlotID;
	@Column
	private String timeSlotPeriod;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "restaurantID")
	private Restaurant restaurant;

	@JsonIgnore
	@Column(name = "SLOT_ENUM_NAMES")
	@Enumerated(EnumType.STRING)
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "T_SLOT_ENUMERATION_TYPES", joinColumns = @JoinColumn(name = "timeSlotID"))
	private Set <SlotEnum> slots;

	protected TimeSlot() {

	}

	public TimeSlot(String timeSlotPeriod, Restaurant restaurant) {

		this.timeSlotPeriod = timeSlotPeriod;
		this.restaurant = restaurant;
		this.slots = new LinkedHashSet<SlotEnum>();

	}

	public void addToSlots(SlotEnum e) {
		this.slots.add(e);
	}

	/**
	 * @return the slots
	 */
	public Set<SlotEnum> getSlots() {
		return slots;
	}
	
	/*public String getTimeSlotReservationSlot(int i)
	{
		return this.slots.get(i).getSlot();
	}

	/**
	 * @return the timeSlotId
	 */
	public long getTimeSlotID() {
		return timeSlotID;
	}

	/**
	 * @return the timeSlotPeriod
	 */
	public String getTimeSlotPeriod() {
		return timeSlotPeriod;
	}

	/**
	 * @param timeSlotPeriod
	 *            the timeSlotPeriod to set
	 */
	public void setTimeSlotPeriod(String timeSlotPeriod) {
		this.timeSlotPeriod = timeSlotPeriod;
	}

	/**
	 * @return the restaurant
	 */
	public Restaurant getRestaurant() {
		return restaurant;
	}

}
