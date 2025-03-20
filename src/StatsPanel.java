import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;

class StatsPanel extends JPanel {
    private JTable statsTable;
    private DefaultTableModel tableModel;
    
    public StatsPanel() {
        super();
        setLayout(new BorderLayout());
        
        // Create table for statistics
        String[] columnNames = {"Statistic", "Value"};
        tableModel = new DefaultTableModel(columnNames, 0);
        statsTable = new JTable(tableModel);
        
        // Make table look better
        statsTable.setRowHeight(25);
        statsTable.getColumnModel().getColumn(0).setPreferredWidth(200);
        statsTable.getColumnModel().getColumn(1).setPreferredWidth(100);
        
        JScrollPane scrollPane = new JScrollPane(statsTable);
        add(scrollPane, BorderLayout.CENTER);
    }
    
    public void updateStats(java.util.List<ScamInstance> scamList) {
        // Filter out blank entries
        java.util.List<ScamInstance> validScams = scamList.stream()
            .filter(scam -> scam.getName() != null && !scam.getName().trim().isEmpty())
            .filter(scam -> scam.getCategory() != null)
            .collect(Collectors.toList());
            
        // Clear previous stats
        tableModel.setRowCount(0);
        
        // 1. Total number of scams
        tableModel.addRow(new Object[]{"Total Scams Reported", validScams.size()});
        
        // 2. Scams by category (count and percentage)
        Map<String, Long> categoryStats = validScams.stream()
            .collect(Collectors.groupingBy(
                scam -> scam.getCategory().toString(),
                Collectors.counting()
            ));
        
        tableModel.addRow(new Object[]{"--- Scams by Category ---", ""});
        categoryStats.entrySet().stream()
            .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
            .forEach(entry -> {
                double percentage = (entry.getValue() * 100.0) / validScams.size();
                tableModel.addRow(new Object[]{
                    entry.getKey(), 
                    String.format("%d (%.1f%%)", entry.getValue(), percentage)
                });
            });
            
        // 3. Top 10 subcategories
        tableModel.addRow(new Object[]{"--- Top 10 Subcategories ---", ""});
        validScams.stream()
            .collect(Collectors.groupingBy(ScamInstance::getSubCategory, Collectors.counting()))
            .entrySet().stream()
            .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
            .limit(10)
            .forEach(entry -> {
                double percentage = (entry.getValue() * 100.0) / validScams.size();
                tableModel.addRow(new Object[]{
                    entry.getKey(), 
                    String.format("%d (%.1f%%)", entry.getValue(), percentage)
                });
            });
            
        // 4. Top reporters
        tableModel.addRow(new Object[]{"--- Top 5 Reporters ---", ""});
        validScams.stream()
            .filter(scam -> scam.getReporter() != null && !scam.getReporter().trim().isEmpty())
            .collect(Collectors.groupingBy(ScamInstance::getReporter, Collectors.counting()))
            .entrySet().stream()
            .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
            .limit(5)
            .forEach(entry -> {
                tableModel.addRow(new Object[]{entry.getKey(), entry.getValue()});
            });
    }
}   