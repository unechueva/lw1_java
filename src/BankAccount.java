import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    public List<Transaction> findByAmount(int amount)
    {
        List<Transaction> res = new ArrayList<>();
        for (Transaction t : transactions)
        {
            if (t.getAmount() == amount)
            {
                res.add(t);
            }
        }
        return res;
    }

    public List<Transaction> findByAmountRange(int min, int max)
    {
        List<Transaction> res = new ArrayList<>();
        for (Transaction t : transactions)
        {
            int a = t.getAmount();
            if (a >= min && a <= max)
            {
                res.add(t);
            }
        }
        return res;
    }

    public List<Transaction> findByType(String type)
    {
        List<Transaction> res = new ArrayList<>();
        for (Transaction t : transactions)
        {
            if (t.getType().equalsIgnoreCase(type))
            {
                res.add(t);
            }
        }
        return res;
    }

    public List<Transaction> findByDateRange(LocalDateTime from, LocalDateTime to)
    {
        List<Transaction> res = new ArrayList<>();
        for (Transaction t : transactions)
        {
            LocalDateTime time = t.getTime();
            if ((time.isEqual(from) || time.isAfter(from)) && (time.isEqual(to) || time.isBefore(to)))
            {
                res.add(t);
            }
        }
        return res;
    }
}
