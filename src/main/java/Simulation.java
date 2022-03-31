import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class Simulation implements Runnable{
    private int nrClients;
    private int nrQueues;

    private int tMAXSimulation;
    private int tMINArrival;
    private int tMAXArrival;
    private int tMINService;
    private int tMAXService;

    private AtomicInteger simulationTime;
    private BlockingQueue<Queue> queues;
    private ArrayList<Client> clients;

    private AtomicBoolean queuesWorking = new AtomicBoolean(true);
    private AtomicBoolean simEnded = new AtomicBoolean(false);

    private View view;
    public Simulation(View view, int nrClients, int nrQueues, int tMAXSimulation, int tMINArrival, int tMAXArrival, int tMINService, int tMAXService) {
        this.view = view;
        this.nrClients = nrClients;
        this.nrQueues = nrQueues;
        this.tMAXSimulation = tMAXSimulation;
        this.tMINArrival = tMINArrival;
        this.tMAXArrival = tMAXArrival;
        this.tMINService = tMINService;
        this.tMAXService = tMAXService;
    }

    @Override
    public void run() {
        //initializing
        simulationTime = new AtomicInteger(0);
        int MAXArrival = clientsGenerator();
        queues = new LinkedBlockingQueue<>();

        view.queuesPanel.setLayout(new GridLayout(0, 1));
        LinkedList<JLabel> labels = new LinkedList<>();
        JLabel waitingLabel = new JLabel("WAITING:");
        view.queuesPanel.add(waitingLabel);
        view.queuesPanel.revalidate();
        view.queuesPanel.repaint();
        for (int i = 1; i <= nrQueues; i++) {
            Queue q = new Queue(i);
            queues.add(q);
            Thread newThread = new Thread(q);
            newThread.start();

            JLabel newLabel = new JLabel("Q[" + i + "] = ");
            labels.add(newLabel);
            view.queuesPanel.add(newLabel);
            view.queuesPanel.revalidate();
            view.queuesPanel.repaint();
        }
        view.queuesPanel.setPreferredSize(new Dimension(800, 400));
        view.pack();
        while( !(simEnded.get())) {
            view.queuesPanel.setBorder(BorderFactory.createTitledBorder("SIMULATION TIME: " + simulationTime.get()));
            view.setProgressBar(100 * simulationTime.get() / MAXArrival);

            for (Client c : clients) {
                if (c.getTimeArrival() == simulationTime.get()) {
                    Queue chosen = queues.peek();
                    for (Queue q : queues) {
                        if (q.getqTime().get() < chosen.getqTime().get())
                            chosen = q;
                    }
                    chosen.addClient(c);
                }
            }
            queuesWorking.set(false); //Finding if all the queues are empty
            for (Queue q : queues) {
                q.setSimEnded(simEnded.get());
                if (!q.isEmpty())
                    queuesWorking.set(true);
            }

            showWaitingClients(waitingLabel);
            showQueues(labels);

            //view.pack();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) { }

            if (simulationTime.incrementAndGet() > MAXArrival)
                simEnded.set(true);
        }
        int totalWaitingTime = 0;
        for (Queue q : queues) {
            totalWaitingTime += q.getWaitingTime();
        }
        waitingLabel.setText(waitingLabel.getText() + "| AVERAGE TIME: " + (1.0 * totalWaitingTime / nrClients));

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) { }
        //Retrieving all the waiting times to calculate the average

        view.queuesPanel.remove(waitingLabel);
        view.queuesPanel.revalidate();
        view.queuesPanel.repaint();
        view.validate();
        for(JLabel label : labels) {
            view.queuesPanel.remove(label);
            view.queuesPanel.revalidate();
            view.queuesPanel.repaint();
        }
        view.pack();
    }
    private void showWaitingClients(JLabel waitingLabel) {
        String buffClients = "";
        for (Client c : clients)
            if (c.getTimeArrival() > simulationTime.get())
                buffClients += c.toString() + " ";
        if (!buffClients.equals(""))
            buffClients = "WAITING: " + buffClients;
        else
            buffClients = "WAITING QUEUE IS EMPTY!";
        waitingLabel.setText(buffClients);
    }
    private void showQueues(LinkedList<JLabel> labels) {
        String buff = "";
        int i = 0;
        for (Queue q : queues) {
            labels.get(i).setText(q.toString());
            i++;
        }
    }
    private int clientsGenerator() {
        ArrayList<Client> generatedList = new ArrayList<>();
        Random random = new Random();
        int max = 0;
        for (int i = 1; i <= nrClients; i++) {
            Client generated = new Client(
                    i,
                    random.nextInt(tMAXArrival - tMINArrival) + tMINArrival,
                    random.nextInt(tMAXService - tMINService) + tMINService
            );
            generatedList.add(generated);
            if (generated.getTimeArrival() > max)
                max = generated.getTimeArrival();
        }
        this.clients = generatedList;
        return max;
    }

    public static void main(String[] args) throws IOException {
//        File file = new File("M:\\CTI\\AN II\\SEM II\\Tehnici de Programare\\PT2022_302210_Ciurea_Mark_Assignment_2\\TEST3.txt");
//        PrintStream stream = new PrintStream(file);
//        System.setOut(stream);

        Simulation sim = new Simulation(null,10,3,20,1,10,2,4);
        Thread thread = new Thread(sim);
        thread.start();
    }
}