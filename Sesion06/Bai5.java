package Sesion06;

import java.util.*;

public class Bai5 {

    // ================= TICKET =================
    static class Ticket {

        String ticketId;
        String roomName;
        boolean isSold = false;

        boolean isHeld = false;
        long holdExpiryTime = 0;
        boolean isVIP;

        public Ticket(String ticketId, String roomName) {
            this.ticketId = ticketId;
            this.roomName = roomName;
        }
    }

    // ================= TICKET POOL =================
    static class TicketPool {

        String roomName;
        List<Ticket> tickets = new ArrayList<>();

        public TicketPool(String roomName, int capacity) {

            this.roomName = roomName;

            for (int i = 1; i <= capacity; i++) {
                tickets.add(new Ticket(roomName + "-" + String.format("%03d", i), roomName));
            }
        }

        // giữ vé
        public synchronized Ticket holdTicket(boolean isVIP, String counterName) {

            for (Ticket t : tickets) {

                if (!t.isSold && !t.isHeld) {

                    t.isHeld = true;
                    t.isVIP = isVIP;
                    t.holdExpiryTime = System.currentTimeMillis() + 5000;

                    System.out.println(counterName +
                            ": Đã giữ vé " + t.ticketId +
                            (isVIP ? " (VIP)" : "") +
                            ". Thanh toán trong 5s");

                    return t;
                }
            }

            return null;
        }

        // thanh toán vé
        public synchronized void sellHeldTicket(Ticket ticket, String counterName) {

            if (ticket != null && ticket.isHeld && !ticket.isSold) {

                ticket.isSold = true;
                ticket.isHeld = false;

                System.out.println(counterName +
                        ": Thanh toán thành công " + ticket.ticketId);
            }
        }

        // trả vé hết hạn
        public synchronized void releaseExpiredTickets() {

            long now = System.currentTimeMillis();

            for (Ticket t : tickets) {

                if (t.isHeld && now > t.holdExpiryTime) {

                    t.isHeld = false;

                    System.out.println(
                            "TimeoutManager: Vé " + t.ticketId +
                                    " hết hạn giữ, đã trả lại kho");
                }
            }
        }
    }

    // ================= BOOKING COUNTER =================
    static class BookingCounter implements Runnable {

        String name;
        List<TicketPool> pools;
        Random random = new Random();

        public BookingCounter(String name, List<TicketPool> pools) {
            this.name = name;
            this.pools = pools;
        }

        @Override
        public void run() {

            for (int i = 0; i < 5; i++) {

                TicketPool pool = pools.get(random.nextInt(pools.size()));

                boolean isVIP = random.nextBoolean();

                Ticket ticket = pool.holdTicket(isVIP, name);

                if (ticket != null) {

                    try {
                        Thread.sleep(3000);
                    } catch (Exception e) {
                    }

                    // 70% khách thanh toán
                    if (random.nextInt(10) < 7) {

                        pool.sellHeldTicket(ticket, name);

                    } else {

                        System.out.println(name +
                                ": Khách không thanh toán " +
                                ticket.ticketId);
                    }
                }

                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                }
            }
        }
    }

    // ================= TIMEOUT MANAGER =================
    static class TimeoutManager implements Runnable {

        List<TicketPool> pools;

        public TimeoutManager(List<TicketPool> pools) {
            this.pools = pools;
        }

        @Override
        public void run() {

            while (true) {

                for (TicketPool pool : pools) {
                    pool.releaseExpiredTickets();
                }

                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                }
            }
        }
    }

    // ================= MAIN =================
    public static void main(String[] args) {

        TicketPool roomA = new TicketPool("A", 5);
        TicketPool roomB = new TicketPool("B", 5);
        TicketPool roomC = new TicketPool("C", 5);

        List<TicketPool> pools = Arrays.asList(roomA, roomB, roomC);

        // 5 quầy bán vé
        for (int i = 1; i <= 5; i++) {

            Thread t = new Thread(
                    new BookingCounter("Quầy " + i, pools));

            t.start();
        }

        // thread kiểm tra timeout
        Thread timeoutThread = new Thread(new TimeoutManager(pools));

        timeoutThread.setDaemon(true);
        timeoutThread.start();
    }
}