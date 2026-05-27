package co.edu.uptc.parking.domain;

import java.time.LocalDateTime;
import java.util.Objects;

public class Ticket {
	private String ticketId;
	private LocalDateTime entryTime;
	private LocalDateTime exitTime;
	private double totalValue;
	private Vehicle vehicle;
	private Client client;
	
	
	/**
	 * Crea una nueva instancia de Ticket.
	 * Parámetro que determina
	 */
	public Ticket() {
		super();
	}


	/**
	 * Crea una nueva instancia de Ticket.
	 *
	 * @param ticketId
	 * @param entryTime
	 * @param exitTime
	 * @param totalValue
	 * @param vehicle
	 * @param client Parámetro que determina
	 */
	public Ticket(String ticketId, LocalDateTime entryTime, LocalDateTime exitTime, double totalValue, Vehicle vehicle,
			Client client) {
		super();
		this.ticketId = ticketId;
		this.entryTime = entryTime;
		this.exitTime = exitTime;
		this.totalValue = totalValue;
		this.vehicle = vehicle;
		this.client = client;
	}


	/**
	 * Método encargado de retornar el valor de ticketId.
	 *
	 * @return valor de ticketId
	 */
	public String getTicketId() {
		return ticketId;
	}


	/**
	 * Método encargado de establecer el valor de ticketId.
	 *
	 * @param ticketId nuevo valor de ticketId
	 */
	public void setTicketId(String ticketId) {
		this.ticketId = ticketId;
	}


	/**
	 * Método encargado de retornar el valor de entryTime.
	 *
	 * @return valor de entryTime
	 */
	public LocalDateTime getEntryTime() {
		return entryTime;
	}


	/**
	 * Método encargado de establecer el valor de entryTime.
	 *
	 * @param entryTime nuevo valor de entryTime
	 */
	public void setEntryTime(LocalDateTime entryTime) {
		this.entryTime = entryTime;
	}


	/**
	 * Método encargado de retornar el valor de exitTime.
	 *
	 * @return valor de exitTime
	 */
	public LocalDateTime getExitTime() {
		return exitTime;
	}


	/**
	 * Método encargado de establecer el valor de exitTime.
	 *
	 * @param exitTime nuevo valor de exitTime
	 */
	public void setExitTime(LocalDateTime exitTime) {
		this.exitTime = exitTime;
	}


	/**
	 * Método encargado de retornar el valor de totalValue.
	 *
	 * @return valor de totalValue
	 */
	public double getTotalValue() {
		return totalValue;
	}


	/**
	 * Método encargado de establecer el valor de totalValue.
	 *
	 * @param totalValue nuevo valor de totalValue
	 */
	public void setTotalValue(double totalValue) {
		this.totalValue = totalValue;
	}


	/**
	 * Método encargado de retornar el valor de vehicle.
	 *
	 * @return valor de vehicle
	 */
	public Vehicle getVehicle() {
		return vehicle;
	}


	/**
	 * Método encargado de establecer el valor de vehicle.
	 *
	 * @param vehicle nuevo valor de vehicle
	 */
	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}


	/**
	 * Método encargado de retornar el valor de client.
	 *
	 * @return valor de client
	 */
	public Client getClient() {
		return client;
	}


	/**
	 * Método encargado de establecer el valor de client.
	 *
	 * @param client nuevo valor de client
	 */
	public void setClient(Client client) {
		this.client = client;
	}


	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		return Objects.hash(ticketId);
	}


	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Ticket other = (Ticket) obj;
		return Objects.equals(ticketId, other.ticketId);
	}


	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
	    return "Ticket [ticketId=" + ticketId 
	            + ", entryTime=" + entryTime 
	            + ", exitTime=" + (exitTime != null ? exitTime : "Sin registrar")
	            + ", totalValue=" + totalValue 
	            + ", vehicle=" + vehicle 
	            + ", client=" + client + "]";
	}
	
	
}
