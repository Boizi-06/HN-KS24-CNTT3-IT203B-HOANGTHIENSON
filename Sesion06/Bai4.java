package Sesion06;

import java.util.*;

public class Bai4 {

    // ================= TICKET =================
    static class Ticket {

        String ticketId;
        String roomName;
        boolean isSold;

        public Ticket(String ticketId, String roomName) {
            this.ticketId = ticketId;
            this.roomName = roomName;
            this.isSold = false;
        }
    }

    // ================= TICKET POOL =================
    static class TicketPool {

        String roomName;
        List<Ticket> tickets = new ArrayList<>();

        public TicketPool(String roomName, int count) {

            this.roomName = roomName;

            for (int i = 1; i <= count; i++) {

                String id = roomName + "-" + String.format("%03d", i);
                tickets.add(new Ticket(id, roomName));
            }
        }

        // bán vé (đồng bộ)
        public synchronized Ticket sellTicket() {

            for (Ticket t : tickets) {

                if (!t.isSold) {
                    t.isSold = true;
                    return t;
                }
            }

            return null;
        }

        public int remainingTickets() {

            int count = 0;

            for (Ticket t : tickets) {
                if (!t.isSold)
                    count++;
            }

            return count;
        }
    }

    // ================= BOOKING COUNTER =================
    static class BookingCounter implements Runnable {

        String counterName;
        TicketPool roomA;
        TicketPool roomB;
        int soldCount = 0;

        Random random = new Random();

        public BookingCounter(String counterName, TicketPool roomA, TicketPool roomB) {

            this.counterName = counterName;
            this.roomA = roomA;
            this.roomB = roomB;
        }

        @Override
        public void run() {

            while (true) {

                // nếu cả 2 phòng hết vé thì dừng
                if (roomA.remainingTickets() == 0 && roomB.remainingTickets() == 0) {
                    break;
                }

                Ticket ticket = null;

                // chọn ngẫu nhiên phòng
                if (random.nextBoolean()) {
                    ticket = roomA.sellTicket();
                } else {
                    ticket = roomB.sellTicket();
                }

                // nếu phòng được chọn hết vé → thử phòng còn lại
                if (ticket == null) {

                    if (roomA.remainingTickets() > 0) {
                        ticket = roomA.sellTicket();
                    } else if (roomB.remainingTickets() > 0) {
                        ticket = roomB.sellTicket();
                    }
                }

                if (ticket != null) {

                    soldCount++;

                    System.out.println(counterName +
                            " đã bán vé " + ticket.ticketId);
                }

                try {
                    Thread.sleep(200);
                } catch (Exception e) {
                }
            }

            System.out.println(counterName + " bán được: " + soldCount + " vé");
        }
    }

    // ================= MAIN =================
    public static void main(String[] args) throws Exception {

        TicketPool roomA = new TicketPool("A", 10);
        TicketPool roomB = new TicketPool("B", 10);

        BookingCounter counter1 = new BookingCounter("Quầy 1", roomA, roomB);

        BookingCounter counter2 = new BookingCounter("Quầy 2", roomA, roomB);

        Thread t1 = new Thread(counter1);
        Thread t2 = new Thread(counter2);

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println("\n===== KẾT QUẢ =====");

        System.out.println("Vé còn lại phòng A: " + roomA.remainingTickets());
        System.out.println("Vé còn lại phòng B: " + roomB.remainingTickets());
    }
}