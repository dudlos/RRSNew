package com.dudlo.reservationsystem.model;

public enum SlotEnum {

	SLOT1("12:00"), SLOT2("13:00"), SLOT3("14:00"), SLOT4("15:00"), SLOT5(
			"16:00"), SLOT6("17:00"), SLOT7("18:00"), SLOT8("19:00"), SLOT9(
			"20:00");

	private String slot;

	SlotEnum(String slot) {
		this.slot = slot;
	}

	/**
	 * @return the slots
	 */
	public String getSlot() {
		return slot;
	}

}
