package simulator.Handler;

import simulator.Counter;
import simulator.Model.*;

import java.util.ArrayList;


public class EventHandler {
    private Event event;
    private Counter counter;
    private ArrayList<Event> eventList;
    private BaseStation[] baseStations;

    public EventHandler(Counter counter, ArrayList<Event> events, BaseStation[] baseStations) {
        this.counter = counter;
        this.eventList = events;
        this.baseStations = baseStations;
    }

    public void handle(Event event) {
        this.event = event;
        switch (event.type){
            case 'I':
                handleInitialEvent();
                break;
            case 'H':
                handleHandoverEvent();
                break;
            case 'T':
                handleTerminationEvent();
                break;
            default:
                System.out.println("Wrong event type.");
        }
    }

    private void handleTerminationEvent() {
        event.getBaseStation().releaseChannel();
    }

    private void handleHandoverEvent() {
        HandoverEvent handover = (HandoverEvent) event;
        handover.getBaseStation().releaseChannel();
        if (handover.getNewStation().getChannels()>0){
            handover.getNewStation().allocateChannel();
            counter.handoverCounter++;
        } else {
            counter.droppedCounter++;
            removeRelevantEvents(handover);
        }
    }

    private void removeRelevantEvents(Event event) {
        int index = 0;
        while (index<eventList.size()) {
            if (eventList.get(index).getId()==event.getId() && (eventList.get(index).type!='I')) {
//                System.out.println("Deleted:"+eventList.get(index).toString());
                eventList.remove(index);
            } else {
                index++;
            }
        }
    }

    private void handleInitialEvent() {
        InitialEvent initial = (InitialEvent) event;
        if (initial.getBaseStation().hasAvailableChannel()) {
            initial.getBaseStation().allocateChannel();
            TerminationEvent terminal = generateTerminationEvent(initial);
            insertEvent(terminal);
            generateHandoverEvent(initial,terminal);
        } else {
            counter.blockCounter++;
        }
        counter.initialHandled++;
    }

    private void generateHandoverEvent(InitialEvent initial, TerminationEvent terminal) {
        double firstTime = initial.getDirection() ? (1-initial.getPosition()):(1+initial.getPosition());
        firstTime = firstTime/initial.getSpeed() + initial.getTime();
        int curStationIndex = initial.getBaseStation().getId();
        int newStationIndex = curStationIndex;
        for (int i = 0; i < Math.abs(terminal.getBaseStation().getId()-initial.getBaseStation().getId()); i++) {
            if (initial.getDirection()){
                newStationIndex++;
            } else {
                newStationIndex--;
            }
            if (newStationIndex<0||newStationIndex>19){
                break;
            }
            insertEvent(
                    new HandoverEvent(baseStations[curStationIndex],
                            baseStations[newStationIndex],
                            firstTime+2*i/initial.getSpeed(),
                            initial.getId(),
                            initial.getSpeed(),
                            initial.getDuration(),
                            initial.getDirection())
            );
            curStationIndex = newStationIndex;
        }
    }

    private TerminationEvent generateTerminationEvent(InitialEvent initialEvent) {
        double terminatePosition = initialEvent.getTerminatePosition();
        double initialPosition = initialEvent.getBaseStation().getId()*2+1+initialEvent.getPosition();
        int stationID = (int)terminatePosition/2;
        double time = initialEvent.getTime()+initialEvent.getDuration();
        if (terminatePosition<0) {
            stationID = 0;
            time = initialEvent.getTime() + initialPosition/initialEvent.getSpeed();
        } else if (terminatePosition>40) {
            stationID = 19;
            time = initialEvent.getTime() + (40-initialPosition)/initialEvent.getSpeed();
        }
        return new TerminationEvent(baseStations[stationID], time, initialEvent.getId());
    }

    private void insertEvent(Event event) {
        int index = 0;
        for (Event e : eventList) {
            if (e.compareTo(event)==1){
                break;
            } else if (e.compareTo(event)==-1){
                index++;
            }
        }
        eventList.add(index, event);
    }
}
