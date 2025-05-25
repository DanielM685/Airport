/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package airport.controller;

import airport.Location;
import airport.controller.utils.Response;
import airport.controller.utils.Status;
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

    public Response createLocation(String id, String name, String city, String country, String lat, String lon, List<Location> currentLocations) {
        try {
            double latitude, longitude;

            if (id.isEmpty() || name.isEmpty() || city.isEmpty() || country.isEmpty() || lat.isEmpty() || lon.isEmpty()) {
                return new Response("All fields must be filled.", Status.BAD_REQUEST);
            }

            if (!id.matches("^[A-Z]{3}$")) {
                return new Response("Airport ID must be 3 uppercase letters (e.g., BOG, JFK).", Status.BAD_REQUEST);
            }

            try {
                latitude = Double.parseDouble(lat);
                if (latitude < -90 || latitude > 90) {
                    return new Response("Latitude must be between -90 and 90.", Status.BAD_REQUEST);
                }
                if (!lat.matches("^-?\\d{1,2}(\\.\\d{1,4})?$")) {
                    return new Response("Latitude must have up to 4 decimal places.", Status.BAD_REQUEST);
                }
            } catch (NumberFormatException e) {
                return new Response("Latitude must be numeric.", Status.BAD_REQUEST);
            }

            try {
                longitude = Double.parseDouble(lon);
                if (longitude < -180 || longitude > 180) {
                    return new Response("Longitude must be between -180 and 180.", Status.BAD_REQUEST);
                }
                if (!lon.matches("^-?\\d{1,3}(\\.\\d{1,4})?$")) {
                    return new Response("Longitude must have up to 4 decimal places.", Status.BAD_REQUEST);
                }
            } catch (NumberFormatException e) {
                return new Response("Longitude must be numeric.", Status.BAD_REQUEST);
            }

            for (Location l : currentLocations) {
                if (l.getAirportId().equals(id)) {
                    return new Response("A location with this ID already exists.", Status.BAD_REQUEST);
                }
            }

            return new Response("Location created successfully.", Status.CREATED);

        } catch (Exception e) {
            return new Response("Internal error: " + e.getMessage(), Status.INTERNAL_SERVER_ERROR);
        }
    }

}
