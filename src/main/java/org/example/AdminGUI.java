package org.example;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Klasa reprezentujaca GUI administratora banku.
 */

public class AdminGUI {
    private final BankService bankService;
    private final ClientGUI clientGUI;
    private JFrame frame;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JTextField balanceField;

    /**
     * Tworzy nowe GUI administratora.
     *
     * @param bankService instancja serwisu bankowego
     * @param clientGUI instancja GUI klienta
     */
    public AdminGUI(BankService bankService, ClientGUI clientGUI) {
        this.bankService = bankService;
        this.clientGUI = clientGUI;
    }

    /**
     * Inicjalizuje GUI administratora.
     */
    public void initialize() {
        frame = new JFrame("Admin Panel");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null);  // Center the frame

        JButton createAccountButton = new JButton("Create Account");
        JButton viewUsersButton = new JButton("View Users");
        JButton changePasswordButton = new JButton("Change Password");
        JButton exitButton = new JButton("Exit");

        createAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createAccount();
            }
        });

        viewUsersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewUsers();
            }
        });

        changePasswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changePassword();
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                clientGUI.show();
            }
        });

        GroupLayout layout = new GroupLayout(frame.getContentPane());
        frame.getContentPane().setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(layout.createSequentialGroup()
                .addGap(100)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addComponent(createAccountButton)
                        .addComponent(viewUsersButton)
                        .addComponent(changePasswordButton)
                        .addComponent(exitButton))
                .addGap(100)
        );

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addGap(20)
                .addComponent(createAccountButton)
                .addComponent(viewUsersButton)
                .addComponent(changePasswordButton)
                .addComponent(exitButton)
                .addGap(20)
        );

        frame.setVisible(true);
    }

    /**
     * Tworzy nowe konto uzytkownika.
     */
    private void createAccount() {
        JFrame createAccountFrame = new JFrame("Create Account");
        createAccountFrame.setSize(300, 250);
        createAccountFrame.setLocationRelativeTo(null);  // Center the frame

        JLabel usernameLabel = new JLabel("Username:");
        usernameField = new JTextField(20);
        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField(20);
        JLabel balanceLabel = new JLabel("Balance:");
        balanceField = new JTextField(20);
        JButton createButton = new JButton("Create");

        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String username = usernameField.getText();
                    String password = new String(passwordField.getPassword());
                    double balance = Double.parseDouble(balanceField.getText());
                    List<String> passwords = generatePasswords();
                    boolean success = bankService.createAccount(username, password, balance, passwords);
                    if (success) {
                        JOptionPane.showMessageDialog(createAccountFrame, "Account created successfully!");
                    } else {
                        JOptionPane.showMessageDialog(createAccountFrame, "Account creation failed. User may already exist.");
                    }
                } catch (RemoteException ex) {
                    ex.printStackTrace();
                }
            }
        });

        GroupLayout layout = new GroupLayout(createAccountFrame.getContentPane());
        createAccountFrame.getContentPane().setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(layout.createSequentialGroup()
                .addGap(50)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addComponent(usernameLabel)
                        .addComponent(usernameField)
                        .addComponent(passwordLabel)
                        .addComponent(passwordField)
                        .addComponent(balanceLabel)
                        .addComponent(balanceField)
                        .addComponent(createButton))
                .addGap(50)
        );

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addGap(20)
                .addComponent(usernameLabel)
                .addComponent(usernameField)
                .addComponent(passwordLabel)
                .addComponent(passwordField)
                .addComponent(balanceLabel)
                .addComponent(balanceField)
                .addComponent(createButton)
                .addGap(20)
        );

        createAccountFrame.setVisible(true);
    }

    /**
     * Wyswietla liste uzytkownikow.
     */
    private void viewUsers() {
        try {
            List<String> users = bankService.getAllUsers();
            JFrame viewUsersFrame = new JFrame("View Users");
            viewUsersFrame.setSize(300, 250);
            viewUsersFrame.setLocationRelativeTo(null);  // Center the frame

            JTextArea usersArea = new JTextArea();
            usersArea.setEditable(false);
            for (String user : users) {
                usersArea.append(user + "\n");
            }

            JScrollPane scrollPane = new JScrollPane(usersArea);

            GroupLayout layout = new GroupLayout(viewUsersFrame.getContentPane());
            viewUsersFrame.getContentPane().setLayout(layout);
            layout.setAutoCreateGaps(true);
            layout.setAutoCreateContainerGaps(true);

            layout.setHorizontalGroup(layout.createSequentialGroup()
                    .addGap(50)
                    .addComponent(scrollPane)
                    .addGap(50)
            );

            layout.setVerticalGroup(layout.createSequentialGroup()
                    .addGap(20)
                    .addComponent(scrollPane)
                    .addGap(20)
            );

            viewUsersFrame.setVisible(true);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    /**
     * Zmienia haslo administratora.
     */
    private void changePassword() {
        JFrame changePasswordFrame = new JFrame("Change Password");
        changePasswordFrame.setSize(300, 150);
        changePasswordFrame.setLocationRelativeTo(null);  // Center the frame

        JLabel newPasswordLabel = new JLabel("New Password:");
        JPasswordField newPasswordField = new JPasswordField(20);
        JButton changeButton = new JButton("Change");

        changeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String newPassword = new String(newPasswordField.getPassword());
                    boolean success = bankService.changePassword("admin", newPassword);
                    if (success) {
                        clientGUI.updateAdminPassword(newPassword);  // Aktualizacja hasła w ClientGUI
                        JOptionPane.showMessageDialog(changePasswordFrame, "Password changed successfully!");
                    } else {
                        JOptionPane.showMessageDialog(changePasswordFrame, "Password change failed.");
                    }
                } catch (RemoteException ex) {
                    ex.printStackTrace();
                }
            }
        });

        GroupLayout layout = new GroupLayout(changePasswordFrame.getContentPane());
        changePasswordFrame.getContentPane().setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(layout.createSequentialGroup()
                .addGap(50)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addComponent(newPasswordLabel)
                        .addComponent(newPasswordField)
                        .addComponent(changeButton))
                .addGap(50)
        );

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addGap(20)
                .addComponent(newPasswordLabel)
                .addComponent(newPasswordField)
                .addComponent(changeButton)
                .addGap(20)
        );

        changePasswordFrame.setVisible(true);
    }

    /**
     * Generuje liste hasel transferowych.
     *
     * @return lista hasel transferowych
     */
    private List<String> generatePasswords() {
        List<String> passwords = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            StringBuilder password = new StringBuilder();
            for (int j = 0; j < 4; j++) {
                password.append(random.nextInt(10));  // Append a random digit (0-9)
            }
            passwords.add(password.toString());
        }
        return passwords;
    }
}
