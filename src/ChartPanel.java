import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

class ChartPanel extends JPanel {
    private JFreeChart chart;
    private org.jfree.chart.ChartPanel chartPanel;
    
    public ChartPanel() {
        super();
        setLayout(new BorderLayout());
        
        // Create an empty dataset initially
        DefaultPieDataset dataset = new DefaultPieDataset();
        
        // Create chart
        chart = ChartFactory.createPieChart(
            "Top 10 Subcategories", // chart title
            dataset,                 // data
            true,                    // include legend
            true,                    // tooltips
            false                    // URLs
        );
        
        // Customize the chart
        PiePlot plot = (PiePlot) chart.getPlot();
        plot.setLabelFont(new Font("SansSerif", Font.PLAIN, 12));
        plot.setNoDataMessage("No data available");
        plot.setCircular(true);
        plot.setLabelGap(0.02);
        
        // Create the chart panel
        chartPanel = new org.jfree.chart.ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(500, 300));
        
        add(chartPanel, BorderLayout.CENTER);
    }
    
    public void updateChart(java.util.List<ScamInstance> scamList) {
        // Get top 10 subcategories (similar to StatsPanel)
        Map<String, Long> subcategoryCounts = scamList.stream()
            .filter(scam -> scam.getSubCategory() != null && !scam.getSubCategory().trim().isEmpty())
            .collect(Collectors.groupingBy(ScamInstance::getSubCategory, Collectors.counting()));
        
        // Sort by count (descending) and take top 10
        List<Map.Entry<String, Long>> topSubcategories = subcategoryCounts.entrySet().stream()
            .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
            .limit(10)
            .collect(Collectors.toList());
        
        // Update the dataset
        DefaultPieDataset dataset = new DefaultPieDataset();
        for (Map.Entry<String, Long> entry : topSubcategories) {
            dataset.setValue(entry.getKey(), entry.getValue().doubleValue());
        }
        
        // Update the chart
        ((PiePlot) chart.getPlot()).setDataset(dataset);
    }
}           