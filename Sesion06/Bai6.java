package Sesion06;

import java.util.*;
import java.util.concurrent.*;
import java.lang.management.*;

public class Bai6 {

    static Scanner sc = new Scanner(System.in);

    static Map<String, Room> rooms = new ConcurrentHashMap<>();
    static ExecutorService executor;
    static boolean running = false;
    static boolean paused = false;

    // ================= ROOM =================
    static class Room {

        String name;
        int totalTickets;
        int soldTickets = 0;

        public Room(String name, int totalTickets) {
            this.name = name;
            this.totalTickets = totalTickets;
        }

        public synchronized boolean sellTicket() {

            if (soldTickets < totalTickets) {
                soldTickets++;
                return true;
            }

            return false;
        }

        public synchronized void addTickets(int n) {
            totalTickets += n;
        }

        public synchronized int getSold() {
            return soldTickets;
        }

        public synchronized int getTotal() {
            return totalTickets;
        }
    }

    // ================= BOOKING COUNTER =================
    static class BookingCounter implements Runnable {

        String name;
        Random random = new Random();

        public BookingCounter(String name) {
            this.name = name;
        }

        public void run() {

            System.out.println(name + " bắt đầu bán vé...");

            while (running) {

                try {

                    if (paused) {
                        Thread.sleep(500);
                        continue;
                    }

                    List<Room> list = new ArrayList<>(rooms.values());

                    if (list.isEmpty()) {
                        Thread.sleep(1000);
                        continue;
                    }

                    Room room = list.get(random.nextInt(list.size()));

                    boolean sold = room.sellTicket();

                    if (sold) {
                        System.out.println(name + " bán vé phòng " + room.name);
                    }

                    Thread.sleep(1000);

                } catch (Exception e) {
                }
            }
        }
    }

    // ================= DEADLOCK DETECTOR =================
    static class DeadlockDetector implements Runnable {

        public void run() {

            while (running) {

                try {

                    ThreadMXBean bean = ManagementFactory.getThreadMXBean();

                    long[] ids = bean.findDeadlockedThreads();

                    if (ids != null) {
                        System.out.println("⚠ Phát hiện DEADLOCK!");

                        ThreadInfo[] infos = bean.getThreadInfo(ids);

                        for (ThreadInfo info : infos) {
                            System.out.println(info.getThreadName());
                        }
                    }

                    Thread.sleep(5000);

                } catch (Exception e) {
                }
            }
        }
    }

    // ================= STATISTICS =================
    static void showStatistics() {

        System.out.println("\n=== THỐNG KÊ HIỆN TẠI ===");

        int revenue = 0;

        for (Room r : rooms.values()) {

            int sold = r.getSold();
            int total = r.getTotal();

            System.out.println("Phòng " + r.name +
                    ": Đã bán " + sold + "/" + total + " vé");

            revenue += sold * 250000;
        }

        System.out.println("Tổng doanh thu: " + revenue + " VNĐ");
    }

    // ================= START SIMULATION =================
    static void startSimulation() {

        System.out.print("Số phòng: ");
        int roomCount = sc.nextInt();

        System.out.print("Số vé mỗi phòng: ");
        int tickets = sc.nextInt();

        System.out.print("Số quầy: ");
        int counters = sc.nextInt();

        rooms.clear();

        for (int i = 0; i < roomCount; i++) {

            char name = (char) ('A' + i);

            rooms.put("" + name, new Room("" + name, tickets));
        }

        running = true;
        paused = false;

        executor = Executors.newFixedThreadPool(counters + 1);

        for (int i = 1; i <= counters; i++) {

            executor.submit(new BookingCounter("Quầy " + i));
        }

        executor.submit(new DeadlockDetector());

        System.out.println("Đã khởi tạo hệ thống với "
                + roomCount + " phòng, "
                + (roomCount * tickets)
                + " vé, "
                + counters + " quầy");
    }

    // ================= ADD TICKETS =================
    static void addTickets() {

        System.out.print("Nhập phòng: ");
        String name = sc.next();

        Room room = rooms.get(name);

        if (room == null) {
            System.out.println("Phòng không tồn tại");
            return;
        }

        System.out.print("Thêm bao nhiêu vé: ");
        int n = sc.nextInt();

        room.addTickets(n);

        System.out.println("Đã thêm vé vào phòng " + name);
    }

    // ================= DEADLOCK CHECK =================
    static void checkDeadlock() {

        System.out.println("Đang quét deadlock...");

        ThreadMXBean bean = ManagementFactory.getThreadMXBean();

        long[] ids = bean.findDeadlockedThreads();

        if (ids == null) {
            System.out.println("Không phát hiện deadlock.");
        } else {
            System.out.println("Phát hiện deadlock!");
        }
    }

    // ================= STOP SYSTEM =================
    static void stopSystem() {

        running = false;

        if (executor != null) {
            executor.shutdownNow();
        }

        System.out.println("Đang dừng hệ thống...");
    }

    // ================= MENU =================
    public static void main(String[] args) {

        while (true) {

            System.out.println("\n===== MENU =====");
            System.out.println("1. Bắt đầu mô phỏng");
            System.out.println("2. Tạm dừng mô phỏng");
            System.out.println("3. Tiếp tục mô phỏng");
            System.out.println("4. Thêm vé vào phòng");
            System.out.println("5. Xem thống kê");
            System.out.println("6. Phát hiện deadlock");
            System.out.println("7. Thoát");

            System.out.print("Chọn: ");
            int choice = sc.nextInt();

            switch (choice) {

                case 1:
                    startSimulation();
                    break;

                case 2:
                    paused = true;
                    System.out.println("Đã tạm dừng tất cả quầy bán vé.");
                    break;

                case 3:
                    paused = false;
                    System.out.println("Đã tiếp tục hoạt động.");
                    break;

                case 4:
                    addTickets();
                    break;

                case 5:
                    showStatistics();
                    break;

                case 6:
                    checkDeadlock();
                    break;

                case 7:
                    stopSystem();
                    System.out.println("Kết thúc chương trình.");
                    return;

                default:
                    System.out.println("Lựa chọn không hợp lệ");
            }
        }
    }
}