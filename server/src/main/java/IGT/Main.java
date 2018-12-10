package IGT;

import com.sun.net.httpserver.HttpServer;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.servlet.ServletContainer;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    private static EntityManagerFactory emf;

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 3; i++) {
            try {
                emf = Persistence.createEntityManagerFactory(
                        Config.DB.name());
                break;
            } catch (Throwable e) {
                System.out.printf("Failed to connect to db %d of 3\n", i + 1);
                if (i == 2) {
                    throw e;
                }
                TimeUnit.SECONDS.sleep(5);
            }
        }
    }

    public static EntityManagerFactory getEntityManagerFactory() {
        return emf;
    }


}

