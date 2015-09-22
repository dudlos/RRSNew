package com.dudlo.reservationsystem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dudlo.reservationsystem.model.Menu;
import com.dudlo.reservationsystem.model.Menu.MenuType;
import com.dudlo.reservationsystem.model.Restaurant;
import com.dudlo.reservationsystem.repository.MenuRepository;

@Service
public class MenuServiceImpl implements MenuService {

	@Autowired
	MenuRepository menuRepository;

	@Override
	public List<Menu> getAllMenus() {
		return menuRepository.getAllMenus();
	}

	@Override
	public List<Menu> getAllMenusForMenuType(String menuType, Long restaurantID) {

		return menuRepository.getAllMenusForMenuType(menuType, restaurantID);
	}

	@Override
	public List<Menu> getAllMenusFromRestaurant(Long restaurantID) {

		return menuRepository.getAllMenusFromRestaurant(restaurantID);

	}

	@Override
	public Menu createMenu(MenuType menuType, String dish,
			String dishDescription, double price_$, Restaurant restaurant) {
		return menuRepository.createMenu(menuType, dish, dishDescription,
				price_$, restaurant);
	}

	@Override
	public void deleteMenu(long menuId) {
		menuRepository.deleteMenu(menuId);
	}

	@Override
	public Menu getSingleMenu(long menuID) {
		return menuRepository.getSingleMenu(menuID);
	}

	// CSVLoader loader;
}
