package com.dudlo.reservationsystem.webapp.dto;




import java.util.List;

/**
 * DTO - Data Transfer Object pre prenos dat medzi server<->klient
 */
public class ReservationDTO {

    private List<String> freeSlots;

    public void setFreeSlots(List<String> freeSlots) {
        this.freeSlots = freeSlots;
    }

    public List<String> getFreeSlots() {
        return freeSlots;
    }
}
