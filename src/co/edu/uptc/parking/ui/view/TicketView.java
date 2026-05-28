package co.edu.uptc.parking.ui.view;

import java.util.List;
import javax.swing.JOptionPane;
import co.edu.uptc.parking.domain.Ticket;
import co.edu.uptc.parking.dto.ResultDTO;
import co.edu.uptc.parking.ui.controller.TicketController;

public class TicketView {

    private TicketController ticketController;
   

    public TicketView(TicketController ticketController) {
        this.ticketController = ticketController;
    }

    public void showMenu() {
        boolean running = true;
        while (running) {
            int op = Integer.parseInt(JOptionPane.showInputDialog(null,
            	  "[1] Registrar entrada\n"
                + "[2] Registrar salida\n"
                + "[3] Consultar todos\n"
                + "[4] Consultar por ID\n"
                + "[5] Eliminar\n"
                + "[6] Volver",
                "── TICKETS ──", JOptionPane.INFORMATION_MESSAGE));
            switch (op) {
                case 1 -> registerEntry();
                case 2 -> registerExit();
                case 3 -> findAllTickets();
                case 4 -> findTicketById();
                case 5 -> deleteTicket();
                case 6 -> running = false;
            }
        }
    }

    private void registerEntry() {
        String ticketId = JOptionPane.showInputDialog("ID del ticket:");
        String clientId = JOptionPane.showInputDialog("ID del cliente:");
        String plate = JOptionPane.showInputDialog("Placa del vehículo:");
        String entry = JOptionPane.showInputDialog("Hora de entrada (dd/MM/yyyy HH:mm):");

        ResultDTO result = ticketController.registerEntry(ticketId, clientId, plate, entry);
        if (!result.isSuccessful()) {
        	showErrors("No se pudo registrar la entrada:", result);
        	return;
        	}
        JOptionPane.showMessageDialog(null, result.getMessage());
    }

    private void registerExit() {
        String ticketId = JOptionPane.showInputDialog("ID del ticket:");
        String exit = JOptionPane.showInputDialog("Hora de salida (dd/MM/yyyy HH:mm):");
        String rate = JOptionPane.showInputDialog("Tarifa por hora ($):");

        ResultDTO result = ticketController.registerExit(ticketId, exit, rate);
        if (!result.isSuccessful()) { 
        	showErrors("No se pudo registrar la salida:", result);
        	return;
        	}
        JOptionPane.showMessageDialog(null, result.getMessage() + "\n\n" + result.getTicket().toString());
    }

    private void findAllTickets() {
        List<Ticket> tickets = ticketController.findAllTickets();
        if (tickets.isEmpty()) { 
        	JOptionPane.showMessageDialog(null, "No hay tickets registrados."); 
        	return;
        	}
        StringBuilder sb = new StringBuilder("── TICKETS ──\n\n");
        tickets.forEach(t -> sb.append(t).append("\n"));
        JOptionPane.showMessageDialog(null, sb.toString());
    }

    private void findTicketById() {
        String id = JOptionPane.showInputDialog("ID del ticket:");
        ResultDTO result = ticketController.findTicketById(id);
        if (!result.isSuccessful()) { 
        	showErrors("Búsqueda fallida:", result); 
        	return; 
        }
        JOptionPane.showMessageDialog(null, result.getTicket().toString());
    }

    private void deleteTicket() {
        String id = JOptionPane.showInputDialog("ID del ticket a eliminar:");
        ResultDTO result = ticketController.deleteTicket(id);
        if (!result.isSuccessful()) { 
        	showErrors("No se pudo eliminar:", result); 
        	return;
        }
        JOptionPane.showMessageDialog(null, result.getMessage());
    }

    private void showErrors(String title, ResultDTO result) {
        StringBuilder sb = new StringBuilder(title).append("\n\n");
        result.getListMessageError().forEach(e -> sb.append("• ").append(e).append("\n"));
        JOptionPane.showMessageDialog(null, sb.toString(), "Error de validación", JOptionPane.ERROR_MESSAGE);
    }
}