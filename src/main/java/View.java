import javax.swing.*;

public class View extends JFrame{
    private JPanel mainPanel;
    private JPanel settingsPanel;

    private JTextField nrClients;
    private JTextField nrQueues;
    private JTextField simTime;
    private JTextField minArrival;
    private JTextField maxArrival;
    private JTextField minService;
    private JTextField maxService;
    private JButton startButton;
    private JProgressBar progressBar;
    public JPanel queuesPanel;

    private Controller controller;
    public View() {
        {
            this.controller = new Controller(this);
            setContentPane(mainPanel);
            setTitle("QUEUES MANAGEMENT");
            setSize(800, 800);
            setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            pack();
            setVisible(true);
        }
        startButton.addActionListener(controller);
    }

    public int getNrClients() throws NumberFormatException {
        return Integer.parseInt(nrClients.getText());
    }
    public int getNrQueues() throws NumberFormatException {
        return Integer.parseInt(nrQueues.getText());
    }
    public int getSimTime() throws NumberFormatException {
        return Integer.parseInt(simTime.getText());
    }
    public int getMinArrival() throws NumberFormatException {
        return Integer.parseInt(minArrival.getText());
    }
    public int getMaxArrival() throws NumberFormatException {
        return Integer.parseInt(maxArrival.getText());
    }
    public int getMinService() throws NumberFormatException {
        return Integer.parseInt(minService.getText());
    }
    public int getMaxService() throws NumberFormatException {
        return Integer.parseInt(maxService.getText());
    }
    public void setProgressBar(int value) {
        this.progressBar.setValue(value);
    }

    public static void main(String[] args) {
        View myView = new View();
    }
}
