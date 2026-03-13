package Sesion06;

import java.util.*;

public class Bai1 {

    // ================= TICKET POOL =================
    static class TicketPool {

        private String roomName;
        private Queue<String> tickets = new LinkedList<>();

        public TicketPool(String roomName, int count) {
            this.roomName = roomName;

            for (int i = 1; i <= count; i++) {
                tickets.add(roomName + "-00" + i);
            }
        }

        public String getTicket() {
            return tickets.poll();
        }

        public void returnTicket(String ticket) {
            if (ticket != null) {
                tickets.add(ticket);
            }
        }

        public boolean hasTicket() {
            return !tickets.isEmpty();
        }

        public String getRoomName() {
            return roomName;
        }
    }

    // ================= BOOKING COUNTER =================
    static class BookingCounter implements Runnable {

        private String name;
        private TicketPool roomA;
        private TicketPool roomB;
        private boolean fixDeadlock;

        public BookingCounter(String name, TicketPool roomA, TicketPool roomB, boolean fixDeadlock) {
            this.name = name;
            this.roomA = roomA;
            this.roomB = roomB;
            this.fixDeadlock = fixDeadlock;
        }

        public void sellCombo() {

            if (!fixDeadlock) {
                // ================= VERSION GÂY DEADLOCK =================
                synchronized (roomA) {

                    System.out.println(name + ": Đã lấy vé phòng " + roomA.getRoomName());

                    try {
                        Thread.sleep(100);
                    } catch (Exception e) {
                    }

                    synchronized (roomB) {

                        System.out.println(name + ": Đã lấy vé phòng " + roomB.getRoomName());

                        if (roomA.hasTicket() && roomB.hasTicket()) {

                            String ticketA = roomA.getTicket();
                            String ticketB = roomB.getTicket();

                            System.out.println(name + " bán combo: " + ticketA + " & " + ticketB);

                        } else {
                            System.out.println(name + " bán combo thất bại");
                        }
                    }
                }

            } else {
                // ================= VERSION FIX DEADLOCK =================

                TicketPool first = roomA;
                TicketPool second = roomB;

                // đảm bảo lock theo cùng thứ tự
                if (roomA.getRoomName().compareTo(roomB.getRoomName()) > 0) {
                    first = roomB;
                    second = roomA;
                }

                synchronized (first) {
                    synchronized (second) {

                        if (roomA.hasTicket() && roomB.hasTicket()) {

                            String ticketA = roomA.getTicket();
                            String ticketB = roomB.getTicket();

                            System.out.println(name + " bán combo thành công: "
                                    + ticketA + " & " + ticketB);

                        } else {

                            System.out.println(name + ": Hết vé, bán combo thất bại");
                        }
                    }
                }
            }
        }

        @Override
        public void run() {
            sellCombo();
        }
    }

    // ================= MAIN =================
    public static void main(String[] args) throws Exception {

        System.out.println("===== DEMO DEADLOCK =====");

        TicketPool roomA = new TicketPool("A", 2);
        TicketPool roomB = new TicketPool("B", 2);

        // quầy khóa A -> B
        BookingCounter counter1 = new BookingCounter("Quầy 1", roomA, roomB, false);

        // quầy khóa B -> A
        BookingCounter counter2 = new BookingCounter("Quầy 2", roomB, roomA, false);

        Thread t1 = new Thread(counter1);
        Thread t2 = new Thread(counter2);

        t1.start();
        t2.start();

        Thread.sleep(2000);

        System.out.println("\n===== FIX DEADLOCK =====");

        TicketPool roomA2 = new TicketPool("A", 2);
        TicketPool roomB2 = new TicketPool("B", 2);

        BookingCounter counter3 = new BookingCounter("Quầy 1", roomA2, roomB2, true);
        BookingCounter counter4 = new BookingCounter("Quầy 2", roomB2, roomA2, true);

        Thread t3 = new Thread(counter3);
        Thread t4 = new Thread(counter4);

        t3.start();
        t4.start();
    }
}