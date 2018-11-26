package IGT;

import IGT.Customer.Customer;
import IGT.Flight.Flight;
import org.hibernate.Session;

public class Helper{
    private Helper(){}

    public static void bookFlight(Customer c, Flight f, Session session){
        session.save(c);
        session.save(f);
        c.flights.add(f);
        f.customers.add(c);
        session.save(c);
        session.save(f);
    }



}