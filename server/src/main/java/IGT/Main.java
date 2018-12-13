package IGT;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.servlet.ServletContainer;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    public static void main(String[] args) throws Exception {

        System.out.println("\n" +
                "__/\\\\\\\\\\\\\\\\\\\\\\_____/\\\\\\\\\\\\\\\\\\\\\\\\__/\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\_        \n" +
                " _\\/////\\\\\\///____/\\\\\\//////////__\\///////\\\\\\/////__       \n" +
                "  _____\\/\\\\\\______/\\\\\\___________________\\/\\\\\\_______      \n" +
                "   _____\\/\\\\\\_____\\/\\\\\\____/\\\\\\\\\\\\\\_______\\/\\\\\\_______     \n" +
                "    _____\\/\\\\\\_____\\/\\\\\\___\\/////\\\\\\_______\\/\\\\\\_______    \n" +
                "     _____\\/\\\\\\_____\\/\\\\\\_______\\/\\\\\\_______\\/\\\\\\_______   \n" +
                "      _____\\/\\\\\\_____\\/\\\\\\_______\\/\\\\\\_______\\/\\\\\\_______  \n" +
                "       __/\\\\\\\\\\\\\\\\\\\\\\_\\//\\\\\\\\\\\\\\\\\\\\\\\\/________\\/\\\\\\_______ \n" +
                "        _\\///////////___\\////////////__________\\///________\n");

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

        // init db
        Hibernate.getInstance().initFlightManagement();

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
