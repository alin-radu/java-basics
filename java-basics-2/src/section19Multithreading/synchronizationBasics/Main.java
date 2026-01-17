package section19Multithreading.synchronizationBasics;

class Customer extends Thread {
    String name;
    int amount;
    ATM atm;

    Customer(String name, int amount, ATM atm) {
        this.name = name;
        this.amount = amount;
        this.atm = atm;
    }

    public void useATM() {
        atm.checkBalance(name);
        atm.withdraw(name, amount);
    }

    @Override
    public void run() {
        useATM();
    }
}

class ATM {
    int cashStored;

    ATM(int cashStored) {
        this.cashStored = cashStored;
    }

    public void checkBalance(String name) {
        System.out.print("\nChecking the balance for " + name + "...");

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            System.out.println(e);
        }

        System.out.print(cashStored);
        System.out.println();
    }

    synchronized public void withdraw(String name, int amount) {
        if (amount > cashStored) {
            System.out.println("The ATM doesn't have enough money for " + name + " !!!");
        } else {
            System.out.println("\n" + name + " is withdrawing " + amount + " ...");

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            if (cashStored > 0) {
                cashStored -= amount;
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("\n" + name + " received the amount " + amount);
            } else {
                System.out.println("\n" + name + " didn't received the money");
            }

            System.out.println("\nAfter completed " + name + " withdraw, there are " + cashStored + " cash stored in ATM ");
        }

    }
}

public class Main {
    public static void main(String[] args) {
        ATM atm = new ATM(1100);

        Customer customer1 = new Customer("Smith", 1000, atm);
        Customer customer2 = new Customer("John", 1000, atm);

        customer1.start();
        customer2.start();
    }
}
