package com.dudlo.reservationsystem.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name = "MENU_RESERVATION_MAPPING_TABLE")

public class MenuReservationMapping implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long menuResMapID;
	
	@OneToOne(fetch = FetchType.EAGER) // Should be ManyToOne???????
	@JoinColumn(name = "menuID")
	private Menu menu;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "reservationID")
	private Reservation reservation;
	
	protected MenuReservationMapping() {}

	public MenuReservationMapping(Reservation reservation, Menu menu) {
		this.reservation = reservation;
		this.menu = menu;
	}

	/**
	 * @return the menuResMapID
	 */
	public long getMenuResMapID() {
		return menuResMapID;
	}

	/**
	 * @return the reservatio
	 */
	public Reservation getBooking() {
		return reservation;
	}

	/**
	 * @param reservation the booking to set
	 */
	public void setBooking(Reservation reservation) {
		this.reservation = reservation;
	}

	/**
	 * @return the menu
	 */
	public Menu getMenu() {
		return menu;
	}

	/**
	 * @param menu the menu to set
	 */
	public void setMenu(Menu menu) {
		this.menu = menu;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (menuResMapID ^ (menuResMapID >>> 32));
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MenuReservationMapping other = (MenuReservationMapping) obj;
		if (menuResMapID != other.menuResMapID)
			return false;
		return true;
	}
	
	
	
}
