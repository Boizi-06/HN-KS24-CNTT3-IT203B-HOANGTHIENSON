package Sesion06;

import java.util.*;

public class Bai2 {

    // ================= TICKET POOL =================
    static class TicketPool {

        private String roomName;
        private Queue<String> tickets = new LinkedList<>();
        private int ticketCounter = 1;

        public TicketPool(String roomName, int initialTickets) {
            this.roomName = roomName;

            for (int i = 0; i < initialTickets; i++) {
                tickets.add(roomName + "-" + String.format("%03d", ticketCounter++));
            }
        }

        // bán vé
        public synchronized String sellTicket(String counterName) {

            while (tickets.isEmpty()) {
                try {
                    System.out.println(counterName + ": Hết vé phòng " + roomName + ", đang chờ...");
                    wait(); // chờ vé mới
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            String ticket = tickets.poll();
            System.out.println(counterName + " bán vé " + ticket);
            return ticket;
        }

        // thêm vé
        public synchronized void addTickets(int count) {

            for (int i = 0; i < count; i++) {
                tickets.add(roomName + "-" + String.format("%03d", ticketCounter++));
            }

            System.out.println("Nhà cung cấp: Đã thêm " + count + " vé vào phòng " + roomName);

            notifyAll(); // đánh thức tất cả quầy
        }
    }

    // ================= BOOKING COUNTER =================
    static class BookingCounter implements Runnable {

        private String name;
        private TicketPool pool;

        public BookingCounter(String name, TicketPool pool) {
            this.name = name;
            this.pool = pool;
        }

        @Override
        public void run() {

            for (int i = 0; i < 5; i++) {
                pool.sellTicket(name);

                try {
                    Thread.sleep(500);
                } catch (Exception e) {
                }
            }
        }
    }

    // ================= SUPPLIER =================
    static class TicketSupplier implements Runnable {

        private TicketPool pool;

        public TicketSupplier(TicketPool pool) {
            this.pool = pool;
        }

        @Override
        public void run() {

            try {
                Thread.sleep(3000);
            } catch (Exception e) {
            }

            pool.addTickets(3);
        }
    }

    // ================= MAIN =================
    public static void main(String[] args) {

        TicketPool roomA = new TicketPool("A", 2);

        Thread counter1 = new Thread(new BookingCounter("Quầy 1", roomA));
        Thread counter2 = new Thread(new BookingCounter("Quầy 2", roomA));

        Thread supplier = new Thread(new TicketSupplier(roomA));

        counter1.start();
        counter2.start();
        supplier.start();
    }
}