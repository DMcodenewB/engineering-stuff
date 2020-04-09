public class Request {
                                                        //klasa Request, jej pola i metody

    private int location;
    private boolean isPrioritized;
    private int arrivalTime;
    private int deadline;

    public Request(int loc, int at){
        this.location = loc;
        this.arrivalTime = at;
    }

    public Request(int loc, int at, boolean isP, int dl){
        this.location = loc;
        this.arrivalTime = at;
        this.isPrioritized = isP;
        this.deadline = dl;
    }

    public int getLocation() {
        return location;
    }

    public void setLocation(int location) {
        this.location = location;
    }

    public boolean isPrioritized() {
        return isPrioritized;
    }

    public void setPrioritized(boolean prioritized) {
        isPrioritized = prioritized;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public int getDeadline() {
        return deadline;
    }

    public void setDeadline(int deadline) {
        this.deadline = deadline;
    }




    public String toString(){
        return "Request:\tArrival time : " + String.format("%3d", arrivalTime) +"\tLocation : " + String.format("%5d", location)
                + "\tisPrioritized: " + String.format("%7s", isPrioritized);
    }






}
