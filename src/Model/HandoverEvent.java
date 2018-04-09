package Model;

public class HandoverEvent extends Event {
    private double speed;
    private double duration;
    private boolean direction;

    public HandoverEvent(BaseStation baseStation, double time, int id,
                         double speed, double duration, boolean direction) {
        super();
        this.setBaseStation(baseStation);
        this.setTime(time);
        this.setId(id);
        this.speed = speed;
        this.duration = duration;
        this.direction = direction;
    }
}
