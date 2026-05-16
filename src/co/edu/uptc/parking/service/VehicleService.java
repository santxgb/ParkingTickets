package co.edu.uptc.parking.service;

import java.util.List;

import co.edu.uptc.parking.domain.Vehicle;
import co.edu.uptc.parking.repository.VehicleRepository;

public class VehicleService {
	
	private VehicleRepository vehicleRepository;

	/**
	 * Crea una nueva instancia de VehicleService.
	 *
	 * @param vehicleRepository Parámetro que determina
	 */
	public VehicleService() {
		super();
		this.vehicleRepository = new VehicleRepository();
	}
	
	public boolean addVehicle(Vehicle vehicle) {
		if(vehicleRepository.existsByLicensePlate(vehicle.getLicensePlate())) {
			return false;
		}
		vehicleRepository.add(vehicle);
		return true;
	}
	
	public Vehicle findByLicensePlate(String licensePlate) {
		return vehicleRepository.findByLicensePlate(licensePlate);
	}
	
	public List<Vehicle> findAll(){
		return vehicleRepository.findAll();
	}
	
	public boolean updateVehicle(Vehicle vehicle) {
		if(!vehicleRepository.existsByLicensePlate(vehicle.getLicensePlate())) {
			return false;
		}
		vehicleRepository.add(vehicle);
		return true;
	}
	
	public boolean deleteVehicle(String licensePlate) {
		return vehicleRepository.deleteByLicensePlate(licensePlate);
	}
	
	public boolean existsByLicensePlate(String licensePlate) {
		return vehicleRepository.existsByLicensePlate(licensePlate);
	}
}
