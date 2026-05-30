package co.edu.uptc.parking.test;

import co.edu.uptc.parking.domain.Client;
import co.edu.uptc.parking.domain.Ticket;
import co.edu.uptc.parking.domain.Vehicle;
import co.edu.uptc.parking.enums.TypeVehicleEnum;
import co.edu.uptc.parking.service.ClientService;
import co.edu.uptc.parking.service.TicketService;
import co.edu.uptc.parking.service.VehicleService;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ParkingTest {

    private ClientService  clientService;
    private VehicleService vehicleService;
    private TicketService  ticketService;

    /* ── Datos de prueba reutilizados en todos los tests ── */
    private Client  client1;
    private Client  client2;
    private Vehicle vehicle1;
    private Vehicle vehicle2;
    private Ticket  ticket1;

    /**
     * Se ejecuta antes de CADA test.
     * Crea instancias nuevas para que los tests no se afecten entre sí.
     */
    @BeforeEach
    void setUp() {
        clientService  = new ClientService();
        vehicleService = new VehicleService();
        ticketService  = new TicketService();

        client1  = new Client("C001", "Juan", "Hernandez", "3001234567", "juanh@mail.com",  "Calle 2b");
        client2  = new Client("C002", "Maria", "Marzal", "3109876543", "mariam19@mail.com", "Calle 27");
        vehicle1 = new Vehicle("ABC123", "Toyota", "Corolla AE86", "Blanco",  TypeVehicleEnum.CAR);
        vehicle2 = new Vehicle("XYZ789", "Honda", "CBR", "Negro", TypeVehicleEnum.MOTORCYCLE);
        ticket1  = new Ticket("T001", LocalDateTime.of(2025, 5, 1, 8, 0),
                null, 0.0, vehicle1, client1);
    }

    /* TESTS DE CLIENT SERVICE */

    /** Verificar que se puede registrar un cliente correctamente */
    @Test
    void testAddClientSuccess() {
        boolean result = clientService.addClient(client1);
        assertTrue(result, "Debe retornar true al agregar un cliente nuevo");
    }

    /** Verificar que no se puede registrar un cliente con ID duplicado */
    @Test
    void testAddClientDuplicate() {
        clientService.addClient(client1);
        boolean result = clientService.addClient(client1);
        assertFalse(result, "Debe retornar false al agregar un cliente con ID duplicado");
    }

    /** Verificar que se puede buscar un cliente por ID */
    @Test
    void testFindClientByIdFound() {
        clientService.addClient(client1);
        Client found = clientService.findById("C001");
        assertNotNull(found, "Debe encontrar el cliente con ID C001");
        assertEquals("Juan", found.getName(), "El nombre debe ser Juan");
    }

    /** Verificar que retorna null cuando el cliente no existe */
    @Test
    void testFindClientByIdNotFound() {
        Client found = clientService.findById("NO_EXISTE");
        assertNull(found, "Debe retornar null cuando el cliente no existe");
    }

    /** Verificar que se listan todos los clientes registrados */
    @Test
    void testFindAllClients() {
        clientService.addClient(client1);
        clientService.addClient(client2);
        assertEquals(2, clientService.findAll().size(), "Debe haber 2 clientes registrados");
    }

    /** Verificar que se puede actualizar un cliente existente */
    @Test
    void testUpdateClientSuccess() {
        clientService.addClient(client1);
        Client updated = new Client("C001", "JuanActualizado", "Perez", "3001234567", "juan@mail.com", "Calle 1");
        boolean result = clientService.updateClient(updated);
        assertTrue(result, "Debe retornar true al actualizar un cliente existente");
        assertEquals("JuanActualizado", clientService.findById("C001").getName());
    }

    /** Verificar que no se puede actualizar un cliente que no existe */
    @Test
    void testUpdateClientNotFound() {
        boolean result = clientService.updateClient(client1);
        assertFalse(result, "Debe retornar false al actualizar un cliente inexistente");
    }

    /** Verificar que se puede eliminar un cliente existente */
    @Test
    void testDeleteClientSuccess() {
        clientService.addClient(client1);
        boolean result = clientService.deleteClient("C001");
        assertTrue(result, "Debe retornar true al eliminar un cliente existente");
        assertNull(clientService.findById("C001"), "El cliente no debe existir después de eliminarse");
    }

    /** Verificar que no se puede eliminar un cliente que no existe */
    @Test
    void testDeleteClientNotFound() {
        boolean result = clientService.deleteClient("NO_EXISTE");
        assertFalse(result, "Debe retornar false al eliminar un cliente inexistente");
    }

    /*TESTS DE VEHICLE SERVICE  */

    /** Verificar que se puede registrar un vehículo correctamente */
    @Test
    void testAddVehicleSuccess() {
        boolean result = vehicleService.addVehicle(vehicle1);
        assertTrue(result, "Debe retornar true al agregar un vehículo nuevo");
    }

    /** Verificar que no se puede registrar una placa duplicada */
    @Test
    void testAddVehicleDuplicatePlate() {
        vehicleService.addVehicle(vehicle1);
        boolean result = vehicleService.addVehicle(vehicle1);
        assertFalse(result, "Debe retornar false al agregar un vehículo con placa duplicada");
    }

    /** Verificar que se puede buscar un vehículo por placa */
    @Test
    void testFindVehicleByPlateFound() {
        vehicleService.addVehicle(vehicle1);
        Vehicle found = vehicleService.findByLicensePlate("ABC123");
        assertNotNull(found, "Debe encontrar el vehículo con placa ABC123");
        assertEquals("Toyota", found.getBrand(), "La marca debe ser Toyota");
    }

    /** Verificar que retorna null cuando el vehículo no existe */
    @Test
    void testFindVehicleByPlateNotFound() {
        Vehicle found = vehicleService.findByLicensePlate("NO_EXISTE");
        assertNull(found, "Debe retornar null cuando el vehículo no existe");
    }

    /** Verificar que se puede eliminar un vehículo existente */
    @Test
    void testDeleteVehicleSuccess() {
        vehicleService.addVehicle(vehicle1);
        boolean result = vehicleService.deleteVehicle("ABC123");
        assertTrue(result, "Debe retornar true al eliminar un vehículo existente");
        assertNull(vehicleService.findByLicensePlate("ABC123"));
    }

    /* TESTS DE TICKET SERVICE  */

    /** Verificar que se puede registrar un ticket correctamente */
    @Test
    void testAddTicketSuccess() {
        boolean result = ticketService.addTicket(ticket1);
        assertTrue(result, "Debe retornar true al agregar un ticket nuevo");
    }

    /** Verificar que no se puede registrar un ticket con ID duplicado */
    @Test
    void testAddTicketDuplicate() {
        ticketService.addTicket(ticket1);
        boolean result = ticketService.addTicket(ticket1);
        assertFalse(result, "Debe retornar false al agregar un ticket con ID duplicado");
    }

    /** Verificar que se puede buscar un ticket por ID */
    @Test
    void testFindTicketByIdFound() {
        ticketService.addTicket(ticket1);
        Ticket found = ticketService.findById("T001");
        assertNotNull(found, "Debe encontrar el ticket con ID T001");
    }

    /** Verificar que un ticket recién creado no tiene salida registrada */
    @Test
    void testTicketHasNoExitOnCreation() {
        ticketService.addTicket(ticket1);
        assertFalse(ticketService.hasExitRegistered("T001"),
            "Un ticket recién creado no debe tener salida registrada");
    }

    /** Verificar que detecta correctamente un ticket abierto para un vehículo */
    @Test
    void testHasOpenTicketForVehicle() {
        ticketService.addTicket(ticket1);
        assertTrue(ticketService.hasOpenTicketForVehicle("ABC123"),
            "Debe detectar que el vehículo tiene un ticket abierto");
    }

    /** Verificar que no detecta ticket abierto cuando no hay ninguno */
    @Test
    void testNoOpenTicketForVehicle() {
        assertFalse(ticketService.hasOpenTicketForVehicle("ABC123"),
            "No debe haber ticket abierto si no se registró ninguno");
    }

    /** Verificar que el cálculo del valor total es correcto para un CAR (sin recargo) */
    @Test
    void testCalculateTotalValueCarNoSurcharge() {
        // Entrada: 08:00 — Salida: 10:00 → 2 horas
        // Tarifa: $5000/hora — CAR: 0% recargo → Total: $10.000
        ticket1.setExitTime(LocalDateTime.of(2025, 5, 1, 10, 0));
        double total = ticketService.calcularValorTotal(ticket1, 5000.0);
        assertEquals(10000.0, total, 0.01, "El total debe ser $10.000 para 2 horas a $5.000 sin recargo");
    }

    /** Verificar que el cálculo incluye el recargo de MOTORCYCLE correctamente */
    @Test
    void testCalculateTotalValueMotorcycleWithSurcharge() {
        // Entrada: 08:00 — Salida: 10:00 → 2 horas
        // Tarifa: $5000/hora — MOTORCYCLE: 10% recargo → Total: $11.000
        Ticket motoTicket = new Ticket("T002",
                LocalDateTime.of(2025, 5, 1, 8, 0),
                LocalDateTime.of(2025, 5, 1, 10, 0),
                0.0, vehicle2, client1);
        double total = ticketService.calcularValorTotal(motoTicket, 5000.0);
        assertEquals(11000.0, total, 0.01, "El total debe ser $11.000 para 2 horas a $5.000 con 10% de recargo");
    }

    /** Verificar que se puede eliminar un ticket */
    @Test
    void testDeleteTicketSuccess() {
        ticketService.addTicket(ticket1);
        boolean result = ticketService.deleteTicket("T001");
        assertTrue(result, "Debe retornar true al eliminar un ticket existente");
        assertNull(ticketService.findById("T001"), "El ticket no debe existir después de eliminarse");
    }

    /** Verificar que existsById retorna true para un ticket registrado */
    @Test
    void testExistsById() {
        ticketService.addTicket(ticket1);
        assertTrue(ticketService.existsById("T001"), "Debe retornar true para un ticket existente");
        assertFalse(ticketService.existsById("NO_EXISTE"), "Debe retornar false para un ticket inexistente");
    }
}