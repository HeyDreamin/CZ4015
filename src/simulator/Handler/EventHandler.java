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
            if (terminateInCurrentStation(handover)){
                insertEvent(new TerminationEvent(baseStations[handover.getNewStation().getId()],
                        handover.getTime()+handover.getDuration(), handover.getId()));
            } else {
                generateHandoverEvent(handover);
            }
        } else {
            counter.droppedCounter++;
        }
    }

    private void handleInitialEvent() {
        InitialEvent initial = (InitialEvent) event;
        if (initial.getBaseStation().hasAvailableChannel()) {
            initial.getBaseStation().allocateChannel();
            if (terminateInCurrentStation(initial)) {
                insertEvent(
                        new TerminationEvent(baseStations[initial.getBaseStation().getId()],
                                initial.getTime()+initial.getDuration(),initial.getId())
                );
            } else {
                generateHandoverEvent(initial);
            }
        } else {
            counter.blockCounter++;
        }
        counter.initialHandled++;
    }

    private boolean terminateInCurrentStation(Event event) {
        return event.getBaseStation().getId()==event.getTerminateID();
    }

    private void generateHandoverEvent(Event event) {
        int curStationIndex = event.getBaseStation().getId();
        if (event instanceof HandoverEvent) {
            curStationIndex = ((HandoverEvent) event).getNewStation().getId();
        }
        int newStationIndex;
        if (event.direction()){
            newStationIndex = curStationIndex + 1;
        } else {
            newStationIndex = curStationIndex - 1;
        }
        if (newStationIndex>19||newStationIndex<0) {
            insertEvent(new TerminationEvent(baseStations[curStationIndex],
                    event.getTime()+event.getRemainTime(), event.getId()));
        } else {
            insertEvent(
                    new HandoverEvent(baseStations[curStationIndex],
                            baseStations[newStationIndex],
                            event.getTime() + event.getRemainTime(),
                            event.getId(),
                            event.getSpeed(),
                            event.getDuration() - event.getRemainTime(),
                            event.direction())
            );
        }
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
