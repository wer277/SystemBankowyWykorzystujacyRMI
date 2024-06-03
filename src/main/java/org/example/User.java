package org.example;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String username;
    private String password;
    private Account account;
    private List<String> transferPasswords;
    private List<String> transactionHistory;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.transactionHistory = new ArrayList<>();
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Account getAccount() {
        return account;
    }

    public void setTransferPasswords(List<String> passwords) {
        this.transferPasswords = passwords;
    }

    public boolean verifyTransferPassword(int index, String password) {
        // Adjust index to 1-based index
        index -= 1;
        return transferPasswords != null && index >= 0 && index < transferPasswords.size() && transferPasswords.get(index).equals(password);
    }

    public void addTransaction(String transaction) {
        transactionHistory.add(transaction);
    }

    public List<String> getTransactionHistory() {
        return transactionHistory;
    }

    public List<String> getTransferPasswords() {
        return transferPasswords;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
