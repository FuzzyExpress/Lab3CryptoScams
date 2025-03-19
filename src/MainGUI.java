import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MainGUI extends JFrame {
    public MainGUI() {
        // Main Window
        setTitle("Crypto Scam Tracker");
        List<ScamInstance> scamList = LoadData.loadData();

        // Create all panels
        TablePanel tablePanel = new TablePanel(scamList);
        
        // Add placeholder text to empty panels
        StatsPanel statsPanel = new StatsPanel();
        statsPanel.add(new JLabel("Statistics Panel"));
        
        ChartPanel chartPanel = new ChartPanel();
        chartPanel.add(new JLabel("Chart Panel"));
        
        DetailsPanel detailsPanel = new DetailsPanel();
        detailsPanel.add(new JLabel("Details Panel"));

        // Create left side split (table and details)
        JSplitPane leftSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
                tablePanel, detailsPanel);
        leftSplit.setResizeWeight(0.75); // 75% top, 25% bottom

        // Create right side split (chart and stats)
        JSplitPane rightSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
                chartPanel, statsPanel);
        rightSplit.setResizeWeight(0.5); // 50% top, 50% bottom

        // Create main split (left and right panels)
        JSplitPane mainSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                leftSplit, rightSplit);
        mainSplit.setResizeWeight(0.7); // 70% left, 30% right

        // Add to frame
        add(mainSplit);

        Dimension windowSize = new Dimension(800, 600);
        setPreferredSize(windowSize);

        // Set window location
        setLocation(
                (Toolkit.getDefaultToolkit().getScreenSize().width  / 2) - (int) (windowSize.getWidth() / 2),
                (Toolkit.getDefaultToolkit().getScreenSize().height / 3) - (int) (windowSize.getHeight() / 2)
        );

        setExtendedState(JFrame.MAXIMIZED_BOTH);  // Make window maximized
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainGUI::new);
    }
}
