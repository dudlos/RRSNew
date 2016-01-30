package com.dudlo.reservationsystem.repository;

import java.sql.Date;
import java.util.List;

import com.dudlo.reservationsystem.model.Menu;
import com.dudlo.reservationsystem.model.Reservation;
import com.dudlo.reservationsystem.model.RestTable;
import com.dudlo.reservationsystem.model.Restaurant;
import com.dudlo.reservationsystem.model.TimeSlot;
import com.dudlo.reservationsystem.model.User;

public interface ReservationRepository {

	List<Reservation> getAllBookings();

	List<String> checkAvailibilityDate(Date date, Restaurant restaurant);

	List<RestTable> checkAvailibilityTime(Restaurant rest, Date date,
			String reservationTime, int numberOfPerson);

	Reservation makeReservation(Date date, int numberOfPerson,
			Restaurant restaurant, RestTable table,
			String reservationTime, User user);

	Reservation addMenus(long reservationID,  long menuID) ;

	TimeSlot getCorrectTimeSlot(Date date, Restaurant restaurant);

	List<String> fromSlotEnumToString(TimeSlot timeSlot);

	List<Reservation> getAllReservationsByReservationTime(
			Restaurant restaurant, Date date, String bookingTime);

	List<RestTable> getAvailableTables(List<Reservation> reservations,
			Restaurant rest, int numberOfPerson);

	void removeReservation(Reservation reservation);
	
	Reservation findByID(long reservationID);
	
	List<Menu> findAllMenusPerReservation(Long reservationID);
	

}
