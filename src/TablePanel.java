import javax.swing.*;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.util.List;

class TablePanel extends JPanel {
    private JTable table;
    
    public TablePanel(List<ScamInstance> scamList) {
        super();
        
        String[] columnNames = {"URL", "Category", "Subcategory", "Description", "Reporter"};
        Object[][] data = scamList.stream()
            .map(scam -> new Object[]{
                scam.getName(), scam.getCategory(), scam.getSubCategory(),
                scam.getDescription(), scam.getReporter()
            })
            .toArray(Object[][]::new);

        // Create table with data
        table = new JTable(data, columnNames);
        
        // Add sorting
        TableRowSorter<javax.swing.table.TableModel> sorter = new TableRowSorter<>(table.getModel());
        table.setRowSorter(sorter);
        
        // Enable selection
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setAutoCreateRowSorter(true);
        
        // Make table fill viewport
        table.setFillsViewportHeight(true);
        
        JScrollPane scrollPane = new JScrollPane(table);
        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);
    }
    
    public JTable getTable() {
        return table;
    }
}   