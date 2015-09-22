package com.dudlo.reservationsystem.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "MENUS_TABLE")
public class Menu implements Serializable {

	public static enum MenuType{Starter, Side, Main, Dessert, Salad}
	
	private static final long serialVersionUID = 1L;

	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long menuID;
	@Column
	private String menuType;
	@Column
	private String dish;
	@Column
	private String dishDescription;
	@Column
	private double price_$;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "restaurantID")
	Restaurant restaurant;

	protected Menu() {

	}

	public Menu(MenuType menuType, String dish, String dishDescription, double price_$, Restaurant restaurant
			) {
		this.menuType = menuType.toString();
		this.dish = dish;
		this.dishDescription = dishDescription;
		this.price_$ = price_$;
		this.restaurant = restaurant;

	}

	/**
	 * @return the restaurant
	 */
	public Restaurant getRestaurant() {
		return restaurant;
	}

	/**
	 * @return the menuId
	 */
	public long getMenuID() {
		return menuID;
	}

	/**
	 * @return the MenuType
	 */
	public String getMenuType() {
		return menuType;
	}

	/**
	 * @param MenuType
	 *            the MenuType to set
	 */
	public void setMenuType(MenuType menuType) {
		this.menuType = menuType.toString();
	}

	/**
	 * @return the dish
	 */
	public String getDish() {
		return dish;
	}

	/**
	 * @param dish the dish to set
	 */
	public void setDish(String dish) {
		this.dish = dish;
	}

	/**
	 * @return the dishDescription
	 */
	public String getDishDescription() {
		return dishDescription;
	}

	/**
	 * @param dishDescription
	 *            the dishDescription to set
	 */
	public void setDishDescription(String dishDescription) {
		this.dishDescription = dishDescription;
	}

	/**
	 * @return the price_$
	 */
	public double getPrice_$() {
		return price_$;
	}

	/**
	 * @param price_$
	 *            the price_$ to set
	 */
	public void setPrice_$(double price_$) {
		this.price_$ = price_$;
	}

}
