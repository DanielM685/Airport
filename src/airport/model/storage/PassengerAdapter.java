/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package airport.model.storage;


import airport.Flight;
import airport.Passenger;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author user
 */
public class PassengerAdapter implements JsonAdapter<Passenger>{
        
    @Override
    public Passenger fromJson(JSONObject obj) {
        return new Passenger(
            obj.getLong("id"),
            obj.getString("firstname"),
            obj.getString("lastname"),
            LocalDate.parse(obj.getString("birthDate")),
            obj.getInt("countryPhoneCode"),
            obj.getLong("phone"),
            obj.getString("country")
        );
    }
    
}
