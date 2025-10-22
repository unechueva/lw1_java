import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class Main
{
    public static void main(String[] args)
    {
        Scanner sc = new Scanner(System.in);

        BankAccount account = BankAccount.loadFromFile();
        int nextId = (account == null) ? 1 : account.getId() + 1;
        if (account != null)
        {
            System.out.println("Данные успешно загружены. Текущий баланс: " + account.getBalance());
        }

        DateTimeFormatter dayFmt = DateTimeFormatter.ofPattern("dd-MM-yyyy");

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
                    System.out.print("Введите дату операции (dd-MM-yyyy) или 'today': ");
                    String depDateStr = sc.nextLine();
                    try
                    {
                        LocalDate depDate;
                        if (depDateStr.trim().isEmpty() || depDateStr.equalsIgnoreCase("today") || depDateStr.equalsIgnoreCase("сегодня"))
                            depDate = LocalDate.now();
                        else
                            depDate = LocalDate.parse(depDateStr, dayFmt);

                        System.out.print("Введите сумму для пополнения (целое число): ");
                        String depositStr = sc.nextLine();
                        int amount = Integer.parseInt(depositStr);
                        if (amount <= 0)
                        {
                            System.out.println("Сумма должна быть положительным целым числом.");
                        }
                        else
                        {
                            account.deposit(depDate, amount);
                            System.out.println("Успешно. Новый баланс: " + account.getBalance());
                        }
                    }
                    catch (NumberFormatException e)
                    {
                        System.out.println("Неверный формат суммы. Введите целое число.");
                    }
                    catch (DateTimeParseException e)
                    {
                        System.out.println("Неверный формат даты. Введите dd-MM-yyyy или 'today'.");
                    }
                    break;

                case "3":
                    if (account == null)
                    {
                        System.out.println("Счёт не открыт. Сначала откройте счёт (пункт 1).");
                        break;
                    }
                    System.out.print("Введите дату операции (dd-MM-yyyy) или 'today': ");
                    String wdDateStr = sc.nextLine();
                    try
                    {
                        LocalDate wdDate;
                        if (wdDateStr.trim().isEmpty() || wdDateStr.equalsIgnoreCase("today") || wdDateStr.equalsIgnoreCase("сегодня"))
                            wdDate = LocalDate.now();
                        else
                            wdDate = LocalDate.parse(wdDateStr, dayFmt);

                        System.out.print("Введите сумму для снятия (целое число): ");
                        String withdrawStr = sc.nextLine();
                        int amount = Integer.parseInt(withdrawStr);
                        if (amount <= 0)
                        {
                            System.out.println("Сумма должна быть положительным целым числом.");
                        }
                        else
                        {
                            boolean ok = account.withdraw(wdDate, amount);
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
                    catch (DateTimeParseException e)
                    {
                        System.out.println("Неверный формат даты. Введите dd-MM-yyyy или 'today'.");
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
                    System.out.println("4 - По диапазону дат (dd-MM-yyyy)");
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
                            System.out.print("Введите начальную дату (dd-MM-yyyy) или 'today': ");
                            String fromStr = sc.nextLine();
                            System.out.print("Введите конечную дату (dd-MM-yyyy) или 'today': ");
                            String toStr = sc.nextLine();
                            try
                            {
                                LocalDate fromDate;
                                LocalDate toDate;
                                if (fromStr.trim().isEmpty() || fromStr.equalsIgnoreCase("today") || fromStr.equalsIgnoreCase("сегодня"))
                                    fromDate = LocalDate.now();
                                else
                                    fromDate = LocalDate.parse(fromStr, dayFmt);

                                if (toStr.trim().isEmpty() || toStr.equalsIgnoreCase("today") || toStr.equalsIgnoreCase("сегодня"))
                                    toDate = LocalDate.now();
                                else
                                    toDate = LocalDate.parse(toStr, dayFmt);

                                if (fromDate.isAfter(toDate))
                                {
                                    System.out.println("Начальная дата позже конечной.");
                                }
                                else
                                {
                                    List<Transaction> res = account.findByDateRange(fromDate, toDate);
                                    printSearchResult(res);
                                }
                            }
                            catch (DateTimeParseException e)
                            {
                                System.out.println("Неверный формат даты. Используйте dd-MM-yyyy или 'today'.");
                            }
                            break;
                        default:
                            System.out.println("Неверный выбор поиска.");
                            break;
                    }
                    break;

                case "0":
                    System.out.println("Выход из программы...");
                    if (account != null)
                    {
                        account.saveToFile();
                        System.out.println("Данные сохранены.");
                    }
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
