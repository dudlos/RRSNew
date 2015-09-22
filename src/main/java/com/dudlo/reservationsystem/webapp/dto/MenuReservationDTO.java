package com.dudlo.reservationsystem.webapp.dto;

import java.util.Set;

import com.dudlo.reservationsystem.model.Menu;
import com.dudlo.reservationsystem.model.MenuReservationMapping;

public class MenuReservationDTO {

	Set<MenuReservationMapping> menuList;

	/**
	 * @return the menuList
	 */
	public Set<MenuReservationMapping> getMenuList() {
		return menuList;
	}

	/**
	 * @param menuList the menuList to set
	 */
	public void setMenuList(Set<MenuReservationMapping> menuList) {
		this.menuList = menuList;
	}
	
}
