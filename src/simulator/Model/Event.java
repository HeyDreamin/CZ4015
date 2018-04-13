package simulator.Model;

public class Event implements Comparable {
    private BaseStation baseStation;
    private double time;
    private int id;
    public char type;
    private boolean direction;
    private double speed;

    Event(BaseStation baseStation, double time, int id, char type, boolean direction, double speed) {
        this.baseStation = baseStation;
        this.time = time;
        this.id = id;
        this.type = type;
        this.direction = direction;
        this.speed = speed;
    }

    public BaseStation getBaseStation() {
        return baseStation;
    }

    public void setBaseStation(BaseStation baseStation) {
        this.baseStation = baseStation;
    }

    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean direction() {
        return direction;
    }

    public void setDirection(boolean direction) {
        this.direction = direction;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public int getTerminateID(){
        return baseStation.getId();
    }

    public double getDuration() { return 0.0; }

    public double getRemainTime() { return 0.0; }

    @Override
    public int compareTo(Object o) {
        if (this.time>((Event)o).time)
            return 1;
        else
            return -1;
    }

    @Override
    public String toString() {
        return String.format("Event:%d Type:%c Time:%.3f Station:%d",id,type,time,baseStation.getId()+1);
    }
}
