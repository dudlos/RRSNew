package com.dudlo.reservationsystem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dudlo.reservationsystem.model.Menu;
import com.dudlo.reservationsystem.model.MenuReservationMapping;
import com.dudlo.reservationsystem.model.Reservation;
import com.dudlo.reservationsystem.repository.MenuReservationMappingRepository;


@Service
public class MenuReservationMapServiceImpl implements MenuReservationMapService {

	@Autowired
	MenuReservationMappingRepository menuReservationRep;

	
	@Override
	public List<MenuReservationMapping> getAllMenuMappings() {
		return menuReservationRep.getAllMenuMappings();
	}

	@Override
	public MenuReservationMapping getSingleMenuResDTO(long menuResMap) {
		 return menuReservationRep.getSingleMenuResDTO(menuResMap);
	}

	@Override
	public MenuReservationMapping createMenuResMap(Reservation reservation, Menu menu) {
		 return menuReservationRep.createMenuResMap(reservation, menu);
	}
	
	

}
