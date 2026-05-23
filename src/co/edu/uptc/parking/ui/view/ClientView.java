package co.edu.uptc.parking.ui.view;

import java.util.List;
import javax.swing.JOptionPane;
import co.edu.uptc.parking.domain.Client;
import co.edu.uptc.parking.dto.ResultDTO;
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
                  "[1] Crear Cliente\n"
                + "[2] Consultar todos\n"
                + "[3] Consultar por ID\n"
                + "[4] Actualizar\n"
                + "[5] Eliminar\n"
                + "[6] Volver",
                "── CLIENTES ──", JOptionPane.INFORMATION_MESSAGE));
            switch (op) {
                case 1 -> createClient();
                case 2 -> findAllClients();
                case 3 -> findClientById();
                case 4 -> updateClient();
                case 5 -> deleteClient();
                case 6 -> running = false;
            }
        }
    }

    private void createClient() {
        String clientId = JOptionPane.showInputDialog("ID del cliente (3-15 caracteres alfanuméricos):");
        String name = JOptionPane.showInputDialog("Nombre (solo letras):");
        String lastName = JOptionPane.showInputDialog("Apellido (solo letras):");
        String email = JOptionPane.showInputDialog("Email (formato válido):");
        String phone = JOptionPane.showInputDialog("Teléfono (7-15 dígitos):");
        ResultDTO result = clientController.addClient(clientId, name, lastName, email, phone);
        if (!result.isSuccessful()) {
            showErrors("No se pudo crear el cliente:", result);
            return;
        }
        JOptionPane.showMessageDialog(null, "Cliente creado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
    }

    private void findAllClients() {
        List<Client> clients = clientController.findAllClients();
        if (clients.isEmpty()) { 
        	JOptionPane.showMessageDialog(null, "No hay clientes registrados."); 
        	return;
        	}
        StringBuilder sb = new StringBuilder("── CLIENTES ──\n\n");
        clients.forEach(c -> sb.append(c).append("\n"));
        JOptionPane.showMessageDialog(null, sb.toString());
    }

    private void findClientById() {
        String id = JOptionPane.showInputDialog("ID del cliente:");
        ResultDTO result = clientController.findClientById(id);
        if (!result.isSuccessful()) { 
        	showErrors("Búsqueda fallida:", result); 
        return; 
        }
        
        JOptionPane.showMessageDialog(null, result.getClient().toString());
    }

    private void updateClient() {
        String id = JOptionPane.showInputDialog("ID del cliente a actualizar:");
        ResultDTO findResult = clientController.findClientById(id);
        if (!findResult.isSuccessful()) { 
        	showErrors("Cliente no encontrado:", findResult); 
        	return; 
        }
        Client existing = findResult.getClient();
        String name = JOptionPane.showInputDialog("Nombre (" + existing.getName() + ") Enter para conservar:");
        String lastName = JOptionPane.showInputDialog("Apellido (" + existing.getLastName() + ") Enter para conservar:");
        String email = JOptionPane.showInputDialog("Email (" + existing.getEmail() + ") Enter para conservar:");
        String phone = JOptionPane.showInputDialog("Teléfono (" + existing.getPhone() + ") Enter para conservar:");
        ResultDTO result = clientController.updateClient(id, name, lastName, email, phone);
        	if (!result.isSuccessful()) { 
        	showErrors("No se pudo actualizar:", result);
        	return; 
        }
        JOptionPane.showMessageDialog(null, result.getMessage());
    }

    private void deleteClient() {
        String id = JOptionPane.showInputDialog("ID del cliente a eliminar:");
        ResultDTO result = clientController.deleteClient(id);
        if (!result.isSuccessful()) { 
        	showErrors("No se pudo eliminar:", result); 
        	return;
        	}
        JOptionPane.showMessageDialog(null, result.getMessage());
    }

    /** Muestra los errores del DTO en un JOptionPane de error. */
    private void showErrors(String title, ResultDTO result) {
        StringBuilder sb = new StringBuilder(title).append("\n\n");
        result.getListMessageError().forEach(e -> sb.append("• ").append(e).append("\n"));
        JOptionPane.showMessageDialog(null, sb.toString(), "Error de validación", JOptionPane.ERROR_MESSAGE);
    }
}