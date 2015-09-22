package com.dudlo.reservationsystem.webapp.dto;

import java.util.List;

import com.dudlo.reservationsystem.model.RestTable;
public class ReservationDTO2 {

	List<RestTable> freeTables;

	/**
	 * @return the freeTables
	 */
	public List<RestTable> getFreeTables() {
		return freeTables;
	}

	/**
	 * @param freeTables the freeTables to set
	 */
	public void setFreeTables(List<RestTable> freeTables) {
		this.freeTables = freeTables;
	}
	
	
}
