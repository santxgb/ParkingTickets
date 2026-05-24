package co.edu.uptc.parking.ui.run;

import javax.swing.JOptionPane;
import co.edu.uptc.parking.ui.view.ClientView;
import co.edu.uptc.parking.ui.view.TicketView;
import co.edu.uptc.parking.ui.view.VehicleView;

public class Main {
    public static void main(String[] args) {
        ClientView  clientView  = new ClientView();
        VehicleView vehicleView = new VehicleView();
        TicketView  ticketView  = new TicketView();

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
