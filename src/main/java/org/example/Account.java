package org.example;

/**
 * Class representing a bank account.
 */
public class Account {
    private double balance;

    /**
     * Creates a new account with an initial balance.
     *
     * @param initialBalance the initial balance of the account
     */
    public Account(double initialBalance) {
        this.balance = initialBalance;
    }

    /**
     * Returns the balance of the account.
     *
     * @return the account balance
     */
    public double getBalance() {
        return balance;
    }

    /**
     * Adds an amount to the account balance.
     *
     * @param amount the amount to add
     */
    public void deposit(double amount) {
        balance += amount;
    }

    /**
     * Subtracts an amount from the account balance.
     *
     * @param amount the amount to subtract
     */
    public void withdraw(double amount) {
        balance -= amount;
    }
}
