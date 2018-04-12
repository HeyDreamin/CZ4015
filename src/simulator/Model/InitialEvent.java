package simulator.Model;

public class InitialEvent extends Event {
    private double speed;
    private double position;
    private double duration;
    private boolean direction;

    public InitialEvent(BaseStation baseStation, double time, int id,
                        double speed, double position, double duration, boolean direction) {
        super(baseStation, time, id, 'I');
        this.speed = speed/3600;
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

    public boolean getDirection() {
        return direction;
    }

    public void setDirection(boolean direction) {
        this.direction = direction;
    }

    public double getTerminatePosition() {
        double newPosition;
        if (direction) {
            newPosition = 2*getBaseStation().getId()+1+position + speed*duration;
        } else {
            newPosition = 2*getBaseStation().getId()+1+position - speed*duration;
        }
        return newPosition;
    }
}
