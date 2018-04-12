package simulator;

import simulator.Model.BaseStation;
import simulator.Model.Event;
import simulator.Model.InitialEvent;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

class Utils {

    static ArrayList<Event> getEvents(String path, BaseStation[] baseStations) {
        ArrayList<Event> events = new ArrayList<Event>();

        try {
            Scanner scanner = new Scanner(new File(path));
            scanner.nextLine();
            while (scanner.hasNextLine()) {
                String[] params = scanner.nextLine().split(",");
                events.add(new InitialEvent(baseStations[Integer.parseInt(params[2])-1],
                                            Double.parseDouble(params[1]),
                                            Integer.parseInt(params[0]),
                                            Double.parseDouble(params[4]),
                                            generatePosition(),
                                            Double.parseDouble(params[3]),
                                            generateDirection()));
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
            e.printStackTrace();
        }
        return events;
    }

    static ArrayList<Event> randomEvents(BaseStation[] baseStations) {
        ArrayList<Event> events = new ArrayList<Event>();
        double clock = 0.0;
        double timeInterval = generateTimeInterval();
        for (int i = 0; i < Counstant.INITIAL_EVENTS; i++) {
            events.add(new InitialEvent(baseStations[generateStation()],
                                        clock,
                                        i,
                                        generateSpeed(),
                                        generatePosition(),
                                        generateDuration(),
                                        generateDirection()
                                        ));
            clock += timeInterval;
            timeInterval = generateTimeInterval();
        }
        return events;
    }

    private static double generateDuration() {
        return (-Counstant.DURATION_MEAN) * Math.log(1 - Math.random());
    }

    private static double generateSpeed() {
        Random random = new Random();
        return random.nextGaussian()*Counstant.SPEED_STD_DEVIATION + Counstant.SPEED_MEAN;
    }

    private static double generateTimeInterval() {
        return (-Counstant.TIME_INTERVAL_MEAN) * Math.log(1 - Math.random());
    }

    private static int generateStation() {
        Random random = new Random();
        return random.nextInt(Counstant.STATIONS);
    }

    private static boolean generateDirection() {
        return Math.random() < 0.5;
    }

    private static double generatePosition() {
        return 1 - Math.random()*2;
    }

}
