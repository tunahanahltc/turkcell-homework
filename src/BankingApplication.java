import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class BankingApplication {
    private static HashMap<String, Customer> customers = new HashMap<>();
    private static List<String> transactions = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        initializeData();
        mainMenu();
        scanner.close();
    }

    private static void initializeData() {
        customers.put("C001", new Customer("C001", "Ahmet Yılmaz", 5000));
        customers.put("C002", new Customer("C002", "Fatma Kaya", 3000));
        customers.put("C003", new Customer("C003", "Mehmet Demir", 7500));
        System.out.println("✓ Sistem başlatıldı. 3 örnek müşteri yüklendi.\n");
    }

    private static void mainMenu() {
        boolean running = true;
        while (running) {
            System.out.println("\n========== BANKA MENU ==========");
            System.out.println("1. Müşteri Kaydı");
            System.out.println("2. Hesap Özeti");
            System.out.println("3. Para Yatırma");
            System.out.println("4. Para Çekme");
            System.out.println("5. Para Transferi");
            System.out.println("6. İşlem Geçmişi");
            System.out.println("7. Veritabanını Görüntüle");
            System.out.println("8. Çıkış");
            System.out.print("Seçiminiz: ");

            int choice = getInput();
            switch (choice) {
                case 1:
                    registerCustomer();
                    break;
                case 2:
                    viewBalance();
                    break;
                case 3:
                    deposit();
                    break;
                case 4:
                    withdraw();
                    break;
                case 5:
                    transfer();
                    break;
                case 6:
                    viewTransactions();
                    break;
                case 7:
                    viewDatabase();
                    break;
                case 8:
                    running = false;
                    System.out.println("\nÇıkılıyor...");
                    break;
                default:
                    System.out.println("✗ Geçersiz seçim!");
            }
        }
    }

    private static void registerCustomer() {
        System.out.print("Müşteri ID: ");
        String id = scanner.nextLine();

        if (customers.containsKey(id)) {
            System.out.println("✗ Bu ID zaten kayıtlı!");
            return;
        }

        System.out.print("Ad Soyad: ");
        String name = scanner.nextLine();
        System.out.print("İlk Bakiye (TL): ");
        double balance = getDoubleInput();

        if (balance < 0) {
            System.out.println("✗ Bakiye negatif olamaz!");
            return;
        }

        customers.put(id, new Customer(id, name, balance));
        transactions.add("→ [" + id + "] " + name + " kaydedildi. Başlangıç: " + balance + " TL");
        System.out.println("✓ Müşteri başarıyla kaydedildi!");
    }

    private static void viewBalance() {
        System.out.print("Müşteri ID: ");
        String id = scanner.nextLine();

        if (!customers.containsKey(id)) {
            System.out.println("✗ Müşteri bulunamadı!");
            return;
        }

        System.out.println("\n" + customers.get(id));
    }

    private static void deposit() {
        System.out.print("Müşteri ID: ");
        String id = scanner.nextLine();

        if (!customers.containsKey(id)) {
            System.out.println("✗ Müşteri bulunamadı!");
            return;
        }

        System.out.print("Yatırılacak Tutar (TL): ");
        double amount = getDoubleInput();

        if (amount <= 0) {
            System.out.println("✗ Geçerli tutar giriniz!");
            return;
        }

        Customer customer = customers.get(id);
        customer.setBalance(customer.getBalance() + amount);
        transactions.add("↓ [" + id + "] " + customer.getName() + " " + amount + " TL yatırdı");
        System.out.println("✓ Para yatırıldı! Yeni bakiye: " + customer.getBalance() + " TL");
    }

    private static void withdraw() {
        System.out.print("Müşteri ID: ");
        String id = scanner.nextLine();

        if (!customers.containsKey(id)) {
            System.out.println("✗ Müşteri bulunamadı!");
            return;
        }

        System.out.print("Çekilecek Tutar (TL): ");
        double amount = getDoubleInput();

        if (amount <= 0) {
            System.out.println("✗ Geçerli tutar giriniz!");
            return;
        }

        Customer customer = customers.get(id);
        if (customer.getBalance() < amount) {
            System.out.println("✗ Yetersiz bakiye!");
            return;
        }

        customer.setBalance(customer.getBalance() - amount);
        transactions.add("↑ [" + id + "] " + customer.getName() + " " + amount + " TL çekti");
        System.out.println("✓ Para çekildi! Yeni bakiye: " + customer.getBalance() + " TL");
    }

    private static void transfer() {
        System.out.print("Gönderen ID: ");
        String fromId = scanner.nextLine();
        System.out.print("Alan ID: ");
        String toId = scanner.nextLine();

        if (!customers.containsKey(fromId) || !customers.containsKey(toId)) {
            System.out.println("✗ Müşteri bulunamadı!");
            return;
        }

        System.out.print("Transfer Tutarı (TL): ");
        double amount = getDoubleInput();

        if (amount <= 0) {
            System.out.println("✗ Geçerli tutar giriniz!");
            return;
        }

        Customer sender = customers.get(fromId);
        Customer receiver = customers.get(toId);

        if (sender.getBalance() < amount) {
            System.out.println("✗ Yetersiz bakiye!");
            return;
        }

        sender.setBalance(sender.getBalance() - amount);
        receiver.setBalance(receiver.getBalance() + amount);
        transactions.add("← [" + fromId + "] " + sender.getName() + " → [" + toId + "] " + receiver.getName() + " " + amount + " TL transfer");
        System.out.println("✓ Transfer başarılı!");
    }

    private static void viewTransactions() {
        System.out.println("\n========== İŞLEM GEÇMİŞİ ==========");
        if (transactions.isEmpty()) {
            System.out.println("İşlem yok.");
        } else {
            for (String transaction : transactions) {
                System.out.println(transaction);
            }
        }
    }

    private static void viewDatabase() {
        System.out.println("\n========== MÜŞTERI VERİTABANI ==========");
        if (customers.isEmpty()) {
            System.out.println("Müşteri yok.");
        } else {
            for (Customer customer : customers.values()) {
                System.out.println(customer);
            }
        }
        System.out.println("Toplam Müşteri: " + customers.size());
    }

    private static int getInput() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private static double getDoubleInput() {
        try {
            return Double.parseDouble(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("✗ Geçerli sayı giriniz!");
            return 0;
        }
    }
}

class Customer {
    private String id;
    private String name;
    private double balance;

    public Customer(String id, String name, double balance) {
        this.id = id;
        this.name = name;
        this.balance = balance;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "ID: " + id + " | Ad: " + name + " | Bakiye: " + balance + " TL";
    }
}
