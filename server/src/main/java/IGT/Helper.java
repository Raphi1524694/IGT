package IGT;

import IGT.Customer.Customer;
import IGT.Flight.Flight;

public class Helper {
    private Helper() {
    }

    public static Long bookFlight(Customer c, Flight f) {
        Hibernate.getInstance().save(c);
        Hibernate.getInstance().save(f);
        c.flights.add(f);
        f.customers.add(c);
        Hibernate.getInstance().save(c);
        Hibernate.getInstance().save(f);
        Hibernate.getInstance().getTable("FlightBookings").forEach(s -> System.out.println(s));
        return 42l;
    }


}