import java.util.Scanner;
import java.util.List;

public class Main
{
    public static void main(String[] args)
    {
        Scanner sc = new Scanner(System.in);
        BankAccount account = null;
        int nextId = 1;

        while (true)
        {
            System.out.println("Меню:");
            System.out.println("1 - Открыть счёт");
            System.out.println("2 - Положить деньги");
            System.out.println("3 - Снять деньги");
            System.out.println("4 - Показать баланс");
            System.out.println("5 - Список транзакций");
            System.out.println("6 - Поиск по атрибутам");
            System.out.println("7 - Выйти");
            System.out.print("Ваш выбор: ");

            String input = sc.nextLine();

            switch (input)
            {
                case "1":
                    if (account == null)
                    {
                        account = new BankAccount(nextId);
                        nextId++;
                        System.out.println("Счёт открыт. Id: " + account.getId());
                    }
                    else
                    {
                        System.out.println("Счёт уже открыт. Id: " + account.getId());
                    }
                    break;
                case "2":
                    if (account == null)
                    {
                        System.out.println("Счёт не открыт. Сначала откройте счёт (пункт 1).");
                        break;
                    }
                    System.out.print("Введите сумму для пополнения (целое число): ");
                    String depositStr = sc.nextLine();
                    try
                    {
                        int amount = Integer.parseInt(depositStr);
                        if (amount <= 0)
                        {
                            System.out.println("Сумма должна быть положительным целым числом.");
                        }
                        else
                        {
                            account.deposit(amount);
                            System.out.println("Успешно. Новый баланс: " + account.getBalance());
                        }
                    }
                    catch (NumberFormatException e)
                    {
                        System.out.println("Неверный формат суммы. Введите целое число.");
                    }
                    break;
                case "3":
                    if (account == null)
                    {
                        System.out.println("Счёт не открыт. Сначала откройте счёт (пункт 1).");
                        break;
                    }
                    System.out.print("Введите сумму для снятия (целое число): ");
                    String withdrawStr = sc.nextLine();
                    try
                    {
                        int amount = Integer.parseInt(withdrawStr);
                        if (amount <= 0)
                        {
                            System.out.println("Сумма должна быть положительным целым числом.");
                        }
                        else
                        {
                            boolean ok = account.withdraw(amount);
                            if (ok)
                            {
                                System.out.println("Снятие успешно. Новый баланс: " + account.getBalance());
                            }
                            else
                            {
                                System.out.println("Операция запрещена: недостаточно средств.");
                            }
                        }
                    }
                    catch (NumberFormatException e)
                    {
                        System.out.println("Неверный формат суммы. Введите целое число.");
                    }
                    break;
                case "4":
                    if (account == null)
                    {
                        System.out.println("Счёт не открыт. Сначала откройте счёт (пункт 1).");
                    }
                    else
                    {
                        System.out.println("Баланс счёта (id " + account.getId() + "): " + account.getBalance());
                    }
                    break;
                case "5":
                    if (account == null)
                    {
                        System.out.println("Счёт не открыт. Транзакций нет.");
                        break;
                    }
                    List<Transaction> list = account.getTransactions();
                    if (list.isEmpty())
                    {
                        System.out.println("Список транзакций пуст.");
                    }
                    else
                    {
                        System.out.println("Транзакции:");
                        for (Transaction t : list)
                        {
                            System.out.println(t.toString());
                        }
                    }
                    break;
                case "6":
                    System.out.println("Поиск по атрибутам (ещё не реализован)");
                    break;
                case "7":
                    System.out.println("До свидания!");
                    return;
                default:
                    System.out.println("Ошибка ввода. Попробуйте ещё раз.");
            }
        }
    }
}
