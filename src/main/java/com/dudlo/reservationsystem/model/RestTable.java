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
@Table(name = "RESTABLE_TABLE")
public class RestTable implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long tableID;

	@Column
	private String tableNumber;

	@Column
	int capacity;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "restaurantID")
	Restaurant restaurant;

	protected RestTable() {

	}

	public RestTable(int capacity, String tableNumber, Restaurant restaurant) {

		this.tableNumber = tableNumber;
		this.capacity = capacity;
		this.restaurant = restaurant;
	}

	/**
	 * @return the tableID
	 */
	public long getTableID() {
		return this.tableID;
	}

	/**
	 * @param tableID
	 *            the tableID to set
	 */
	public void setTableID(int tableID) {
		this.tableID = tableID;
	}

	/**
	 * @return the capacity
	 * 
	 */
	public int getCapacity() {
		return capacity;
	}

	/**
	 * @param capacity
	 *            the capacity to set
	 * 
	 * 
	 */
	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	/**
	 * @return the tableNumber
	 */
	public String getTableNumber() {
		return tableNumber;
	}

	/**
	 * @param tableNumber
	 *            the tableNumber to set
	 */
	public void setTableNumber(String tableNumber) {
		this.tableNumber = tableNumber;
	}

	/**
	 * @return the restaurant
	 */
	public Restaurant getRestaurant() {
		return restaurant;
	}

}
