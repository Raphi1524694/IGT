package IGT.Server;

import IGT.Customer.Customer;
import IGT.Flight.Flight;
import IGT.Hibernate;
import org.codehaus.jettison.json.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/booking")
public class BookingApi {
    /**
     * <b>Create new customer</b>
     * <p>
     * Path: /booking/new
     * Method: POST
     */
    @POST
    @Path("/new")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response bookFlight(String json) {
        try {
            JSONObject request = new JSONObject(json);
            if (!request.has("flightId") || !request.has("customerId")) {
                return Responder.badRequest();
            }
            Long flightId = request.getLong("flightId");
            Long customerId = request.getLong("customerId");
            Customer c = (Customer) Hibernate.getInstance().getElementById(Customer.class, customerId);
            Flight f = (Flight) Hibernate.getInstance().getElementById(Flight.class, flightId);
            if (c == null || f == null) {
                return Responder.badRequest();
            }
            c.bookFlight(f);
            Hibernate.getInstance().save(c);
            return Responder.created(c.toJSON());
        } catch (Exception e) {
            return Responder.exception(e);
        }
    }



    @OPTIONS
    @Path("/new")
    public Response optionsNew() {
        return Responder.preFlight();
    }

}
