package co.edu.uptc.parking.ui.view;

import java.util.List;
import javax.swing.JOptionPane;
import co.edu.uptc.parking.domain.Vehicle;
import co.edu.uptc.parking.dto.ResultDTO;
import co.edu.uptc.parking.enums.TypeVehicleEnum;
import co.edu.uptc.parking.ui.controller.VehicleController;

public class VehicleView {

    private VehicleController vehicleController;

    public VehicleView(VehicleController vehicleController) {
        this.vehicleController = vehicleController;
    }

    public void showMenu() {
        boolean running = true;
        while (running) {
            int op = Integer.parseInt(JOptionPane.showInputDialog(null,
                  "[1] Crear Vehículo\n"
                + "[2] Consultar todos\n"
                + "[3] Consultar por placa\n"
                + "[4] Actualizar\n"
                + "[5] Eliminar\n"
                + "[6] Volver",
                "── VEHÍCULOS ──", JOptionPane.INFORMATION_MESSAGE));
            switch (op) {
                case 1 -> createVehicle();
                case 2 -> findAllVehicles();
                case 3 -> findVehicleByPlate();
                case 4 -> updateVehicle();
                case 5 -> deleteVehicle();
                case 6 -> running = false;
            }
        }
    }

    private void createVehicle() {
        String plate = JOptionPane.showInputDialog("Placa (5-7 caracteres alfanuméricos):");
        String brand = JOptionPane.showInputDialog("Marca:");
        String model = JOptionPane.showInputDialog("Modelo:");
        String color = JOptionPane.showInputDialog("Color:");
        TypeVehicleEnum type = selectType();
        if (type == null) 
        	return;
        ResultDTO result = vehicleController.addVehicle(plate, brand, model, color, type);
        if (!result.isSuccessful()) { 
        	showErrors("No se pudo crear el vehículo:", result); 
        	return;
        	}
        JOptionPane.showMessageDialog(null, "Vehículo creado correctamente.");
    }

    private void findAllVehicles() {
        List<Vehicle> vehicles = vehicleController.findAllVehicles();
        if (vehicles.isEmpty()) { 
        	JOptionPane.showMessageDialog(null, "No hay vehículos registrados."); 
        	return;
        }
        StringBuilder sb = new StringBuilder("── VEHÍCULOS ──\n\n");
        vehicles.forEach(v -> sb.append(v).append("\n"));
        JOptionPane.showMessageDialog(null, sb.toString());
    }

    private void findVehicleByPlate() {
        String plate = JOptionPane.showInputDialog("Placa:");
        ResultDTO result = vehicleController.findVehicleByLicensePlate(plate);
        if (!result.isSuccessful()) { 
        	showErrors("Búsqueda fallida:", result); 
        	return;
        	}
        JOptionPane.showMessageDialog(null, result.getVehicle().toString());
    }

    private void updateVehicle() {
        String plate = JOptionPane.showInputDialog("Placa del vehículo a actualizar:");
        ResultDTO findResult = vehicleController.findVehicleByLicensePlate(plate);
        if (!findResult.isSuccessful()) {
        	showErrors("Vehículo no encontrado:", findResult);
        	return;
        }
        Vehicle existing = findResult.getVehicle();
        String brand = JOptionPane.showInputDialog("Marca (" + existing.getBrand() + ") Enter para conservar:");
        String model = JOptionPane.showInputDialog("Modelo (" + existing.getModel() + ") Enter para conservar:");
        String color = JOptionPane.showInputDialog("Color (" + existing.getColor() + ") Enter para conservar:");
        TypeVehicleEnum type = selectType();
        if (type == null)
        	return;
        ResultDTO result = vehicleController.updateVehicle(plate, brand, model, color, type);
        if (!result.isSuccessful()) { showErrors("No se pudo actualizar:", result); return; }
        JOptionPane.showMessageDialog(null, result.getMessage());
    }

    private void deleteVehicle() {
        String plate = JOptionPane.showInputDialog("Placa del vehículo a eliminar:");
        ResultDTO result = vehicleController.deleteVehicle(plate);
        if (!result.isSuccessful()) { 
        	showErrors("No se pudo eliminar:", result);
        	return;
        	}
        JOptionPane.showMessageDialog(null, result.getMessage());
    }

    private TypeVehicleEnum selectType() {
        TypeVehicleEnum[] types = TypeVehicleEnum.values();
        String[] names = new String[types.length];
        for (int i = 0; i < types.length; i++)
            names[i] = types[i].name() + " (recargo " + (types[i].getSurcharge() * 100) + "%)";
        int idx = JOptionPane.showOptionDialog(null, "Tipo de vehículo:", "Tipo",
            JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, names, names[0]);
        return idx >= 0 ? types[idx] : null;
    }

    private void showErrors(String title, ResultDTO result) {
        StringBuilder sb = new StringBuilder(title).append("\n\n");
        result.getListMessageError().forEach(e -> sb.append("• ").append(e).append("\n"));
        JOptionPane.showMessageDialog(null, sb.toString(), "Error de validación", JOptionPane.ERROR_MESSAGE);
    }
}