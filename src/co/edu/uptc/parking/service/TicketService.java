package co.edu.uptc.parking.service;

import java.time.LocalDateTime;
import java.util.List;

import co.edu.uptc.parking.domain.Ticket;
import co.edu.uptc.parking.repository.TicketRepository;

public class TicketService {
	
	private TicketRepository ticketRepository;

	/**
	 * Crea una nueva instancia de TicketService.
	 *
	 * @param ticketRepository Parámetro que determina
	 */
	public TicketService() {
		super();
		this.ticketRepository = new TicketRepository();
	}
	
	public boolean addTicket(Ticket ticket) {
		if(ticketRepository.existsById(ticket.getTicketId())) {
			return false;
		}
		ticketRepository.add(ticket);
		return true;
	}
	
	public Ticket findById(String ticketId) {
		return ticketRepository.findById(ticketId);
	}
	
	public List<Ticket> findAll(){
		return ticketRepository.findAll();
	}
	
	public boolean updateTicket(Ticket ticket) {
		if(!ticketRepository.existsById(ticket.getTicketId())) {
			return false;
		}
		ticketRepository.add(ticket);
		return true;
	}
	
	public boolean deleteTicket(String ticketId) {
		return ticketRepository.deleteById(ticketId);
	}
	
	public boolean existsById(String ticketId) {
		return ticketRepository.existsById(ticketId);
	}
	
	public double calcularValorTotal(Ticket ticket, double ratePerHour) {
	    LocalDateTime entry = ticket.getEntryTime();
	    LocalDateTime exit  = ticket.getExitTime();

	    long entryMinutesTotal = (long) entry.getDayOfYear() * 24 * 60
	                           + entry.getHour() * 60
	                           + entry.getMinute();
	    long exitMinutesTotal  = (long) exit.getDayOfYear() * 24 * 60
	                           + exit.getHour() * 60
	                           + exit.getMinute();

	    long totalMinutes = exitMinutesTotal - entryMinutesTotal;
	    double hours = Math.ceil(totalMinutes / 60.0);

	    double surcharge = ticket.getVehicle().getTypeVehicle().getSurcharge();
	    double base = hours * ratePerHour;
	    return base + (base * surcharge);
	}
}
