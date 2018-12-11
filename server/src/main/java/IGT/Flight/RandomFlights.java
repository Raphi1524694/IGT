package IGT.Flight;

import IGT.Hibernate;

import java.util.Date;
import java.util.List;

public class RandomFlights {

    public static void generate(int i) throws Exception {
        int existingFlights = Hibernate.getInstance().getTable("Flight").size();
        if (existingFlights >= i) return;
        i = i - existingFlights;
        List<Airport> airportList = Hibernate.getInstance().getTable("Airport");
        int l = airportList.size();
        for (int j = 0; j < i; j++) {
            Flight f = new Flight();
            int nrOfSegments = rand(2, 4);
            Airport last =  airportList.get(rand(l));
            for (int k = 0; k < nrOfSegments; k++){
                Airport next = airportList.get(rand(l));
                f.addFlightSegment(new FlightSegment(f, last, next));
                last = next;
            }
            f.setMiles(rand(100, 5000));
            f.setStartTime(ext(rand(24)) + ":" + ext(rand(60)));
            f.setStartDate("2018-" + ext(rand(10, 12)) + "-" + ext(rand(1, 30)));
            f.setPlane(PlaneType.FuckingBig);
            f.setSeatsEconomyClass(rand(100, 500));
            f.setSeatsFistClass(rand(5, 50));
            f.setDuration(rand(600));
            Long id = Hibernate.getInstance().save(f);
            System.out.println(f.toJSON().toString());
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
