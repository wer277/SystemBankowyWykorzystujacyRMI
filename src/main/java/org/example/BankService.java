package org.example;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface BankService extends Remote {
    boolean createAccount(String username, String password, double initialBalance, List<String> transferPasswords) throws RemoteException;
    boolean transfer(String fromUser, String toUser, double amount, int passwordIndex, String password) throws RemoteException;
    double getBalance(String username) throws RemoteException;
    List<String> getTransactionHistory(String username) throws RemoteException;
    boolean deleteUser(String username) throws RemoteException;
    List<String> getTransferPasswordList(String username) throws RemoteException;
    boolean userExists(String username) throws RemoteException;
    boolean verifyUser(String username, String password) throws RemoteException;
    List<String> getAllUsers() throws RemoteException;
    boolean changePassword(String username, String newPassword) throws RemoteException; // Dodane
}
