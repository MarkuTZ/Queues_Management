import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class Queue implements Runnable{
    private BlockingQueue<Client> clients;
    private AtomicInteger qTime;

    @Override
    public void run() {

    }

    public void addClient(Client newClient) {
        clients.add(newClient);
        //qtime.
    }
}
