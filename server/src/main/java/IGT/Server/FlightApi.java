package IGT.Server;

import IGT.Flight.Airport;
import IGT.Flight.Flight;
import IGT.Flight.FlightSegment;
import IGT.Hibernate;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

import java.util.List;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/flight")
public class FlightApi {

    /**
     * Create a new flight object and save it.
     *
     * @param json flight json
     * @return flight json
     */
    @POST
    @Path("/new")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createFlight(String json) {
        try {
            JSONObject object = new JSONObject(json);

            // parse json
            int duration = object.getInt("duration");
            int miles = object.getInt("miles");

            JSONObject seats = object.getJSONObject("seats");
            int seatsF = seats.getInt("first");
            int seatsE = seats.getInt("economy");

            JSONObject prices = object.getJSONObject("prices");
            int pricesF = prices.getInt("first");
            int pricesE = prices.getInt("economy");

            String time = object.getString("time");
            String date = object.getString("date");
            JSONArray airportsList = object.getJSONArray("airportsList");

            if (miles < 1 || airportsList.length() < 2 || duration < 1 || seatsF < 1 || seatsE < 1 || pricesE < 1 || pricesF < 1 || time == null || date == null) {
                return Responder.badRequest();
            }

            // create flight
            Flight newFlight = new Flight();

            // parse flight segments
            for (int i = 0; i < airportsList.length() - 1; i++) {
                Long currentId = airportsList.getLong(i);
                Long nextId = airportsList.getLong(i + 1);
                Airport currentAirport = Hibernate.getInstance().getElementById(currentId, "Airport");
                Airport nextAirport = Hibernate.getInstance().getElementById(nextId, "Airport");
                FlightSegment nextSegment = new FlightSegment(newFlight, currentAirport, nextAirport);
                newFlight.addFlightSegment(nextSegment);
            }

            // set fields
            newFlight.setStartTime(time);
            newFlight.setStartDate(date);
            newFlight.setDuration(duration);
            newFlight.setMiles(miles);
            newFlight.setSeatsFistClass(seatsF);
            newFlight.setSeatsEconomyClass(seatsE);
            newFlight.setPriceFistClass(pricesF);
            newFlight.setPriceEconomyClass(pricesE);

            // save flight in db
            Hibernate.getInstance().save(newFlight);

            return Responder.created(newFlight.toJSON());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Responder.exception(e);
        }
    }


    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllFlights() {
        try {
            JSONArray response = new JSONArray();
            for (Flight f : Hibernate.getInstance().<Flight>getTable("Flight")) {
                response.put(f.toJSON());
            }
            return Responder.ok(response);
        } catch (Exception e) {
            return Responder.exception(e);
        }
    }


    @POST
    @Path("/filter")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getFilterFlights(String reqString) {
        try {
            JSONObject request = new JSONObject(reqString);
            JSONArray response = new JSONArray();
            String date = request.getString("date");
            int startAirport = request.getInt("startAirport");
            int arrivalAirport = request.getInt("arrivalAirport");
            List<Flight> flights = Hibernate.getInstance().<Flight>getTable("Flight");
            for (int i = 0; i < flights.size(); i++) {
                Flight f = flights.get(i);
                if (f.getStartDate().equals(date)
                        && f.getStartAirport().getId() == startAirport
                        && f.getDestinationAirport().getId() == arrivalAirport) {
                    response.put(flights.get(i).toJSON());
                }
            }
            return Responder.ok(response);
        } catch (Exception e) {
            return Responder.exception(e);
        }
    }


    @OPTIONS
    @Path("/new")
    public Response optionsNew() {
        return Responder.preFlight();
    }

    @OPTIONS
    @Path("/all")
    public Response optionsAll() {
        return Responder.preFlight();
    }

    @OPTIONS
    @Path("/filter")
    public Response optionsFilter() {
        return Responder.preFlight();
    }

}
