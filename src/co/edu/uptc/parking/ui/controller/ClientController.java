package co.edu.uptc.parking.ui.controller;

import java.util.List;

import co.edu.uptc.parking.domain.Client;
import co.edu.uptc.parking.service.ClientService;

public class ClientController {
	
	private ClientService clientService;

	/**
	 * Crea una nueva instancia de ClientController.
	 *
	 * @param clientService Parámetro que determina
	 */
	public ClientController() {
		super();
		this.clientService = new ClientService();
	}
	
	public boolean addClient(String clientId, String name, String lastName, String email, String phone) {
		Client client = new Client(clientId, name, lastName, email, phone);
		return clientService.addClient(client);
	}
	
	public Client findClientById(String clientId) {
		return clientService.findById(clientId);
	}
	
	public List<Client> findAllClients(){
		return clientService.findAll();
	}
	
	public boolean updateClient(String clientId, String name, String lastName, String email, String phone) {
		Client client = new Client(clientId, name, lastName, email, phone);
		return clientService.updateClient(client);	
		}
	public boolean deleteClient(String clientId) {
		return clientService.deleteClient(clientId);
	}
	
	public boolean existsClient(String clientId) {
		return clientService.existsById(clientId);
	}
}
