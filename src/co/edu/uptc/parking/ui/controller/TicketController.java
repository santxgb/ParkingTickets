package co.edu.uptc.parking.ui.controller;

import java.time.LocalDateTime;
import java.util.List;
import co.edu.uptc.parking.domain.Client;
import co.edu.uptc.parking.domain.Ticket;
import co.edu.uptc.parking.domain.Vehicle;
import co.edu.uptc.parking.dto.ResultDTO;
import co.edu.uptc.parking.service.ClientService;
import co.edu.uptc.parking.service.TicketService;
import co.edu.uptc.parking.service.VehicleService;

public class TicketController {

    private TicketService ticketService;
    private ClientService clientService;
    private VehicleService vehicleService;

    public TicketController() {
        this.ticketService = new TicketService();
        this.clientService = new ClientService();
        this.vehicleService = new VehicleService();
    }

    public ResultDTO registerEntry(String ticketId, String clientId,
            String licensePlate, String entryTimeStr) {
    	ResultDTO result = new ResultDTO();
    	result.setSuccessful(true);

    	if (ticketId == null || ticketId.trim().isEmpty()) { 
    		result.setSuccessful(false); result.getListMessageError().add("El ID del ticket no puede estar vacío.");
			}
    	if (clientId == null || clientId.trim().isEmpty()) { 
    		result.setSuccessful(false); result.getListMessageError().add("El ID del cliente no puede estar vacío.");
			}
    	if (licensePlate == null || licensePlate.trim().isEmpty()) { 
    		result.setSuccessful(false); result.getListMessageError().add("La placa no puede estar vacía.");
			}
    	if (entryTimeStr == null || entryTimeStr.trim().isEmpty()) { 
    		result.setSuccessful(false); result.getListMessageError().add("La hora de entrada no puede estar vacía.");
			}
    	if (!result.isSuccessful()) 
    		return result;
    	// Validar formato de fecha con split 
    	LocalDateTime entryTime = parseDateTime(entryTimeStr, result);
    	if (!result.isSuccessful()) 
    		return result;
    	// Validar que cliente y vehículo existan
    	Client  client  = clientService.findById(clientId);
    	Vehicle vehicle = vehicleService.findByLicensePlate(licensePlate);
    	if (client  == null) { result.setSuccessful(false); 
			result.getListMessageError().add("El cliente con ID " + clientId + " no existe."); 
			}
    	if (vehicle == null) { result.setSuccessful(false); 
			result.getListMessageError().add("El vehículo con placa " + licensePlate + " no existe.");
			}
    	if (!result.isSuccessful()) 
    		return result;
		Ticket ticket = new Ticket(ticketId, entryTime, null, 0.0, vehicle, client);
		boolean added = ticketService.addTicket(ticket);
		if (!added) {
			result.setSuccessful(false);
			result.getListMessageError().add("Ya existe un ticket con el ID: " + ticketId);
			} else {
			result.setTicket(ticket);
			result.setMessage("Entrada registrada correctamente.");
		}
		return result;
    }
    public ResultDTO registerExit(String ticketId, String exitTimeStr, String rateStr) {
        ResultDTO result = new ResultDTO();
        result.setSuccessful(true);

        if (ticketId == null || ticketId.trim().isEmpty()) {
            result.setSuccessful(false);
            result.getListMessageError().add("El ID del ticket no puede estar vacío.");
        }
        if (exitTimeStr == null || exitTimeStr.trim().isEmpty()) {
            result.setSuccessful(false);
            result.getListMessageError().add("La hora de salida no puede estar vacía.");
        }
        if (rateStr == null || rateStr.trim().isEmpty()) {
            result.setSuccessful(false);
            result.getListMessageError().add("La tarifa no puede estar vacía.");
        }
        if (!result.isSuccessful()) return result;

        LocalDateTime exitTime = parseDateTime(exitTimeStr, result);
        if (!result.isSuccessful()) return result;

        // Buscar el ticket antes de validar fechas
        Ticket ticket = ticketService.findById(ticketId);
        if (ticket == null) {
            result.setSuccessful(false);
            result.getListMessageError().add("No se encontró el ticket con ID: " + ticketId);
            return result;
        }

        // Validar que la salida sea posterior a la entrada
        if (!exitTime.isAfter(ticket.getEntryTime())) {
            result.setSuccessful(false);
            result.getListMessageError().add("La hora de salida debe ser posterior a la hora de entrada.");
            return result;
        }

        double ratePerHour = Double.parseDouble(rateStr);
        if (ratePerHour <= 0) {
            result.setSuccessful(false);
            result.getListMessageError().add("La tarifa debe ser mayor a 0.");
            return result;
        }

        ticket.setExitTime(exitTime);
        double total = ticketService.calcularValorTotal(ticket, ratePerHour);
        ticket.setTotalValue(total);
        ticketService.updateTicket(ticket);
        result.setTicket(ticket);
        result.setMessage("Salida registrada. Total a pagar: $" + String.format("%.2f", total));
        return result;
    }

