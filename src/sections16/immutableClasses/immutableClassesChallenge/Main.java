package sections16.immutableClasses.immutableClassesChallenge;

import sections16.immutableClasses.immutableClassesChallenge.bank.BankAccount;
import sections16.immutableClasses.immutableClassesChallenge.bank.BankCustomer;

import java.util.List;

public class Main {

    public static void main(String[] args) {

//        BankAccount account =
//                new BankAccount(BankAccount.AccountType.CHECKING, 500);
//        System.out.println(account);

        BankCustomer joe = new BankCustomer("Joe", 500.00,
                10000.00);
        System.out.println(joe);

        List<BankAccount> accounts = joe.getAccounts();
        accounts.clear();
        System.out.println(joe);

    }
}