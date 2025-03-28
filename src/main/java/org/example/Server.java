package org.example;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

/**
 * Class for starting the RMI server.
 */
public class Server {
    public static void main(String[] args) {
        try {
            LocateRegistry.createRegistry(1099);
            BankService bankService = new BankServiceImpl();
            Naming.rebind("rmi://localhost:1099/BankService", bankService);
            System.out.println("BankService is running...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
