/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package airport.controller;

import airport.Flight;
import airport.Location;
import airport.Plane;
import airport.model.storage.FlightAdapter;
import airport.model.storage.JsonRepository;
import java.util.List;

/**
 *
 * @author user
 */
public class FlightController {

    private final JsonRepository<Flight> flightRepo;

    public FlightController(List<Plane> planes, List<Location> locations) {
        this.flightRepo = new JsonRepository<>("json/flights.json", new FlightAdapter(planes, locations));
    }

    public List<Flight> getAllFlights() {
        return flightRepo.getAll();
    }

}
