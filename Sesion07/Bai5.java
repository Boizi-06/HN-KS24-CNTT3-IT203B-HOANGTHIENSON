package Sesion07;

import java.util.*;

// ===== MODEL =====
class Product {
    String id, name, category;
    double price;

    public Product(String id, String name, double price, String category) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.category = category;
    }
}

class Customer {
    String name, email, phone;

    public Customer(String name, String email, String phone) {
        this.name = name;
        this.email = email;
        this.phone = phone;
    }
}

class OrderItem {
    Product product;
    int quantity;

    public OrderItem(Product p, int q) {
        product = p;
        quantity = q;
    }

    public double getTotal() {
        return product.price * quantity;
    }
}

class Order {
    String id;
    Customer customer;
    List<OrderItem> items = new ArrayList<>();
    double finalAmount;

    public Order(String id, Customer customer) {
        this.id = id;
        this.customer = customer;
    }
}

// ===== DISCOUNT (OCP) =====
interface DiscountStrategy {
    double apply(double total);
}

class PercentageDiscount implements DiscountStrategy {
    double percent;

    public PercentageDiscount(double percent) {
        this.percent = percent;
    }

    public double apply(double total) {
        return total * (1 - percent / 100);
    }
}

class FixedDiscount implements DiscountStrategy {
    double amount;

    public FixedDiscount(double amount) {
        this.amount = amount;
    }

    public double apply(double total) {
        return total - amount;
    }
}

class HolidayDiscount implements DiscountStrategy {
    public double apply(double total) {
        return total * 0.85;
    }
}

// ===== PAYMENT (LSP + ISP) =====
interface PaymentMethod {
    void pay(double amount);
}

class CODPayment implements PaymentMethod {
    public void pay(double amount) {
        System.out.println("Thanh toán COD: " + amount);
    }
}

class CreditCardPayment implements PaymentMethod {
    public void pay(double amount) {
        System.out.println("Thanh toán thẻ: " + amount);
    }
}

class MomoPayment implements PaymentMethod {
    public void pay(double amount) {
        System.out.println("Thanh toán MoMo: " + amount);
    }
}

class VNPayPayment implements PaymentMethod {
    public void pay(double amount) {
        System.out.println("Thanh toán VNPay: " + amount);
    }
}

// ===== REPOSITORY (DIP) =====
interface OrderRepository {
    void save(Order order);

    List<Order> findAll();
}

class FileOrderRepository implements OrderRepository {
    List<Order> list = new ArrayList<>();

    public void save(Order order) {
        list.add(order);
        System.out.println("Đã lưu đơn hàng " + order.id);
    }

    public List<Order> findAll() {
        return list;
    }
}

// ===== NOTIFICATION =====
interface NotificationService {
    void send(String msg);
}

class EmailNotification implements NotificationService {
    public void send(String msg) {
        System.out.println("Gửi email: " + msg);
    }
}

class SMSNotification implements NotificationService {
    public void send(String msg) {
        System.out.println("Gửi SMS: " + msg);
    }
}

// ===== INVOICE =====
class InvoiceGenerator {
    public void print(Order order, double total, double finalAmount) {
        System.out.println("\n=== HÓA ĐƠN ===");
        System.out.println("Khách: " + order.customer.name);

        for (OrderItem i : order.items) {
            System.out.println(i.product.name + " - SL: " + i.quantity +
                    " - Giá: " + i.product.price +
                    " - Thành tiền: " + i.getTotal());
        }

        System.out.println("Tổng tiền: " + total);
        System.out.println("Cần thanh toán: " + finalAmount);
    }
}

// ===== SERVICE (DIP) =====
class OrderService {
    private OrderRepository repo;
    private NotificationService noti;

    public OrderService(OrderRepository repo, NotificationService noti) {
        this.repo = repo;
        this.noti = noti;
    }

    public void createOrder(Order order,
            DiscountStrategy discount,
            PaymentMethod payment,
            InvoiceGenerator invoice) {

        double total = 0;
        for (OrderItem i : order.items) {
            total += i.getTotal();
        }

        double finalAmount = discount.apply(total);
        order.finalAmount = finalAmount;

        invoice.print(order, total, finalAmount);

        payment.pay(finalAmount);

        repo.save(order);

        noti.send("Đơn hàng " + order.id + " đã được tạo");
    }

    public List<Order> getAll() {
        return repo.findAll();
    }
}

// ===== MAIN =====
public class Bai5 {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        List<Product> products = new ArrayList<>();
        List<Customer> customers = new ArrayList<>();

        OrderRepository repo = new FileOrderRepository();
        NotificationService noti = new EmailNotification();
        OrderService service = new OrderService(repo, noti);

        InvoiceGenerator invoice = new InvoiceGenerator();

        while (true) {
            System.out.println("\n1.Thêm SP  2.Thêm KH  3.Tạo đơn  4.Xem đơn  5.Doanh thu  0.Thoát");
            int choice = sc.nextInt();

            if (choice == 1) {
                sc.nextLine();
                System.out.print("Mã: ");
                String id = sc.nextLine();
                System.out.print("Tên: ");
                String name = sc.nextLine();
                System.out.print("Giá: ");
                double price = sc.nextDouble();
                sc.nextLine();
                System.out.print("Danh mục: ");
                String cat = sc.nextLine();

                products.add(new Product(id, name, price, cat));
                System.out.println("Đã thêm sản phẩm " + id);
            }

            else if (choice == 2) {
                sc.nextLine();
                System.out.print("Tên: ");
                String name = sc.nextLine();
                System.out.print("Email: ");
                String email = sc.nextLine();
                System.out.print("SĐT: ");
                String phone = sc.nextLine();

                customers.add(new Customer(name, email, phone));
                System.out.println("Đã thêm khách hàng");
            }

            else if (choice == 3) {
                Customer c = customers.get(0);
                Product p = products.get(0);

                Order order = new Order("ORD" + (repo.findAll().size() + 1), c);
                order.items.add(new OrderItem(p, 1));

                DiscountStrategy discount = new PercentageDiscount(10);
                PaymentMethod payment = new CreditCardPayment();

                service.createOrder(order, discount, payment, invoice);
            }

            else if (choice == 4) {
                for (Order o : service.getAll()) {
                    System.out.println(o.id + " - " + o.customer.name + " - " + o.finalAmount);
                }
            }

            else if (choice == 5) {
                double sum = 0;
                for (Order o : service.getAll()) {
                    sum += o.finalAmount;
                }
                System.out.println("Doanh thu: " + sum);
            }

            else if (choice == 0)
                break;
        }
    }
}