package com.dudlo.reservationsystem.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ApplicationConfig.class)
public class AddMenuTest {

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
	public void Add_Menu_Test() throws InterruptedException {

		Restaurant rest1 = restaurantService.getAllRestaurants().get(0);
		long rest11 = rest1.getRestaurantID();
		assertNotNull(rest1);

		List<RestTable> tables = restTableService.findAllByRestaurant(rest1);
		assertNotNull(tables);
		assertEquals(4, tables.size());

		List<TimeSlot> timeSlots = timeSlotService.getAllTimeSlotsForRestaurant(rest1);
		assertNotNull(timeSlots);
		assertEquals(7, timeSlots.size());

		// assertEquals(3, timeSlots.get(0).getSlots().size());
		// assertEquals(3, timeSlots.get(1).getSlots().size());
		//assertEquals("12:00", (timeSlots.get(0).getSlots().contains("12:00")));
		// assertEquals("16:00",
		// (timeSlots.get(1).getTimeSlotReservationSlot(1)));

		List<Menu> menus = menuService.getAllMenusFromRestaurant(rest11);// 2
		assertNotNull(menus);
		assertEquals(4, menus.size());
		List<Menu> menusSelection = menuService.getAllMenusForMenuType("Starter", rest11);
		assertEquals(1, menusSelection.size());
		List<Menu> menusSelection1 = menuService.getAllMenusForMenuType("Dessert", rest11);
		assertNotNull(menusSelection1);
		assertEquals(1, menusSelection1.size());

		long ID = reservationService.getAllBookings().get(0).getReservationID();
		/*List<Menu> menusSelection2 = menuService.getAllMenusForMenuType("Main", rest1);
		assertEquals("Main", menusSelection2.get(0).getMenuType());

		reservationService.addMenus(ID, menusSelection2.get(0).getMenuID());
		List<Reservation> reservations = reservationService.getAllBookings();
		Reservation reservation1 = reservations.get(0);
		System.out.print("The square root of    "  + System.lineSeparator()  + reservation1.getMenus().toString() +  System.lineSeparator() + "     look for this");
		assertEquals(2,reservation1.getMenus().size());*/
		
				
		List<Menu> menusSelection3 = menuService.getAllMenusForMenuType("Starter", rest11);
		assertEquals("Starter", menusSelection3.get(0).getMenuType());
		Reservation reservation2 = reservationService.addMenus(ID, menusSelection3.get(0).getMenuID());
		
		System.out.print("The square root of    "  + System.lineSeparator()  + reservation2.getMenus().toString() +  System.lineSeparator() + "     look for this");
		
		assertEquals(1,reservation2.getMenus().size());

	}

}
