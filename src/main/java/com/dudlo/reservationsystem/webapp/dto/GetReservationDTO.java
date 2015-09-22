package com.dudlo.reservationsystem.webapp.dto;

import com.dudlo.reservationsystem.model.Reservation;

public class GetReservationDTO {
	
	Reservation newReservation;

	/**
	 * @return the newReservation
	 */
	public Reservation getNewReservation() {
		return newReservation;
	}

	/**
	 * @param newReservation the newReservation to set
	 */
	public void setNewReservation(Reservation newReservation) {
		this.newReservation = newReservation;
	}
	

}