    public ResultDTO findTicketById(String ticketId) {
        ResultDTO result = new ResultDTO();
        result.setSuccessful(true);
        if (ticketId == null || ticketId.trim().isEmpty()) {
            result.setSuccessful(false);
            result.getListMessageError().add("El ID del ticket no puede estar vacío.");
            return result;
        }
        Ticket ticket = ticketService.findById(ticketId);
        if (ticket == null) {
            result.setSuccessful(false);
            result.getListMessageError().add("No se encontró el ticket con ID: " + ticketId);
        } else {
            result.setTicket(ticket);
        }
        return result;
    }

    public List<Ticket> findAllTickets() {
        return ticketService.findAll();
    }

    public ResultDTO deleteTicket(String ticketId) {
        ResultDTO result = new ResultDTO();
        result.setSuccessful(true);
        if (ticketId == null || ticketId.trim().isEmpty()) {
            result.setSuccessful(false);
            result.getListMessageError().add("El ID del ticket no puede estar vacío.");
            return result;
        }
        boolean deleted = ticketService.deleteTicket(ticketId);
        if (!deleted) {
            result.setSuccessful(false);
            result.getListMessageError().add("No se encontró el ticket con ID: " + ticketId);
        	} else {
            result.setMessage("Ticket eliminado correctamente.");
        }
        return result;
    }

    /**
     * Parsea una fecha con formato dd/MM/yyyy HH:mm usando split.
     * Valida la estructura antes de parsear.
     * Si el formato es incorrecto agrega el error al ResultDTO.
     */
    private LocalDateTime parseDateTime(String dateTimeStr, ResultDTO result) {
        // Separar fecha y hora por el espacio
        String[] partes = dateTimeStr.split(" ");
        if (partes.length != 2) {
            result.setSuccessful(false);
            result.getListMessageError().add("Formato de fecha inválido. Use dd/MM/yyyy HH:mm");
            return null;
        } 
        // Separar día, mes y año
        String[] fecha = partes[0].split("/");
        if (fecha.length != 3) {
            result.setSuccessful(false);
            result.getListMessageError().add("Formato de fecha inválido. Use dd/MM/yyyy HH:mm");
            return null;
        }
        // Separar hora y minutos
        String[] hora = partes[1].split(":");
        if (hora.length != 2) {
            result.setSuccessful(false);
            result.getListMessageError().add("Formato de hora inválido. Use HH:mm");
            return null;
        }
        // Construir el LocalDateTime con los valores separados
        int dia = Integer.parseInt(fecha[0]);
        int mes = Integer.parseInt(fecha[1]);
        int anio = Integer.parseInt(fecha[2]);
        int horas = Integer.parseInt(hora[0]);
        int minutos = Integer.parseInt(hora[1]);
        return LocalDateTime.of(anio, mes, dia, horas, minutos);
    }
}
