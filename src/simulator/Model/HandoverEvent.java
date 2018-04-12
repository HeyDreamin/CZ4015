package simulator.Model;

public class HandoverEvent extends Event {
    private double speed;
    private double duration;
    private boolean direction;
    private BaseStation newStation;

    public HandoverEvent(BaseStation baseStation, BaseStation newStation, double time, int id,
                         double speed, double duration, boolean direction) {
        super(baseStation, time, id, 'H');
        this.newStation = newStation;
        this.speed = speed;
        this.duration = duration;
        this.direction = direction;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public boolean isDirection() {
        return direction;
    }

    public void setDirection(boolean direction) {
        this.direction = direction;
    }

    public BaseStation getNewStation() {
        return newStation;
    }

    public void setNewStation(BaseStation newStation) {
        this.newStation = newStation;
    }
}
