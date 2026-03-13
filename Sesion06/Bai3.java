package Sesion06;

import java.util.*;

public class Bai3 {

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

        public synchronized String sellTicket(String counterName) {

            if (tickets.isEmpty()) {
                return null;
            }

            String ticket = tickets.poll();
            System.out.println(counterName + " đã bán vé " + ticket);

            return ticket;
        }

        public synchronized void addTickets(int count) {

            for (int i = 0; i < count; i++) {
                tickets.add(roomName + "-" + String.format("%03d", ticketCounter++));
            }

            System.out.println("Nhà cung cấp: Đã thêm " + count + " vé vào phòng " + roomName);
        }

        public synchronized int remainingTickets() {
            return tickets.size();
        }
    }

    // ================= BOOKING COUNTER =================
    static class BookingCounter implements Runnable {

        private String name;
        private TicketPool pool;
        private int soldCount = 0;

        public BookingCounter(String name, TicketPool pool) {
            this.name = name;
            this.pool = pool;
        }

        @Override
        public void run() {

            for (int i = 0; i < 20; i++) {

                String ticket = pool.sellTicket(name);

                if (ticket != null) {
                    soldCount++;
                }

                try {
                    Thread.sleep(500);
                } catch (Exception e) {
                }
            }

            System.out.println(name + " bán được: " + soldCount + " vé");
        }
    }

    // ================= TICKET SUPPLIER =================
    static class TicketSupplier implements Runnable {

        private TicketPool roomA;
        private TicketPool roomB;
        private int supplyCount;
        private int interval;
        private int rounds;

        public TicketSupplier(TicketPool roomA, TicketPool roomB, int supplyCount, int interval, int rounds) {
            this.roomA = roomA;
            this.roomB = roomB;
            this.supplyCount = supplyCount;
            this.interval = interval;
            this.rounds = rounds;
        }

        @Override
        public void run() {

            for (int i = 0; i < rounds; i++) {

                try {
                    Thread.sleep(interval);
                } catch (Exception e) {
                }

                roomA.addTickets(supplyCount);
                roomB.addTickets(supplyCount);
            }
        }
    }

    // ================= MAIN =================
    public static void main(String[] args) throws Exception {

        TicketPool roomA = new TicketPool("A", 5);
        TicketPool roomB = new TicketPool("B", 5);

        BookingCounter counter1 = new BookingCounter("Quầy 1", roomA);
        BookingCounter counter2 = new BookingCounter("Quầy 2", roomB);

        TicketSupplier supplier = new TicketSupplier(roomA, roomB, 3, 3000, 3);

        Thread t1 = new Thread(counter1);
        Thread t2 = new Thread(counter2);
        Thread t3 = new Thread(supplier);

        t1.start();
        t2.start();
        t3.start();

        t1.join();
        t2.join();

        System.out.println("Vé còn lại phòng A: " + roomA.remainingTickets());
        System.out.println("Vé còn lại phòng B: " + roomB.remainingTickets());
    }
}