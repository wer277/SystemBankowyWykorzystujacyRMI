package org.example;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

/**
 * Implementation of the remote bank service.
 */
public class BankServiceImpl extends UnicastRemoteObject implements BankService {

    private Map<String, User> users;

    /**
     * Creates a new bank service implementation.
     *
     * @throws RemoteException if there is an RMI communication issue
     */
    public BankServiceImpl() throws RemoteException {
        super();
        users = new HashMap<>();
        // Create admin user
        createAccount("admin", "admin123", 0.0, generatePasswords());
    }

    @Override
    public boolean createAccount(String username, String password, double initialBalance, List<String> transferPasswords) throws RemoteException {
        if (!users.containsKey(username)) {
            User user = new User(username, password);
            user.setAccount(new Account(initialBalance));
            user.setTransferPasswords(transferPasswords);
            users.put(username, user);
            return true;
        }
        return false;
    }

    @Override
    public boolean transfer(String fromUser, String toUser, double amount, int passwordIndex, String password) throws RemoteException {
        User userFrom = users.get(fromUser);
        User userTo = users.get(toUser);

        if (userFrom != null && userTo != null && userFrom.verifyTransferPassword(passwordIndex, password) && userFrom.getAccount().getBalance() >= amount) {
            userFrom.getAccount().withdraw(amount);
            userTo.getAccount().deposit(amount);
            userFrom.addTransaction("Transfer to " + toUser + ": " + amount);
            userTo.addTransaction("Transfer from " + fromUser + ": " + amount);
            return true;
        }
        return false;
    }

    @Override
    public double getBalance(String username) throws RemoteException {
        User user = users.get(username);
        if (user != null && user.getAccount() != null) {
            return user.getAccount().getBalance();
        }
        return 0;
    }

    @Override
    public List<String> getTransactionHistory(String username) throws RemoteException {
        User user = users.get(username);
        if (user != null) {
            return user.getTransactionHistory();
        }
        return new ArrayList<>();
    }

    @Override
    public boolean deleteUser(String username) throws RemoteException {
        if (users.containsKey(username)) {
            users.remove(username);
            return true;
        }
        return false;
    }

    @Override
    public List<String> getTransferPasswordList(String username) throws RemoteException {
        User user = users.get(username);
        if (user != null) {
            return user.getTransferPasswords();
        }
        return new ArrayList<>();
    }

    @Override
    public boolean userExists(String username) throws RemoteException {
        return users.containsKey(username);
    }

    @Override
    public boolean verifyUser(String username, String password) throws RemoteException {
        User user = users.get(username);
        return user != null && user.getPassword().equals(password);
    }

    @Override
    public List<String> getAllUsers() throws RemoteException {
        return new ArrayList<>(users.keySet());
    }

    @Override
    public boolean changePassword(String username, String newPassword) throws RemoteException {
        User user = users.get(username);
        if (user != null) {
            user.setPassword(newPassword);
            return true;
        }
        return false;
    }

    /**
     * Generates a list of transfer passwords.
     *
     * @return a list of transfer passwords
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
