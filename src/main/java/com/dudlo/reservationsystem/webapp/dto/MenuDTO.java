package com.dudlo.reservationsystem.webapp.dto;

import java.util.List;

import com.dudlo.reservationsystem.model.Menu;

public class MenuDTO {
	
	List<Menu> menuList;

	/**
	 * @return the freeTables
	 */
	public List<Menu> getMenus() {
		return menuList;
	}

	/**
	 * @param freeTables the freeTables to set
	 */
	public void setMenus(List<Menu> menuList) {
		this.menuList = menuList;
	}

}
