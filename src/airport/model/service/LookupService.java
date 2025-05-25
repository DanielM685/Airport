/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package airport.model.service;

import airport.Flight;
import airport.Location;
import airport.Passenger;
import airport.Plane;
import java.util.List;

/**
 *
 * @author user
 */
public class LookupService {
    
    private final List<Plane> planes;
    private final List<Location> locations;
    private final List<Passenger> passengers;

    public LookupService(List<Plane> planes, List<Location> locations, List<Passenger> passengers) {
        this.planes = planes;
        this.locations = locations;
        this.passengers = passengers;
    }

    public Plane getPlaneById(String id) {
        for (Plane plane : planes) {
            if (plane.getId().equals(id)) {
                return plane;
            }
        }
        throw new IllegalArgumentException("Plane not found: " + id);
    }

    public Location getLocationById(String airportId) {
        for (Location location : locations) {
            if (location.getAirportId().equals(airportId)) {
                return location;
            }
        }
        throw new IllegalArgumentException("Location not found: " + airportId);
    }

    public Passenger getPassengerById(long id) {
        for (Passenger passenger : passengers) {
            if (passenger.getId() == id) {
                return passenger;
            }
        }
        throw new IllegalArgumentException("Passenger not found: " + id);
    }

    // Este método puede ser útil para asignar vuelos a pasajeros una vez cargados
    public void linkFlightToPassengers(Flight flight, List<Long> passengerIds) {
        for (long id : passengerIds) {
            Passenger p = getPassengerById(id);
            p.getFlights().add(flight);
            flight.getPassengers().add(p);
        }
    }
    
}
