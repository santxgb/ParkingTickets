package co.edu.uptc.parking.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import co.edu.uptc.parking.domain.Client;


public class ClientRepository {
	private Map<String, Client> clients;

	/**
	 * Crea una nueva instancia de ClientRepository.
	 *
	 * @param clients Parámetro que determina
	 */
	public ClientRepository() {
		super();
		this.clients = new TreeMap<>();
	}
	
	public void add(Client client) {
		clients.put(client.getClientId(), client);
	}
	
	public Client findById(String clientId) {
		return clients.get(clientId);
	}
	
	public List<Client> findAll(){
		return new ArrayList<>(clients.values());
	}
	
	public boolean deleteById(String clientId) {
		return clients.remove(clientId) != null;
	}
	
	public boolean existsById(String clientId) {
		return clients.containsKey(clientId);
	}
}
