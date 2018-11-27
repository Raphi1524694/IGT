package IGT.Flight;

import IGT.Customer.Customer;
import IGT.IClassID;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "Flights")
public class Flight implements IClassID {

    @Id
    @GeneratedValue
    @Column(name = "flight_id")
    private Long id;

    private Date startTime;

    public Date getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(Date arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public int getSeatsEconomyClass() {
        return seatsEconomyClass;
    }

    public void setSeatsEconomyClass(int seatsEconomyClass) {
        this.seatsEconomyClass = seatsEconomyClass;
    }

    public int getSeatsFistClass() {
        return seatsFistClass;
    }

    public void setSeatsFistClass(int seatsFistClass) {
        this.seatsFistClass = seatsFistClass;
    }

    public PlaneType getPlane() {
        return plane;
    }

    public void setPlane(PlaneType plane) {
        this.plane = plane;
    }

    public Set<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(Set<Customer> customers) {
        this.customers = customers;
    }

    public List<FlightSegment> getFlightSegmentList() {
        return flightSegmentList;
    }

    public void setFlightSegmentList(List<FlightSegment> flightSegmentList) {
        this.flightSegmentList = flightSegmentList;
    }

    private Date arrivalTime;

    private int seatsEconomyClass;
    private int seatsFistClass;

    private PlaneType plane;

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "FlightBookings",
            joinColumns = {@JoinColumn(name = "flight_id")},
            inverseJoinColumns = {@JoinColumn(name = "customer_id")}
    )
    public Set<Customer> customers = new HashSet<>();

    public Flight() {
    }

    @OneToMany(mappedBy = "belongsToFlight", cascade = CascadeType.ALL)
    private List<FlightSegment> flightSegmentList = new ArrayList<>();

    public void addFlightSegment(FlightSegment p) {
        if (!flightSegmentList.contains(p)) {
            flightSegmentList.add(p);
            if (p.belongsToFlight != null) {
                p.belongsToFlight.flightSegmentList.remove(p);
            }
            p.setBelongsToFlight(this);
        }
    }

    public JSONObject toJSON() {
        JSONObject flight = new JSONObject();
        try {
            flight.put("flightId", this.getId());
            flight.put("startTime", this.getStartTime());
            flight.put("arrivalTime", this.getArrivalTime());
            flight.put("airportsList", this.getFlightSegmentList());
        } catch(JSONException e) {
            e.printStackTrace();
        }


    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public String getClassId() {
        return "Flight";
    }
}