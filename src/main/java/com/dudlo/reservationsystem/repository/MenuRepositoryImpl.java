package com.dudlo.reservationsystem.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.dudlo.reservationsystem.model.Menu;
import com.dudlo.reservationsystem.model.Menu.MenuType;
import com.dudlo.reservationsystem.model.Restaurant;
import com.dudlo.reservationsystem.service.RestaurantService;

@Repository
@Transactional
public class MenuRepositoryImpl implements MenuRepository {

	@Autowired
	RestaurantService restaurantService;
	
	protected MenuRepositoryImpl() {

	}

	@PersistenceContext
	private EntityManager manager;

	@Override
	public List<Menu> getAllMenus() {
		return manager.createQuery("from Menu", Menu.class).getResultList();
	}

	@Override
	public List<Menu> getAllMenusFromRestaurant(Long restaurantID) {
		Restaurant rest = restaurantService.findById(restaurantID);
		return manager
				.createQuery(
						"Select distinct c from Menu c where c.restaurant = :restparam",
						Menu.class).setParameter("restparam", rest)
				.getResultList();
	}

	@Override
	public List<Menu> getAllMenusForMenuType(String menuType, Long restaurantID) {
		Restaurant rest = restaurantService.findById(restaurantID);
		return manager
				.createQuery(
						"Select distinct c from Menu c where c.restaurant = :restparam and c.menuType = :menuparam",
						Menu.class).setParameter("menuparam", menuType)
				.setParameter("restparam", rest).getResultList();

	}

	@Override
	public Menu createMenu(MenuType menuType, String dish,
			String dishDescription, double price_$, Restaurant restaurant) {
		Menu menu = new Menu(menuType, dish, dishDescription, price_$,
				restaurant);
		manager.persist(menu);
		return menu;

	}

	@Override
	public void deleteMenu(long menuID) {

		Menu menu = manager.getReference(Menu.class, menuID);
		manager.remove(menu);

	}

	@Override
	public Menu getSingleMenu(long menuID) {

		return manager
				.createQuery(
						"Select distinct c from Menu c Where c.menuID = :menuIDparam",
						Menu.class).setParameter("menuIDparam", menuID)
				.getSingleResult();

	}

}
