package Sesion07;

import java.util.*;

// ===== Order =====
class Order {
    String id;

    public Order(String id) {
        this.id = id;
    }
}

// ===== Repository Interface =====
interface OrderRepository {
    void save(Order order);

    List<Order> findAll();
}

// ===== Notification Interface =====
interface NotificationService {
    void send(String message, String recipient);
}

// ===== File Repository =====
class FileOrderRepository implements OrderRepository {
    @Override
    public void save(Order order) {
        System.out.println("Lưu đơn hàng vào file: " + order.id);
    }

    @Override
    public List<Order> findAll() {
        return new ArrayList<>();
    }
}

// ===== Database Repository =====
class DatabaseOrderRepository implements OrderRepository {
    @Override
    public void save(Order order) {
        System.out.println("Lưu đơn hàng vào database: " + order.id);
    }

    @Override
    public List<Order> findAll() {
        return new ArrayList<>();
    }
}

// ===== Email =====
class EmailService implements NotificationService {
    @Override
    public void send(String message, String recipient) {
        System.out.println("Gửi email: " + message);
    }
}

// ===== SMS =====
class SMSNotification implements NotificationService {
    @Override
    public void send(String message, String recipient) {
        System.out.println("Gửi SMS: " + message);
    }
}

// ===== Order Service (CHUẨN DIP) =====
class OrderService {
    private OrderRepository repository;
    private NotificationService notification;

    // Inject qua constructor
    public OrderService(OrderRepository repository, NotificationService notification) {
        this.repository = repository;
        this.notification = notification;
    }

    public void createOrder(Order order) {
        repository.save(order);
        notification.send("Đơn hàng " + order.id + " đã được tạo", "user");
    }
}

// ===== MAIN =====
public class Bai4 {
    public static void main(String[] args) {

        // ===== Cấu hình 1 =====
        System.out.println("=== File + Email ===");

        OrderRepository repo1 = new FileOrderRepository();
        NotificationService noti1 = new EmailService();

        OrderService service1 = new OrderService(repo1, noti1);
        service1.createOrder(new Order("ORD001"));

        // ===== Cấu hình 2 =====
        System.out.println("\n=== Database + SMS ===");

        OrderRepository repo2 = new DatabaseOrderRepository();
        NotificationService noti2 = new SMSNotification();

        OrderService service2 = new OrderService(repo2, noti2);
        service2.createOrder(new Order("ORD002"));
    }
}