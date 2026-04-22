package aircraft_management;

import javax.swing.*;
import java.awt.*;

public class MainUI extends JFrame {

    public MainUI() {
        setTitle("Aircraft Maintenance System");
        setSize(500, 420);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JLabel title = new JLabel("Aircraft Maintenance System", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 18));
        title.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(title, BorderLayout.NORTH);

        JPanel panel = new JPanel(new GridLayout(6, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 80, 30, 80));

        JButton btnAdd    = new JButton("Add Aircraft");
        JButton btnPart   = new JButton("Add Part to Aircraft");
        JButton btnSearch = new JButton("Search Aircraft");
        JButton btnUrgent = new JButton("Show Urgent Repairs");
        JButton btnDelete = new JButton("Delete Aircraft");
        JButton btnExit   = new JButton("Save and Exit");

        panel.add(btnAdd);
        panel.add(btnPart);
        panel.add(btnSearch);
        panel.add(btnUrgent);
        panel.add(btnDelete);
        panel.add(btnExit);
        add(panel, BorderLayout.CENTER);

        btnAdd.addActionListener(e -> addAircraftDialog());
        btnPart.addActionListener(e -> addPartDialog());
        btnSearch.addActionListener(e -> searchDialog());
        btnUrgent.addActionListener(e -> showUrgentDialog());
        btnDelete.addActionListener(e -> deleteDialog());
        btnExit.addActionListener(e -> {
            FileHelper.saveFleet(MainMenu.fleet);
            JOptionPane.showMessageDialog(this, "Data saved! Goodbye.");
            System.exit(0);
        });
    }

    void addAircraftDialog() {
        JTextField id      = new JTextField();
        JTextField model   = new JTextField();
        JTextField airline = new JTextField();
        JTextField hours   = new JTextField();
        JTextField fuel    = new JTextField();
        JTextField engTemp = new JTextField();
        JTextField landings = new JTextField();
        JTextField fault   = new JTextField("0");
        JTextField days    = new JTextField();

        Object[] fields = {
            "Aircraft ID:", id,
            "Model:", model,
            "Airline:", airline,
            "Flying Hours:", hours,
            "Fuel Level (0-100):", fuel,
            "Engine Temperature (°C):", engTemp,
            "Total Landings:", landings,
            "Engine Fault? (1=Yes, 0=No):", fault,
            "Days Since Last Maintenance:", days
        };

        int res = JOptionPane.showConfirmDialog(this, fields, "Add Aircraft", JOptionPane.OK_CANCEL_OPTION);
        if (res == JOptionPane.OK_OPTION) {
            try {
                Aircraft a = new Aircraft(
                    id.getText(), model.getText(), airline.getText(),
                    Integer.parseInt(hours.getText()),
                    Integer.parseInt(fuel.getText()),
                    Integer.parseInt(engTemp.getText()),
                    Integer.parseInt(landings.getText()),
                    fault.getText().trim().equals("1"),
                    Integer.parseInt(days.getText())
                );
                MainMenu.fleet.add(a);
                FileHelper.saveFleet(MainMenu.fleet);
                JOptionPane.showMessageDialog(this, "Aircraft added successfully!");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter valid numbers for all numeric fields.");
            }
        }
    }

    void addPartDialog() {
        JTextField id    = new JTextField();
        JTextField pname = new JTextField();
        JTextField date  = new JTextField();

        Object[] fields = {
            "Aircraft ID:", id,
            "Part Name:", pname,
            "Next Repair Date (e.g. 2026-08-01):", date
        };

        int res = JOptionPane.showConfirmDialog(this, fields, "Add Part", JOptionPane.OK_CANCEL_OPTION);
        if (res == JOptionPane.OK_OPTION) {
            for (Aircraft a : MainMenu.fleet) {
                if (a.id.equalsIgnoreCase(id.getText())) {
                    a.addPart(pname.getText(), date.getText());
                    FileHelper.saveFleet(MainMenu.fleet);
                    JOptionPane.showMessageDialog(this, "Part added successfully!");
                    return;
                }
            }
            JOptionPane.showMessageDialog(this, "Aircraft ID not found.");
        }
    }

    void searchDialog() {
        String term = JOptionPane.showInputDialog(this, "Enter Aircraft ID, Model, or Airline:");
        if (term == null || term.isEmpty()) return;

        StringBuilder sb = new StringBuilder();
        for (Aircraft a : MainMenu.fleet) {
            if (a.id.toLowerCase().contains(term.toLowerCase()) ||
                a.model.toLowerCase().contains(term.toLowerCase()) ||
                a.airline.toLowerCase().contains(term.toLowerCase())) {
                sb.append(a).append("\n");
                for (Part p : a.parts)
                    sb.append("   Part: ").append(p).append("\n");
                sb.append("\n");
            }
        }

        JOptionPane.showMessageDialog(this,
            sb.length() > 0 ? sb.toString() : "No aircraft found.",
            "Search Results", JOptionPane.INFORMATION_MESSAGE);
    }

    void showUrgentDialog() {
        StringBuilder sb = new StringBuilder();
        for (Aircraft a : MainMenu.fleet)
            for (Part p : a.parts)
                if (p.status == 1)
                    sb.append("Aircraft: ").append(a.id)
                      .append("  |  Part: ").append(p.name)
                      .append("  |  Repair by: ").append(p.nextRepairDate)
                      .append("\n");

        JOptionPane.showMessageDialog(this,
            sb.length() > 0 ? sb.toString() : "No urgent repairs right now!",
            "Urgent Repairs", JOptionPane.WARNING_MESSAGE);
    }

    void deleteDialog() {
        String id = JOptionPane.showInputDialog(this, "Enter Aircraft ID to delete:");
        if (id == null || id.isEmpty()) return;

        boolean removed = MainMenu.fleet.removeIf(a -> a.id.equalsIgnoreCase(id));
        FileHelper.saveFleet(MainMenu.fleet);
        JOptionPane.showMessageDialog(this,
            removed ? "Aircraft deleted successfully." : "Aircraft ID not found.");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainUI().setVisible(true));
    }
}