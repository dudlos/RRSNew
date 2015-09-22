package com.dudlo.reservationsystem.service;

import java.util.List;

import com.dudlo.reservationsystem.model.RestTable;
import com.dudlo.reservationsystem.model.Restaurant;

public interface RestTableService {

	RestTable findById(Long id);

	List<RestTable> findAll();

	List<RestTable> findAllByRestaurant(Restaurant rest);

	RestTable createTable(int capacity, String tableNumber,
			Restaurant restaurant);

}
