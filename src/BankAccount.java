import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BankAccount
{
    private int id;
    private int balance;
    private boolean opened;
    private List<Transaction> transactions;

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

    public List<Transaction> getTransactions()
    {
        return transactions;
    }

    public void deposit(LocalDate date, int amount)
    {
        if (amount > 0)
        {
            balance += amount;
            transactions.add(new Transaction(date, "DEPOSIT", amount));
        }
    }

    public boolean withdraw(LocalDate date, int amount)
    {
        if (amount > 0 && amount <= balance)
        {
            balance -= amount;
            transactions.add(new Transaction(date, "WITHDRAW", amount));
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
                res.add(t);
        }
        return res;
    }

    public List<Transaction> findByAmountRange(int min, int max)
    {
        List<Transaction> res = new ArrayList<>();
        for (Transaction t : transactions)
        {
            if (t.getAmount() >= min && t.getAmount() <= max)
                res.add(t);
        }
        return res;
    }

    public List<Transaction> findByType(String type)
    {
        List<Transaction> res = new ArrayList<>();
        for (Transaction t : transactions)
        {
            if (t.getType().equalsIgnoreCase(type))
                res.add(t);
        }
        return res;
    }

    public List<Transaction> findByDateRange(LocalDate from, LocalDate to)
    {
        List<Transaction> res = new ArrayList<>();
        for (Transaction t : transactions)
        {
            LocalDate d = t.getDate();
            if ((d.isEqual(from) || d.isAfter(from)) && (d.isEqual(to) || d.isBefore(to)))
                res.add(t);
        }
        return res;
    }

    public void saveToFile()
    {
        try
        {
            File dir = new File("data");
            if (!dir.exists()) dir.mkdir();

            File accFile = new File(dir, "account.txt");
            try (PrintWriter pw = new PrintWriter(accFile))
            {
                pw.println(id);
                pw.println(balance);
                pw.println(opened);
            }

            File trFile = new File(dir, "transactions.txt");
            try (PrintWriter pw = new PrintWriter(trFile))
            {
                for (Transaction t : transactions)
                {
                    pw.println(t.getDate().toString() + ";" + t.getType() + ";" + t.getAmount());
                }
            }
        }
        catch (Exception e)
        {
            System.out.println("Ошибка при сохранении данных: " + e.getMessage());
        }
    }

    public static BankAccount loadFromFile()
    {
        try
        {
            File dir = new File("data");
            File accFile = new File(dir, "account.txt");
            File trFile = new File(dir, "transactions.txt");

            if (!accFile.exists() || !trFile.exists())
                return null;

            BufferedReader br = new BufferedReader(new FileReader(accFile));
            String line;
            line = br.readLine();
            if (line == null) { br.close(); return null; }
            int id = Integer.parseInt(line);
            line = br.readLine();
            if (line == null) { br.close(); return null; }
            int balance = Integer.parseInt(line);
            line = br.readLine();
            if (line == null) { br.close(); return null; }
            boolean opened = Boolean.parseBoolean(line);
            br.close();

            BankAccount account = new BankAccount(id);
            account.balance = balance;
            account.opened = opened;

            br = new BufferedReader(new FileReader(trFile));
            while ((line = br.readLine()) != null)
            {
                String[] parts = line.split(";");
                if (parts.length == 3)
                {
                    LocalDate date = LocalDate.parse(parts[0]);
                    String type = parts[1];
                    int amount = Integer.parseInt(parts[2]);
                    account.transactions.add(new Transaction(date, type, amount));
                }
            }
            br.close();

            return account;
        }
        catch (Exception e)
        {
            System.out.println("Ошибка при загрузке данных: " + e.getMessage());
            return null;
        }
    }
}
