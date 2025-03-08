import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MainGUI extends JFrame {
    public MainGUI() {
        // Main Window
        setTitle("Crypto Scam Tracker");
        List<ScamInstance> scamList = LoadData.loadData();


        Dimension windowSize = new Dimension(800, 600);
        setPreferredSize(windowSize);

        // Set window location
        setLocation(
                (Toolkit.getDefaultToolkit().getScreenSize().width  / 2) - (int) (windowSize.getWidth() / 2),
                (Toolkit.getDefaultToolkit().getScreenSize().height / 3) - (int) (windowSize.getHeight() / 2)
        );

        // Create tabbed pane
        JTabbedPane tabbedPane = new JTabbedPane();
        
        // Add panels to tabs
        tabbedPane.addTab("Table", new TablePanel());
        tabbedPane.addTab("Statistics", new StatsPanel());
        tabbedPane.addTab("Charts", new ChartPanel());
        tabbedPane.addTab("Details", new DetailsPanel());

        // Add tabbed pane to frame
        add(tabbedPane);

        setExtendedState(JFrame.MAXIMIZED_BOTH);  // Make window maximized
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainGUI());
    }
}
