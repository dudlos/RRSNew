package com.dudlo.reservationsystem.model;

import java.io.Serializable;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.Cascade;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "RESERVATION_TABLE")
public class Reservation implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long reservationID;

	@Column
	private Date bookingDate;

	@Column
	private int numberOfPerson;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "tableID")
	private RestTable table;

	@JsonIgnore
	@OneToMany(mappedBy = "reservation", cascade = {CascadeType.MERGE,  CascadeType.REMOVE}, fetch = FetchType.EAGER) //cascade = CascadeType.ALL
	private Set <MenuReservationMapping> menus;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "restaurantID")
	private Restaurant restaurant;

	@Column
	private String reservationTime;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "userID")
	private User user;

	protected Reservation() {
	}

	public Reservation(Date date, int numberOfPerson, Restaurant restaurant, RestTable table, String reservationTime, User user) {

		this.bookingDate = date;
		this.numberOfPerson = numberOfPerson;
		this.restaurant = restaurant;
		this.menus = new HashSet<MenuReservationMapping>();
		this.table = table;
		this.reservationTime = reservationTime;
		this.user = user;
	}

	/**
	 * @return the reservationTime
	 */
	public String getReservationTime() {
		return reservationTime;
	}

	/**
	 * @param bookingDate
	 *            the bookingDate to set
	 */
	public void setBookingDate(Date bookingDate) {
		this.bookingDate = bookingDate;
	}

	/**
	 * @return the table
	 */
	public RestTable getTable() {
		return table;
	}

	/**
	 * @param table
	 *            the table to set
	 */
	public void setTable(RestTable table) {
		this.table = table;
	}

	/**
	 * @add menu to this reservation instance
	 */
	public void addMenu(MenuReservationMapping menu) {
		menus.add(menu);
	}

	/**
	 * @return the menus
	 */
	public Set<MenuReservationMapping> getMenus() {
		return menus;
	}

	/**
	 * @param menus
	 *            the menus to set
	 */
	public void setMenus(Set<MenuReservationMapping> menus) {
		this.menus = menus;
	}

	/**
	 * @return the numberOfPerson
	 */
	public int getNumberOfPerson() {
		return numberOfPerson;
	}

	/**
	 * @param numberOfPerson
	 *            the numberOfPerson to set
	 */
	public void setNumberOfPerson(int numberOfPerson) {
		this.numberOfPerson = numberOfPerson;
	}

	/**
	 * @return the reservationID
	 */
	public long getReservationID() {
		return reservationID;
	}

	/**
	 * @return the bookingDate
	 */
	public Date getBookingDate() {
		return bookingDate;
	}

	/**
	 * @return the tables
	 */

	/**
	 * @return the restaurant
	 */
	public Restaurant getRestaurant() {
		return restaurant;
	}

	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}

}
