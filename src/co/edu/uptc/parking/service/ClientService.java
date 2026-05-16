package co.edu.uptc.parking.service;

import java.util.List;

import co.edu.uptc.parking.domain.Client;
import co.edu.uptc.parking.repository.ClientRepository;

public class ClientService {
	
	private ClientRepository clientRepository;

	/**
	 * Crea una nueva instancia de ClientService.
	 *
	 * @param clientRepository Parámetro que determina
	 */
	public ClientService() {
		super();
		this.clientRepository = new ClientRepository();
	}
	
	public boolean addClient(Client client) {
		if(clientRepository.existsById(client.getClientId())) {
			return false;
		}
		clientRepository.add(client);
		return true;
	}
	
	public Client findById(String clientId) {
		return clientRepository.findById(clientId);
	}
	
	public List<Client> findAll(){
		return clientRepository.findAll();
	}
	
	public boolean updateClient(Client client) {
		if(!clientRepository.existsById(client.getClientId())) {
			return false;
		}
		clientRepository.add(client);
		return true;
	}
	
	public boolean deleteClient(String clientId) {
		return clientRepository.deleteById(clientId);
	}
	
	public boolean existsById(String clientId) {
		return clientRepository.existsById(clientId);
	}
}
