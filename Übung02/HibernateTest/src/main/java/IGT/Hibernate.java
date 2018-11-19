package IGT;

import IGT.Customer.Customer;
import IGT.Customer.Phone;
import IGT.Flight.Airport;
import IGT.Flight.Flight;
import IGT.Flight.FlightSegment;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.ArrayList;
import java.util.List;

public class Hibernate {

    private static final Hibernate instance = new Hibernate();
    static Configuration conf;
    static SessionFactory sf;

    //private constructor to avoid client applications to use constructor
    private Hibernate() {
        conf = new Configuration().configure()
                .addAnnotatedClass(Customer.class)
                .addAnnotatedClass(Flight.class)
                .addAnnotatedClass(FlightSegment.class)
                .addAnnotatedClass(Airport.class)
                .addAnnotatedClass(Phone.class);
        sf = conf.buildSessionFactory();
    }

    public static Hibernate getInstance() {
        return instance;
    }

    public void save(Object o) {
        try {
            Session session = sf.openSession();
            Transaction tx = session.beginTransaction();
            session.save(o);
            tx.commit();
        } catch (Exception e) {
            System.err.println("inserting failed");
            e.printStackTrace();
        }
    }

    public void update(Object o) {
        try {
            Session session = sf.openSession();
            Transaction tx = session.beginTransaction();
            session.update(o);
            tx.commit();
        } catch (Exception e) {
            System.err.println("updating failed");
            e.printStackTrace();
        }
    }

    public List<Customer> getCustomers() {
        List<Customer> cust = new ArrayList<>();
        try {
            Session session = sf.openSession();
            Transaction tx = session.beginTransaction();

            for (Object c : session.createQuery("FROM Customer").list()) {
                cust.add((Customer) c);
            }

            cust.forEach(customer -> System.out.println(customer.getName()));

            tx.commit();
        } catch (Exception e) {
            System.err.println("inserting failed");
            e.printStackTrace();
        }
        return cust;
    }
}

