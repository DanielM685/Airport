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
    public JSONObject toJson(Passenger passenger) {
        JSONObject obj = new JSONObject();
        obj.put("id", passenger.getId());
        obj.put("firstname", passenger.getFirstname());
        obj.put("lastname", passenger.getLastname());
        obj.put("birthDate", passenger.getBirthDate().toString());
        obj.put("countryPhoneCode", passenger.getCountryPhoneCode());
        obj.put("phone", passenger.getPhone());
        obj.put("country", passenger.getCountry());

        // Guardar solo los IDs de los vuelos
        JSONArray flightIds = new JSONArray();
        for (Flight flight : passenger.getFlights()) {
            flightIds.put(flight.getId());
        }
        obj.put("flights", flightIds);

        return obj;
    }

    @Override
    public Passenger fromJson(JSONObject obj) {
        long id = obj.getLong("id");
        String firstname = obj.getString("firstname");
        String lastname = obj.getString("lastname");
        LocalDate birthDate = LocalDate.parse(obj.getString("birthDate"));
        int countryPhoneCode = obj.getInt("countryPhoneCode");
        long phone = obj.getLong("phone");
        String country = obj.getString("country");

        Passenger passenger = new Passenger(id, firstname, lastname, birthDate, countryPhoneCode, phone, country);

        // No cargamos los vuelos aquí. Los vuelos deben añadirse luego usando FlightAdapter + LookupService

        return passenger;
    }

    /**
     * Este método auxiliar puede usarse desde el controlador o el adaptador de vuelos
     * para asignar vuelos a pasajeros una vez cargados.
     */
    public void linkFlightsToPassenger(Passenger passenger, List<Flight> allFlights) {
        List<Flight> matched = new ArrayList<>();
        for (Flight f : allFlights) {
            if (f.getPassengers().contains(passenger)) {
                matched.add(f);
            }
        }
        passenger.setFlights(new ArrayList<>(matched));
    }
    
}
