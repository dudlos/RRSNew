package com.dudlo.reservationsystem.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.dudlo.reservationsystem.ApplicationConfig;
import com.dudlo.reservationsystem.model.Menu.MenuType;
import com.dudlo.reservationsystem.model.Restaurant;
import com.dudlo.reservationsystem.model.SlotEnum;
import com.dudlo.reservationsystem.model.TimeSlot;
import com.dudlo.reservationsystem.repository.RestTableRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = ApplicationConfig.class)
// @Transactional
public class SingleMethodTest {

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
	public void PreLoad_Data_Reservation_Test() throws InterruptedException {
		restaurantService.createNewRestaurant("Havran");
		restaurantService.createNewRestaurant("Yamamori");

		Restaurant rest = restaurantService.getAllRestaurants().get(0);
		Restaurant rest2 = restaurantService.getAllRestaurants().get(1);

		menuService.createMenu(MenuType.Main, "Beef Steak", "Rib eye beef steak", 13, rest);// 3
		menuService.createMenu(MenuType.Starter, "Sushi Plate", "Selection of fresh sushi served with soya sauce", 11, rest);

		menuService.createMenu(MenuType.Dessert, "Apple Pie", "Home Made Apple Pie", 10.50, rest);

		menuService.createMenu(MenuType.Main, "Roated Lamb", "Slowly cooked roated lamb", 20, rest);
		
		menuService.createMenu(MenuType.Main, "Beef Roast", "Slowly cooked roatedBeef", 20, rest2);

		restTableService.createTable(4, "A12", rest);
		restTableService.createTable(4, "A13", rest);
		restTableService.createTable(1, "A14", rest);
		restTableService.createTable(3, "A08", rest);
		
		restTableService.createTable(4, "A00", rest2);
		restTableService.createTable(3, "A01", rest2);

		// pred kazdym testom musi byt TimeSLot s dnom ako je dnesny datum
		timeSlotService.createTimeSlot("MONDAY", rest);
		timeSlotService.createTimeSlot("TUESDAY", rest);
		timeSlotService.createTimeSlot("WEDNESDAY", rest);
		timeSlotService.createTimeSlot("THURSDAY", rest);
		timeSlotService.createTimeSlot("FRIDAY", rest);
		timeSlotService.createTimeSlot("SATURDAY", rest);
		timeSlotService.createTimeSlot("SUNDAY", rest);
		
		timeSlotService.createTimeSlot("MONDAY", rest2);
		timeSlotService.createTimeSlot("TUESDAY", rest2);
		timeSlotService.createTimeSlot("WEDNESDAY", rest2);
		timeSlotService.createTimeSlot("THURSDAY", rest2);
		timeSlotService.createTimeSlot("FRIDAY", rest2);
		timeSlotService.createTimeSlot("SATURDAY", rest2);
		timeSlotService.createTimeSlot("SUNDAY", rest2);

		List<SlotEnum> slotsEnumList1 = new ArrayList<SlotEnum>();
		slotsEnumList1.add(SlotEnum.SLOT1);
		slotsEnumList1.add(SlotEnum.SLOT2);
		slotsEnumList1.add(SlotEnum.SLOT3);
		List<SlotEnum> slotsEnumList2 = new ArrayList<SlotEnum>();
		slotsEnumList2.add(SlotEnum.SLOT4);
		slotsEnumList2.add(SlotEnum.SLOT5);
		slotsEnumList2.add(SlotEnum.SLOT6);
		List<SlotEnum> slotsEnumList3 = new ArrayList<SlotEnum>();
		slotsEnumList3.add(SlotEnum.SLOT1);
		slotsEnumList3.add(SlotEnum.SLOT9);

		List<TimeSlot> timeSlotsRest1 = timeSlotService.getAllTimeSlotsForRestaurant(rest); 
		List<TimeSlot> timeSlotsRest2 = timeSlotService.getAllTimeSlotsForRestaurant(rest2); 

		timeSlotService.addSlots(timeSlotsRest1.get(0), slotsEnumList1);// 3
		timeSlotService.addSlots(timeSlotsRest1.get(1), slotsEnumList2);// 3
		
		timeSlotService.addSlots(timeSlotsRest2.get(0), slotsEnumList1);// 3
		timeSlotService.addSlots(timeSlotsRest2.get(1), slotsEnumList2);// 3

		timeSlotService.addSlots(timeSlotsRest1.get(2), slotsEnumList3);// 3{
		timeSlotService.addSlots(timeSlotsRest1.get(3), slotsEnumList1);
		
		timeSlotService.addSlots(timeSlotsRest2.get(2), slotsEnumList3);// 3{
		timeSlotService.addSlots(timeSlotsRest2.get(3), slotsEnumList1);


		timeSlotService.addSlots(timeSlotsRest1.get(4), slotsEnumList2);

		timeSlotService.addSlots(timeSlotsRest1.get(5), slotsEnumList3);
		timeSlotService.addSlots(timeSlotsRest1.get(6), slotsEnumList1);
	}

}
