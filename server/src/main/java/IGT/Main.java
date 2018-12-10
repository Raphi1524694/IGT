package IGT;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.servlet.ServletContainer;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        if (args.length > 0) {
            System.out.println("args: " + args[0]);
            for (Config.PERSISTENCE_UNIT unit : Config.PERSISTENCE_UNIT.values()) {
                if (unit.name().equals(args[0])) {
                    Config.DB = unit;
                    break;
                }
            }
        } else {
            System.out.println("default config");
        }

        for (int i = 0; i < 3; i++) {
            try {
                // init
                Hibernate.getInstance().initFlightManagement();
                break;
            } catch (Throwable e) {
                System.out.printf("Failed to connect to db %d of 3\n", i + 1);
                if (i == 2) {
                    throw e;
                }
                TimeUnit.SECONDS.sleep(5);
            }
        }

        Server server = new Server(Config.DB.getPort());
        System.out.println("Server running on Port: " + Config.DB.getPort());

        ServletContextHandler ctx = new ServletContextHandler(ServletContextHandler.NO_SESSIONS);

        ctx.setContextPath("/");
        server.setHandler(ctx);

        ServletHolder serHol = ctx.addServlet(ServletContainer.class, "/api/*");
        serHol.setInitParameter("jersey.config.server.provider.packages", "IGT/Server");
        try {
            server.start();
            server.join();
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            server.destroy();
        }
    }


}
