/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package airport.controller;

import airport.Location;
import airport.model.storage.JsonRepository;
import airport.model.storage.LocationAdapter;
import java.util.List;

/**
 *
 * @author user
 */
public class LocationController {

    private final JsonRepository<Location> locationRepo;

    public LocationController() {
        this.locationRepo = new JsonRepository<>("json/locations.json", new LocationAdapter());
    }

    public List<Location> getAllLocations() {
        return locationRepo.getAll();
    }

}
