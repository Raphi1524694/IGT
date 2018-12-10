package IGT;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.servlet.ServletContainer;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    public static void main(String[] args) {
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

        Server server = new Server(Config.DB.getPort());
        System.out.println("Server running on Port: " + Config.DB.getPort());

        ServletContextHandler ctx = new ServletContextHandler(ServletContextHandler.NO_SESSIONS);

        ctx.setContextPath("/");
        server.setHandler(ctx);

        ServletHolder serHol = ctx.addServlet(ServletContainer.class, "/api/*");
        serHol.setInitParameter("jersey.config.server.provider.packages", "IGT/Server");

        // init
        Hibernate.getInstance().initFlightManagement();

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
