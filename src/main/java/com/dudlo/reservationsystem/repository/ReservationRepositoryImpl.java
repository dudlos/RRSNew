package com.dudlo.reservationsystem.repository;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
//import javax.persistence.TemporalType;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.dudlo.reservationsystem.model.Menu;
import com.dudlo.reservationsystem.model.MenuReservationMapping;
import com.dudlo.reservationsystem.model.Reservation;
import com.dudlo.reservationsystem.model.RestTable;
import com.dudlo.reservationsystem.model.Restaurant;
import com.dudlo.reservationsystem.model.SlotEnum;
import com.dudlo.reservationsystem.model.TimeSlot;
import com.dudlo.reservationsystem.service.MenuReservationMapService;

@Repository
@Transactional
public class ReservationRepositoryImpl implements ReservationRepository {

	public ReservationRepositoryImpl() {
	}

	@PersistenceContext
	private EntityManager manager;

	@Autowired
	TimeSlotRepository timeSlotRepository;
	@Autowired
	RestTableRepository restTable;
	@Autowired
	MenuRepository menuService;
	@Autowired
	MenuReservationMapService menuReservationMapService;

	@Override
	public List<Reservation> getAllBookings() {

		return manager.createQuery("from Reservation", Reservation.class)
				.getResultList();
	}

	public List<String> checkAvailibilityDate(Date date, Restaurant restaurant) {
		TimeSlot timeSlot = this.getCorrectTimeSlot(date, restaurant);
		return this.fromSlotEnumToString(timeSlot);
	}

	/*
	 * @return List of restTables
	 */

	public List<RestTable> checkAvailibilityTime(Restaurant rest, Date date,
			String reservationTime, int numberOfPerson) {
		List<Reservation> resultReservations = new ArrayList<Reservation>();
		resultReservations = this.getAllReservationsByReservationTime(rest,
				date, reservationTime);
		return this
				.getAvailableTables(resultReservations, rest, numberOfPerson);
	}

	@Override
	public Reservation makeReservation(Date date, int numberOfPerson,
			Restaurant restaurant,  RestTable table,
			String reservationTime) {

		Reservation reservation = new Reservation(date, numberOfPerson,
				restaurant, table, reservationTime);
		
		manager.persist(reservation);
		return reservation;
	}

	@Override
	public Reservation addMenus(long reservationID, long menuID)  {
		Reservation reservation = manager
				.createQuery(
						"Select c from Reservation c where c.reservationID = :IDparam",
						Reservation.class)
				.setParameter("IDparam", reservationID).getSingleResult();
		
		Menu menu = menuService.getSingleMenu(menuID);
		MenuReservationMapping menuResMap;
		menuResMap= menuReservationMapService.createMenuResMap(reservation, menu);//persist new object

		reservation.addMenu(menuResMap);
		
		manager.merge(this.findByID(reservationID));
		
		return reservation;
		
	}

	/*
	 * @return TimeSlot: transfers date to particular dayOfWeek enum and
	 * ...compares it to TimeSlotPeriod string if match is found the matching
	 * ...timeSlotPeriod is selected as the correct timeSlot for the booking is
	 * returned to be added to the reservation
	 */
	@Override
	public TimeSlot getCorrectTimeSlot(Date date, Restaurant restaurant) {

		String timeSlotPeriod = date.toLocalDate().getDayOfWeek().toString();
		TimeSlot timeSlot = timeSlotRepository.getTimeSlotByDay(restaurant,
				timeSlotPeriod);
		return timeSlot;
	}

	/*
	 * @return List<String> timeSlots converts list of all slots for particular
	 * ...slot from SlotEnum to String SK: Nadvezuje na predchazdajucu
	 * getCorrectTimeSlot(Date date, Restaurant restaurant)
	 */
	@Override
	public List<String> fromSlotEnumToString(TimeSlot timeSlot) {

		ArrayList<String> reservationTimeList = new ArrayList<String>();

		for (SlotEnum reservation : timeSlot.getSlots()) {
			reservationTimeList.add(reservation.getSlot());
		}

		return reservationTimeList;
	}

