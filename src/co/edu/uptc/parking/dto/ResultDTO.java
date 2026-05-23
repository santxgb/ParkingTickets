package co.edu.uptc.parking.dto;

import java.util.ArrayList;
import java.util.List;
import co.edu.uptc.parking.domain.Client;
import co.edu.uptc.parking.domain.Ticket;
import co.edu.uptc.parking.domain.Vehicle;

public class ResultDTO {

    private boolean isSuccessful;
    private String  message;
    private List<String> listMessageError;
    private Client client;
    private Vehicle vehicle;
    private Ticket ticket;

    public ResultDTO() {
        this.listMessageError = new ArrayList<>();
    }

    public boolean isSuccessful() {
    	return isSuccessful;
    	}
    
    public void setSuccessful(boolean isSuccessful) { 
    	this.isSuccessful = isSuccessful; 
    	}
    
    public String getMessage()  {
    	return message; 
    	}
    
    public void setMessage(String message) { 
    	this.message = message;
    	}
    
    public List<String> getListMessageError() { 
    	return listMessageError; 
    	}
    
    public void setListMessageError(List<String> l) { 
    	this.listMessageError = l; 
    	}
    
    public Client getClient() { 
    	return client;
    	}
    
    public void setClient(Client client) { 
    	this.client = client;
    	}
    
    public Vehicle getVehicle() { 
    	return vehicle;
    	}
    
    public void setVehicle(Vehicle vehicle)  {
    	this.vehicle = vehicle;
    	}
    
    public Ticket getTicket() { 
    	return ticket;
    	}
    
    public void setTicket(Ticket ticket) { 
    	this.ticket = ticket; 
    	}
}