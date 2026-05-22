package co.edu.uptc.parking.ui.view;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.JOptionPane;
import co.edu.uptc.parking.domain.Ticket;
import co.edu.uptc.parking.ui.controller.TicketController;

public class TicketView {

    private TicketController ticketController;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public TicketView() {
        this.ticketController = new TicketController();
    }
    public void showMenu() {
        boolean running = true;
        while (running) {
            int op = Integer.parseInt(JOptionPane.showInputDialog(null,
                "[1] Registrar entrada"       +
                "[2] Registrar salida"        +
                "[3] Consultar todos"         +
                "[4] Consultar por ID"        +
                "[5] Eliminar Ticket"         +
                "[6] Volver",
                "── GESTIÓN DE TICKETS ──",
                JOptionPane.INFORMATION_MESSAGE));

            switch (op) {
                case 1 -> registerEntry();
                case 2 -> registerExit();
                case 3 -> findAllTickets();
                case 4 -> findTicketById();
                case 5 -> deleteTicket();
                case 6 -> running = false;
                default -> JOptionPane.showMessageDialog(null, "Opción inválida.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    private void registerEntry() {
        String ticketId  = JOptionPane.showInputDialog(null, "ID del ticket:",         "Registrar Entrada", JOptionPane.QUESTION_MESSAGE);
        String clientId  = JOptionPane.showInputDialog(null, "ID del cliente:",        "Registrar Entrada", JOptionPane.QUESTION_MESSAGE);
        String plate     = JOptionPane.showInputDialog(null, "Placa del vehículo:",    "Registrar Entrada", JOptionPane.QUESTION_MESSAGE);
        String entryStr  = JOptionPane.showInputDialog(null, "Hora de entrada (dd/MM/yyyy HH:mm):", "Registrar Entrada", JOptionPane.QUESTION_MESSAGE);
        if (ticketId == null || clientId == null || plate == null || entryStr == null) return;

        try {
            LocalDateTime entryTime = LocalDateTime.parse(entryStr, FORMATTER);
            boolean result = ticketController.registerEntry(ticketId, clientId, plate, entryTime);
            JOptionPane.showMessageDialog(null,
                result ? "Entrada registrada correctamente." : "No se pudo registrar. Verifique que el cliente y el vehículo existan.",
                "Registrar Entrada", result ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Formato de fecha inválido. Use dd/MM/yyyy HH:mm",
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    private void registerExit() {
        String ticketId = JOptionPane.showInputDialog(null, "ID del ticket:",  "Registrar Salida", JOptionPane.QUESTION_MESSAGE);
        String exitStr = JOptionPane.showInputDialog(null, "Hora de salida (dd/MM/yyyy HH:mm):", "Registrar Salida", JOptionPane.QUESTION_MESSAGE);
        String rateStr = JOptionPane.showInputDialog(null, "Tarifa por hora ($):", "Registrar Salida", JOptionPane.QUESTION_MESSAGE);
        if (ticketId == null || exitStr == null || rateStr == null) 
        
        	return;

        try {
            LocalDateTime exitTime = LocalDateTime.parse(exitStr, FORMATTER);
            double ratePerHour     = Double.parseDouble(rateStr);
            boolean result = ticketController.registerExit(ticketId, exitTime, ratePerHour);

            if (result) {
                Ticket ticket = ticketController.findTicketById(ticketId);
                JOptionPane.showMessageDialog(null,
                    "Salida registrada.\n\n" + ticket.toString(),
                    "Registrar Salida", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Ticket no encontrado.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Datos inválidos. Verifique el formato.",
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    private void findAllTickets() {
        List<Ticket> tickets = ticketController.findAllTickets();
        if (tickets.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay tickets registrados.", "Consultar Tickets", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        StringBuilder sb = new StringBuilder("── TICKETS REGISTRADOS ──\n\n");
        tickets.forEach(t -> sb.append(t).append("\n"));
        JOptionPane.showMessageDialog(null, sb.toString(), "Consultar Tickets", JOptionPane.INFORMATION_MESSAGE);
    }
    private void findTicketById() {
        String id = JOptionPane.showInputDialog(null, "ID del ticket a buscar:", "Buscar Ticket", JOptionPane.QUESTION_MESSAGE);
        if (id == null) return;
        Ticket ticket = ticketController.findTicketById(id);
        JOptionPane.showMessageDialog(null,
            ticket != null ? ticket.toString() : "Ticket no encontrado.",
            "Buscar Ticket", ticket != null ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.ERROR_MESSAGE);
    }

    private void deleteTicket() {
        String id = JOptionPane.showInputDialog(null, "ID del ticket a eliminar:", "Eliminar Ticket", JOptionPane.QUESTION_MESSAGE);
        if (id == null) return;
        boolean result = ticketController.deleteTicket(id);
        JOptionPane.showMessageDialog(null,
            result ? "Ticket eliminado correctamente." : "Ticket no encontrado.",
            "Eliminar Ticket", result ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.ERROR_MESSAGE);
    }
}