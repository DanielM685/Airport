/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package airport.controller;

import airport.Plane;
import airport.controller.utils.Response;
import airport.controller.utils.Status;
import airport.model.storage.JsonRepository;
import airport.model.storage.PlaneAdapter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 *
 * @author user
 */
public class PlaneController {

    private final JsonRepository<Plane> planeRepo;

    public PlaneController() {
        this.planeRepo = new JsonRepository<>("json/planes.json", new PlaneAdapter());
    }

    public List<Plane> getAllPlanes() {
        List<Plane> sorted = new ArrayList<>(planeRepo.getAll());
        sorted.sort(Comparator.comparing(Plane::getId));
        return sorted;
    }

    public Response createPlane(String id, String brand, String model, String capacity, String airline, List<Plane> currentPlanes) {
        try {
            int maxCapacity;

            // Validaciones de campos vacíos
            if (id.isEmpty() || brand.isEmpty() || model.isEmpty() || capacity.isEmpty() || airline.isEmpty()) {
                return new Response("All fields must be filled.", Status.BAD_REQUEST);
            }

            // Validar formato del ID (ej. XX99999)
            if (!id.matches("^[A-Z]{2}\\d{5}$")) {
                return new Response("ID must follow format: two uppercase letters followed by 5 digits.", Status.BAD_REQUEST);
            }

            // Validar capacidad
            try {
                maxCapacity = Integer.parseInt(capacity);
                if (maxCapacity <= 0) {
                    return new Response("Max capacity must be a positive number.", Status.BAD_REQUEST);
                }
            } catch (NumberFormatException e) {
                return new Response("Max capacity must be numeric.", Status.BAD_REQUEST);
            }

            // Verificar que el ID sea único
            for (Plane p : currentPlanes) {
                if (p.getId().equals(id)) {
                    return new Response("A plane with this ID already exists.", Status.BAD_REQUEST);
                }
            }

            return new Response("Plane created successfully.", Status.CREATED);

        } catch (Exception e) {
            return new Response("Internal error: " + e.getMessage(), Status.INTERNAL_SERVER_ERROR);
        }
    }

}
