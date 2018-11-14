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

    public void insert() {
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
}

