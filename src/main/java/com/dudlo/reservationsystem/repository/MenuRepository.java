package com.dudlo.reservationsystem.repository;

import java.util.List;

import com.dudlo.reservationsystem.model.Menu;
import com.dudlo.reservationsystem.model.Menu.MenuType;
import com.dudlo.reservationsystem.model.Restaurant;

public interface MenuRepository {

	List<Menu> getAllMenus();

	List<Menu> getAllMenusFromRestaurant(Long restaurantID);

	List<Menu> getAllMenusForMenuType(String menuType, Long restaurantID);

	Menu createMenu(MenuType menuType, String dish, String dishDescription,
			double price_$, Restaurant restaurant);

	Menu getSingleMenu(long menuID);

	void deleteMenu(long menuID);

}
