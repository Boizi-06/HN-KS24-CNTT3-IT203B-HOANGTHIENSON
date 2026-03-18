package Sesion08;

import java.util.Scanner;

// ===== INTERFACE MỚI =====
interface CamBienNhietDo {
    double layNhietDoC();
}

// ===== CẢM BIẾN CŨ =====
class NhietKeCu {
    public int layNhietDoF() {
        return 78; // giả lập 78°F
    }
}

// ===== ADAPTER =====
class AdapterNhietKe implements CamBienNhietDo {
    private NhietKeCu nhietKeCu;

    public AdapterNhietKe(NhietKeCu nhietKeCu) {
        this.nhietKeCu = nhietKeCu;
    }

    public double layNhietDoC() {
        int f = nhietKeCu.layNhietDoF();
        return (f - 32) * 5.0 / 9;
    }
}

// ===== CÁC THIẾT BỊ =====
class Den {
    public void tat() {
        System.out.println("FACADE: Tắt đèn");
    }
}

class Quat {
    public void tat() {
        System.out.println("FACADE: Tắt quạt");
    }

    public void tocDoThap() {
        System.out.println("FACADE: Quạt chạy tốc độ thấp");
    }
}

class DieuHoa {
    public void tat() {
        System.out.println("FACADE: Tắt điều hòa");
    }

    public void set28() {
        System.out.println("FACADE: Điều hòa set 28°C");
    }
}

// ===== FACADE =====
class SmartHomeFacade {
    private Den den = new Den();
    private Quat quat = new Quat();
    private DieuHoa dieuHoa = new DieuHoa();
    private CamBienNhietDo camBien;

    public SmartHomeFacade(CamBienNhietDo camBien) {
        this.camBien = camBien;
    }

    // rời nhà
    public void leaveHome() {
        den.tat();
        quat.tat();
        dieuHoa.tat();
    }

    // chế độ ngủ
    public void sleepMode() {
        den.tat();
        dieuHoa.set28();
        quat.tocDoThap();
    }

    // lấy nhiệt độ
    public void getCurrentTemperature() {
        double temp = camBien.layNhietDoC();
        System.out.printf("Nhiệt độ hiện tại: %.1f°C\n", temp);
    }
}

// ===== MAIN =====
public class Bai2 {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        // dùng adapter cho cảm biến cũ
        CamBienNhietDo camBien = new AdapterNhietKe(new NhietKeCu());
        SmartHomeFacade facade = new SmartHomeFacade(camBien);

        while (true) {
            System.out.println("\n===== MENU =====");
            System.out.println("1. Xem nhiệt độ");
            System.out.println("2. Rời nhà");
            System.out.println("3. Chế độ ngủ");
            System.out.println("0. Thoát");

            int chon = sc.nextInt();

            switch (chon) {
                case 1:
                    facade.getCurrentTemperature();
                    break;

                case 2:
                    facade.leaveHome();
                    break;

                case 3:
                    facade.sleepMode();
                    break;

                case 0:
                    return;
            }
        }
    }
}