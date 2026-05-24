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

    public ResultDTO registerEntry(String ticketId, String clientId, String licensePlate, String entryTimeStr) {
        ResultDTO result = new ResultDTO();
        result.setSuccessful(true);

        // Validar campos requeridos
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

        if (!entryTimeStr.matches("^\\d{2}/\\d{2}/\\d{4} \\d{2}:\\d{2}$")) {
            result.setSuccessful(false);
            result.getListMessageError().add("Formato de fecha inválido. Use dd/MM/yyyy HH:mm");
            return result;
        }

        LocalDateTime entryTime = parseDateTime(entryTimeStr);
        Client  client  = clientService.findById(clientId);
        Vehicle vehicle = vehicleService.findByLicensePlate(licensePlate);
        	if (client  == null) { 
        		result.setSuccessful(false); result.getListMessageError().add("El cliente con ID " + clientId + " no existe."); 
        		}
        	if (vehicle == null) { 
        		result.setSuccessful(false); result.getListMessageError().add("El vehículo con placa " + licensePlate + " no existe."); 
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
        	result.setSuccessful(false); result.getListMessageError().add("El ID del ticket no puede estar vacío.");
        	}
        if (exitTimeStr == null || exitTimeStr.trim().isEmpty()) { 
        	result.setSuccessful(false); result.getListMessageError().add("La hora de salida no puede estar vacía.");
        	}
        if (rateStr == null || rateStr.trim().isEmpty()) { 
        	result.setSuccessful(false); result.getListMessageError().add("La tarifa no puede estar vacía."); }
        if (!result.isSuccessful()) 
        	return result;
        if (!exitTimeStr.matches("^\\d{2}/\\d{2}/\\d{4} \\d{2}:\\d{2}$")) {
            result.setSuccessful(false);
            result.getListMessageError().add("Formato de fecha inválido. Use dd/MM/yyyy HH:mm");
            return result;
        	}
        if (!rateStr.matches("^\\d+(\\.\\d+)?$")) {
            result.setSuccessful(false);
            result.getListMessageError().add("La tarifa debe ser un valor numérico positivo.");
            return result;
        }
        LocalDateTime exitTime    = parseDateTime(exitTimeStr);
        double        ratePerHour = Double.parseDouble(rateStr);
        Ticket ticket = ticketService.findById(ticketId);
        if (ticket == null) {
            result.setSuccessful(false);
            result.getListMessageError().add("No se encontró el ticket con ID: " + ticketId);
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

    private LocalDateTime parseDateTime(String dateTimeStr) {
        String[] parts     = dateTimeStr.split(" ");
        String[] dateParts = parts[0].split("/");
        String[] timeParts = parts[1].split(":");
        return LocalDateTime.of(
            Integer.parseInt(dateParts[2]),
            Integer.parseInt(dateParts[1]),
            Integer.parseInt(dateParts[0]),
            Integer.parseInt(timeParts[0]),
            Integer.parseInt(timeParts[1])
        );
    }
}