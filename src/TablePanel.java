import javax.swing.*;
import java.awt.*;
import java.util.List;

class TablePanel extends JPanel {
    public TablePanel(List<ScamInstance> scamList) {
        super();
        
        String[] columnNames = {"URL", "Category", "Subcategory", "Description", "Reporter"};
        Object[][] data = scamList.stream()
            .map(scam -> new Object[]{
                scam.getName(), scam.getCategory(), scam.getSubCategory(),
                scam.getDescription(), scam.getReporter()
            })
            .toArray(Object[][]::new);

        JTable table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);
        
        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);
    }
}   