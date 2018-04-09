package Model;

public class InitialEvent extends Event {
    private double speed;
    private double position;
    private double duration;
    private boolean direction;

    public InitialEvent(BaseStation baseStation, double time, int id,
                        double speed, double position, double duration, boolean direction) {
        super();
        this.setBaseStation(baseStation);
        this.setTime(time);
        this.setId(id);
        this.speed = speed;
        this.position = position;
        this.duration = duration;
        this.direction = direction;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getPosition() {
        return position;
    }

    public void setPosition(double position) {
        this.position = position;
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
}
