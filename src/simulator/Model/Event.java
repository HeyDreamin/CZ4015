package simulator.Model;

public class Event implements Comparable {
    private BaseStation baseStation;
    private double time;
    private int id;
    public char type;

    Event(BaseStation baseStation, double time, int id, char type) {
        this.baseStation = baseStation;
        this.time = time;
        this.id = id;
        this.type = type;
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
