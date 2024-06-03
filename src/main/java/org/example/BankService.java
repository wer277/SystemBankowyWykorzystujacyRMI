package org.example;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * Interface for the remote bank service.
 */
public interface BankService extends Remote {

    /**
     * Creates a new user account.
     *
     * @param username the username of the user
     * @param password the password of the user
     * @param initialBalance the initial balance of the account
     * @param transferPasswords the list of transfer passwords
     * @return true if the account was successfully created, false otherwise
     * @throws RemoteException if there is a remote communication issue
     */
    boolean createAccount(String username, String password, double initialBalance, List<String> transferPasswords) throws RemoteException;

    /**
     * Transfers funds between user accounts.
     *
     * @param fromUser the username of the sender
     * @param toUser the username of the recipient
     * @param amount the amount to transfer
     * @param passwordIndex the index of the transfer password
     * @param password the transfer password
     * @return true if the transfer was successful, false otherwise
     * @throws RemoteException if there is a remote communication issue
     */
    boolean transfer(String fromUser, String toUser, double amount, int passwordIndex, String password) throws RemoteException;

    /**
     * Returns the balance of the user's account.
     *
     * @param username the username of the user
     * @return the account balance
     * @throws RemoteException if there is a remote communication issue
     */
    double getBalance(String username) throws RemoteException;

    /**
     * Returns the transaction history of the user.
     *
     * @param username the username of the user
     * @return the list of transactions
     * @throws RemoteException if there is a remote communication issue
     */
    List<String> getTransactionHistory(String username) throws RemoteException;

    /**
     * Deletes a user account.
     *
     * @param username the username of the user
     * @return true if the account was successfully deleted, false otherwise
     * @throws RemoteException if there is a remote communication issue
     */
    boolean deleteUser(String username) throws RemoteException;

    /**
     * Returns the list of transfer passwords for the user.
     *
     * @param username the username of the user
     * @return the list of transfer passwords
     * @throws RemoteException if there is a remote communication issue
     */
    List<String> getTransferPasswordList(String username) throws RemoteException;

    /**
     * Checks if a user with the given username exists.
     *
     * @param username the username to check
     * @return true if the user exists, false otherwise
     * @throws RemoteException if there is a remote communication issue
     */
    boolean userExists(String username) throws RemoteException;

    /**
     * Verifies the user's login credentials.
     *
     * @param username the username of the user
     * @param password the password of the user
     * @return true if the login credentials are correct, false otherwise
     * @throws RemoteException if there is a remote communication issue
     */
    boolean verifyUser(String username, String password) throws RemoteException;

    /**
     * Returns the list of all users.
     *
     * @return the list of usernames
     * @throws RemoteException if there is a remote communication issue
     */
    List<String> getAllUsers() throws RemoteException;

    /**
     * Changes the user's password.
     *
     * @param username the username of the user
     * @param newPassword the new password
     * @return true if the password was successfully changed, false otherwise
     * @throws RemoteException if there is a remote communication issue
     */
    boolean changePassword(String username, String newPassword) throws RemoteException;
}
