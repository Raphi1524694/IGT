package IGT.Flight;

import IGT.Hibernate;

import java.util.Date;
import java.util.List;

public class RandomFlights {

    public static void generate(int i) throws Exception {
        for(Flight f : Hibernate.getInstance().<Flight>getTable("Flight")){
            Hibernate.getInstance().delete(f);
        }
        System.out.println("\n" +
                "  ____                      _                              _    \n" +
                " |  _ \\                    | |                            | |   \n" +
                " | |_) |  ___  _ __    ___ | |__   _ __ ___    __ _  _ __ | | __\n" +
                " |  _ <  / _ \\| '_ \\  / __|| '_ \\ | '_ ` _ \\  / _` || '__|| |/ /\n" +
                " | |_) ||  __/| | | || (__ | | | || | | | | || (_| || |   |   < \n" +
                " |____/  \\___||_| |_| \\___||_| |_||_| |_| |_| \\__,_||_|   |_|\\_\\\n" +
                "                                                                \n" +
                "                                                                \n");
        long startTime = System.currentTimeMillis();
        List<Airport> airportList = Hibernate.getInstance().getTable("Airport");
        int l = airportList.size();
        for (int j = 0; j < i; j++) {
            Flight f = new Flight();
            int nrOfSegments = rand(2, 4);
            Airport last = airportList.get(rand(l));
            for (int k = 0; k < nrOfSegments; k++) {
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
            f.setPriceEconomyClass(rand(100, 1500));
            f.setPriceFistClass(rand(900, 3000));
            f.setDuration(rand(600));
            Hibernate.getInstance().save(f);
        }
        long duration = System.currentTimeMillis() - startTime;
        double seconds = duration / 1000.0;
        double perEntry = duration / new Double(i);
        System.out.printf("Total time for inputting %d Entrys: %fs %n", i, seconds);
        System.out.printf("Time per entry: %f ms %n", perEntry);

        long startTime2 = System.currentTimeMillis();

        List flights = Hibernate.getInstance().getTable("Flight");
        duration = System.currentTimeMillis() - startTime2;
        seconds = duration / 1000.0;
        perEntry = duration / new Double(flights.size());
        System.out.printf("Total time for retreving %d Entrys: %fs %n", flights.size(), seconds);
        System.out.printf("Time per entry: %f ms %n", perEntry);
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
