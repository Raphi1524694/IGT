package IGT.Server;

import IGT.Flight.Airport;
import IGT.Flight.Flight;
import IGT.Flight.FlightSegment;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Path("/flights")
public class FlightApi {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createFlight(String json) {
        DateFormat df = new SimpleDateFormat("EEE MMM dd yyyy kk:mm:ss z");
        Date start, arrival;
        JSONArray airportsList;
        int miles;
        try {
            JSONObject object = new JSONObject(json);
            start = df.parse(object.getString("startTime"));
            arrival = df.parse(object.getString("arrivalTime"));
            airportsList = object.getJSONArray("airportsList");
            miles = object.getInt("miles");
            if (miles < 1 || airportsList.length() < 2) {
                return Responder.badRequest();
            }
            Flight newFlight = new Flight();
            for (int i = 0; i < airportsList.length() - 1; i++) {
                newFlight.addFlightSegment(new FlightSegment(newFlight, new Airport((String) airportsList.get(i)), new Airport((String) airportsList.get(i + 1))));
            }
            newFlight.setStartTime(start);
            newFlight.setArrivalTime(arrival);
            return Responder.created(newFlight.toJSON());
        } catch(Exception e) {
            return Responder.badRequest();
        }

    }

}
