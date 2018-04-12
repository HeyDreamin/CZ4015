package simulator.App;

import simulator.Counstant;
import simulator.Counter;
import simulator.Handler.EventHandler;
import simulator.Model.BaseStation;
import simulator.Model.Event;

import java.util.ArrayList;

public class Simulator {
    private ArrayList<Event> events = new ArrayList<Event>();
    private BaseStation[] baseStations;
    public Counter counter = new Counter();

    public Simulator(boolean policy, ArrayList<Event> eventList, BaseStation[] baseStations) {
        initial();
        this.events = eventList;
        this.baseStations = baseStations;
        for (int i = 0; i < Counstant.STATIONS; i++) {
            baseStations[i].setPolicy(policy);
        }
    }

    public void initial(){
        counter.initial();
        events.clear();
    }

    public void simulate() {
        Event event;
        EventHandler eventHandler = new EventHandler(counter, events, baseStations);
        boolean warmed = false;
        while (!events.isEmpty()) {
            event = events.get(0);
            eventHandler.handle(event);
            if (counter.initialHandled==Counstant.WARM_UP_PERIOD && !warmed) {
                counter.initial();
                warmed = true;
            }
            events.remove(event);
        }
    }

    public int printStations(){
        int s = 0;
        for (int i = 0; i < 20; i++) {
            System.out.print("No."+(i+1)+":"+baseStations[i].getChannels()+" ");
        }
        System.out.println(s);
        return s;
    }

    public void setEvents(ArrayList<Event> events) {
        this.events = events;
    }

    public ArrayList<Event> getEvents() {
        return events;
    }

}
