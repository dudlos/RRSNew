package com.dudlo.reservationsystem.repository;

import java.util.List;

import com.dudlo.reservationsystem.model.Menu;
import com.dudlo.reservationsystem.model.MenuReservationMapping;
import com.dudlo.reservationsystem.model.Reservation;

public interface MenuReservationMappingRepository {

	List <MenuReservationMapping>getAllMenuMappings();
	
	MenuReservationMapping getSingleMenuResDTO(long menuResMap);
	
	MenuReservationMapping createMenuResMap(Reservation reservation, Menu menu);
}
