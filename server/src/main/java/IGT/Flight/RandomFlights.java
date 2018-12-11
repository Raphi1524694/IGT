package IGT.Flight;

import IGT.Hibernate;

import java.util.Date;
import java.util.List;

public class RandomFlights {

    public static void generate(int i) throws Exception {
        if (Hibernate.getInstance().getTable("Flight").size() > 0) return;
        List<Airport> airportList = Hibernate.getInstance().getTable("Airport");
        int l = airportList.size();
        for (int j = 0; j < i; j++) {
            Flight f = new Flight();
            f.addFlightSegment(new FlightSegment(f, airportList.get(rand(l)), airportList.get(rand(l))));
            f.setMiles(rand(100, 5000));
            f.setStartTime(ext(rand(24)) + ":" + ext(rand(60)));
            f.setStartDate("2018-" + ext(rand(1, 9)) + "-" + ext(rand(1, 9)));
            f.setPlane(PlaneType.FuckingBig);
            f.setSeatsEconomyClass(rand(100, 500));
            f.setSeatsFistClass(rand(5, 50));
            f.setDuration(rand(600));
            Long id = Hibernate.getInstance().save(f);
            System.out.println("generated Flight with id:" + id);
            System.out.println("          " + f.toJSON().toString());
        }
    }

    private static int rand(int stop) {
        return (int) (Math.random() * stop);
    }

    private static int rand(int start, int stop) {
        return rand(stop - start) + start;
    }

    private static String ext(int s) {
        String l = s + "";
        if (s < 10) {
            l = "0" + l;
        }
        return l;
    }
}
