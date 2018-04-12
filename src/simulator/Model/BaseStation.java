package simulator.Model;

public class BaseStation {
    private int channels;
    private int id;
    private boolean policy;
    private int reserved;

    public BaseStation(int id, int reserved){
        this.id = id;
        this.channels = 10;
        this.reserved = reserved;
    }

    public boolean useReservation() {
        return policy;
    }

    public void setPolicy(boolean policy) {
        this.policy = policy;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getChannels() {
        return channels;
    }

    public void setChannels(int channels) {
        this.channels = channels;
    }

    public void releaseChannel() {
        if (this.channels!=10)
            this.channels++;
    }

    public void allocateChannel() {
        this.channels--;
    }

    public boolean hasAvailableChannel() {
        return this.policy ? this.channels>this.reserved:this.channels>0;
    }

}
