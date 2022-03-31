import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class Queue implements Runnable{
    private int qID;
    private BlockingQueue<Client> clients;
    private AtomicInteger qTime = new AtomicInteger(0);
    private int waitingTime = 0;
    private boolean simEnded = false;

    public Queue(int qID) {
        clients = new LinkedBlockingQueue<>();
        this.qID = qID;
    }

    @Override
    public void run() {
        while( !simEnded || !isEmpty()) {
            Client c = clients.peek();
            if (c != null) {
                c.decreaseTimeService();
                qTime.decrementAndGet();
                if(c.getTimeService() == 0) clients.remove();
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {}

        }
        //System.out.println("Q[" + qID + "] NO CLIENTS: " + noClients + " WAITING TIME: " + waitingTime +
        //        " AVERAGE WAITING TIME: "  + 1.*waitingTime/noClients);
    }
    public void setSimEnded(boolean simStatus) {
        this.simEnded = simStatus;
    }
    public void addClient(Client newClient) {
        clients.add(newClient);
        waitingTime += qTime.getAndAdd(newClient.getTimeService());
    }
    public boolean isEmpty() {
        return clients.isEmpty();
    }
    public AtomicInteger getqTime() {
        return qTime;
    }

    public int getWaitingTime() {
        return waitingTime;
    }

    @Override
    public String toString() {
        String buff = "Q[" + qID + "] = ";
        for (Client c : clients) {
            buff += "[" + c.getID() + ", " + c.getTimeArrival() + ", " + c.getTimeService() + "] ";
        }
        if (buff.equals("Q[" + qID + "] = "))
            buff += "EMPTY";
        else
            buff += "(" + qTime.get() +")";
        return buff;
    }
}
