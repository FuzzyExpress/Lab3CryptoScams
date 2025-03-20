import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;

class DetailsPanel extends JPanel {
    private JLabel titleLabel;
    private JLabel urlLabel;
    private JLabel categoryLabel;
    private JLabel subcategoryLabel;
    private JLabel descriptionLabel;
    private JLabel reporterLabel;
    private JLabel addressesLabel;
    private JLabel nameLabel;
    
    private JTextArea urlField;
    private JTextField categoryField;
    private JTextField subcategoryField;
    private JTextArea descriptionField;
    private JTextField reporterField;
    private JTextArea addressesField;
    private JTextField nameField;
    
    public DetailsPanel() {
        super();
        
        // Set up panel
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(10, 10, 10, 10));
        
        // Header
        titleLabel = new JLabel("Scam Details");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        
        // Create form panel with GridBagLayout for form fields
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 5, 5, 5);
        c.fill = GridBagConstraints.HORIZONTAL;
        
        // URL
        urlLabel = new JLabel("URL:");
        urlField = new JTextArea(2, 20);
        urlField.setEditable(false);
        urlField.setLineWrap(true);
        urlField.setWrapStyleWord(true);
        JScrollPane urlScroll = new JScrollPane(urlField);
        
        // Name
        nameLabel = new JLabel("Name:");
        nameField = new JTextField(20);
        nameField.setEditable(false);
        
        // Category
        categoryLabel = new JLabel("Category:");
        categoryField = new JTextField(20);
        categoryField.setEditable(false);
        
        // Subcategory
        subcategoryLabel = new JLabel("Subcategory:");
        subcategoryField = new JTextField(20);
        subcategoryField.setEditable(false);
        
        // Description
        descriptionLabel = new JLabel("Description:");
        descriptionField = new JTextArea(3, 20);
        descriptionField.setEditable(false);
        descriptionField.setLineWrap(true);
        descriptionField.setWrapStyleWord(true);
        JScrollPane descScroll = new JScrollPane(descriptionField);
        
        // Reporter
        reporterLabel = new JLabel("Reporter:");
        reporterField = new JTextField(20);
        reporterField.setEditable(false);
        
        // Addresses
        addressesLabel = new JLabel("Addresses:");
        addressesField = new JTextArea(3, 20);
        addressesField.setEditable(false);
        addressesField.setLineWrap(true);
        addressesField.setWrapStyleWord(true);
        JScrollPane addressesScroll = new JScrollPane(addressesField);
        
        // Add components to form
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 1;
        c.weightx = 0.2;
        formPanel.add(urlLabel, c);
        
        c.gridx = 1;
        c.weightx = 0.8;
        formPanel.add(urlScroll, c);
        
        c.gridx = 0;
        c.gridy = 1;
        c.weightx = 0.2;
        formPanel.add(nameLabel, c);
        
        c.gridx = 1;
        c.weightx = 0.8;
        formPanel.add(nameField, c);
        
        c.gridx = 0;
        c.gridy = 2;
        c.weightx = 0.2;
        formPanel.add(categoryLabel, c);
        
        c.gridx = 1;
        c.weightx = 0.8;
        formPanel.add(categoryField, c);
        
        c.gridx = 0;
        c.gridy = 3;
        c.weightx = 0.2;
        formPanel.add(subcategoryLabel, c);
        
        c.gridx = 1;
        c.weightx = 0.8;
        formPanel.add(subcategoryField, c);
        
        c.gridx = 0;
        c.gridy = 4;
        c.weightx = 0.2;
        c.anchor = GridBagConstraints.NORTH;
        formPanel.add(descriptionLabel, c);
        
        c.gridx = 1;
        c.weightx = 0.8;
        c.gridheight = 1;
        formPanel.add(descScroll, c);
        
        c.gridx = 0;
        c.gridy = 5;
        c.weightx = 0.2;
        formPanel.add(reporterLabel, c);
        
        c.gridx = 1;
        c.weightx = 0.8;
        formPanel.add(reporterField, c);
        
        c.gridx = 0;
        c.gridy = 6;
        c.weightx = 0.2;
        c.anchor = GridBagConstraints.NORTH;
        formPanel.add(addressesLabel, c);
        
        c.gridx = 1;
        c.weightx = 0.8;
        c.gridheight = 1;
        formPanel.add(addressesScroll, c);
        
        // Add components to main panel
        add(titleLabel, BorderLayout.NORTH);
        add(formPanel, BorderLayout.CENTER);
        
        // Default message
        setNoSelection();
    }
    
    public void updateDetails(ScamInstance scam) {
        if (scam == null) {
            setNoSelection();
            return;
        }
        
        // Separate URL and name
        urlField.setText(scam.getName()); // This is actually the URL
        nameField.setText(scam.getName()); // Use URL as name too since we don't have a separate name field
        
        categoryField.setText(scam.getCategory().toString());
        subcategoryField.setText(scam.getSubCategory());
        descriptionField.setText(scam.getDescription());
        reporterField.setText(scam.getReporter());
        
        // Parse and format addresses properly
        String address = scam.getAddress();
        if (address != null && !address.trim().isEmpty() && !address.equals("null")) {
            try {
                // Handle format like {'ETH': ['0x4cdc1cba0aeb5539f2e0ba158281e67e0e54a9b1']}
                StringBuilder formattedAddress = new StringBuilder();
                
                // Remove outer braces
                address = address.trim().replaceAll("^\\{|\\}$", "");
                
                // Split by coin type
                String[] parts = address.split(":");
                if (parts.length >= 2) {
                    String coinType = parts[0].replaceAll("'", "").trim();
                    String addressesPart = parts[1].trim();
                    
                    // Remove brackets and quotes, split multiple addresses
                    addressesPart = addressesPart.replaceAll("\\[|\\]|'", "");
                    String[] addresses = addressesPart.split(",");
                    
                    formattedAddress.append(coinType).append(":\n");
                    for (String addr : addresses) {
                        formattedAddress.append(addr.trim()).append("\n");
                    }
                } else {
                    formattedAddress.append(address);
                }
                
                addressesField.setText(formattedAddress.toString());
            } catch (Exception e) {
                // If parsing fails, just show the raw address
                addressesField.setText(address);
            }
        } else {
            addressesField.setText("No address available");
        }
    }
    
    private void setNoSelection() {
        urlField.setText("");
        nameField.setText("");
        categoryField.setText("");
        subcategoryField.setText("");
        descriptionField.setText("Select a row from the table to view details");
        reporterField.setText("");
        addressesField.setText("");
    }
}   