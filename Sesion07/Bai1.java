package Sesion07;

import java.util.*;

// ===== Product =====
class Product {
    String id;
    String name;
    double price;

    public Product(String id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }
}

// ===== Customer =====
class Customer {
    String name;
    String email;
    String address;

    public Customer(String name, String email, String address) {
        this.name = name;
        this.email = email;
        this.address = address;
    }
}

// ===== Order Item =====
class OrderItem {
    Product product;
    int quantity;

    public OrderItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }
}

// ===== Order =====
class Order {
    String orderId;
    Customer customer;
    List<OrderItem> items = new ArrayList<>();
    double total;

    public Order(String orderId, Customer customer) {
        this.orderId = orderId;
        this.customer = customer;
    }

    public void addItem(Product p, int quantity) {
        items.add(new OrderItem(p, quantity));
    }
}

// ===== OrderCalculator =====
class OrderCalculator {
    public double calculateTotal(Order order) {
        double total = 0;
        for (OrderItem item : order.items) {
            total += item.product.price * item.quantity;
        }
        return total;
    }
}

// ===== OrderRepository =====
class OrderRepository {
    public void save(Order order) {
        System.out.println("Đã lưu đơn hàng " + order.orderId);
    }
}

// ===== EmailService =====
class EmailService {
    public void sendEmail(Order order) {
        System.out.println("Đã gửi email đến " + order.customer.email +
                ": Đơn hàng " + order.orderId + " đã được tạo");
    }
}

// ===== MAIN =====
public class Bai1 {
    public static void main(String[] args) {

        // 1. Tạo sản phẩm
        Product p1 = new Product("SP01", "Laptop", 15000000);
        Product p2 = new Product("SP02", "Chuột", 300000);
        System.out.println("Đã thêm sản phẩm SP01, SP02");

        // 2. Tạo khách hàng
        Customer customer = new Customer("Nguyễn Văn A", "a@example.com", "Hà Nội");
        System.out.println("Đã thêm khách hàng");

        // 3. Tạo đơn hàng
        Order order = new Order("ORD001", customer);
        order.addItem(p1, 1);
        order.addItem(p2, 2);
        System.out.println("Đơn hàng ORD001 được tạo");

        // 4. Tính tiền
        OrderCalculator calculator = new OrderCalculator();
        double total = calculator.calculateTotal(order);
        order.total = total;
        System.out.println("Tổng tiền: " + total);

        // 5. Lưu đơn
        OrderRepository repo = new OrderRepository();
        repo.save(order);

        // 6. Gửi email
        EmailService emailService = new EmailService();
        emailService.sendEmail(order);
    }
}