package com.dudlo.reservationsystem.service;

import java.sql.Date;
import java.util.List;

import com.dudlo.reservationsystem.model.Menu;
import com.dudlo.reservationsystem.model.Reservation;
import com.dudlo.reservationsystem.model.RestTable;
import com.dudlo.reservationsystem.model.Restaurant;
import com.dudlo.reservationsystem.model.TimeSlot;

public interface ReservationService {

	List<Reservation> getAllBookings();

	List<String> checkAvailibilityDate(Date date, Restaurant restaurant);

	List<RestTable> checkAvailibilityTime(Restaurant rest, Date date,
			String reservationTime, int numberOfPerson);

	Reservation makeReservation(Date date, int numberOfPerson,
			Restaurant restaurant, RestTable table, String reservationTime);

	Reservation addMenus(long reservationID, long menuID) ;

	TimeSlot getCorrectTimeSlot(Date date, Restaurant restaurant);

	List<String> fromSlotEnumToString(TimeSlot timeSlot);

	/*
	 * String getReservationTimeFromTimeSlot(List<String> reservationTimes, int
	 * reservationTime);
	 */

	List<Reservation> getAllReservationsByReservationTime(
			Restaurant restaurant, Date date, String bookingTime);

	List<RestTable> getAvailableTables(List<Reservation> reservations,
			Restaurant rest, int numberOfPerson);

	void removeReservation(Reservation booking);

	Reservation findByID(long reservationID);
	
	List<Menu> findAllMenusPerReservation(Long reservationID);
}