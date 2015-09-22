package com.dudlo.reservationsystem.repository;


import java.util.List;

import com.dudlo.reservationsystem.model.Restaurant;
import com.dudlo.reservationsystem.model.SlotEnum;
import com.dudlo.reservationsystem.model.TimeSlot;

public interface TimeSlotRepository {

	TimeSlot createTimeSlot(String timeSlotPeriod, Restaurant restaurant);
	void addSlots(TimeSlot timeSlot, List<SlotEnum> e);
	List<TimeSlot> getAllTimeSlots();
	List<TimeSlot> getAllTimeSlotsForRestaurant(Restaurant rest);
	TimeSlot findById(long id);
	TimeSlot getTimeSlotByDay(Restaurant restaurant,String timeSlotPeriod);
	
}
