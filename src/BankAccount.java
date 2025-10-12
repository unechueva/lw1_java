import java.time.LocalDateTime;
import java.util.ArrayList;

public class BankAccount
{
    private int id;
    private int balance;
    private boolean opened;
    private ArrayList<Transaction> transactions;

    public BankAccount(int id)
    {
        this.id = id;
        this.balance = 0;
        this.opened = true;
        this.transactions = new ArrayList<>();
    }

    public int getId()
    {
        return id;
    }

    public int getBalance()
    {
        return balance;
    }

    public boolean isOpened()
    {
        return opened;
    }

    public ArrayList<Transaction> getTransactions()
    {
        return transactions;
    }

    public void deposit(int amount)
    {
        if (amount > 0)
        {
            balance += amount;
            transactions.add(new Transaction(LocalDateTime.now(), "DEPOSIT", amount));
        }
    }

    public boolean withdraw(int amount)
    {
        if (amount > 0 && amount <= balance)
        {
            balance -= amount;
            transactions.add(new Transaction(LocalDateTime.now(), "WITHDRAW", amount));
            return true;
        }
        return false;
    }
}
