package co.edu.uptc.parking.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import co.edu.uptc.parking.domain.Ticket;

public class TicketRepository {
	
	private Map<String, Ticket> tickets;

	/**
	 * Crea una nueva instancia de TicketRepository.
	 *
	 * @param tickets Parámetro que determina
	 */
	public TicketRepository() {
		super();
		this.tickets = new TreeMap<>();
	}
	
	public void add(Ticket ticket) {
		tickets.put(ticket.getTicketId(), ticket);
	}
	
	public Ticket findById(String TicketId) {
		return tickets.get(TicketId);
	}
	
	public List<Ticket> findAll(){
		return new ArrayList<>(tickets.values());
	}
	
	public boolean deleteById(String ticketId) {
		return tickets.remove(ticketId) != null;
	}
	
	public boolean existsById(String ticketId) {
		return tickets.containsKey(ticketId);
	}
	
	public boolean hasOpenTicketForVehicle(String licensePlate) {
	    for (Ticket ticket : tickets.values()) {
	        if (ticket.getVehicle().getLicensePlate().equals(licensePlate)
	                && ticket.getExitTime() == null) {
	            return true;
	        }
	    }
	    return false;
	}
}
