package com.dudlo.reservationsystem.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.dudlo.reservationsystem.model.Reservation;
import com.dudlo.reservationsystem.model.Restaurant;

@Repository
@Transactional
public class RestaurantRepositoryImpl implements RestaurantRepository {

	protected RestaurantRepositoryImpl() {

	}

	@PersistenceContext
	private EntityManager manager;

	@Override
	public List<Restaurant> getAllRestaurants() {
		return manager.createQuery("from Restaurant", Restaurant.class)
				.getResultList();
	}

	@Override
	public Restaurant createNewRestaurant(String restaurantName) {
		Restaurant restaurant = new Restaurant(restaurantName);
		manager.persist(restaurant);
		return restaurant;
	}

	@Override
	public void removeAllReservations(Restaurant restaurant) {
		
		List<Reservation> allReservations = new ArrayList<Reservation>();
		
		allReservations = manager.createQuery("Select c.Reservations from Restaurant c where c = :restparam", Reservation.class)
				.setParameter("restparam", restaurant).getResultList();
		for(Reservation booking : allReservations)
		{
			manager.remove(booking);
		}
	}

	@Override
	public Restaurant findById(long restaurantID) {
		return manager.createQuery("Select distinct c from Restaurant c where c.restaurantID = :restparam", Restaurant.class)
				.setParameter("restparam", restaurantID).getSingleResult();
		
		
	}

	@Override
	public void removeAllRestaurants() {
		List<Restaurant> allRestaurants =  getAllRestaurants() ;
		for(Restaurant restaurant : allRestaurants)
		{
			manager.remove(restaurant);
		}
	}

}
