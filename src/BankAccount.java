public class BankAccount
{
    private int id;
    private int balance;
    private boolean opened;

    public BankAccount(int id)
    {
        this.id = id;
        this.balance = 0;
        this.opened = true;
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

    public void deposit(int amount)
    {
        if (amount > 0)
        {
            balance += amount;
        }
    }

    public boolean withdraw(int amount)
    {
        if (amount > 0 && amount <= balance)
        {
            balance -= amount;
            return true;
        }
        return false;
    }
}
