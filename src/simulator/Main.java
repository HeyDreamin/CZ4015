package simulator;
import simulator.App.Simulator;
import simulator.Model.BaseStation;

import java.io.FileWriter;
import java.io.IOException;

public class Main {
    private static Counter noReservedCounter = new Counter();
    private static Counter reservedCounter = new Counter();

    public static void main(String[] args) {
        BaseStation[] baseStations = new BaseStation[Counstant.STATIONS];
        for (int i = 0; i < Counstant.STATIONS; i++) {
            baseStations[i] = new BaseStation(i, Counstant.RESERVED_CHANNELS);
        }
        BaseStation[] reservedStations = new BaseStation[Counstant.STATIONS];
        for (int i = 0; i < Counstant.STATIONS; i++) {
            reservedStations[i] = new BaseStation(i, Counstant.RESERVED_CHANNELS);
        }
        BaseStation[] stationTest = new BaseStation[Counstant.STATIONS];
        for (int i = 0; i < Counstant.STATIONS; i++) {
            stationTest[i] = new BaseStation(i, Counstant.RESERVED_CHANNELS+1);
        }

        for (int iteration = 0; iteration < 100; iteration++) {
            Simulator noReservedSimulator = new Simulator(false, Utils.getEvents(Counstant.DATA_PATH, baseStations), baseStations);
            noReservedSimulator.setEvents(Utils.randomEvents(baseStations));
            noReservedSimulator.simulate();
            System.out.printf("Iteration No.%d\nFCA: No channel reservation\n",iteration+1);
            printStatics(noReservedSimulator.counter);
            logStatics(noReservedSimulator.counter, noReservedCounter);

            Simulator reservedSimulator = new Simulator(true, Utils.getEvents(Counstant.DATA_PATH, reservedStations), reservedStations);
            reservedSimulator.setEvents(Utils.randomEvents(reservedStations));
            reservedSimulator.simulate();
            System.out.printf("FCA: %d Channel(s) reserved for Handover\n", Counstant.RESERVED_CHANNELS);
            printStatics(reservedSimulator.counter);
            logStatics(reservedSimulator.counter, reservedCounter);

            Simulator testSimulator = new Simulator(true, Utils.getEvents(Counstant.DATA_PATH, stationTest), stationTest);
            testSimulator.setEvents(Utils.randomEvents(stationTest));
            testSimulator.simulate();
            System.out.printf("FCA: %d Channel(s) reserved for Handover\n", Counstant.RESERVED_CHANNELS+1);
            printStatics(testSimulator.counter);
            logStatics(testSimulator.counter, reservedCounter);
            writeFile(noReservedSimulator.counter, reservedSimulator.counter, testSimulator.counter);
            System.out.println();
        }

        System.out.println("===========================");
        System.out.println("FCA: No channel reservation");
        printStatics(noReservedCounter);
        System.out.printf("\nFCA: %d Channel(s) reserved for Handover\n", Counstant.RESERVED_CHANNELS);
        printStatics(reservedCounter);

    }

    private static void writeFile(Counter noReservedCounter, Counter reservedCounter, Counter testCounter) {
        try {
            FileWriter fw = new FileWriter("data\\result_10k.csv",true);
            StringBuilder writer = new StringBuilder();
            writer.append(noReservedCounter.blockCounter).append(",").append(noReservedCounter.droppedCounter);
            writer.append(",").append(reservedCounter.blockCounter).append(",").append(reservedCounter.droppedCounter);
            writer.append(",").append(testCounter.blockCounter).append(",").append(testCounter.droppedCounter).append("\n");
            fw.write(writer.toString());
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void logStatics(Counter simulator, Counter counter) {
        counter.initialHandled += simulator.initialHandled;
        counter.blockCounter += simulator.blockCounter;
        counter.droppedCounter += simulator.droppedCounter;
    }

    private static void printStatics(Counter counter) {
        int initialCalls = counter.initialHandled;
        double blockedCalls = (double)counter.blockCounter*100/initialCalls;
        double droppedCalls = (double)counter.droppedCounter*100/initialCalls;
        System.out.println("Initial: " + initialCalls);
//        System.out.println("Handover: "+ counter.handoverCounter);
        System.out.printf("Blocked: %.3f%%\n", blockedCalls);
        System.out.printf("Dropped: %.3f%%\n", droppedCalls);
    }
}
