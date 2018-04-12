package simulator;

public class Counter {
    public int blockCounter = 0;
    public int droppedCounter = 0;
    public int handoverCounter = 0;
    public int initialHandled = 0;

    public Counter(){
        initial();
    }

    public void initial() {
        this.blockCounter = 0;
        this.droppedCounter = 0;
        this.handoverCounter = 0;
        this.initialHandled = 0;
    }

}
