public class Client {
    private int ID;
    private int timeArrival;
    private int timeService;

    public Client(int ID, int timeArrival, int timeService) {
        this.ID = ID;
        this.timeArrival = timeArrival;
        this.timeService = timeService;
    }

    public int getTimeArrival() {
        return timeArrival;
    }

    public int getTimeService() {
        return timeService;
    }
}
