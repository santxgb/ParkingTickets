package co.edu.uptc.parking.enums;

public enum TypeVehicleEnum {
	CAR(0.0),
	MOTORCYCLE(0.10),
	TRUCK(0.20),
	BUS(0.30);
	
	private double surcharge;

	/**
	 * Crea una nueva instancia de TypeVehicleEnum.
	 *
	 * @param surcharge Parámetro que determina
	 */
	private TypeVehicleEnum(double surcharge) {
		this.surcharge = surcharge;
	}

	/**
	 * Método encargado de retornar el valor de surcharge.
	 *
	 * @return valor de surcharge
	 */
	public double getSurcharge() {
		return surcharge;
	}

	/**
	 * Método encargado de establecer el valor de surcharge.
	 *
	 * @param surcharge nuevo valor de surcharge
	 */
	public void setSurcharge(double surcharge) {
		this.surcharge = surcharge;
	}
	
	
}
