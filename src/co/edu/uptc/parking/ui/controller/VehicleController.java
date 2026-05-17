package co.edu.uptc.parking.ui.controller;

import java.util.List;

import co.edu.uptc.parking.domain.Vehicle;
import co.edu.uptc.parking.enums.TypeVehicleEnum;
import co.edu.uptc.parking.service.VehicleService;

public class VehicleController {
	private VehicleService vehicleService;

	/**
	 * Crea una nueva instancia de VehicleController.
	 *
	 * @param vehicleService Parámetro que determina
	 */
	public VehicleController() {
		super();
		this.vehicleService = new VehicleService();
	}
	
	public boolean addVehicle(String licensePlate, String brand, String model, String color, TypeVehicleEnum typeVehicle) {
		Vehicle vehicle = new Vehicle(licensePlate, brand, model, color, typeVehicle);
		return vehicleService.addVehicle(vehicle);
	}
	
	public Vehicle findVehicleByLicensePlate(String licensePlate){
		return vehicleService.findByLicensePlate(licensePlate);
	}
	public List<Vehicle> findAllVehicles(){
		return vehicleService.findAll();
	}
	public boolean updateVehicle(String licensePlate, String brand, String model, String color, TypeVehicleEnum typeVehicle) {
		Vehicle vehicle = new Vehicle(licensePlate, brand, model, color, typeVehicle);
		return vehicleService.updateVehicle(vehicle);
	}
	
	public boolean deleteVehicle(String licensePlate) {
		return vehicleService.deleteVehicle(licensePlate);
	}
	
	public boolean existsVehicle(String licensePlate) {
		return vehicleService.existsByLicensePlate(licensePlate);
	}
}
