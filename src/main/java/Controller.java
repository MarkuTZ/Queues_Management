import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controller implements ActionListener {
    private final View view;

    public Controller(View view) {
        this.view = view;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton buttonPressed = (JButton) e.getSource();
        switch (buttonPressed.getText()) {
            case "START SIMULATION" -> {
                try {
                    System.out.println("NR. CLIENTS: " + view.getNrClients());
                    System.out.println("NR. QUEUES: " + view.getNrQueues());
                    System.out.println("SIMULATION INTERVAL: " + view.getSimTime());
                    System.out.println("ARRIVAL TIME: min " + view.getMinArrival() + " max " + view.getMaxArrival());
                    System.out.println("SERVICE TIME: min " + view.getMinService() + " max " + view.getMaxService());
                } catch (NumberFormatException exception) {
                    System.out.println("INVALID NUMBERS!");
                    return;
                }
                //Simulation simulation = new Simulation(view, 5,4,10,2,4,1,4);
                Simulation simulation = new Simulation(view, view.getNrClients(), view.getNrQueues(), view.getSimTime(), view.getMinArrival(), view.getMaxArrival(), view.getMinService(), view.getMaxService());
                Thread thread = new Thread(simulation);
                thread.start();

            }
        }
    }
}