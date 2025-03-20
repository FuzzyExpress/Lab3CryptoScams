import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

class FilterPanel extends JPanel {
    private JTextField searchField;
    private Map<String, JToggleButton> categoryToggleButtons = new HashMap<>();
    
    public FilterPanel(java.util.List<String> categories, FilterListener listener) {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Search panel
        JPanel searchPanel = new JPanel(new BorderLayout());
        JLabel searchLabel = new JLabel("Search: ");
        searchField = new JTextField(20);
        searchField.addActionListener(e -> fireSearchChanged(listener));
        searchField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) { fireSearchChanged(listener); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { fireSearchChanged(listener); }
            public void changedUpdate(javax.swing.event.DocumentEvent e) { fireSearchChanged(listener); }
        });
        
        searchPanel.add(searchLabel, BorderLayout.WEST);
        searchPanel.add(searchField, BorderLayout.CENTER);
        
        // Categories panel
        JPanel categoriesPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        categoriesPanel.add(new JLabel("Categories: "));
        
        for (String category : categories) {
            JToggleButton toggleButton = new JToggleButton(category);
            toggleButton.setSelected(true);
            toggleButton.addActionListener(e -> fireCategoryChanged(listener));
            categoryToggleButtons.put(category, toggleButton);
            categoriesPanel.add(toggleButton);
        }
        
        // Combine panels
        JPanel filtersPanel = new JPanel(new BorderLayout());
        filtersPanel.add(searchPanel, BorderLayout.NORTH);
        filtersPanel.add(categoriesPanel, BorderLayout.CENTER);
        
        add(filtersPanel, BorderLayout.CENTER);
    }
    
    private void fireSearchChanged(FilterListener listener) {
        if (listener != null) {
            listener.onFilterChanged(getSearchText(), getSelectedCategories());
        }
    }
    
    private void fireCategoryChanged(FilterListener listener) {
        if (listener != null) {
            listener.onFilterChanged(getSearchText(), getSelectedCategories());
        }
    }
    
    public String getSearchText() {
        return searchField.getText();
    }
    
    public Set<String> getSelectedCategories() {
        Set<String> selected = new HashSet<>();
        for (Map.Entry<String, JToggleButton> entry : categoryToggleButtons.entrySet()) {
            if (entry.getValue().isSelected()) {
                selected.add(entry.getKey());
            }
        }
        return selected;
    }
    
    // Interface for filter change listeners
    public interface FilterListener {
        void onFilterChanged(String searchText, Set<String> selectedCategories);
    }
} 