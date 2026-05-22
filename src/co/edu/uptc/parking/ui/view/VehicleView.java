package co.edu.uptc.parking.ui.view;

import java.util.List;
import javax.swing.JOptionPane;

import co.edu.uptc.parking.domain.Vehicle;
import co.edu.uptc.parking.enums.TypeVehicleEnum;
import co.edu.uptc.parking.ui.controller.VehicleController;

public class VehicleView {

    private VehicleController vehicleController;

    public VehicleView() {
        this.vehicleController = new VehicleController();
    }

    public void showMenu() {
        boolean running = true;
        while (running) {
            int op = Integer.parseInt(JOptionPane.showInputDialog(null,
                "[1] Crear Vehículo\n"          +
                "[2] Consultar todos\n"         +
                "[3] Consultar por placa\n"     +
                "[4] Actualizar Vehículo\n"     +
                "[5] Eliminar Vehículo\n"       +
                "[6] Volver",
                "── GESTIÓN DE VEHÍCULOS ──",
                JOptionPane.INFORMATION_MESSAGE));

            switch (op) {
                case 1 -> createVehicle();
                case 2 -> findAllVehicles();
                case 3 -> findVehicleByPlate();
                case 4 -> updateVehicle();
                case 5 -> deleteVehicle();
                case 6 -> running = false;
                default -> JOptionPane.showMessageDialog(null, "Opción inválida.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void createVehicle() {
        String plate = JOptionPane.showInputDialog(null, "Placa:",  "Crear Vehículo", JOptionPane.QUESTION_MESSAGE);
        String brand = JOptionPane.showInputDialog(null, "Marca:",  "Crear Vehículo", JOptionPane.QUESTION_MESSAGE);
        String model = JOptionPane.showInputDialog(null, "Modelo:", "Crear Vehículo", JOptionPane.QUESTION_MESSAGE);
        String color = JOptionPane.showInputDialog(null, "Color:",  "Crear Vehículo", JOptionPane.QUESTION_MESSAGE);
        if (plate == null || brand == null || model == null || color == null) return;

        TypeVehicleEnum type = selectVehicleType();
        if (type == null) return;

        boolean result = vehicleController.addVehicle(plate, brand, model, color, type);
        JOptionPane.showMessageDialog(null,
            result ? "Vehículo registrado correctamente." : "Ya existe un vehículo con esa placa.",
            "Crear Vehículo", result ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.ERROR_MESSAGE);
    }
    private void findAllVehicles() {
        List<Vehicle> vehicles = vehicleController.findAllVehicles();
        if (vehicles.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay vehículos registrados.", "Consultar Vehículos", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        StringBuilder sb = new StringBuilder("── VEHÍCULOS REGISTRADOS ──\n\n");
        vehicles.forEach(v -> sb.append(v).append("\n"));
        JOptionPane.showMessageDialog(null, sb.toString(), "Consultar Vehículos", JOptionPane.INFORMATION_MESSAGE);
    }
    private void findVehicleByPlate() {
        String plate = JOptionPane.showInputDialog(null, "Placa a buscar:", "Buscar Vehículo", JOptionPane.QUESTION_MESSAGE);
        if (plate == null) return;
        Vehicle vehicle = vehicleController.findVehicleByLicensePlate(plate);
        JOptionPane.showMessageDialog(null,
            vehicle != null ? vehicle.toString() : "Vehículo no encontrado.",
            "Buscar Vehículo", vehicle != null ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.ERROR_MESSAGE);
    }
    private void updateVehicle() {
        String plate = JOptionPane.showInputDialog(null, "Placa del vehículo a actualizar:", "Actualizar Vehículo", JOptionPane.QUESTION_MESSAGE);
        if (plate == null || !vehicleController.existsVehicle(plate)) {
            JOptionPane.showMessageDialog(null, "Vehículo no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String brand = JOptionPane.showInputDialog(null, "Nueva marca:",  "Actualizar Vehículo", JOptionPane.QUESTION_MESSAGE);
        String model = JOptionPane.showInputDialog(null, "Nuevo modelo:", "Actualizar Vehículo", JOptionPane.QUESTION_MESSAGE);
        String color = JOptionPane.showInputDialog(null, "Nuevo color:",  "Actualizar Vehículo", JOptionPane.QUESTION_MESSAGE);
        if (brand == null || model == null || color == null) return;

        TypeVehicleEnum type = selectVehicleType();
        if (type == null) return;

        boolean result = vehicleController.updateVehicle(plate, brand, model, color, type);
        JOptionPane.showMessageDialog(null,
            result ? "Vehículo actualizado correctamente." : "No se pudo actualizar.",
            "Actualizar Vehículo", result ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.ERROR_MESSAGE);
    }

    private void deleteVehicle() {
        String plate = JOptionPane.showInputDialog(null, "Placa del vehículo a eliminar:", "Eliminar Vehículo", JOptionPane.QUESTION_MESSAGE);
        if (plate == null) return;
        boolean result = vehicleController.deleteVehicle(plate);
        JOptionPane.showMessageDialog(null,
            result ? "Vehículo eliminado correctamente." : "Vehículo no encontrado.",
            "Eliminar Vehículo", result ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.ERROR_MESSAGE);
    }
    private TypeVehicleEnum selectVehicleType() {
        TypeVehicleEnum[] types = TypeVehicleEnum.values();
        String[] typeNames = new String[types.length];
        for (int i = 0; i < types.length; i++) {
            typeNames[i] = types[i].name() + " (recargo " + (types[i].getSurcharge() * 100) + "%)";
        }
        int idx = JOptionPane.showOptionDialog(null, "Tipo de vehículo:",
            "Tipo de Vehículo", JOptionPane.DEFAULT_OPTION,
            JOptionPane.QUESTION_MESSAGE, null, typeNames, typeNames[0]);
        return idx >= 0 ? types[idx] : null;
    }
}
