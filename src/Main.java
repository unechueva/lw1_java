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
                    System.out.println("Положить деньги (сделать)");
                    break;
                case "3":
                    System.out.println("Снять деньги (сделать)");
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
                    System.out.println("Список транзакций (сделать)");
                    break;
                case "6":
                    System.out.println("Поиск по атрибутам (сделать)");
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
