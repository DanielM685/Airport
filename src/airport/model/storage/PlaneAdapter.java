/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package airport.model.storage;

import airport.Flight;
import airport.Plane;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;

/**
 *
 * @author user
 */
public class PlaneAdapter implements JsonAdapter<Plane> {
    
    

    @Override
    public Plane fromJson(JSONObject obj) {
        return new Plane(
            obj.getString("id"),
            obj.getString("brand"),
            obj.getString("model"),
            obj.getInt("maxCapacity"),
            obj.getString("airline")
        );
    }
    
}
