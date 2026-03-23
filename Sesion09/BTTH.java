package Sesion09;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;

// ================= VEHICLE =================
abstract class Vehicle implements Runnable {
    protected String id;
    protected int speed;
    protected Intersection intersection;

    public Vehicle(String id, int speed, Intersection intersection) {
        this.id = id;
        this.speed = speed;
        this.intersection = intersection;
    }

    public abstract boolean isPriority();

    @Override
    public void run() {
        try {
            System.out.println(id + " đang tiến tới ngã tư...");
            Thread.sleep(speed * 500);

            intersection.enter(this);

            System.out.println(id + " đã đi qua ngã tư");

        } catch (Exception e) {
            System.out.println(id + " lỗi: " + e.getMessage());
        }
    }

    public String getId() {
        return id;
    }
}

// ================= STANDARD VEHICLE =================
class StandardVehicle extends Vehicle {
    public StandardVehicle(String id, int speed, Intersection intersection) {
        super(id, speed, intersection);
    }

    @Override
    public boolean isPriority() {
        return false;
    }
}

// ================= PRIORITY VEHICLE =================
class PriorityVehicle extends Vehicle {
    public PriorityVehicle(String id, int speed, Intersection intersection) {
        super(id, speed, intersection);
    }

    @Override
    public boolean isPriority() {
        return true;
    }
}

// ================= STATE PATTERN =================
interface TrafficLightState {
    void handle();

    String getColor();
}

class GreenState implements TrafficLightState {
    public void handle() {
        System.out.println("Đèn XANH - Xe được đi");
    }

    public String getColor() {
        return "GREEN";
    }
}

class YellowState implements TrafficLightState {
    public void handle() {
        System.out.println("Đèn VÀNG - Chuẩn bị dừng");
    }

    public String getColor() {
        return "YELLOW";
    }
}

class RedState implements TrafficLightState {
    public void handle() {
        System.out.println("Đèn ĐỎ - Dừng lại");
    }

    public String getColor() {
        return "RED";
    }
}

// ================= TRAFFIC LIGHT =================
class TrafficLight implements Runnable {
    private TrafficLightState state;

    public TrafficLight() {
        state = new RedState();
    }

    public synchronized String getColor() {
        return state.getColor();
    }

    @Override
    public void run() {
        try {
            while (true) {
                state = new GreenState();
                state.handle();
                Thread.sleep(3000);

                state = new YellowState();
                state.handle();
                Thread.sleep(2000);

                state = new RedState();
                state.handle();
                Thread.sleep(3000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

// ================= INTERSECTION =================
class Intersection {
    private ReentrantLock lock = new ReentrantLock();
    private TrafficLight trafficLight;

    public Intersection(TrafficLight trafficLight) {
        this.trafficLight = trafficLight;
    }

    public void enter(Vehicle vehicle) throws InterruptedException {
        while (true) {

            // Xe ưu tiên đi luôn
            if (vehicle.isPriority()) {
                System.out.println(vehicle.getId() + " 🚑 vượt đèn!");
                pass(vehicle);
                return;
            }

            // Đèn xanh mới được đi
            if (trafficLight.getColor().equals("GREEN")) {
                pass(vehicle);
                return;
            }

            System.out.println(vehicle.getId() + " đang chờ đèn...");
            Thread.sleep(1000);
        }
    }

    private void pass(Vehicle vehicle) {
        lock.lock();
        try {
            System.out.println(vehicle.getId() + " đang đi qua giao lộ...");
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}

// ================= FACTORY =================
class VehicleFactory {
    private static int counter = 1;

    public static Vehicle createVehicle(Intersection intersection) {
        Random rand = new Random();

        if (rand.nextInt(5) == 0) {
            return new PriorityVehicle("🚑 Ambulance-" + counter++, 1, intersection);
        }

        return new StandardVehicle("🚗 Car-" + counter++, rand.nextInt(3) + 1, intersection);
    }
}

// ================= MAIN =================
public class BTTH {
    public static void main(String[] args) {

        TrafficLight light = new TrafficLight();
        Intersection intersection = new Intersection(light);

        // chạy đèn giao thông
        Thread lightThread = new Thread(light);
        lightThread.setDaemon(true);
        lightThread.start();

        ExecutorService executor = Executors.newFixedThreadPool(5);

        // sinh xe
        for (int i = 0; i < 20; i++) {
            Vehicle v = VehicleFactory.createVehicle(intersection);
            executor.submit(v);

            try {
                Thread.sleep(1000);
            } catch (Exception e) {
            }
        }

        executor.shutdown();
    }
}