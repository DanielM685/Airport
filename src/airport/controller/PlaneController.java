/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package airport.controller;

import airport.Plane;
import airport.model.storage.JsonRepository;
import airport.model.storage.PlaneAdapter;
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
        return planeRepo.getAll();
    }

}
