/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package airport.model.storage;

import airport.Flight;
import airport.Location;
import airport.Plane;
import org.json.JSONObject;
import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author user
 */
public class FlightAdapter implements JsonAdapter<Flight> {
    
    private final List<Plane> planes;
    private final List<Location> locations;

    public FlightAdapter(List<Plane> planes, List<Location> locations) {
        this.planes = planes;
        this.locations = locations;
    }
    
    private Plane findPlane(String id) {
        for (Plane p : planes) {
            if (p.getId().equals(id)) return p;
        }
        return null;
    }

    private Location findLocation(String id) {
        for (Location l : locations) {
            if (l.getAirportId().equals(id)) return l;
        }
        return null;
    }

    

    @Override
    public Flight fromJson(JSONObject obj) {
        String id = obj.getString("id");

        Plane plane = findPlane(obj.getString("plane"));
        Location departure = findLocation(obj.getString("departureLocation"));
        Location arrival = findLocation(obj.getString("arrivalLocation"));
        Location scale = obj.isNull("scaleLocation") ? null : findLocation(obj.getString("scaleLocation"));

        LocalDateTime departureDate = LocalDateTime.parse(obj.getString("departureDate"));

        int hoursArrival = obj.getInt("hoursDurationArrival");
        int minutesArrival = obj.getInt("minutesDurationArrival");
        int hoursScale = obj.getInt("hoursDurationScale");
        int minutesScale = obj.getInt("minutesDurationScale");

        return new Flight(id, plane, departure, scale, arrival, departureDate,
                hoursArrival, minutesArrival, hoursScale, minutesScale);
    }
    
}
