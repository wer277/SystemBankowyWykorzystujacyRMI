package org.example;

import java.util.ArrayList;
import java.util.List;

/**
 * Class representing a bank user.
 */
public class User {
    private String username;
    private String password;
    private Account account;
    private List<String> transferPasswords;
    private List<String> transactionHistory;

    /**
     * Creates a new user.
     *
     * @param username the username of the user
     * @param password the password of the user
     */
    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.transactionHistory = new ArrayList<>();
    }

    /**
     * Sets the user's account.
     *
     * @param account an instance of Account representing the user's account
     */
    public void setAccount(Account account) {
        this.account = account;
    }

    /**
     * Returns the user's account.
     *
     * @return an instance of Account representing the user's account
     */
    public Account getAccount() {
        return account;
    }

    /**
     * Sets the list of transfer passwords.
     *
     * @param passwords the list of transfer passwords
     */
    public void setTransferPasswords(List<String> passwords) {
        this.transferPasswords = passwords;
    }

    /**
     * Verifies the user's transfer password based on the index.
     *
     * @param index the index of the password (1-based index)
     * @param password the password to verify
     * @return true if the password is correct, false otherwise
     */
    public boolean verifyTransferPassword(int index, String password) {
        // Adjust index to 1-based index
        index -= 1;
        return transferPasswords != null && index >= 0 && index < transferPasswords.size() && transferPasswords.get(index).equals(password);
    }

    /**
     * Adds a transaction to the user's transaction history.
     *
     * @param transaction the description of the transaction
     */
    public void addTransaction(String transaction) {
        transactionHistory.add(transaction);
    }

    /**
     * Returns the user's transaction history.
     *
     * @return the list of the user's transactions
     */
    public List<String> getTransactionHistory() {
        return transactionHistory;
    }

    /**
     * Returns the list of the user's transfer passwords.
     *
     * @return the list of transfer passwords
     */
    public List<String> getTransferPasswords() {
        return transferPasswords;
    }

    /**
     * Returns the user's password.
     *
     * @return the user's password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the user's new password.
     *
     * @param password the new password
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
