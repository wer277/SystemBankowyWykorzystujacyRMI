package org.example;

import javax.swing.*;
import java.rmi.Naming;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.formdev.flatlaf.FlatLightLaf;

/**
 * Class representing the bank client's GUI.
 */
public class ClientGUI {
    private JFrame frame;
    private BankService bankService;
    private String adminPassword = "admin123";  // Initial admin password

    public static void main(String[] args) {
        try {
            FlatLightLaf.setup();  // Setup FlatLaf
            BankService bankService = (BankService) Naming.lookup("rmi://localhost:1099/BankService");
            SwingUtilities.invokeLater(() -> new ClientGUI(bankService).initialize());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates a new client GUI.
     *
     * @param bankService instance of the bank service
     */
    public ClientGUI(BankService bankService) {
        this.bankService = bankService;
    }

    /**
     * Initializes the client GUI.
     */
    public void initialize() {
        frame = new JFrame("Bank Login");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null);  // Center the frame

        JLabel roleLabel = new JLabel("Select Role:");
        JRadioButton adminButton = new JRadioButton("Admin");
        JRadioButton userButton = new JRadioButton("User");
        ButtonGroup group = new ButtonGroup();
        group.add(adminButton);
        group.add(userButton);

        JLabel usernameLabel = new JLabel("Username:");
        JTextField usernameField = new JTextField(20);
        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField(20);

        JButton loginButton = new JButton("Login");

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                if (adminButton.isSelected()) {
                    if ("admin".equals(username) && adminPassword.equals(password)) {
                        new AdminGUI(bankService, ClientGUI.this).initialize();
                        frame.setVisible(false);
                    } else {
                        JOptionPane.showMessageDialog(frame, "Invalid admin credentials. Please try again.");
                    }
                } else if (userButton.isSelected()) {
                    try {
                        if (bankService.verifyUser(username, password)) {
                            new UserGUI(bankService, username, ClientGUI.this).initialize();
                            frame.setVisible(false);
                        } else {
                            JOptionPane.showMessageDialog(frame, "Invalid username or password. Please try again.");
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "Please select a role.");
                }
            }
        });

        GroupLayout layout = new GroupLayout(frame.getContentPane());
        frame.getContentPane().setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(layout.createSequentialGroup()
                .addGap(100)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addComponent(roleLabel)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(adminButton)
                                .addComponent(userButton))
                        .addComponent(usernameLabel)
                        .addComponent(usernameField)
                        .addComponent(passwordLabel)
                        .addComponent(passwordField)
                        .addComponent(loginButton))
                .addGap(100)
        );

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addGap(20)
                .addComponent(roleLabel)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(adminButton)
                        .addComponent(userButton))
                .addComponent(usernameLabel)
                .addComponent(usernameField)
                .addComponent(passwordLabel)
                .addComponent(passwordField)
                .addComponent(loginButton)
                .addGap(20)
        );

        frame.setVisible(true);
    }

    /**
     * Displays the GUI.
     */
    public void show() {
        frame.setVisible(true);
    }

    /**
     * Updates the admin password.
     *
     * @param newPassword the new admin password
     */
    public void updateAdminPassword(String newPassword) {
        this.adminPassword = newPassword;
    }
}
