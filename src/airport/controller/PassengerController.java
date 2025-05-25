/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package airport.controller;

import airport.Flight;
import airport.Location;
import airport.Passenger;
import airport.Plane;
import airport.controller.utils.Response;
import airport.controller.utils.Status;
import airport.model.storage.FlightAdapter;
import airport.model.storage.JsonRepository;
import airport.model.storage.PassengerAdapter;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author user
 */
public class PassengerController {

    private final JsonRepository<Passenger> passengerRepo;
    private final JsonRepository<Flight> flightRepo;

    public PassengerController(List<Plane> planes, List<Location> locations) {
        this.passengerRepo = new JsonRepository<>("json/passengers.json", new PassengerAdapter());
        this.flightRepo = new JsonRepository<>("json/flights.json", new FlightAdapter(planes, locations)); // igual que antes: necesitas listas
    }

    public List<Passenger> getAllPassengers() {
        return passengerRepo.getAll();
    }

    private int getAge(LocalDate birthDate) {
        return LocalDate.now().getYear() - birthDate.getYear();
    }

    public Response addPassengerToFlight(long passengerId, String flightId) {
        try {
            Passenger passenger = null;
            Flight flight = null;

            for (Passenger p : passengerRepo.getAll()) {
                if (p.getId() == passengerId) {
                    passenger = p;
                    break;
                }
            }

            for (Flight f : flightRepo.getAll()) {
                if (f.getId().equals(flightId)) {
                    flight = f;
                    break;
                }
            }

            if (passenger == null) {
                return new Response("Passenger not found with ID: " + passengerId, Status.NOT_FOUND);
            }

            if (flight == null) {
                return new Response("Flight not found with ID: " + flightId, Status.NOT_FOUND);
            }

            // Validación: evitar duplicados
            if (passenger.getFlights().contains(flight)) {
                return new Response("Passenger is already registered to this flight", Status.BAD_REQUEST);
            }

            // Establecer relación temporal
            passenger.addFlight(flight);
            flight.addPassenger(passenger);

            return new Response("Passenger added to flight successfully", Status.OK);

        } catch (Exception e) {
            return new Response("Internal server error: " + e.getMessage(), Status.INTERNAL_SERVER_ERROR);
        }
    }

    public Response createPassenger(String id, String firstname, String lastname, String year, String month, String day, String code, String phone, String country, List<Passenger> currentPassengers) {
        try {
            long idLong;
            int phoneCode;
            long phoneNumber;
            LocalDate birthDate;

            // ID
            try {
                idLong = Long.parseLong(id);
                if (idLong < 0 || String.valueOf(idLong).length() > 15) {
                    return new Response("ID must be positive and at most 15 digits.", Status.BAD_REQUEST);
                }
            } catch (NumberFormatException e) {
                return new Response("ID must be numeric.", Status.BAD_REQUEST);
            }

            // Campos vacíos
            if (firstname.isEmpty() || lastname.isEmpty() || year.isEmpty() || month.isEmpty() || day.isEmpty() || code.isEmpty() || phone.isEmpty() || country.isEmpty()) {
                return new Response("All fields must be filled.", Status.BAD_REQUEST);
            }

            // Fecha
            try {
                birthDate = LocalDate.of(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day));
            } catch (Exception e) {
                return new Response("Invalid birth date.", Status.BAD_REQUEST);
            }

            // Phone code
            try {
                phoneCode = Integer.parseInt(code);
                if (phoneCode < 0 || String.valueOf(phoneCode).length() > 3) {
                    return new Response("Phone code must be 1–3 digits and positive.", Status.BAD_REQUEST);
                }
            } catch (NumberFormatException e) {
                return new Response("Phone code must be numeric.", Status.BAD_REQUEST);
            }

            // Phone number
            try {
                phoneNumber = Long.parseLong(phone);
                if (phoneNumber < 0 || String.valueOf(phoneNumber).length() > 11) {
                    return new Response("Phone number must be at most 11 digits and positive.", Status.BAD_REQUEST);
                }
            } catch (NumberFormatException e) {
                return new Response("Phone number must be numeric.", Status.BAD_REQUEST);
            }

            // ID único
            for (Passenger p : currentPassengers) {
                if (p.getId() == idLong) {
                    return new Response("A passenger with this ID already exists.", Status.BAD_REQUEST);
                }
            }

            // Aquí NO se añade el pasajero (eso lo hará el Frame si todo va bien)
            return new Response("Passenger created successfully.", Status.CREATED);

        } catch (Exception e) {
            return new Response("Internal error: " + e.getMessage(), Status.INTERNAL_SERVER_ERROR);
        }
    }

    public Response updatePassenger(String id, String firstname, String lastname, String year, String month, String day,
            String code, String phone, String country, List<Passenger> currentPassengers) {
        try {
            long idLong;
            int phoneCode;
            long phoneNumber;
            LocalDate birthDate;

            // Validar ID
            try {
                idLong = Long.parseLong(id);
                if (idLong < 0 || String.valueOf(idLong).length() > 15) {
                    return new Response("ID must be positive and at most 15 digits.", Status.BAD_REQUEST);
                }
            } catch (NumberFormatException e) {
                return new Response("ID must be numeric.", Status.BAD_REQUEST);
            }

            // Validar campos vacíos
            if (firstname.isEmpty() || lastname.isEmpty() || year.isEmpty() || month.isEmpty() || day.isEmpty()
                    || code.isEmpty() || phone.isEmpty() || country.isEmpty()) {
                return new Response("All fields must be filled.", Status.BAD_REQUEST);
            }

            // Validar fecha
            try {
                birthDate = LocalDate.of(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day));
            } catch (Exception e) {
                return new Response("Invalid birth date.", Status.BAD_REQUEST);
            }

            // Validar código país
            try {
                phoneCode = Integer.parseInt(code);
                if (phoneCode < 0 || String.valueOf(phoneCode).length() > 3) {
                    return new Response("Phone code must be 1–3 digits and positive.", Status.BAD_REQUEST);
                }
            } catch (NumberFormatException e) {
                return new Response("Phone code must be numeric.", Status.BAD_REQUEST);
            }

            // Validar número
            try {
                phoneNumber = Long.parseLong(phone);
                if (phoneNumber < 0 || String.valueOf(phoneNumber).length() > 11) {
                    return new Response("Phone number must be at most 11 digits and positive.", Status.BAD_REQUEST);
                }
            } catch (NumberFormatException e) {
                return new Response("Phone number must be numeric.", Status.BAD_REQUEST);
            }

            // Buscar pasajero
            Passenger passenger = null;
            for (Passenger p : currentPassengers) {
                if (p.getId() == idLong) {
                    passenger = p;
                    break;
                }
            }
            if (passenger == null) {
                return new Response("Passenger with that ID does not exist.", Status.NOT_FOUND);
            }

            // Actualizar campos
            passenger.setFirstname(firstname);
            passenger.setLastname(lastname);
            passenger.setBirthDate(birthDate);
            passenger.setCountryPhoneCode(phoneCode);
            passenger.setPhone(phoneNumber);
            passenger.setCountry(country);

            return new Response("Passenger updated successfully.", Status.OK);

        } catch (Exception e) {
            return new Response("Internal error: " + e.getMessage(), Status.INTERNAL_SERVER_ERROR);
        }
    }

}
