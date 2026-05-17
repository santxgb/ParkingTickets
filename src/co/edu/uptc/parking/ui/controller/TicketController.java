package co.edu.uptc.parking.ui.controller;

import java.time.LocalDateTime;

import co.edu.uptc.parking.domain.Client;
import co.edu.uptc.parking.domain.Ticket;
import co.edu.uptc.parking.domain.Vehicle;
import co.edu.uptc.parking.service.ClientService;
import co.edu.uptc.parking.service.TicketService;
import co.edu.uptc.parking.service.VehicleService;

public class TicketController {
	
	private TicketService ticketService;
	private ClientService clientService;
	private VehicleService vehicleService;
	/**
	 * Crea una nueva instancia de TicketController.
	 *
	 * @param ticketService
	 * @param clientService
	 * @param vehicleService Parámetro que determina
	 */
	public TicketController() {
		super();
		this.ticketService = new TicketService();
		this.clientService = new ClientService();
		this.vehicleService = new VehicleService();
	}
	
	public boolean registerEntry(String ticketId, String clientId, String licensePlate, LocalDateTime entryTime) {
		Client client = clientService.findById(clientId);
		Vehicle vehicle = vehicleService.findByLicensePlate(licensePlate);
		
		if(client == null || vehicle == null) {
			return false;
		}
		
		Ticket ticket = new Ticket(ticketId, entryTime, null, 0.0, vehicle, client);
		return ticketService.addTicket(ticket);
	}
	
	
}
