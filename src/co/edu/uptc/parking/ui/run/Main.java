package co.edu.uptc.parking.ui.run;

import javax.swing.JOptionPane;
import co.edu.uptc.parking.service.ClientService;
import co.edu.uptc.parking.service.VehicleService;
import co.edu.uptc.parking.service.TicketService;
import co.edu.uptc.parking.ui.controller.ClientController;
import co.edu.uptc.parking.ui.controller.VehicleController;
import co.edu.uptc.parking.ui.controller.TicketController;
import co.edu.uptc.parking.ui.view.ClientView;
import co.edu.uptc.parking.ui.view.TicketView;
import co.edu.uptc.parking.ui.view.VehicleView;

public class Main {
    public static void main(String[] args) {
        ClientService  clientService  = new ClientService();
        VehicleService vehicleService = new VehicleService();
        TicketService  ticketService  = new TicketService();

        ClientController  clientController  = new ClientController(clientService);
        VehicleController vehicleController = new VehicleController(vehicleService);
        TicketController  ticketController  = new TicketController(ticketService, clientService, vehicleService);

        ClientView  clientView  = new ClientView(clientController);
        VehicleView vehicleView = new VehicleView(vehicleController);
        TicketView  ticketView  = new TicketView(ticketController);

        boolean running = true;
        while (running) {
            int op = Integer.parseInt(JOptionPane.showInputDialog(null,
                  "[1] Gestionar Clientes\n"
                + "[2] Gestionar Vehículos\n"
                + "[3] Gestionar Tickets\n"
                + "[4] Salir",
                "═══ SISTEMA DE PARQUEADERO ═══", JOptionPane.INFORMATION_MESSAGE));
            switch (op) {
                case 1 -> clientView.showMenu();
                case 2 -> vehicleView.showMenu();
                case 3 -> ticketView.showMenu();
                case 4 -> { running = false;
                    JOptionPane.showMessageDialog(null, "Gracias por usar el sistema :D");
                }
            }
        }
    }
}