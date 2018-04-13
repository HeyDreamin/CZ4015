package simulator.Model;

public class HandoverEvent extends Event {
    private double duration;
    private BaseStation newStation;

    public HandoverEvent(BaseStation baseStation, BaseStation newStation, double time, int id,
                         double speed, double duration, boolean direction) {
        super(baseStation, time, id, 'H', direction, speed);
        this.newStation = newStation;
        this.duration = duration;
    }

    @Override
    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public BaseStation getNewStation() {
        return newStation;
    }

    public void setNewStation(BaseStation newStation) {
        this.newStation = newStation;
    }

    @Override
    public int getTerminateID() {
        int currentStation = getBaseStation().getId();
        if (direction()){
            return getSpeed()*duration>2 ? currentStation+1:currentStation;
        } else {
            return getSpeed()*duration>2 ? currentStation-1:currentStation;
        }
    }

    @Override
    public double getRemainTime() {
        return getTerminateID()==getBaseStation().getId()? duration:2/getSpeed();
    }
}
