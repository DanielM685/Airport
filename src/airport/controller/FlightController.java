/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package airport.controller;

import airport.Flight;
import airport.Location;
import airport.Plane;
import airport.controller.utils.Response;
import airport.controller.utils.Status;
import airport.model.storage.FlightAdapter;
import airport.model.storage.JsonRepository;
import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author user
 */
public class FlightController {

    private final JsonRepository<Flight> flightRepo;

    public FlightController(List<Plane> planes, List<Location> locations) {
        this.flightRepo = new JsonRepository<>("json/flights.json", new FlightAdapter(planes, locations));
    }

    public List<Flight> getAllFlights() {
        return flightRepo.getAll();
    }

    public Response createFlight(String id, String planeId, String departureId, String arrivalId, String scaleId,
            String year, String month, String day, String hour, String minute,
            String hArrival, String mArrival, String hScale, String mScale,
            List<Flight> currentFlights, List<Plane> planes, List<Location> locations) {
        try {
            // Validación de campos vacíos
            if (id.isEmpty() || planeId.isEmpty() || departureId.isEmpty() || arrivalId.isEmpty()
                    || year.isEmpty() || month.isEmpty() || day.isEmpty()
                    || hour.isEmpty() || minute.isEmpty()
                    || hArrival.isEmpty() || mArrival.isEmpty()
                    || hScale.isEmpty() || mScale.isEmpty()) {
                return new Response("All fields must be filled.", Status.BAD_REQUEST);
            }

            // ID de vuelo: 3 letras + 3 dígitos
            if (!id.matches("^[A-Z]{3}\\d{3}$")) {
                return new Response("Flight ID must follow format XXX999.", Status.BAD_REQUEST);
            }

            // Verificar que el ID sea único
            for (Flight f : currentFlights) {
                if (f.getId().equals(id)) {
                    return new Response("A flight with this ID already exists.", Status.BAD_REQUEST);
                }
            }

            // Validar que el avión exista
            Plane plane = planes.stream()
                    .filter(p -> p.getId().equals(planeId))
                    .findFirst()
                    .orElse(null);
            if (plane == null) {
                return new Response("Selected plane does not exist.", Status.NOT_FOUND);
            }

            // Validar que los aeropuertos existan
            Location departure = null, arrival = null, scale = null;
            for (Location l : locations) {
                if (l.getAirportId().equals(departureId)) {
                    departure = l;
                }
                if (l.getAirportId().equals(arrivalId)) {
                    arrival = l;
                }
                if (l.getAirportId().equals(scaleId)) {
                    scale = l;
                }
            }
            if (departure == null || arrival == null) {
                return new Response("Departure and arrival locations must be valid.", Status.NOT_FOUND);
            }

            // Validar fecha
            LocalDateTime departureDate;
            try {
                departureDate = LocalDateTime.of(
                        Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day),
                        Integer.parseInt(hour), Integer.parseInt(minute)
                );
            } catch (Exception e) {
                return new Response("Invalid departure date or time.", Status.BAD_REQUEST);
            }

            // Validar duración
            int hA = Integer.parseInt(hArrival);
            int mA = Integer.parseInt(mArrival);
            int hS = Integer.parseInt(hScale);
            int mS = Integer.parseInt(mScale);
            if ((hA == 0 && mA == 0)) {
                return new Response("Arrival duration must be greater than 0.", Status.BAD_REQUEST);
            }
            if (scale == null && (hS != 0 || mS != 0)) {
                return new Response("Cannot have scale duration without a scale location.", Status.BAD_REQUEST);
            }

            return new Response("Flight created successfully.", Status.CREATED);

        } catch (Exception e) {
            return new Response("Internal error: " + e.getMessage(), Status.INTERNAL_SERVER_ERROR);
        }
    }

    public Response delayFlight(String flightId, String hours, String minutes, List<Flight> currentFlights) {
        try {
            int h, m;

            // Validar campos vacíos
            if (flightId.isEmpty() || hours.isEmpty() || minutes.isEmpty()) {
                return new Response("All fields must be filled.", Status.BAD_REQUEST);
            }

            // Buscar vuelo por ID
            Flight flight = null;
            for (Flight f : currentFlights) {
                if (f.getId().equals(flightId)) {
                    flight = f;
                    break;
                }
            }
            if (flight == null) {
                return new Response("Flight with ID " + flightId + " not found.", Status.NOT_FOUND);
            }

            // Validar horas y minutos
            try {
                h = Integer.parseInt(hours);
                m = Integer.parseInt(minutes);
            } catch (NumberFormatException e) {
                return new Response("Delay time must be numeric.", Status.BAD_REQUEST);
            }

            if (h == 0 && m == 0) {
                return new Response("Delay time must be greater than 00:00.", Status.BAD_REQUEST);
            }

            // Aplicar retraso
            flight.delay(h, m);
            return new Response("Flight delayed successfully.", Status.OK);

        } catch (Exception e) {
            return new Response("Internal error: " + e.getMessage(), Status.INTERNAL_SERVER_ERROR);
        }
    }

}
