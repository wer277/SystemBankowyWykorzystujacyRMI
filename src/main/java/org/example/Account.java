package org.example;

/**
 * Klasa reprezentujaca konto bankowe.
 */

public class Account {
    private double balance;

    /**
     * Tworzy nowe konto z poczatkowym stanem.
     *
     * @param initialBalance poczatkowy stan konta
     */

    public Account(double initialBalance) {
        this.balance = initialBalance;
    }

    /**
     * Zwraca stan konta.
     *
     * @return stan konta
     */

    public double getBalance() {
        return balance;
    }

    /**
     * Dodaje kwote do stanu konta.
     *
     * @param amount kwota do dodania
     */
    public void deposit(double amount) {
        balance += amount;
    }
    /**
     * Odejmuje kwote od stanu konta.
     *
     * @param amount kwota do odjęcia
     */
    public void withdraw(double amount) {
        balance -= amount;
    }
}
