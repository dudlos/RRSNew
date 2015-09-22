package com.dudlo.reservationsystem.repository;

import java.util.List;

import com.dudlo.reservationsystem.model.Restaurant;

public interface RestaurantRepository {
	
	List<Restaurant> getAllRestaurants();
	Restaurant createNewRestaurant(String restaurantName);
	void removeAllReservations(Restaurant restaurant);
	Restaurant findById(long restaurantID);
	void removeAllRestaurants();

}
