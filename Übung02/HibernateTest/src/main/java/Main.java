import Customer.Customer;
import Customer.Phone;
import Flight.Flight;
import Flight.FlightSegment;
import Flight.Airport;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.util.List;


public class Main {
    static Configuration conf;

    static SessionFactory sf;

    static {
        conf = new Configuration().configure()
                .addAnnotatedClass(Customer.class)
                .addAnnotatedClass(Flight.class)
                .addAnnotatedClass(FlightSegment.class)
                .addAnnotatedClass(Airport.class)
                .addAnnotatedClass(Phone.class);
        sf = conf.buildSessionFactory();
    }

    public static void main(String[] args) {
        insert();
        read();
        flighHIGH();
    }

    public static void insert(){
        Session session = sf.openSession();
        Transaction tx = session.beginTransaction();


        Customer cust = new Customer("Raphi");
        cust.addPhone(new Phone("12345"));
        cust.addPhone(new Phone("012345"));

        Customer cust2 = new Customer("Philip");
        cust2.addPhone(new Phone("0012345"));
        cust2.addPhone(new Phone("00012345"));
        cust2.addPhone(cust.getPhones().get(0));

        Customer fail = new Customer(null);

        session.save(cust2);
        session.save(cust);
        session.save(fail);

        tx.commit();
    }

    public static void flighHIGH(){
        Session session = sf.openSession();
        Transaction tx = session.beginTransaction();

        Flight f = new Flight();
        f.addFlightSegment(new FlightSegment(new Airport("test"), new Airport("test2")));

        session.save(f);

        tx.commit();
    }

    public static void read() {
        Session session = sf.openSession();
        Transaction tx = session.beginTransaction();

        Query query = session.createQuery("FROM Customer");
        List list = query.list();

        for (Object c: list){
            System.out.println(((Customer)c).getName());
        }

        tx.commit();
    }
}
