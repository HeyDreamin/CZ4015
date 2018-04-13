package simulator.Model;

public class InitialEvent extends Event {
    private double position;
    private double duration;

    public InitialEvent(BaseStation baseStation, double time, int id,
                        double speed, double position, double duration, boolean direction) {
        super(baseStation, time, id, 'I', direction, speed/3600);
        this.position = position;
        this.duration = duration;
    }

    public double getPosition() {
        return position;
    }

    public void setPosition(double position) {
        this.position = position;
    }

    @Override
    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public double getTerminatePosition() {
        double newPosition;
        if (this.direction()) {
            newPosition = 2*getBaseStation().getId()+1+position + getSpeed()*duration;
        } else {
            newPosition = 2*getBaseStation().getId()+1+position - getSpeed()*duration;
        }
        return newPosition;
    }

    @Override
    public int getTerminateID() {
        return (int)getTerminatePosition()/2;
    }

    @Override
    public double getRemainTime() {
        return direction() ? (1-position)/getSpeed():(1+position)/getSpeed();
    }
}
