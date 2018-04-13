package simulator;
import simulator.App.Simulator;
import simulator.Model.BaseStation;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

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
        int iteration = 0;
        int limit = 10;
        double[] blockProbabilities = new double[limit];
        double[] dropProbabilities = new double[limit];
        while (iteration<limit*10){
            iteration++;
            Simulator noReservedSimulator = new Simulator(false, Utils.getEvents(Counstant.DATA_PATH, baseStations), baseStations);
            noReservedSimulator.setEvents(Utils.randomEvents(baseStations));
            noReservedSimulator.simulate();
//            System.out.printf("Iteration No.%d\nFCA: No channel reservation\n",iteration+1);
//            printStatics(noReservedSimulator.counter);
            logStatics(noReservedSimulator.counter, noReservedCounter, blockProbabilities, dropProbabilities, iteration);

//            Simulator reservedSimulator = new Simulator(true, Utils.getEvents(Counstant.DATA_PATH, reservedStations), reservedStations);
//            reservedSimulator.setEvents(Utils.randomEvents(reservedStations));
//            reservedSimulator.simulate();
//            System.out.printf("FCA: %d Channel(s) reserved for Handover\n", Counstant.RESERVED_CHANNELS);
//            printStatics(reservedSimulator.counter);
//            logStatics(reservedSimulator.counter, reservedCounter, blockProbabilities, dropProbabilities, iteration);
//
//            Simulator testSimulator = new Simulator(true, Utils.getEvents(Counstant.DATA_PATH, stationTest), stationTest);
//            testSimulator.setEvents(Utils.randomEvents(stationTest));
//            testSimulator.simulate();
//            System.out.printf("FCA: %d Channel(s) reserved for Handover\n", Counstant.RESERVED_CHANNELS+1);
//            printStatics(testSimulator.counter);
//            logStatics(testSimulator.counter, reservedCounter, blockProbabilities, dropProbabilities, iteration);

//            writeFile(noReservedSimulator.counter, reservedSimulator.counter, testSimulator.counter);
//            System.out.println();
            if (iteration%10==0) {
                double blockConfidence = confidenceInterval(blockProbabilities);
                double dropConfidence = confidenceInterval(dropProbabilities);
                printStatics(noReservedCounter);
                printStatics(reservedCounter);
                blockProbabilities = new double[limit];
                dropProbabilities = new double[limit];
                System.out.printf("Block Delta:%.4f, Drop Delta:%.4f\n",blockConfidence,dropConfidence);
            }
        }

//        System.out.println("===========================");
//        System.out.println("FCA: No channel reservation");
//        printStatics(noReservedCounter);
//        System.out.printf("\nFCA: %d Channel(s) reserved for Handover\n", Counstant.RESERVED_CHANNELS);
//        printStatics(reservedCounter);

    }

    private static double confidenceInterval(double[] prob) {
        double mean = mean(prob);
        double stdD = stdD(prob, mean);
        return 1.984*stdD/Math.sqrt(prob.length);
//        return 2.262*stdD/Math.sqrt(prob.length);
//        return 3.182*stdD/Math.sqrt(prob.length);
    }

    private static double stdD(double[] array, double mean) {
        double result = 0.0;
        for (double elements:array) {
            result += Math.pow(elements-mean, 2);
        }
        return Math.sqrt(result/(array.length-1));
    }

    private static double mean(double[] array) {
        double result = 0.0;
        for (double element:array) {
            result += element;
        }
        return result/array.length;
    }

    private static void writeFile(Counter noReservedCounter, Counter reservedCounter, Counter testCounter) {
        try {
            FileWriter fw = new FileWriter("data\\result.csv",true);
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

    private static void logStatics(Counter simulator, Counter counter, double[] blockConfidence, double[] dropConfidence, int iteration) {
        counter.initialHandled += simulator.initialHandled;
        counter.blockCounter += simulator.blockCounter;
        counter.droppedCounter += simulator.droppedCounter;
        blockConfidence[(iteration-1)%10] = (double)counter.blockCounter/counter.initialHandled;
        dropConfidence[(iteration-1)%10] = (double)counter.droppedCounter/counter.initialHandled;

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
