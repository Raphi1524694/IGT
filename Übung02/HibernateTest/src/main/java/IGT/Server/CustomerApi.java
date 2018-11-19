package IGT.Server;

import IGT.Customer.Customer;
import IGT.Customer.Phone;
import IGT.Hibernate;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/customer")
public class CustomerApi {

    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllCustomers() {
        JSONArray response = new JSONArray();
        Hibernate.getInstance().getCustomers().forEach(customer -> response.put(customer.toJSON()));
        return Responder.created(response);
    }


    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOneCustomer(@PathParam("id") String id) {
        try {
            for (Customer c : Hibernate.getInstance().getCustomers()) {
                if (c.getId() == Long.parseLong(id)) {
                    return Responder.created(c.toJSON());
                }
            }
            return Responder.badRequest();
        } catch (Exception e) {
            return Responder.exception(e);
        }
    }

    @POST
    @Path("/new")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response postNewCustomer(String json) {
        try {
            JSONObject object = new JSONObject(json);
            if (object.getString("name") != null && object.getJSONArray("phones") != null) {
                Customer newCustomer = new Customer();
                newCustomer.setName(object.getString("name"));
                JSONArray phones = object.getJSONArray("phones");
                for (int i = 0; i < phones.length(); i++) {
                    newCustomer.addPhone(new Phone((String) phones.get(i)));
                }
                Hibernate.getInstance().save(newCustomer);
                return Responder.created(newCustomer.toJSON());
            }
            return Responder.badRequest();
        } catch (Exception e) {
            return Responder.exception(e);
        }
    }

    @POST
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateCustomer(String json, @PathParam("id") String id) {
        try {
            JSONObject object = new JSONObject(json);
            for (Customer c : Hibernate.getInstance().getCustomers()) {
                if (c.getId() == Long.parseLong(id)) {
                    if (object.has("name")) {
                        c.setName(object.getString("name"));
                    }
                    if (object.has("phones")) {
                        c.removeAllPhones();
                        JSONArray phones = object.getJSONArray("phones");
                        for (int i = 0; i < phones.length(); i++) {
                            Phone p = new Phone((String) phones.get(i));
                            c.addPhone(p);
                            Hibernate.getInstance().save(p);
                        }
                    }
                    Hibernate.getInstance().update(c);
                    return Responder.created(c.toJSON());
                }
            }
            return Responder.badRequest();
        } catch (Exception e) {
            return Responder.exception(e);
        }
    }

}

