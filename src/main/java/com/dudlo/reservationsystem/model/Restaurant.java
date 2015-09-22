package com.dudlo.reservationsystem.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "RESTAUTANT_TABLE")
public class Restaurant implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long restaurantID;

	@Column
	private String restaurantName;

	@JsonIgnore
	@OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<TimeSlot> timeSlots;

	@JsonIgnore
	@OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Menu> menus;

	@JsonIgnore
	@OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<RestTable> restTables;
	
	@JsonIgnore
	@OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Reservation> reservations;

	protected Restaurant() {

	}

	public Restaurant(String restaurantName) {

		this.restaurantName = restaurantName;
		this.menus = new ArrayList<Menu>();
		this.timeSlots = new ArrayList<TimeSlot>();
		this.restTables = new ArrayList<RestTable>();
		this.reservations = new ArrayList<Reservation>();
	}

	public void addMenu(Menu menu) {
		this.menus.add(menu);
	}

	public void addTimeSlot(TimeSlot timeSlot) {

		this.timeSlots.add(timeSlot);
	}

	public void addRestTables(RestTable restTable) {
		this.restTables.add(restTable);
	}
	
	public void addReservation(Reservation booking)
	{
		this.reservations.add(booking);
	}

	/**
	 * @return the restaurantID
	 */
	public long getRestaurantID() {
		return restaurantID;
	}

	/**
	 * @return the restaurantName
	 */
	public String getRestaurantName() {
		return restaurantName;
	}

	/**
	 * @param restaurantName
	 *            the restaurantName to set
	 */
	public void setRestaurantName(String restaurantName) {
		this.restaurantName = restaurantName;
	}

	/**
	 * @return the timeSlots
	 */
	public List<TimeSlot> getTimeSlots() {
		return timeSlots;
	}

	/**
	 * @return the menus
	 */
	public List<Menu> getMenus() {
		return menus;
	}

	/**
	 * @return the restTables
	 */
	public List<RestTable> getRestTables() {
		return restTables;
	}

	/**
	 * @return the reservations
	 */
	public List<Reservation> getReservations() {
		return reservations;
	}

}
