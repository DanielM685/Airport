/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package airport.model.storage;


import airport.Plane;
import org.json.JSONObject;

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
