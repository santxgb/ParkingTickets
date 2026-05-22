package co.edu.uptc.parking.ui.view;

import java.util.List;
import javax.swing.JOptionPane;
import co.edu.uptc.parking.domain.Client;
import co.edu.uptc.parking.ui.controller.ClientController;

public class ClientView {

    private ClientController clientController;

    public ClientView() {
        this.clientController = new ClientController();
    }

    public void showMenu() {
        boolean running = true;
        while (running) {
            int op = Integer.parseInt(JOptionPane.showInputDialog(null,
                "[1] Crear Cliente"           +
                "[2] Consultar todos"         +
                "[3] Consultar por ID"        +
                "[4] Actualizar Cliente"      +
                "[5] Eliminar Cliente"        +
                "[6] Volver",
                "── GESTIÓN DE CLIENTES ──",
                JOptionPane.INFORMATION_MESSAGE));

            switch (op) {
                case 1 -> createClient();
                case 2 -> findAllClients();
                case 3 -> findClientById();
                case 4 -> updateClient();
                case 5 -> deleteClient();
                case 6 -> running = false;
                default -> JOptionPane.showMessageDialog(null, "Opción inválida.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    private void createClient() {
        String clientId = JOptionPane.showInputDialog(null, "ID del cliente:", "Crear Cliente", JOptionPane.QUESTION_MESSAGE);
        String name = JOptionPane.showInputDialog(null, "Nombre:",  "Crear Cliente", JOptionPane.QUESTION_MESSAGE);
        String lastName = JOptionPane.showInputDialog(null, "Apellido:",    "Crear Cliente", JOptionPane.QUESTION_MESSAGE);
        String email = JOptionPane.showInputDialog(null, "Email:",    "Crear Cliente", JOptionPane.QUESTION_MESSAGE);
        String phone = JOptionPane.showInputDialog(null, "Teléfono:",   "Crear Cliente", JOptionPane.QUESTION_MESSAGE);
        if (clientId == null || name == null || lastName == null || email == null || phone == null) return;

        boolean result = clientController.addClient(clientId, name, lastName, email, phone);
        JOptionPane.showMessageDialog(null,
            result ? "Cliente registrado correctamente." : "Ya existe un cliente con ese ID.",
            "Crear Cliente", result ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.ERROR_MESSAGE);
    }

    private void findAllClients() {
        List<Client> clients = clientController.findAllClients();
        if (clients.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay clientes registrados.", "Consultar Clientes", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        StringBuilder sb = new StringBuilder("── CLIENTES REGISTRADOS ──\n\n");
        clients.forEach(c -> sb.append(c).append("\n"));
        JOptionPane.showMessageDialog(null, sb.toString(), "Consultar Clientes", JOptionPane.INFORMATION_MESSAGE);
    }

    private void findClientById() {
        String id = JOptionPane.showInputDialog(null, "ID del cliente a buscar:", "Buscar Cliente", JOptionPane.QUESTION_MESSAGE);
        if (id == null) return;
        Client client = clientController.findClientById(id);
        JOptionPane.showMessageDialog(null,
            client != null ? client.toString() : "Cliente no encontrado.",
            "Buscar Cliente", client != null ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.ERROR_MESSAGE);
    }
    private void updateClient() {
        String clientId = JOptionPane.showInputDialog(null, "ID del cliente a actualizar:", "Actualizar Cliente", JOptionPane.QUESTION_MESSAGE);
        if (clientId == null || !clientController.existsClient(clientId)) {
            JOptionPane.showMessageDialog(null, "Cliente no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
      }
        String name = JOptionPane.showInputDialog(null, "Nuevo nombre:",    "Actualizar Cliente", JOptionPane.QUESTION_MESSAGE);
        String lastName = JOptionPane.showInputDialog(null, "Nuevo apellido:",  "Actualizar Cliente", JOptionPane.QUESTION_MESSAGE);
        String email = JOptionPane.showInputDialog(null, "Nuevo email:",     "Actualizar Cliente", JOptionPane.QUESTION_MESSAGE);
        String phone = JOptionPane.showInputDialog(null, "Nuevo teléfono:", "Actualizar Cliente", JOptionPane.QUESTION_MESSAGE);
        if (name == null || lastName == null || email == null || phone == null) return;

        boolean result = clientController.updateClient(clientId, name, lastName, email, phone);
        JOptionPane.showMessageDialog(null,
            result ? "Cliente actualizado correctamente." : "No se pudo actualizar.",
            "Actualizar Cliente", result ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.ERROR_MESSAGE);
    }
    private void deleteClient() {
        String id = JOptionPane.showInputDialog(null, "ID del cliente a eliminar:", "Eliminar Cliente", JOptionPane.QUESTION_MESSAGE);
        if (id == null) return;
        boolean result = clientController.deleteClient(id);
        JOptionPane.showMessageDialog(null,
            result ? "Cliente eliminado correctamente." : "Cliente no encontrado.",
            "Eliminar Cliente", result ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.ERROR_MESSAGE);
    }
}