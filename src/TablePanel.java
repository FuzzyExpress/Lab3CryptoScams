import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.util.List;

class TablePanel extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;
    private String[] columnNames = {"URL", "Category", "Subcategory", "Description", "Reporter"};
    
    // Add a field for the selection listener
    private TableSelectionListener selectionListener;
    
    // Add this to the constructor after table initialization
    public void setSelectionListener(TableSelectionListener listener) {
        this.selectionListener = listener;
        
        // Add row selection listener with null check
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && currentScamList != null) {  // Add null check here
                int selectedRow = table.getSelectedRow();
                if (selectedRow >= 0) {
                    // Convert view row index to model row index in case of sorting
                    int modelRow = table.convertRowIndexToModel(selectedRow);
                    
                    
                    // Get data from the selected row
                    String url = tableModel.getValueAt(modelRow, 0).toString();
                    
                    // Find the matching scam
                    for (ScamInstance scam : currentScamList) {
                        if (scam.getName().equals(url)) {
                            if (selectionListener != null) {
                                selectionListener.onScamSelected(scam);
                            }
                            break;
                        }
                    }
                }
            }
        });
    }
    
    // Also add this field to keep track of the current list
    private java.util.List<ScamInstance> currentScamList;
    
    public TablePanel(List<ScamInstance> scamList) {
        super();
        
        // Initialize currentScamList first
        this.currentScamList = scamList;
        
        // Create table model
        tableModel = new DefaultTableModel(columnNames, 0);
        updateTableData(scamList);
        
        // Create table with data
        table = new JTable(tableModel);
        
        // Add sorting
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(tableModel);
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
    
    public void updateData(List<ScamInstance> scamList) {
        this.currentScamList = scamList;
        // Clear existing data
        tableModel.setRowCount(0);
        
        // Add new data
        updateTableData(scamList);
        
        // Notify the table that data has changed
        tableModel.fireTableDataChanged();
    }
    
    private void updateTableData(List<ScamInstance> scamList) {
        for (ScamInstance scam : scamList) {
            // Skip entries with blank name or category
            if (scam.getName() == null || scam.getName().trim().isEmpty() || 
                scam.getCategory() == null) {
                continue;
            }
            
            tableModel.addRow(new Object[]{
                scam.getName(),
                scam.getCategory(),
                scam.getSubCategory(),
                scam.getDescription(),
                scam.getReporter()
            });
        }
    }
    
    public JTable getTable() {
        return table;
    }
    
    // Add this interface
    public interface TableSelectionListener {
        void onScamSelected(ScamInstance scam);
    }
}   