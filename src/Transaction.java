import java.time.LocalDateTime;

public class Transaction
{
    private LocalDateTime time;
    private String type;
    private int amount;

    public Transaction(LocalDateTime time, String type, int amount)
    {
        this.time = time;
        this.type = type;
        this.amount = amount;
    }

    public LocalDateTime getTime()
    {
        return time;
    }

    public String getType()
    {
        return type;
    }

    public int getAmount()
    {
        return amount;
    }

    public String toString()
    {
        return time.toString() + " | " + type + " | " + amount;
    }
}
