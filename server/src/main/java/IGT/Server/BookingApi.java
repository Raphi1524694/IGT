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
    public Response bookFlight(@QueryParam("customer") long customerId, @QueryParam("flight") long flightId) {
        try {
            if (customerId <= 0 || flightId <= 0) {
                return Responder.badRequest();
            }
            System.out.println(customerId + ", " + flightId);
            Customer c = Hibernate.getInstance().getElementById(customerId, "Customer");
            Flight f = Hibernate.getInstance().getElementById(flightId, "Flight");
            JSONObject response = new JSONObject();
            f.addCustomer(c);
            c.addFlight(f);
            response.put("customer", c.getId());
            response.put("flight", f.getId());
            return Responder.created(response);
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
