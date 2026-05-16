package co.edu.uptc.parking.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import co.edu.uptc.parking.domain.Vehicle;

public class VehicleRepository {
	
	private Map<String, Vehicle> vehicles;

	/**
	 * Crea una nueva instancia de VehicleRepository.
	 *
	 * @param vehicles Parámetro que determina
	 */
	public VehicleRepository() {
		super();
		this.vehicles = new TreeMap<>();
	}
	
	public void add(Vehicle vehicle) {
		vehicles.put(vehicle.getLicensePlate(), vehicle);
	}
	
	public Vehicle findByLicensePlate(String licensePlate) {
		return vehicles.get(licensePlate);
	}
	
	public List<Vehicle> findAll(){
		return new ArrayList<>(vehicles.values());
	}
	
	public boolean deleteByLicensePlate(String licensePlate) {
		return vehicles.remove(licensePlate) != null;
	}
	
	public boolean existsByLicensePlate(String licensePlate) {
		return vehicles.containsKey(licensePlate);
	}
	
}
