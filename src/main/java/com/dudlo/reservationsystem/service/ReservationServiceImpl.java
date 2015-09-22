package com.dudlo.reservationsystem.service;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dudlo.reservationsystem.model.Menu;
import com.dudlo.reservationsystem.model.Reservation;
import com.dudlo.reservationsystem.model.RestTable;
import com.dudlo.reservationsystem.model.Restaurant;
import com.dudlo.reservationsystem.model.TimeSlot;
import com.dudlo.reservationsystem.repository.ReservationRepository;

@Service
public class ReservationServiceImpl implements ReservationService {

	ReservationRepository reservationRepository;

	@Autowired
	public ReservationServiceImpl(ReservationRepository reservationRepository) {
		this.reservationRepository = reservationRepository;
	}

	@Override
	public List<Reservation> getAllBookings() {
		
		return this.reservationRepository.getAllBookings();
	}

	@Override
	public List<String> checkAvailibilityDate(Date date, Restaurant restaurant) {
		
		return this.reservationRepository.checkAvailibilityDate(date, restaurant);
	}

	@Override
	public List<RestTable> checkAvailibilityTime(Restaurant rest, Date date,
			String reservationTime, int numberOfPerson) {
		return this.reservationRepository.checkAvailibilityTime(rest, date, reservationTime, numberOfPerson);
	}

	@Override
	public Reservation makeReservation(Date date, int numberOfPerson,
			Restaurant restaurant, RestTable table, String reservationTime) {
		return this.reservationRepository.makeReservation(date, numberOfPerson, restaurant,  table, reservationTime);
	}

	@Override
	public Reservation addMenus(long reservationID, long menuID)  {
		return this.reservationRepository.addMenus(reservationID, menuID);
	}

	
	
	@Override
	public TimeSlot getCorrectTimeSlot(Date date, Restaurant restaurant) {
		return this.reservationRepository.getCorrectTimeSlot(date, restaurant);
	}

	@Override
	public List<String> fromSlotEnumToString(TimeSlot timeSlot) {
		return this.reservationRepository.fromSlotEnumToString(timeSlot);
	}

	@Override
	public List<Reservation> getAllReservationsByReservationTime(
			Restaurant restaurant, Date date, String bookingTime) {
		return this.getAllReservationsByReservationTime(restaurant, date, bookingTime);
	}

	@Override
	public List<RestTable> getAvailableTables(List<Reservation> reservations,
			Restaurant rest, int numberOfPerson) {
		return this.reservationRepository.getAvailableTables(reservations, rest, numberOfPerson);
	}

	@Override
	public void removeReservation(Reservation booking) {
		this.reservationRepository.removeReservation(booking);
		
	}

	@Override
	public Reservation findByID(long reservationID) {
			return reservationRepository.findByID(reservationID);
	}

	@Override
	public List<Menu> findAllMenusPerReservation(Long reservationID) {
		return reservationRepository.findAllMenusPerReservation(reservationID);
	}

	

}
