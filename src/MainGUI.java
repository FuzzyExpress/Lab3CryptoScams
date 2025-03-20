import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.stream.Collectors;

public class MainGUI extends JFrame {
    private TablePanel tablePanel;
    private java.util.List<ScamInstance> scamList;
    private java.util.List<ScamInstance> filteredScamList;
    
    public MainGUI() {
        // Main Window
        setTitle("Crypto Scam Tracker");
        scamList = LoadData.loadData();
        filteredScamList = new java.util.ArrayList<>(scamList);

        // Create all panels
        tablePanel = new TablePanel(filteredScamList);
        
        // Add placeholder text to empty panels
        StatsPanel statsPanel = new StatsPanel();
        statsPanel.updateStats(scamList);
        
        ChartPanel chartPanel = new ChartPanel();
        chartPanel.add(new JLabel("Chart Panel"));
        
        DetailsPanel detailsPanel = new DetailsPanel();
        detailsPanel.add(new JLabel("Details Panel"));
        
        // Get unique categories
        java.util.List<String> categories = scamList.stream()
                .map(scam -> scam.getCategory().toString())
                .distinct()
                .sorted()
                .collect(Collectors.toList());
        
        // Create filter panel
        FilterPanel filterPanel = new FilterPanel(categories, (String searchText, Set<String> selectedCategories) -> {
            // Filter the scam list based on search text and selected categories
            filteredScamList.clear();
            
            for (ScamInstance scam : scamList) {
                // Check if category is selected
                if (!selectedCategories.contains(scam.getCategory().toString())) {
                    continue;
                }
                
                // Check if search text is in any field
                if (!searchText.isEmpty()) {
                    String searchLower = searchText.toLowerCase();
                    boolean matches = scam.getName().toLowerCase().contains(searchLower) ||
                                     scam.getCategory().toString().toLowerCase().contains(searchLower) ||
                                     scam.getSubCategory().toLowerCase().contains(searchLower) ||
                                     scam.getDescription().toLowerCase().contains(searchLower) ||
                                     scam.getReporter().toLowerCase().contains(searchLower);
                    
                    if (!matches) {
                        continue;
                    }
                }
                
                filteredScamList.add(scam);
            }
            
            // Update the table with filtered data
            tablePanel.updateData(filteredScamList);
            
            // Update stats panel with filtered data
            statsPanel.updateStats(filteredScamList);
        });

        // Create the main panel structure
        JPanel mainPanel = new JPanel(new BorderLayout());
        
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
        
        // Add filter panel at the top and main split in the center
        mainPanel.add(filterPanel, BorderLayout.NORTH);
        mainPanel.add(mainSplit, BorderLayout.CENTER);

        // Add to frame
        add(mainPanel);

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
