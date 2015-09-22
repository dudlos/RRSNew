package com.dudlo.reservationsystem.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.dudlo.reservationsystem.ApplicationConfig;
import com.dudlo.reservationsystem.model.Menu;
import com.dudlo.reservationsystem.model.Reservation;
import com.dudlo.reservationsystem.model.RestTable;
import com.dudlo.reservationsystem.model.Restaurant;
import com.dudlo.reservationsystem.model.TimeSlot;
import com.dudlo.reservationsystem.repository.RestTableRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = ApplicationConfig.class)

public class MakeReservationTest {

	@Autowired
	ReservationService reservationService;
	@Autowired
	RestTableService restTableService;
	@Autowired
	MenuService menuService;
	@Autowired
	RestaurantService restaurantService;
	@Autowired
	TimeSlotService timeSlotService;
	@Autowired
	RestTableRepository restTableRepository;

	@Test
	public void Make_Reservation_Test() {
		
		Restaurant rest1 = restaurantService.getAllRestaurants().get(0);
		long rest11 = rest1.getRestaurantID();
		assertNotNull(rest1);
		
		Restaurant rest2 = restaurantService.getAllRestaurants().get(1);
		long rest12 = rest1.getRestaurantID();
		assertNotNull(rest2);
		
		List <RestTable> tables = restTableService.findAllByRestaurant(rest1);
		assertNotNull(tables);
		assertEquals(4, tables.size());
		
		List <RestTable> tables2 = restTableService.findAllByRestaurant(rest2);
		assertNotNull(tables2);
		assertEquals(2, tables2.size());
	
		List<TimeSlot> timeSlots = timeSlotService.getAllTimeSlotsForRestaurant(rest1);
		assertNotNull(timeSlots);
		assertEquals(7, timeSlots.size());
		
		List<TimeSlot> timeSlots2 = timeSlotService.getAllTimeSlotsForRestaurant(rest2);
		assertNotNull(timeSlots);
		
		List<Menu> menus2 = menuService.getAllMenusFromRestaurant(rest12);//2
		assertNotNull(menus2);
	
		
		
		List<Menu> menus = menuService.getAllMenusFromRestaurant(rest11);//2
		assertNotNull(menus);
		assertEquals(4, menus.size());
			
	
		List<Menu> menusSelection = menuService.getAllMenusForMenuType("Starter", rest11);
		assertEquals(1, menusSelection.size());
		List<Menu> menusSelection1 = menuService.getAllMenusForMenuType("Dessert", rest11);
		assertNotNull(menusSelection1);
		assertEquals(1, menusSelection1.size());
		
		Date bookingDate = Date.valueOf(LocalDate.now());
		String localDate = bookingDate.toLocalDate().getDayOfWeek().toString();
		TimeSlot timeSlot = timeSlotService.getTimeSlotByDay(rest1, localDate);
		assertNotNull(timeSlot);
		assertEquals(timeSlot.getTimeSlotPeriod(), localDate);// replace "wendesday" with current day if needed
		
		
		 List<String> allReservationSlots = reservationService.checkAvailibilityDate(bookingDate, rest1);
		 String reservationTime = allReservationSlots.get(0);
		 
		 //reservationService.
		 List<RestTable> availableTables = reservationService.checkAvailibilityTime(rest1, bookingDate, reservationTime, 3 );
		 
		 
		 //Retrieves a table, if not available an IndexOutofbound exception occurs.
		 RestTable table1 = availableTables.get(0);
		 assertNotNull(table1);
		 
		 reservationService.makeReservation(bookingDate, 3, rest1, table1, reservationTime);
		 List <Reservation> bookings =reservationService.getAllBookings();
		 assertEquals(1, bookings.size());
				 
		 reservationService.makeReservation(bookingDate, 3, rest2, table1, reservationTime);
		 List <Reservation> bookings2 =reservationService.getAllBookings();
		 assertEquals(2, bookings2.size());
		 
		 
	}
	
	
}

