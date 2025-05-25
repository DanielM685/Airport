/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package airport.model.storage;

import airport.Location;
import org.json.JSONObject;

/**
 *
 * @author user
 */
public class LocationAdapter implements JsonAdapter<Location>{
    
    

    @Override
    public Location fromJson(JSONObject obj) {
        return new Location(
            obj.getString("airportId"),
            obj.getString("airportName"),
            obj.getString("airportCity"),
            obj.getString("airportCountry"),
            obj.getDouble("airportLatitude"),
            obj.getDouble("airportLongitude")
        );
    }
    
}
