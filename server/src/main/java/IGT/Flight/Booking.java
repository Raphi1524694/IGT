package IGT.Flight;

import IGT.Customer.Customer;
import IGT.IClassID;
import org.codehaus.jettison.json.JSONObject;

import javax.persistence.*;

@Entity
@Table(name = "Bookings")
public class Booking implements IClassID {

    @EmbeddedId
    private BookingId id;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("customerId")
    private Customer customer;


    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("flightId")
    private Flight flight;

    private Booking() { }

    public Booking(Customer customer, Flight flight) {
        this.customer = customer;
        this.flight = flight;
        this.id = new BookingId(customer.getId(), flight.getId());
    }

    public Flight getFlight() {
        return this.flight;
    }

    @Override
    public String getClassId() {
        return "Booking";
    }

    @Override
    public BookingId getId() {
        return this.id;
    }

    @Override
    public JSONObject toJSON() {
        JSONObject object = new JSONObject();
        try {
            object.put("booking_id", this.id);
            object.put("customer_id", this.customer.getId());
            object.put("flight_id", this.flight.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return object;
    }
}
