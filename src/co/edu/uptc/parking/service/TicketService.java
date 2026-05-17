package co.edu.uptc.parking.service;

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
	
	public boolean deleteTicket(String ticketId) {
		return ticketRepository.deleteById(ticketId);
	}
	
	public boolean existsById(String ticketId) {
		return ticketRepository.existsById(ticketId);
	}
	
	//Falta hacer el método de calcular el valor total
}
