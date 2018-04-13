package simulator.Model;

public class TerminationEvent extends Event {

    public TerminationEvent(BaseStation baseStation, double time, int id) {
        super(baseStation, time, id, 'T', true, 0);
    }

}
