package com.dudlo.reservationsystem.repository;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.dudlo.reservationsystem.model.Restaurant;
import com.dudlo.reservationsystem.model.SlotEnum;
import com.dudlo.reservationsystem.model.TimeSlot;

@Repository
@Transactional
public class TimeSlotRepositoryImpl implements TimeSlotRepository {

	@PersistenceContext
	private EntityManager manager;

	@Override
	public TimeSlot createTimeSlot(String timeSlotPeriod, Restaurant restaurant) {
		TimeSlot slot = new TimeSlot(timeSlotPeriod, restaurant);
		manager.persist(slot);
		return slot;
	}

	@Override
	public void addSlots(TimeSlot timeSlot, List<SlotEnum> e) {
		for(SlotEnum slotEnum : e)
		{
			timeSlot.addToSlots(slotEnum);
		}
		manager.merge(timeSlot);

	}

	@Override
	public List<TimeSlot> getAllTimeSlots() {
		return manager.createQuery("from TimeSlot", TimeSlot.class)
				.getResultList();
	}

	@Override
	public List<TimeSlot> getAllTimeSlotsForRestaurant(Restaurant rest) {
		return manager.createQuery("Select distinct c from TimeSlot c where c.restaurant = :restparam", TimeSlot.class).setParameter("restparam", rest)
				.getResultList();
	}

	@Override
	public TimeSlot findById(long id) 
	{
		return manager.find(TimeSlot.class, id);
	}

	@Override
	public TimeSlot getTimeSlotByDay(Restaurant restaurant,
			String timeSlotPeriod) {
		return manager.createQuery("Select distinct c from TimeSlot c where "
				+ "c.restaurant = :restparam and c.timeSlotPeriod = :timeSlotParam", TimeSlot.class ).setParameter("restparam", restaurant)
				.setParameter("timeSlotParam", timeSlotPeriod).getSingleResult();
	}

}