	/*
	 * @return a String from the above reservationTimeList
	 * 
	 * SK:vrati a String z List ktory obsahuje vsetky reservationTime pre dany
	 * TimeSlot Mozno Zbytocne??
	 * 
	 * @Override public String getReservationTimeFromTimeSlot(List<String>
	 * reservationTimes, int reservationTime) {
	 * 
	 * return reservationTimes.get(reservationTime); }
	 */

	/*
	 * @return List<Reservation> ...method will be used to compare the and
	 * narrow down the amount of ...reservations before executing
	 * makeReservation ..SK:robi to iste co metoda dole ale bez
	 * timeReservation(bookingTime) String parametru
	 */

	public List<Reservation> getAllReservationsByDate(Restaurant rest, Date date) {

		return manager
				.createQuery(
						"Select distinct c from Reservation c where c.restaurant = :restparam and c.bookingDate = :dateparam ",
						Reservation.class).setParameter("restparam", rest)
				.setParameter("dateparam", date).getResultList();
	}

	/*
	 * @return List<Reservation> of all reservations of the "restaurant" on
	 * particular "date"and "bookingTime" extracted previously
	 */
	public List<Reservation> getAllReservationsByReservationTime(
			Restaurant rest, Date date, String reservationTime) {

		return manager
				.createQuery(
						"Select distinct c from Reservation c where c.restaurant = :restparam and c.bookingDate = :dateparam "
								+ "and c.reservationTime = :reservationparam",
						Reservation.class).setParameter("restparam", rest)
				.setParameter("dateparam", date)
				.setParameter("reservationparam", reservationTime)
				.getResultList();

	}

	/*
	 * @return List<RestTable> ...Returns list of all available tables for
	 * particular restaurant,date and timeSlot ...Uses methods
	 * this.getAllReservationsByReservationTime(), and ResTable
	 * findAllByRestaurant(Restaurant rest) compares the list of actual number
	 * of existing reservation for the period against the list with all the
	 * tables(capacity) of the restaurant. Returns null if there are no
	 * available tables(hence fully booked)
	 */

	public List<RestTable> getAvailableTables(List<Reservation> reservations,
			Restaurant rest, int numberOfPerson) {

		List<RestTable> allAvailableTables = new ArrayList<RestTable>();
		List<RestTable> allAvailableTables2 = new ArrayList<RestTable>();
		List<RestTable> allTablesForRestaurant = new ArrayList<RestTable>();
		allTablesForRestaurant = restTable.findAllByRestaurant(rest);

		if (reservations.size() < allTablesForRestaurant.size()) 
		{
			allAvailableTables.addAll(allTablesForRestaurant);
					for (Reservation booking : reservations) {
						if (allTablesForRestaurant.contains(booking.getTable())) 
						{
							allAvailableTables.remove(booking.getTable());
						}
					}
					allAvailableTables2.addAll(allAvailableTables);
					for (RestTable table : allAvailableTables2) {
						if (table.getCapacity() < numberOfPerson) {
							allAvailableTables.remove(table);
							}
					}
		}

		return allAvailableTables;
	}

	@Override
	public void removeReservation(Reservation boking) {
		List <Reservation> reservations = getAllBookings();
		for(Reservation reservation : reservations)
		{
			manager.remove(reservation);
		}

	}

	@Override
	public Reservation findByID(long reservationID) {
		return  manager
				.createQuery(
						"Select c from Reservation c where c.reservationID = :IDparam",
						Reservation.class).setParameter("IDparam", reservationID).getSingleResult();
		
	}

	@Override
	public List<Menu> findAllMenusPerReservation(Long reservationID) {
				  
		  List<MenuReservationMapping> menuReservations = manager.createQuery(
					"Select c from MenuReservationMapping c where c.reservationID = :IDparam",
					MenuReservationMapping.class).setParameter("IDparam", reservationID).getResultList();
		  
		   List<Menu> menus = new ArrayList<Menu>();
		  for(MenuReservationMapping menuRes: menuReservations)
		  {
			  menus.add(menuRes.getMenu());
		   }
		  return menus;
	}

}
