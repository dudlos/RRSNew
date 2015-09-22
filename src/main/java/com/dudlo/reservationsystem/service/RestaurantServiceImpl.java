package com.dudlo.reservationsystem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dudlo.reservationsystem.model.Restaurant;
import com.dudlo.reservationsystem.repository.RestaurantRepository;

@Service
public class RestaurantServiceImpl implements RestaurantService {

	@Autowired
	RestaurantRepository restaurantRepository;

	@Override
	public List<Restaurant> getAllRestaurants() {
		return restaurantRepository.getAllRestaurants();
	}

	@Override
	public Restaurant createNewRestaurant(String restaurantName) {
		return restaurantRepository.createNewRestaurant(restaurantName);
	}

	@Override
	public void removeAllReservations(Restaurant restaurant) {
		this.restaurantRepository.removeAllReservations(restaurant);
		
	}

	@Override
	public Restaurant findById(long restaurantID) {
		return restaurantRepository.findById(restaurantID);
	}

	@Override
	public void removeAllRestaurants() {
		restaurantRepository.removeAllRestaurants();
		
	}
	
	

}
