package com.dudlo.reservationsystem.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.dudlo.reservationsystem.model.Menu;
import com.dudlo.reservationsystem.model.MenuReservationMapping;
import com.dudlo.reservationsystem.model.Reservation;

@Repository
@Transactional
public class MenuReservationMappingImpl implements MenuReservationMappingRepository {

	@PersistenceContext
	private EntityManager manager;
	
	@Override
	public List<MenuReservationMapping> getAllMenuMappings() {
		return manager.createQuery("from MenuReservationMapping", MenuReservationMapping.class).getResultList();
	}

	@Override
	public MenuReservationMapping getSingleMenuResDTO(long menuResMap) {
		 return manager.createQuery("Select distinct c from MenuReservationMapping c Where c.menuResMapID = :menuIDparam",
									MenuReservationMapping .class).setParameter("menuIDparam", menuResMap)	.getSingleResult();
	}

	@Override
	public MenuReservationMapping createMenuResMap(Reservation reservation, Menu menu) {
		MenuReservationMapping menuReservationMap = new MenuReservationMapping(reservation, menu);
		 manager.persist(menuReservationMap);
		 return menuReservationMap;
	}
	
}
