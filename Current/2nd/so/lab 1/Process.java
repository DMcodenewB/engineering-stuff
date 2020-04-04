public class Process {

    int id;
    int arrival_time;
    int burst_time;
    int remaining_burst_time;
    int waiting_time;

    public Process(int id, int at, int bt){
        this.id = id;
        this.arrival_time = at;
        this.burst_time = bt;
        this.remaining_burst_time = bt;
        this.waiting_time = 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getArrival_time() {
        return arrival_time;
    }

    public void setArrival_time(int arrival_time) {
        this.arrival_time = arrival_time;
    }

    public int getBurst_time() {
        return burst_time;
    }

    public void setBurst_time(int burst_time) {
        this.burst_time = burst_time;
    }

    public int getRemaining_burst_time() {
        return remaining_burst_time;
    }

    public void setRemaining_burst_time(int remaining_burst_time) {
        this.remaining_burst_time = remaining_burst_time;
    }

    public int getWaiting_time() {
        return waiting_time;
    }

    public void setWaiting_time(int waiting_time) {
        this.waiting_time = waiting_time;
    }

    public String toString(){
        return String.format("Id: %6d", getId()) + "\t\t" + String.format("at: %5d", getArrival_time())
                + "\t\t" + String.format("bt: %3d", getBurst_time());
    }
}
