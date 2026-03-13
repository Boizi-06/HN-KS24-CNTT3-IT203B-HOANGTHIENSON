package Sesion05;

import java.util.*;

class InvalidProductException extends Exception {
    public InvalidProductException(String message) {
        super(message);
    }
}

class Product {
    private int id;
    private String name;
    private double price;
    private int quantity;
    private String category;

    public Product() {
    }

    public Product(int id, String name, double price, int quantity, String category) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}

public class MiniPRJ {

    static ArrayList<Product> productList = new ArrayList<>();
    static Scanner sc = new Scanner(System.in);

    public static void addProduct() throws InvalidProductException {

        System.out.print("Nhap ID: ");
        int id = sc.nextInt();
        sc.nextLine();

        boolean exists = productList.stream()
                .anyMatch(p -> p.getId() == id);

        if (exists) {
            throw new InvalidProductException("ID da ton tai!");
        }

        System.out.print("Nhap ten: ");
        String name = sc.nextLine();

        System.out.print("Nhap gia: ");
        double price = sc.nextDouble();

        System.out.print("Nhap so luong: ");
        int quantity = sc.nextInt();
        sc.nextLine();

        System.out.print("Nhap category: ");
        String category = sc.nextLine();

        productList.add(new Product(id, name, price, quantity, category));

        System.out.println("Them san pham thanh cong!");
    }

    public static void displayProducts() {

        if (productList.isEmpty()) {
            System.out.println("Danh sach trong!");
            return;
        }

        System.out.printf("%-5s %-15s %-10s %-10s %-15s\n",
                "ID", "Name", "Price", "Qty", "Category");

        productList.forEach(p -> System.out.printf("%-5d %-15s %-10.2f %-10d %-15s\n",
                p.getId(),
                p.getName(),
                p.getPrice(),
                p.getQuantity(),
                p.getCategory()));
    }

    public static void updateQuantity() throws InvalidProductException {

        System.out.print("Nhap ID can cap nhat: ");
        int id = sc.nextInt();

        Optional<Product> product = productList.stream()
                .filter(p -> p.getId() == id)
                .findFirst();

        if (!product.isPresent()) {
            throw new InvalidProductException("Khong tim thay san pham!");
        }

        System.out.print("Nhap so luong moi: ");
        int newQty = sc.nextInt();

        product.get().setQuantity(newQty);

        System.out.println("Cap nhat thanh cong!");
    }

    public static void deleteOutOfStock() {
        productList.removeIf(p -> p.getQuantity() == 0);
        System.out.println("Da xoa san pham het hang!");
    }

    public static void main(String[] args) {

        while (true) {
            System.out.println("\n========= PRODUCT MANAGEMENT SYSTEM =========");
            System.out.println("1. Them san pham moi");
            System.out.println("2. Hien thi danh sach san pham");
            System.out.println("3. Cap nhat so luong theo ID");
            System.out.println("4. Xoa san pham da het hang");
            System.out.println("5. Thoat");
            System.out.println("=============================================");
            System.out.print("Lua chon: ");

            int choice = sc.nextInt();

            switch (choice) {

                case 1:
                    try {
                        addProduct();
                    } catch (InvalidProductException e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                case 2:
                    displayProducts();
                    break;

                case 3:
                    try {
                        updateQuantity();
                    } catch (InvalidProductException e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                case 4:
                    deleteOutOfStock();
                    displayProducts();
                    break;

                case 5:
                    System.out.println("Thoat chuong trinh...");
                    return;

                default:
                    System.out.println("Lua chon khong hop le!");
            }
        }
    }
}