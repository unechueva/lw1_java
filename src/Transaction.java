import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Transaction
{
    private LocalDate date;
    private String type;
    private int amount;

    public Transaction(LocalDate date, String type, int amount)
    {
        this.date = date;
        this.type = type;
        this.amount = amount;
    }

    public LocalDate getDate()
    {
        return date;
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
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return fmt.format(date) + " | " + type + " | " + amount;
    }
}
