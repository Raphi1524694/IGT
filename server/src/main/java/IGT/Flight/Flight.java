package IGT.Flight;

import IGT.Customer.Customer;
import IGT.IClassID;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "Flights")
public class Flight implements IClassID {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String startTime;

    private String startDate;

    private int duration;

    private int miles;

    private int seatsEconomyClass;

    private int seatsFistClass;

    private double priceEconomyClass;

    private double priceFistClass;

    private PlaneType plane;

    @OneToMany(
            mappedBy = "flight",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Booking> bookings = new ArrayList<>();

    @OneToMany(mappedBy = "belongsToFlight", cascade = CascadeType.ALL, orphanRemoval = true)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<FlightSegment> flightSegmentList = new ArrayList<>();

    public Flight() {
    }

    public List getBookings() {
        return this.bookings;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public long getDuration() {
        return duration;
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

    public double getPriceEconomyClass() {
        return priceEconomyClass;
    }

    public void setPriceEconomyClass(double priceEconomyClass) {
        this.priceEconomyClass = priceEconomyClass;
    }

    public double getPriceFistClass() {
        return priceFistClass;
    }

    public void setPriceFistClass(double priceFistClass) {
        this.priceFistClass = priceFistClass;
    }

    public void setPlane(PlaneType plane) {
        this.plane = plane;
    }

//    public void addCustomer(Customer customer) {
//        this.customers.add(customer);
//    }

    public void setMiles(int miles) {
        this.miles = miles;
    }

    public int getMiles() {
        return miles;
    }

    public List<FlightSegment> getFlightSegmentList() {
        return flightSegmentList;
    }

    public void setFlightSegmentList(List<FlightSegment> flightSegmentList) {
        this.flightSegmentList = flightSegmentList;
    }

    public void addFlightSegment(FlightSegment p) {
        if (!this.flightSegmentList.contains(p)) {
            this.flightSegmentList.add(p);
            p.setBelongsToFlight(this);
        }
    }

    public Airport getStartAirport(){
        return flightSegmentList.get(0).getStart();
    }

    public Airport getDestinationAirport(){
        return flightSegmentList.get(flightSegmentList.size() - 1).getGoal();
    }

    @Override
    public JSONObject toJSON() {
        JSONObject flight = new JSONObject();
        try {
            flight.put("flightId", getId());
            flight.put("time", getStartTime());
            flight.put("date", getStartDate());
            flight.put("miles", getMiles());
            flight.put("duration", getDuration());
            List<Long> airportsList = new ArrayList<>();
            for (int i = 0; i < getFlightSegmentList().size(); i++) {
                airportsList.add(getFlightSegmentList().get(i).getStart().getId());
                if (i == getFlightSegmentList().size() - 1) {
                    airportsList.add(getFlightSegmentList().get(i).getGoal().getId());
                }
            }
            flight.put("airportsList", new JSONArray(airportsList));

            JSONObject seats = new JSONObject();
            seats.put("first", getSeatsFistClass());
            seats.put("economy", getSeatsEconomyClass());
            flight.put("seats", seats);

            JSONObject prices = new JSONObject();
            prices.put("first", getPriceFistClass());
            prices.put("economy", getPriceEconomyClass());
            flight.put("prices", prices);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return flight;
    }

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public String getClassId() {
        return "Flight";
    }
}