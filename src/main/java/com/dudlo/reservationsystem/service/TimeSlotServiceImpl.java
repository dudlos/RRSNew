package com.dudlo.reservationsystem.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dudlo.reservationsystem.model.Restaurant;
import com.dudlo.reservationsystem.model.SlotEnum;
import com.dudlo.reservationsystem.model.TimeSlot;
import com.dudlo.reservationsystem.repository.TimeSlotRepository;

@Service
public class TimeSlotServiceImpl implements TimeSlotService {

	@Autowired
	TimeSlotRepository timeSlotRepository;
	
	@Override
	public TimeSlot createTimeSlot(String timeSlotPeriod, Restaurant restaurant) {
		return timeSlotRepository.createTimeSlot(timeSlotPeriod, restaurant);
	}

	@Override
	public void addSlots(TimeSlot timeSlot, List<SlotEnum> e) {
		 timeSlotRepository.addSlots(timeSlot, e);
	
	}

	@Override
	public List<TimeSlot> getAllTimeSlots() {
		return timeSlotRepository.getAllTimeSlots();
	}

	@Override
	public List<TimeSlot> getAllTimeSlotsForRestaurant(Restaurant rest) {
		return timeSlotRepository.getAllTimeSlotsForRestaurant(rest);
	}

	@Override
	public TimeSlot findById(long id) {
		return timeSlotRepository.findById(id);
	}

	@Override
	public TimeSlot getTimeSlotByDay(Restaurant restaurant,
			String timeSlotPeriod) {
		return timeSlotRepository.getTimeSlotByDay(restaurant, timeSlotPeriod);
	}

}
