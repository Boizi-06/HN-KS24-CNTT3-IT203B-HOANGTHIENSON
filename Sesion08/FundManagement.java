package Sesion08;

import java.util.*;
import java.util.stream.Collectors;

// ================= ENTITY =================
class Transaction {
    private String transactionId;
    private String studentName;
    private String message;
    private double amount;

    public Transaction() {
    }

    public Transaction(String transactionId, String studentName, String message, double amount) {
        this.transactionId = transactionId;
        this.studentName = studentName;
        this.message = message;
        this.amount = amount;
    }

    // Getter Setter
    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    // Nhập dữ liệu
    public void inputData(Scanner sc) {
        System.out.print("Nhập mã GD: ");
        this.transactionId = sc.nextLine();

        do {
            System.out.print("Tên người đóng: ");
            this.studentName = sc.nextLine();
        } while (this.studentName.trim().isEmpty());

        System.out.print("Lời nhắn: ");
        this.message = sc.nextLine();

        do {
            System.out.print("Số tiền: ");
            this.amount = Double.parseDouble(sc.nextLine());
        } while (this.amount <= 0);
    }

    // Hiển thị
    public void displayData() {
        System.out.printf("| %-6s | %-15s | %-20s | %-10.0f |\n",
                transactionId, studentName, message, amount);
    }
}

// ================= BUSINESS (Singleton) =================
class TransactionBusiness {
    private static TransactionBusiness instance;
    private List<Transaction> list = new ArrayList<>();

    private TransactionBusiness() {
    }

    public static TransactionBusiness getInstance() {
        if (instance == null) {
            instance = new TransactionBusiness();
        }
        return instance;
    }

    // Hiển thị
    public void displayAll() {
        if (list.isEmpty()) {
            System.out.println("Chưa có giao dịch!");
            return;
        }
        System.out.println("===== SAO KÊ =====");
        list.forEach(Transaction::displayData);
    }

    // Thêm
    public boolean add(Transaction t) {
        boolean exists = list.stream()
                .anyMatch(x -> x.getTransactionId().equals(t.getTransactionId()));
        if (exists)
            return false;
        list.add(t);
        return true;
    }

    // Tìm theo ID
    public Optional<Transaction> findById(String id) {
        return list.stream()
                .filter(x -> x.getTransactionId().equals(id))
                .findFirst();
    }

    // Xóa
    public boolean delete(String id) {
        Optional<Transaction> t = findById(id);
        t.ifPresent(list::remove);
        return t.isPresent();
    }

    // Tìm theo tên
    public List<Transaction> searchByName(String name) {
        return list.stream()
                .filter(x -> x.getStudentName().toLowerCase().contains(name.toLowerCase()))
                .collect(Collectors.toList());
    }

    // Sắp xếp
    public void sortDesc() {
        list.sort((a, b) -> Double.compare(b.getAmount(), a.getAmount()));
    }

    // Lọc >250k
    public List<Transaction> vipList() {
        return list.stream()
                .filter(x -> x.getAmount() > 250000)
                .collect(Collectors.toList());
    }

    // Thống kê
    public void statistics() {
        double sum = list.stream().mapToDouble(Transaction::getAmount).sum();

        Optional<Transaction> max = list.stream()
                .max(Comparator.comparing(Transaction::getAmount));

        System.out.println("Tổng quỹ: " + sum);

        max.ifPresent(x -> {
            System.out.println("Người đóng nhiều nhất: " + x.getStudentName()
                    + " - " + x.getAmount());
        });
    }
}

// ================= MAIN =================
public class FundManagement {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        TransactionBusiness service = TransactionBusiness.getInstance();

        while (true) {
            System.out.println("\n===== MENU =====");
            System.out.println("1. Hiển thị");
            System.out.println("2. Thêm");
            System.out.println("3. Cập nhật");
            System.out.println("4. Xóa");
            System.out.println("5. Tìm theo tên");
            System.out.println("6. Lọc VIP");
            System.out.println("7. Sắp xếp");
            System.out.println("8. Thống kê");
            System.out.println("9. Thoát");

            System.out.print("Chọn: ");
            int choice = Integer.parseInt(sc.nextLine());

            switch (choice) {
                case 1:
                    service.displayAll();
                    break;

                case 2:
                    while (true) {
                        Transaction t = new Transaction();
                        t.inputData(sc);

                        if (!service.add(t)) {
                            System.out.println("Trùng mã! Nhập lại!");
                            continue;
                        }

                        System.out.print("Thêm tiếp? (y/n): ");
                        if (!sc.nextLine().equalsIgnoreCase("y"))
                            break;
                    }
                    break;

                case 3:
                    System.out.print("Nhập ID cần sửa: ");
                    String id = sc.nextLine();

                    service.findById(id).ifPresentOrElse(t -> {
                        System.out.println("1. Tên");
                        System.out.println("2. Lời nhắn");

                        int c = Integer.parseInt(sc.nextLine());
                        if (c == 1) {
                            System.out.print("Tên mới: ");
                            t.setStudentName(sc.nextLine());
                        } else {
                            System.out.print("Lời nhắn mới: ");
                            t.setMessage(sc.nextLine());
                        }
                    }, () -> System.out.println("Không tìm thấy!"));
                    break;

                case 4:
                    System.out.print("Nhập ID xóa: ");
                    if (!service.delete(sc.nextLine()))
                        System.out.println("Không tồn tại!");
                    break;

                case 5:
                    System.out.print("Tên cần tìm: ");
                    List<Transaction> rs = service.searchByName(sc.nextLine());
                    if (rs.isEmpty())
                        System.out.println("Không tìm thấy!");
                    else
                        rs.forEach(Transaction::displayData);
                    break;

                case 6:
                    service.vipList().forEach(Transaction::displayData);
                    break;

                case 7:
                    service.sortDesc();
                    System.out.println("Đã sắp xếp!");
                    break;

                case 8:
                    service.statistics();
                    break;

                case 9:
                    return;
            }
        }
    }
}