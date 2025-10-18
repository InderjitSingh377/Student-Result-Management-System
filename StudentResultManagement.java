import javax.swing.*;
import java.awt.*;
// import java.awt.event.*; <-- REMOVED: This import is no longer needed.
import java.io.*;

public class StudentResultManagement extends JFrame {
    private JTextField usernameField, passwordField;
    private JPanel loginPanel, mainPanel;
    private CardLayout cardLayout;

    private JTextField nameField, rollField, mathField, scienceField, englishField;
    private JTextArea resultArea;

    public StudentResultManagement() {
        setTitle("Student Result Management System");
        setSize(600, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        setLayout(cardLayout);

        loginPanel = createLoginPanel();
        mainPanel = createMainPanel();

        add(loginPanel, "login");
        add(mainPanel, "main");

        cardLayout.show(getContentPane(), "login");
        setVisible(true);
    }

    private JPanel createLoginPanel() {
        JPanel panel = new JPanel(null);

        JLabel label = new JLabel("Login", JLabel.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 24));
        label.setBounds(200, 40, 200, 40);
        panel.add(label);

        JLabel userLabel = new JLabel("Username:");
        userLabel.setBounds(150, 120, 100, 30);
        panel.add(userLabel);

        usernameField = new JTextField();
        usernameField.setBounds(250, 120, 150, 30);
        panel.add(usernameField);

        JLabel passLabel = new JLabel("Password:");
        passLabel.setBounds(150, 170, 100, 30);
        panel.add(passLabel);

        passwordField = new JTextField();
        passwordField.setBounds(250, 170, 150, 30);
        panel.add(passwordField);

        JButton loginBtn = new JButton("Login");
        loginBtn.setBounds(250, 220, 150, 35);
        // FIX: Replaced 'e' with '_' to mark the parameter as intentionally unused
        loginBtn.addActionListener(_ -> {
            String user = usernameField.getText();
            String pass = passwordField.getText();
            if (user.equals("admin") && pass.equals("1234")) {
                cardLayout.show(getContentPane(), "main");
            } else {
                JOptionPane.showMessageDialog(this, "Invalid credentials!");
            }
        });
        panel.add(loginBtn);

        return panel;
    }

    private JPanel createMainPanel() {
        JPanel panel = new JPanel(null);

        JLabel title = new JLabel("Enter Student Details", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setBounds(150, 10, 300, 40);
        panel.add(title);

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setBounds(50, 70, 100, 30);
        panel.add(nameLabel);

        nameField = new JTextField();
        nameField.setBounds(150, 70, 150, 30);
        panel.add(nameField);

        JLabel rollLabel = new JLabel("Roll No:");
        rollLabel.setBounds(350, 70, 100, 30);
        panel.add(rollLabel);

        rollField = new JTextField();
        rollField.setBounds(450, 70, 100, 30);
        panel.add(rollField);

        JLabel mathLabel = new JLabel("Maths Marks:");
        mathLabel.setBounds(50, 120, 100, 30);
        panel.add(mathLabel);

        mathField = new JTextField();
        mathField.setBounds(150, 120, 150, 30);
        panel.add(mathField);

        JLabel sciLabel = new JLabel("Science Marks:");
        sciLabel.setBounds(350, 120, 100, 30);
        panel.add(sciLabel);

        scienceField = new JTextField();
        scienceField.setBounds(450, 120, 100, 30);
        panel.add(scienceField);

        JLabel engLabel = new JLabel("English Marks:");
        engLabel.setBounds(50, 170, 100, 30);
        panel.add(engLabel);

        englishField = new JTextField();
        englishField.setBounds(150, 170, 150, 30);
        panel.add(englishField);

        JButton calcBtn = new JButton("Calculate Result");
        calcBtn.setBounds(200, 220, 180, 40);
        // FIX: Replaced 'e' with '_'
        calcBtn.addActionListener(_ -> calculateResult());
        panel.add(calcBtn);

        JButton saveBtn = new JButton("Save Result");
        saveBtn.setBounds(200, 270, 180, 40);
        // FIX: Replaced 'e' with '_'
        saveBtn.addActionListener(_ -> saveResult());
        panel.add(saveBtn);

        JButton viewBtn = new JButton("View All Results");
        viewBtn.setBounds(200, 320, 180, 40);
        // FIX: Replaced 'e' with '_'
        viewBtn.addActionListener(_ -> viewResults());
        panel.add(viewBtn);

        resultArea = new JTextArea();
        resultArea.setEditable(false);
        JScrollPane scroll = new JScrollPane(resultArea);
        scroll.setBounds(50, 370, 500, 80);
        panel.add(scroll);

        return panel;
    }

    private void calculateResult() {
        try {
            int math = Integer.parseInt(mathField.getText());
            int science = Integer.parseInt(scienceField.getText());
            int english = Integer.parseInt(englishField.getText());

            int total = math + science + english;
            double percent = total / 3.0;
            String grade = (percent >= 40) ? "Pass" : "Fail";

            resultArea.setText(
                    "Total: " + total + "\nPercentage: " + String.format("%.2f", percent) + "%\nResult: " + grade);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter valid numeric marks.");
        }
    }

    private void saveResult() {
        String name = nameField.getText().trim();
        String roll = rollField.getText().trim();
        String resultText = resultArea.getText();

        if (name.isEmpty() || roll.isEmpty() || resultText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all details and calculate result first.");
            return;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("results.txt", true))) {
            writer.write("Name: " + name + ", Roll No: " + roll + ", " + resultText.replace("\n", ", ") + "\n");
            JOptionPane.showMessageDialog(this, "Result saved successfully.");
            clearFields();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error saving result.");
        }
    }

    private void viewResults() {
        try (BufferedReader reader = new BufferedReader(new FileReader("results.txt"))) {
            resultArea.setText("");
            String line;
            while ((line = reader.readLine()) != null) {
                resultArea.append(line + "\n");
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "No saved results found.");
        }
    }

    private void clearFields() {
        nameField.setText("");
        rollField.setText("");
        mathField.setText("");
        scienceField.setText("");
        englishField.setText("");
        resultArea.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(StudentResultManagement::new);
    }
}