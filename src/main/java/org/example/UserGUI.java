package org.example;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Random;

/**
 * Class representing the bank user GUI.
 */
public class UserGUI {
    private final BankService bankService;
    private final String username;
    private final ClientGUI clientGUI;
    private JFrame frame;

    /**
     * Creates a new user GUI.
     *
     * @param bankService instance of the bank service
     * @param username user's username
     * @param clientGUI instance of the client GUI
     */
    public UserGUI(BankService bankService, String username, ClientGUI clientGUI) {
        this.bankService = bankService;
        this.username = username;
        this.clientGUI = clientGUI;
    }

    /**
     * Initializes the user GUI.
     */
    public void initialize() {
        frame = new JFrame("User Panel");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null);  // Center the frame

        JButton transferMoneyButton = new JButton("Transfer Money");
        JButton checkBalanceButton = new JButton("Check Balance");
        JButton viewTransactionsButton = new JButton("View Transactions");
        JButton viewPasswordsButton = new JButton("View Passwords");
        JButton changePasswordButton = new JButton("Change Password");
        JButton deleteAccountButton = new JButton("Delete Account");
        JButton exitButton = new JButton("Exit");

        transferMoneyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                transferMoney();
            }
        });

        checkBalanceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkBalance();
            }
        });

        viewTransactionsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewTransactions();
            }
        });

        viewPasswordsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewPasswords();
            }
        });

        changePasswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changePassword();
            }
        });

        deleteAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteAccount();
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
                        .addComponent(transferMoneyButton)
                        .addComponent(checkBalanceButton)
                        .addComponent(viewTransactionsButton)
                        .addComponent(viewPasswordsButton)
                        .addComponent(changePasswordButton)
                        .addComponent(deleteAccountButton)
                        .addComponent(exitButton))
                .addGap(100)
        );

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addGap(20)
                .addComponent(transferMoneyButton)
                .addComponent(checkBalanceButton)
                .addComponent(viewTransactionsButton)
                .addComponent(viewPasswordsButton)
                .addComponent(changePasswordButton)
                .addComponent(deleteAccountButton)
                .addComponent(exitButton)
                .addGap(20)
        );

        frame.setVisible(true);
    }

    /**
     * Transfers money to another user's account.
     */
    private void transferMoney() {
        JFrame transferFrame = new JFrame("Transfer Money");
        transferFrame.setSize(300, 300); // Increased size to fit all components
        transferFrame.setLocationRelativeTo(null);  // Center the frame

        JLabel toUserLabel = new JLabel("To User:");
        JTextField toUserField = new JTextField(20);
        JLabel amountLabel = new JLabel("Amount:");
        JTextField amountField = new JTextField(20);
        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField(20);
        JLabel passwordIndexLabel = new JLabel();  // Label to show which password index to use
        JButton transferButton = new JButton("Transfer");

        // Generate the password index before the transfer button is clicked
        List<String> passwordList;
        int passwordIndex;
        try {
            passwordList = bankService.getTransferPasswordList(username);
            passwordIndex = new Random().nextInt(passwordList.size()) + 1; // Random index (1-based)
            passwordIndexLabel.setText("Enter password #" + passwordIndex + " from the list.");
        } catch (RemoteException e) {
            e.printStackTrace();
            return;
        }

        transferButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String toUser = toUserField.getText();
                    double amount = Double.parseDouble(amountField.getText());
                    String password = new String(passwordField.getPassword());

                    if (bankService.transfer(username, toUser, amount, passwordIndex, password)) {
                        JOptionPane.showMessageDialog(transferFrame, "Transfer successful!");
                    } else {
                        JOptionPane.showMessageDialog(transferFrame, "Invalid password or transfer failed.");
                    }
                } catch (RemoteException ex) {
                    ex.printStackTrace();
                }
            }
        });

        GroupLayout layout = new GroupLayout(transferFrame.getContentPane());
        transferFrame.getContentPane().setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(layout.createSequentialGroup()
                .addGap(50)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addComponent(toUserLabel)
                        .addComponent(toUserField)
                        .addComponent(amountLabel)
                        .addComponent(amountField)
                        .addComponent(passwordLabel)
                        .addComponent(passwordField)
                        .addComponent(passwordIndexLabel)
                        .addComponent(transferButton))
                .addGap(50)
        );

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addGap(20)
                .addComponent(toUserLabel)
                .addComponent(toUserField)
                .addComponent(amountLabel)
                .addComponent(amountField)
                .addComponent(passwordLabel)
                .addComponent(passwordField)
                .addComponent(passwordIndexLabel)
                .addComponent(transferButton)
                .addGap(20)
        );

        transferFrame.setVisible(true);
    }

    /**
     * Checks the user's account balance.
     */
    private void checkBalance() {
        try {
            double balance = bankService.getBalance(username);
            JOptionPane.showMessageDialog(frame, "Balance: " + balance);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    /**
     * Displays the user's transaction history.
     */
    private void viewTransactions() {
        try {
            List<String> transactions = bankService.getTransactionHistory(username);
            JFrame transactionsFrame = new JFrame("Transactions");
            transactionsFrame.setSize(300, 250);
            transactionsFrame.setLocationRelativeTo(null);  // Center the frame

            JTextArea transactionsArea = new JTextArea();
            transactionsArea.setEditable(false);
            for (String transaction : transactions) {
                transactionsArea.append(transaction + "\n");
            }

            JScrollPane scrollPane = new JScrollPane(transactionsArea);

            GroupLayout layout = new GroupLayout(transactionsFrame.getContentPane());
            transactionsFrame.getContentPane().setLayout(layout);
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

            transactionsFrame.setVisible(true);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    /**
     * Displays the user's transfer passwords.
     */
    private void viewPasswords() {
        try {
            List<String> passwordList = bankService.getTransferPasswordList(username);
            JFrame passwordsFrame = new JFrame("Passwords");
            passwordsFrame.setSize(300, 250);
            passwordsFrame.setLocationRelativeTo(null);  // Center the frame

            JTextArea passwordsArea = new JTextArea();
            passwordsArea.setEditable(false);
            for (int i = 0; i < passwordList.size(); i++) {
                passwordsArea.append((i + 1) + ": " + passwordList.get(i) + "\n");
            }

            JScrollPane scrollPane = new JScrollPane(passwordsArea);

            GroupLayout layout = new GroupLayout(passwordsFrame.getContentPane());
            passwordsFrame.getContentPane().setLayout(layout);
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

            passwordsFrame.setVisible(true);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    /**
     * Changes the user's password.
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
                    boolean success = bankService.changePassword(username, newPassword);
                    if (success) {
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
     * Deletes the user's account.
     */
    private void deleteAccount() {
        try {
            boolean success = bankService.deleteUser(username);
            if (success) {
                JOptionPane.showMessageDialog(frame, "Account deleted successfully!");
                frame.dispose();
                clientGUI.show();
            } else {
                JOptionPane.showMessageDialog(frame, "Account deletion failed.");
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
