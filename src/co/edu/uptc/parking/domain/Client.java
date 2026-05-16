package co.edu.uptc.parking.domain;

import java.util.Objects;

public class Client {
	private String clientId;
	private String name;
	private String lastName;
	private String phone;
	private String email;
	/**
	 * Crea una nueva instancia de Client.
	 * Parámetro que determina
	 */
	public Client() {
		super();
	}
	/**
	 * Crea una nueva instancia de Client.
	 *
	 * @param clientId
	 * @param name
	 * @param lastName
	 * @param email Parámetro que determina
	 */
	public Client(String clientId, String name, String lastName, String phone, String email) {
		super();
		this.clientId = clientId;
		this.name = name;
		this.lastName = lastName;
		this.phone = phone;
		this.email = email;
	}
	/**
	 * Método encargado de retornar el valor de clientId.
	 *
	 * @return valor de clientId
	 */
	public String getClientId() {
		return clientId;
	}
	/**
	 * Método encargado de establecer el valor de clientId.
	 *
	 * @param clientId nuevo valor de clientId
	 */
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	/**
	 * Método encargado de retornar el valor de name.
	 *
	 * @return valor de name
	 */
	public String getName() {
		return name;
	}
	/**
	 * Método encargado de establecer el valor de name.
	 *
	 * @param name nuevo valor de name
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * Método encargado de retornar el valor de lastName.
	 *
	 * @return valor de lastName
	 */
	public String getLastName() {
		return lastName;
	}
	/**
	 * Método encargado de establecer el valor de lastName.
	 *
	 * @param lastName nuevo valor de lastName
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	/**
	 * Método encargado de retornar el valor de email.
	 *
	 * @return valor de email
	 */
	
	public String getEmail() {
		return email;
	}
	/**
	 * Método encargado de retornar el valor de phone.
	 *
	 * @return valor de phone
	 */
	public String getPhone() {
		return phone;
	}
	/**
	 * Método encargado de establecer el valor de phone.
	 *
	 * @param phone nuevo valor de phone
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}
	/**
	 * Método encargado de establecer el valor de email.
	 *
	 * @param email nuevo valor de email
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		return Objects.hash(clientId);
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
		Client other = (Client) obj;
		return Objects.equals(clientId, other.clientId);
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return "Client [clientId=" + clientId + ", name=" + name + ", lastName=" + lastName + ", phone=" + phone
				+ ", email=" + email + "]";
	}
	
	
	
}
