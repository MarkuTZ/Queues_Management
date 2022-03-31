public class Client {
    private int ID;
    private int timeArrival;
    private int timeService;

    public Client(int ID, int timeArrival, int timeService) {
        this.ID = ID;
        this.timeArrival = timeArrival;
        this.timeService = timeService;
    }

    @Override
    public String toString() {
        return  "(" + ID +
                ", " + timeArrival +
                ", " + timeService +
                ')';
    }

    public int getTimeArrival() {
        return timeArrival;
    }

    public int getTimeService() {
        return timeService;
    }

    public int getID() {
        return ID;
    }

    public void decreaseTimeService() {
        this.timeService--;
    }
}
