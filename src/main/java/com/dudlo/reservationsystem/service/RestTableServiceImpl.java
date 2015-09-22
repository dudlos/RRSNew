package com.dudlo.reservationsystem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dudlo.reservationsystem.model.RestTable;
import com.dudlo.reservationsystem.model.Restaurant;
import com.dudlo.reservationsystem.repository.RestTableRepository;

@Service
public class RestTableServiceImpl implements RestTableService {

	@Autowired
	RestTableRepository restTableRepository;

	@Override
	public RestTable findById(Long id) {
		return restTableRepository.findById(id);
	}

	@Override
	public List<RestTable> findAll() {
		return restTableRepository.findAll();
	}

	@Override
	public RestTable createTable(int capacity, String tableNumber, Restaurant restaurant) {
		return restTableRepository.createTable(capacity, tableNumber, restaurant);
	}

	@Override
	public List<RestTable> findAllByRestaurant(Restaurant rest) {
		
		return restTableRepository.findAllByRestaurant(rest);
	}

}
