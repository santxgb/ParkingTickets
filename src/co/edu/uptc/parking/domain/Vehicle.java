package co.edu.uptc.parking.domain;

import java.util.Objects;

import co.edu.uptc.parking.enums.TypeVehicleEnum;

public class Vehicle {
	private String licensePlate;
	private String brand;
	private String model;
	private String color;
	private TypeVehicleEnum typeVehicle;
	/**
	 * Crea una nueva instancia de Vehicle.
	 * Parámetro que determina
	 */
	public Vehicle() {
		super();
	}
	/**
	 * Crea una nueva instancia de Vehicle.
	 *
	 * @param licensePlate
	 * @param brand
	 * @param model
	 * @param color
	 * @param typeVehicle Parámetro que determina
	 */
	public Vehicle(String licensePlate, String brand, String model, String color, TypeVehicleEnum typeVehicle) {
		super();
		this.licensePlate = licensePlate;
		this.brand = brand;
		this.model = model;
		this.color = color;
		this.typeVehicle = typeVehicle;
	}
	/**
	 * Método encargado de retornar el valor de licensePlate.
	 *
	 * @return valor de licensePlate
	 */
	public String getLicensePlate() {
		return licensePlate;
	}
	/**
	 * Método encargado de establecer el valor de licensePlate.
	 *
	 * @param licensePlate nuevo valor de licensePlate
	 */
	public void setLicensePlate(String licensePlate) {
		this.licensePlate = licensePlate;
	}
	/**
	 * Método encargado de retornar el valor de brand.
	 *
	 * @return valor de brand
	 */
	public String getBrand() {
		return brand;
	}
	/**
	 * Método encargado de establecer el valor de brand.
	 *
	 * @param brand nuevo valor de brand
	 */
	public void setBrand(String brand) {
		this.brand = brand;
	}
	/**
	 * Método encargado de retornar el valor de model.
	 *
	 * @return valor de model
	 */
	public String getModel() {
		return model;
	}
	/**
	 * Método encargado de establecer el valor de model.
	 *
	 * @param model nuevo valor de model
	 */
	public void setModel(String model) {
		this.model = model;
	}
	/**
	 * Método encargado de retornar el valor de color.
	 *
	 * @return valor de color
	 */
	public String getColor() {
		return color;
	}
	/**
	 * Método encargado de establecer el valor de color.
	 *
	 * @param color nuevo valor de color
	 */
	public void setColor(String color) {
		this.color = color;
	}
	/**
	 * Método encargado de retornar el valor de typeVehicle.
	 *
	 * @return valor de typeVehicle
	 */
	public TypeVehicleEnum getTypeVehicle() {
		return typeVehicle;
	}
	/**
	 * Método encargado de establecer el valor de typeVehicle.
	 *
	 * @param typeVehicle nuevo valor de typeVehicle
	 */
	public void setTypeVehicle(TypeVehicleEnum typeVehicle) {
		this.typeVehicle = typeVehicle;
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		return Objects.hash(licensePlate);
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Vehicle other = (Vehicle) obj;
		return Objects.equals(licensePlate, other.licensePlate);
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return "Vehicle [licensePlate=" + licensePlate + ", brand=" + brand + ", model=" + model + ", color=" + color
				+ ", typeVehicle=" + typeVehicle + "]";
	}
	
	
}
