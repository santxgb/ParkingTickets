package co.edu.uptc.parking.ui.controller;

import java.util.List;
import co.edu.uptc.parking.domain.Client;
import co.edu.uptc.parking.dto.ResultDTO;
import co.edu.uptc.parking.service.ClientService;

public class ClientController {

    private ClientService clientService;

    public ClientController() {
        this.clientService = new ClientService();
    }

    public ResultDTO addClient(String clientId, String name, String lastName, String phone, String email, String address) {
    	ResultDTO result = validateRequiredFields(clientId, name, lastName, phone, email, address);
        if (!result.isSuccessful()) 
        	return result;

        validateAlphanumericField("ValidationClientId", clientId, "^[A-Za-z0-9]{3,15}$", result);
        validateAlphanumericField("ValidationName", name, "^[a-zA-ZÁÉÍÓÚáéíóúÑñ ]+$", result);
        validateAlphanumericField("ValidationLastName", lastName, "^[a-zA-ZÁÉÍÓÚáéíóúÑñ ]+$", result);
        validateAlphanumericField("ValidationPhone", phone, "^[0-9]{7,15}$", result);
        validateAlphanumericField("ValidationEmail", email, "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$", result);
        validateAlphanumericField("ValidationAddress", address, "^[a-zA-ZÁÉÍÓÚáéíóúÑñ0-9 #\\-\\.]+$", result);
        
        if (!result.isSuccessful()) return result;

        boolean added = clientService.addClient(new Client(clientId, name, lastName, phone, email, address));
        if (!added) {
            result.setSuccessful(false);
            result.getListMessageError().add("Ya existe un cliente con el ID: " + clientId);
        }
        return result;
    }

    public ResultDTO findClientById(String clientId) {
        ResultDTO result = new ResultDTO();
        result.setSuccessful(true);
        if (clientId == null || clientId.trim().isEmpty()) {
            result.setSuccessful(false);
            result.getListMessageError().add("El ID del cliente no puede estar vacío.");
            return result;
        }
        Client client = clientService.findById(clientId);
        if (client == null) {
            result.setSuccessful(false);
            result.getListMessageError().add("No se encontró un cliente con el ID: " + clientId);
        } else {
            result.setClient(client);
        }
        return result;
    }

    public List<Client> findAllClients() {
        return clientService.findAll();
    }

    public ResultDTO updateClient(String clientId, String name, String lastName, String email, String phone, String address){
        ResultDTO result = new ResultDTO();
        result.setSuccessful(true);
        if (name     != null && !name.trim().isEmpty())
            validateAlphanumericField("ValidationName",     name,     "^[a-zA-ZÁÉÍÓÚáéíóúÑñ ]+$", result);
        if (lastName != null && !lastName.trim().isEmpty())
            validateAlphanumericField("ValidationLastName", lastName, "^[a-zA-ZÁÉÍÓÚáéíóúÑñ ]+$", result);
        if (email    != null && !email.trim().isEmpty())
            validateAlphanumericField("ValidationEmail",    email,    "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$", result);
        if (phone    != null && !phone.trim().isEmpty())
            validateAlphanumericField("ValidationPhone",    phone,    "^[0-9]{7,15}$", result);
        if (!result.isSuccessful()) return result;
        boolean updated = clientService.updateClient(new Client(clientId, name, lastName, phone, email, address));
        if (!updated) {
            result.setSuccessful(false);
            result.getListMessageError().add("No se encontró el cliente con ID: " + clientId);
        } else {
            result.setMessage("Cliente actualizado correctamente.");
        }
        return result;
    }

    public ResultDTO deleteClient(String clientId) {
        ResultDTO result = new ResultDTO();
        result.setSuccessful(true);
        if (clientId == null || clientId.trim().isEmpty()) {
            result.setSuccessful(false);
            result.getListMessageError().add("El ID del cliente no puede estar vacío.");
            return result;
        }
        boolean deleted = clientService.deleteClient(clientId);
        if (!deleted) {
            result.setSuccessful(false);
            result.getListMessageError().add("No se encontró el cliente con ID: " + clientId);
        } else {
            result.setMessage("Cliente eliminado correctamente.");
        }
        return result;
    }

    public boolean existsClient(String clientId) {
        return clientService.existsById(clientId);
    }

    private ResultDTO validateRequiredFields(String clientId, String name, String lastName, String email, String phone, String address) {
        ResultDTO result = new ResultDTO();
        result.setSuccessful(true);
        if (clientId == null || clientId.trim().isEmpty()) { 
        	result.setSuccessful(false); result.getListMessageError().add("El ID del cliente no puede ser nulo ni vacío."); 
        	}
        if (name == null || name.trim().isEmpty()) { 
        	result.setSuccessful(false); result.getListMessageError().add("El nombre no puede ser nulo ni vacío."); 
        	}
        if (lastName == null || lastName.trim().isEmpty()) { 
        	result.setSuccessful(false); result.getListMessageError().add("El apellido no puede ser nulo ni vacío."); 
        	}
        if (email == null || email.trim().isEmpty()){ 
        	result.setSuccessful(false); result.getListMessageError().add("El email no puede ser nulo ni vacío."); 
        	}
        if (phone == null || phone.trim().isEmpty()) { 
        	result.setSuccessful(false); result.getListMessageError().add("El teléfono no puede ser nulo ni vacío."); 
        	}
        if (address == null || address.trim().isEmpty()) {
            result.setSuccessful(false);
            result.getListMessageError().add("La dirección no puede ser nula ni vacía.");
        }
        return result;
    }

    private void validateAlphanumericField(String fieldName, String value, String pattern, ResultDTO result) {
        if (!value.matches(pattern)) {
            result.setSuccessful(false);
            result.getListMessageError().add("Falló la validación: " + fieldName);
        }
    }
}
