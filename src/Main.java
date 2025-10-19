import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Scanner;

public class Main
{
    public static void main(String[] args)
    {
        Scanner sc = new Scanner(System.in);
        BankAccount account = null;
        int nextId = 1;

        while (true)
        {
            System.out.println("\nМеню:");
            System.out.println("1 - Открыть счёт");
            System.out.println("2 - Пополнить счёт");
            System.out.println("3 - Снять со счёта");
            System.out.println("4 - Проверить баланс");
            System.out.println("5 - Показать транзакции");
            System.out.println("6 - Поиск по атрибутам");
            System.out.println("0 - Выход");
            System.out.print("Ваш выбор: ");

            String input = sc.nextLine();

            switch (input)
            {
                case "1":
                    if (account == null)
                    {
                        account = new BankAccount(nextId++);
                        System.out.println("Счёт открыт. Id: " + account.getId());
                    }
                    else
                    {
                        System.out.println("Счёт уже открыт.");
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
                        System.out.println("Счёт не открыт.");
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
                    if (account == null)
                    {
                        System.out.println("Счёт не открыт. Сначала откройте счёт (пункт 1).");
                        break;
                    }
                    System.out.println("Выберите тип поиска:");
                    System.out.println("1 - По точной сумме");
                    System.out.println("2 - По диапазону сумм");
                    System.out.println("3 - По типу (DEPOSIT/WITHDRAW)");
                    System.out.println("4 - По диапазону дат (гггг-мм-дд)");
                    System.out.print("Выбор: ");
                    String sChoice = sc.nextLine();
                    switch (sChoice)
                    {
                        case "1":
                            System.out.print("Введите сумму (целое число): ");
                            String sumStr = sc.nextLine();
                            try
                            {
                                int sum = Integer.parseInt(sumStr);
                                List<Transaction> res = account.findByAmount(sum);
                                printSearchResult(res);
                            }
                            catch (NumberFormatException e)
                            {
                                System.out.println("Неверный формат суммы.");
                            }
                            break;
                        case "2":
                            System.out.print("Введите минимальную сумму (целое число): ");
                            String minStr = sc.nextLine();
                            System.out.print("Введите максимальную сумму (целое число): ");
                            String maxStr = sc.nextLine();
                            try
                            {
                                int min = Integer.parseInt(minStr);
                                int max = Integer.parseInt(maxStr);
                                if (min > max)
                                {
                                    System.out.println("Мин не может быть больше макс.");
                                }
                                else
                                {
                                    List<Transaction> res = account.findByAmountRange(min, max);
                                    printSearchResult(res);
                                }
                            }
                            catch (NumberFormatException e)
                            {
                                System.out.println("Неверный формат суммы.");
                            }
                            break;
                        case "3":
                            System.out.print("Введите тип операции (DEPOSIT или WITHDRAW): ");
                            String type = sc.nextLine();
                            if (!type.equalsIgnoreCase("DEPOSIT") && !type.equalsIgnoreCase("WITHDRAW"))
                            {
                                System.out.println("Неверный тип. Допустимо DEPOSIT или WITHDRAW.");
                            }
                            else
                            {
                                List<Transaction> res = account.findByType(type);
                                printSearchResult(res);
                            }
                            break;
                        case "4":
                            System.out.print("Введите начальную дату (гггг-мм-дд): ");
                            String fromStr = sc.nextLine();
                            System.out.print("Введите конечную дату (гггг-мм-дд): ");
                            String toStr = sc.nextLine();
                            try
                            {
                                LocalDate fromDate = LocalDate.parse(fromStr);
                                LocalDate toDate = LocalDate.parse(toStr);
                                LocalDateTime fromDT = fromDate.atStartOfDay();
                                LocalDateTime toDT = toDate.atTime(LocalTime.MAX);
                                if (fromDT.isAfter(toDT))
                                {
                                    System.out.println("Начальная дата позже конечной.");
                                }
                                else
                                {
                                    List<Transaction> res = account.findByDateRange(fromDT, toDT);
                                    printSearchResult(res);
                                }
                            }
                            catch (Exception e)
                            {
                                System.out.println("Неверный формат даты. Используйте гггг-мм-дд.");
                            }
                            break;
                        default:
                            System.out.println("Неверный выбор поиска.");
                            break;
                    }
                    break;

                case "0":
                    System.out.println("Выход из программы...");
                    return;

                default:
                    System.out.println("Неверный выбор. Введите число от 0 до 6.");
                    break;
            }
        }
    }

    private static void printSearchResult(List<Transaction> res)
    {
        if (res == null || res.isEmpty())
        {
            System.out.println("Ничего не найдено.");
        }
        else
        {
            System.out.println("Найденные транзакции:");
            for (Transaction t : res)
            {
                System.out.println(t.toString());
            }
        }
    }
}
