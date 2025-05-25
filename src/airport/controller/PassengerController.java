/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package airport.controller;

import airport.Flight;
import airport.Location;
import airport.Passenger;
import airport.Plane;
import airport.controller.utils.Response;
import airport.controller.utils.Status;
import airport.model.storage.FlightAdapter;
import airport.model.storage.JsonRepository;
import airport.model.storage.PassengerAdapter;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author user
 */
public class PassengerController {

    private final JsonRepository<Passenger> passengerRepo;
    private final JsonRepository<Flight> flightRepo;

    public PassengerController(List<Plane> planes, List<Location> locations) {
        this.passengerRepo = new JsonRepository<>("json/passengers.json", new PassengerAdapter());
        this.flightRepo = new JsonRepository<>("json/flights.json", new FlightAdapter(planes, locations)); // igual que antes: necesitas listas
    }

    public List<Passenger> getAllPassengers() {
        return passengerRepo.getAll();
    }

    private int getAge(LocalDate birthDate) {
        return LocalDate.now().getYear() - birthDate.getYear();
    }

    public Response addPassengerToFlight(long passengerId, String flightId) {
        try {
            Passenger passenger = null;
            Flight flight = null;

            for (Passenger p : passengerRepo.getAll()) {
                if (p.getId() == passengerId) {
                    passenger = p;
                    break;
                }
            }

            for (Flight f : flightRepo.getAll()) {
                if (f.getId().equals(flightId)) {
                    flight = f;
                    break;
                }
            }

            if (passenger == null) {
                return new Response("Passenger not found with ID: " + passengerId, Status.NOT_FOUND);
            }

            if (flight == null) {
                return new Response("Flight not found with ID: " + flightId, Status.NOT_FOUND);
            }

            // Validación: evitar duplicados
            if (passenger.getFlights().contains(flight)) {
                return new Response("Passenger is already registered to this flight", Status.BAD_REQUEST);
            }

            // Establecer relación temporal
            passenger.addFlight(flight);
            flight.addPassenger(passenger);

            return new Response("Passenger added to flight successfully", Status.OK);

        } catch (Exception e) {
            return new Response("Internal server error: " + e.getMessage(), Status.INTERNAL_SERVER_ERROR);
        }
    }

}
