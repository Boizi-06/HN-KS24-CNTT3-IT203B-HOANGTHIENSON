package Sesion05;

import java.util.*;
import java.util.stream.*;

public class BTTH {

    // ================= MENU ITEM =================

    static abstract class MenuItem {
        private String id;
        private String name;
        private double price;

        public MenuItem(String id, String name, double price) {
            this.id = id;
            this.name = name;
            this.price = price;
        }

        public String getName() {
            return name;
        }

        public double getPrice() {
            return price;
        }

        public abstract double calculatePrice();
    }

    // ================= FOOD =================

    static class Food extends MenuItem {

        public Food(String id, String name, double price) {
            super(id, name, price);
        }

        @Override
        public double calculatePrice() {
            return getPrice();
        }
    }

    // ================= DRINK =================

    static class Drink extends MenuItem {

        private String size;

        public Drink(String id, String name, double price, String size) {
            super(id, name, price);
            this.size = size;
        }

        @Override
        public double calculatePrice() {

            if (size.equals("L"))
                return getPrice() + 5000;
            if (size.equals("M"))
                return getPrice() + 3000;

            return getPrice();
        }
    }

    // ================= ORDER =================

    static class Order {

        private Map<MenuItem, Integer> items = new HashMap<>();

        public void addItem(MenuItem item, int quantity) {
            items.put(item, quantity);
        }

        public double calculateTotal() {

            return items.entrySet()
                    .stream()
                    .mapToDouble(e -> e.getKey().calculatePrice() * e.getValue())
                    .sum();
        }
    }

    // ================= MENU SERVICE =================

    static class MenuService {

        private List<MenuItem> menu = new ArrayList<>();

        public void addItem(MenuItem item) {
            menu.add(item);
        }

        public List<MenuItem> searchByName(String keyword) {

            return menu.stream()
                    .filter(i -> i.getName().toLowerCase().contains(keyword.toLowerCase()))
                    .collect(Collectors.toList());
        }

        public void showMenu() {

            menu.forEach(i -> System.out.println(i.getName() + " - " + i.getPrice()));
        }
    }

    // ================= MAIN =================

    public static void main(String[] args) {

        MenuService menuService = new MenuService();

        Food burger = new Food("F01", "Burger", 50000);
        Drink coca = new Drink("D01", "Coca", 10000, "L");

        menuService.addItem(burger);
        menuService.addItem(coca);

        System.out.println("=== MENU ===");
        menuService.showMenu();

        Order order = new Order();

        order.addItem(burger, 2);
        order.addItem(coca, 1);

        double total = order.calculateTotal();

        System.out.println("Total: " + total);
    }
}