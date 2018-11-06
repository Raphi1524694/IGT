import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;


public class Main {
    public static void main(String[] args) {
        Customer cust = new Customer("Raphi");
        Phone p = new Phone("12345");
        cust.addPhone(p);

        Configuration conf = new Configuration().configure()
                .addAnnotatedClass(Customer.class)
                .addAnnotatedClass(Phone.class);

        SessionFactory sf = conf.buildSessionFactory();

        Session session = sf.openSession();

        Transaction tx = session.beginTransaction();

        session.save(cust);


        tx.commit();

    }
}
