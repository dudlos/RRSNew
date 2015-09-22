package com.dudlo.reservationsystem.service;

import java.util.List;

import com.dudlo.reservationsystem.model.Restaurant;

public interface RestaurantService {

	List<Restaurant> getAllRestaurants();
	Restaurant createNewRestaurant(String restaurantName);
	void removeAllReservations(Restaurant restaurant);
	Restaurant findById(long restaurantID);
	void removeAllRestaurants();
}
