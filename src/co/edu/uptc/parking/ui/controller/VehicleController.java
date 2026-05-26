package co.edu.uptc.parking.ui.controller;

import java.util.List;
import co.edu.uptc.parking.domain.Vehicle;
import co.edu.uptc.parking.dto.ResultDTO;
import co.edu.uptc.parking.enums.TypeVehicleEnum;
import co.edu.uptc.parking.service.VehicleService;

public class VehicleController {

    private VehicleService vehicleService;

    public VehicleController() {
        this.vehicleService = new VehicleService();
    }

    public ResultDTO addVehicle(String licensePlate, String brand, String model, String color, TypeVehicleEnum typeVehicle) {
        ResultDTO result = validateRequiredFields(licensePlate, brand, model, color);
        if (!result.isSuccessful()) return result;

        validateAlphanumericField("ValidationLicensePlate", licensePlate, "^[A-Za-z0-9]{5,7}$", 
        		result);
        validateAlphanumericField("ValidationBrand", brand, "^[a-zA-ZÁÉÍÓÚáéíóúÑñ0-9 ]+$", 
        		result);
        validateAlphanumericField("ValidationModel", model, "^[a-zA-Z0-9 ]+$", 
        		result);
        validateAlphanumericField("ValidationColor", color, "^[a-zA-ZÁÉÍÓÚáéíóúÑñ ]+$", 
        		result);
        if (!result.isSuccessful()) 
        	return result;

        boolean added = vehicleService.addVehicle(new Vehicle(licensePlate, brand, model, color, typeVehicle));
        if (!added) {
            result.setSuccessful(false);
            result.getListMessageError().add("Ya existe un vehículo con la placa: " + licensePlate);
        }
        return result;
    }

    public ResultDTO findVehicleByLicensePlate(String licensePlate) {
        ResultDTO result = new ResultDTO();
        result.setSuccessful(true);
        if (licensePlate == null || licensePlate.trim().isEmpty()) {
            result.setSuccessful(false);
            result.getListMessageError().add("La placa no puede estar vacía.");
            return result;
        		}
        Vehicle vehicle = vehicleService.findByLicensePlate(licensePlate);
        if (vehicle == null) {
            result.setSuccessful(false);
            result.getListMessageError().add("No se encontró el vehículo con placa: " + licensePlate);
        		} else {
        			result.setVehicle(vehicle);
        		}
        	return result;
    }

    public List<Vehicle> findAllVehicles() {
        return vehicleService.findAll();
    }

 // DESPUÉS
    public ResultDTO updateVehicle(String licensePlate, String brand, String model, String color, TypeVehicleEnum typeVehicle) {
        ResultDTO result = new ResultDTO();
        result.setSuccessful(true);

        Vehicle existing = vehicleService.findByLicensePlate(licensePlate);
        if (existing == null) {
            result.setSuccessful(false);
            result.getListMessageError().add("No se encontró el vehículo con placa: " + licensePlate);
            return result;
        }

        if (brand == null || brand.trim().isEmpty()) brand = existing.getBrand();
        if (model == null || model.trim().isEmpty()) model = existing.getModel();
        if (color == null || color.trim().isEmpty()) color = existing.getColor();
        if (typeVehicle == null)                     typeVehicle = existing.getTypeVehicle();

        validateAlphanumericField("ValidationBrand", brand, "^[a-zA-ZÁÉÍÓÚáéíóúÑñ0-9 ]+$", result);
        validateAlphanumericField("ValidationModel", model, "^[a-zA-Z0-9 ]+$", result);
        validateAlphanumericField("ValidationColor", color, "^[a-zA-ZÁÉÍÓÚáéíóúÑñ ]+$", result);

        if (!result.isSuccessful()) return result;

        boolean updated = vehicleService.updateVehicle(
                new Vehicle(licensePlate, brand, model, color, typeVehicle));
        if (!updated) {
            result.setSuccessful(false);
            result.getListMessageError().add("No se encontró el vehículo con placa: " + licensePlate);
        } else {
            result.setMessage("Vehículo actualizado correctamente.");
        }
        return result;
    }

    public ResultDTO deleteVehicle(String licensePlate) {
        ResultDTO result = new ResultDTO();
        result.setSuccessful(true);
        if (licensePlate == null || licensePlate.trim().isEmpty()) {
            result.setSuccessful(false);
            result.getListMessageError().add("La placa no puede estar vacía.");
            	return result;
        	}
        boolean deleted = vehicleService.deleteVehicle(licensePlate);
        if (!deleted) {
            result.setSuccessful(false);
            result.getListMessageError().add("No se encontró el vehículo con placa: " + licensePlate);
        } else {
            result.setMessage("Vehículo eliminado correctamente.");
        	}
        	return result;
    }

    public boolean existsVehicle(String licensePlate) {
        return vehicleService.existsByLicensePlate(licensePlate);
    }

    private ResultDTO validateRequiredFields(String plate, String brand, String model, String color) {
        ResultDTO result = new ResultDTO();
        result.setSuccessful(true);
        if (plate == null || plate.trim().isEmpty()) { 
        	result.setSuccessful(false); result.getListMessageError().add("La placa no puede ser nulo ni vacía."); }
        if (brand == null || brand.trim().isEmpty()) { 
        	result.setSuccessful(false); result.getListMessageError().add("La marca no puede ser nulo ni vacía."); }
        if (model == null || model.trim().isEmpty()) { 
        	result.setSuccessful(false); result.getListMessageError().add("El modelo no puede ser nulo ni vacío."); }
        if (color == null || color.trim().isEmpty()) { 
        	result.setSuccessful(false); result.getListMessageError().add("El color no puede ser nulo ni vacío."); }
        return result;
    }

    private void validateAlphanumericField(String fieldName, String value, String pattern, ResultDTO result) {
        if (!value.matches(pattern)) {
            result.setSuccessful(false);
            result.getListMessageError().add("Falló la validación: " + fieldName);
        }
    }
}
