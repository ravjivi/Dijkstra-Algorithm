import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class AlgorithmSummary {
    private JDialog dialog;

    public AlgorithmSummary() {
        dialog = new JDialog();
        dialog.setTitle("Dijkstra Summary");
        dialog.setModal(true); // Block until closed
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        // Set layout and padding
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        dialog.setContentPane(panel);
    }

    public void showSummary(int endCost, int linksVisited, ArrayList<Node> nodesList) {
        JPanel panel = (JPanel) dialog.getContentPane();
        panel.removeAll(); // Clear any old content

        panel.add(new JLabel("Algorithm finished."));
        panel.add(Box.createVerticalStrut(10));
        if (endCost == Integer.MAX_VALUE) {
            panel.add(new JLabel("→ Cost to end node: ∞"));
        } else {
            panel.add(new JLabel("→ Cost to end node: " + endCost));
        }
        panel.add(new JLabel("→ Number of links visited: " + linksVisited));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        JButton okButton = new JButton("Ok");
        okButton.addActionListener(e -> dialog.dispose());

        JButton detailsButton = new JButton("More Details");
        detailsButton.addActionListener(e -> showDetailsTable(nodesList));

        buttonPanel.add(okButton);
        buttonPanel.add(detailsButton);
        panel.add(buttonPanel);

        dialog.pack();
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }
    
    private void showDetailsTable(ArrayList<Node> nodeslist) {
        int n = nodeslist.size(); // Number of rows 
        int i = 3; // Number of columns

        // Column names and data
        String[] columnNames = {"Node", "Cost", "Parent Node"};
        Object[][] data = new Object[n][i];

        // Fill in table data
        for (int r = 0; r < n; r++) {
            data[r][0] = "Node " + nodeslist.get(r).getName();
            data[r][1] = nodeslist.get(r).getCost();
            if ((int)data[r][1] == Integer.MAX_VALUE) {
                data[r][1] = "∞";
            }
            try {
                data[r][2] = nodeslist.get(r).getPreviousNode().getName();
            } catch (Exception e) { // If there is no parent node 
                data[r][2] = "Null"; 
            }
        }

        // Create the table
        JTable table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);

        // Create a new dialog to display the table
        JDialog detailsDialog = new JDialog(dialog, "Algorithm Details", true);
        detailsDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        detailsDialog.setSize(400, 200);
        detailsDialog.setLocationRelativeTo(dialog);
        detailsDialog.add(scrollPane);
        detailsDialog.setVisible(true);
    }
    
    public boolean showingSummary() {
        if (dialog.isVisible()) {
            return true;
        } else {
            return false;
        }
    }
}
