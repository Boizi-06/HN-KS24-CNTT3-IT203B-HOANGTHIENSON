package Sesion09;

import java.util.*;

// ===== Main Class =====
public class MiniPrj {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ProductService service = new ProductService();

        while (true) {
            System.out.println("\n----- QUAN LY SAN PHAM -----");
            System.out.println("1. Them");
            System.out.println("2. Xem");
            System.out.println("3. Cap nhat");
            System.out.println("4. Xoa");
            System.out.println("5. Thoat");
            System.out.print("Chon: ");

            int choice = sc.nextInt();
            sc.nextLine();

            try {
                switch (choice) {
                    case 1:
                        System.out.print("Loai (1 Physical, 2 Digital): ");
                        int type = sc.nextInt();
                        sc.nextLine();

                        System.out.print("ID: ");
                        String id = sc.nextLine();

                        System.out.print("Ten: ");
                        String name = sc.nextLine();

                        System.out.print("Gia: ");
                        double price = sc.nextDouble();

                        System.out.print(type == 1 ? "Weight: " : "Size: ");
                        double extra = sc.nextDouble();

                        Product p = ProductFactory.create(type, id, name, price, extra);
                        service.addProduct(p);
                        System.out.println("Thanh cong!");
                        break;

                    case 2:
                        service.showAll();
                        break;

                    case 3:
                        System.out.print("ID can sua: ");
                        String uid = sc.nextLine();

                        System.out.print("Ten moi: ");
                        String newName = sc.nextLine();

                        System.out.print("Gia moi: ");
                        double newPrice = sc.nextDouble();

                        service.update(uid, newName, newPrice);
                        System.out.println("Da cap nhat!");
                        break;

                    case 4:
                        System.out.print("ID can xoa: ");
                        String did = sc.nextLine();
                        service.delete(did);
                        System.out.println("Da xoa!");
                        break;

                    case 5:
                        System.out.println("Thoat...");
                        return;
                }
            } catch (InvalidProductException e) {
                System.out.println("Loi: " + e.getMessage());
            }
        }
    }
}

// ===== Exception =====
class InvalidProductException extends Exception {
    public InvalidProductException(String message) {
        super(message);
    }
}

// ===== Abstract Product =====
abstract class Product {
    protected String id;
    protected String name;
    protected double price;

    public Product(String id, String name, double price) throws InvalidProductException {
        if (id.isEmpty() || name.isEmpty() || price < 0) {
            throw new InvalidProductException("Du lieu khong hop le!");
        }
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public abstract void displayInfo();
}

// ===== Physical Product =====
class PhysicalProduct extends Product {
    private double weight;

    public PhysicalProduct(String id, String name, double price, double weight)
            throws InvalidProductException {
        super(id, name, price);
        if (weight < 0)
            throw new InvalidProductException("Weight khong hop le!");
        this.weight = weight;
    }

    @Override
    public void displayInfo() {
        System.out.println("[Physical] " + id + " | " + name +
                " | " + price + " | Weight: " + weight);
    }
}

// ===== Digital Product =====
class DigitalProduct extends Product {
    private double size;

    public DigitalProduct(String id, String name, double price, double size)
            throws InvalidProductException {
        super(id, name, price);
        if (size < 0)
            throw new InvalidProductException("Size khong hop le!");
        this.size = size;
    }

    @Override
    public void displayInfo() {
        System.out.println("[Digital] " + id + " | " + name +
                " | " + price + " | Size: " + size + "MB");
    }
}

// ===== Singleton Database =====
class ProductDatabase {
    private static ProductDatabase instance;
    private List<Product> products;

    private ProductDatabase() {
        products = new ArrayList<>();
    }

    public static ProductDatabase getInstance() {
        if (instance == null) {
            instance = new ProductDatabase();
        }
        return instance;
    }

    public List<Product> getAll() {
        return products;
    }
}

// ===== Service =====
class ProductService {
    private ProductDatabase db = ProductDatabase.getInstance();

    public void addProduct(Product p) {
        db.getAll().add(p);
    }

    public void showAll() {
        if (db.getAll().isEmpty()) {
            System.out.println("Danh sach rong!");
            return;
        }
        db.getAll().forEach(Product::displayInfo);
    }

    public Product findById(String id) {
        return db.getAll().stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public void delete(String id) {
        db.getAll().removeIf(p -> p.getId().equals(id));
    }

    public void update(String id, String name, double price) {
        Product p = findById(id);
        if (p != null) {
            p.setName(name);
            p.setPrice(price);
        } else {
            System.out.println("Khong tim thay!");
        }
    }
}

// ===== Factory =====
class ProductFactory {
    public static Product create(int type, String id, String name,
            double price, double extra)
            throws InvalidProductException {

        switch (type) {
            case 1:
                return new PhysicalProduct(id, name, price, extra);
            case 2:
                return new DigitalProduct(id, name, price, extra);
            default:
                throw new InvalidProductException("Loai khong hop le!");
        }
    }
}