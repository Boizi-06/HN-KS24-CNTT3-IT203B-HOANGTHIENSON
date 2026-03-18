package Sesion08;

import java.util.*;

// ===== SINGLETON: KẾT NỐI PHẦN CỨNG =====
class KetNoiPhanCung {
    private static KetNoiPhanCung instance;
    private boolean daKetNoi = false;

    private KetNoiPhanCung() {
    }

    public static KetNoiPhanCung getInstance() {
        if (instance == null) {
            instance = new KetNoiPhanCung();
        }
        return instance;
    }

    public void connect() {
        if (!daKetNoi) {
            System.out.println("HardwareConnection: Đã kết nối phần cứng.");
            daKetNoi = true;
        }
    }

    public void disconnect() {
        if (daKetNoi) {
            System.out.println("Ngắt kết nối phần cứng.");
            daKetNoi = false;
        }
    }
}

// ===== DEVICE =====
interface ThietBi {
    void bat();

    void tat();
}

// ===== CÁC THIẾT BỊ =====
class Den implements ThietBi {
    public void bat() {
        System.out.println("Đèn: Bật sáng.");
    }

    public void tat() {
        System.out.println("Đèn: Tắt.");
    }
}

class Quat implements ThietBi {
    public void bat() {
        System.out.println("Quạt: Quay.");
    }

    public void tat() {
        System.out.println("Quạt: Dừng.");
    }
}

class DieuHoa implements ThietBi {
    public void bat() {
        System.out.println("Điều hòa: Bật.");
    }

    public void tat() {
        System.out.println("Điều hòa: Tắt.");
    }
}

// ===== FACTORY METHOD =====
abstract class NhaMayThietBi {
    abstract ThietBi taoThietBi();
}

// ===== FACTORY CON =====
class DenFactory extends NhaMayThietBi {
    public ThietBi taoThietBi() {
        System.out.println("LightFactory: Đã tạo đèn mới.");
        return new Den();
    }
}

class QuatFactory extends NhaMayThietBi {
    public ThietBi taoThietBi() {
        System.out.println("FanFactory: Đã tạo quạt mới.");
        return new Quat();
    }
}

class DieuHoaFactory extends NhaMayThietBi {
    public ThietBi taoThietBi() {
        System.out.println("AirConditionerFactory: Đã tạo điều hòa mới.");
        return new DieuHoa();
    }
}

// ===== MAIN =====
public class Bai1 {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        List<ThietBi> danhSach = new ArrayList<>();

        while (true) {
            System.out.println("\n===== MENU =====");
            System.out.println("1. Kết nối phần cứng");
            System.out.println("2. Tạo thiết bị");
            System.out.println("3. Bật thiết bị");
            System.out.println("4. Tắt thiết bị");
            System.out.println("0. Thoát");

            int chon = sc.nextInt();

            switch (chon) {

                case 1:
                    KetNoiPhanCung.getInstance().connect();
                    break;

                case 2:
                    System.out.println("Chọn: 1. Đèn  2. Quạt  3. Điều hòa");
                    int loai = sc.nextInt();

                    NhaMayThietBi factory = null;

                    if (loai == 1)
                        factory = new DenFactory();
                    else if (loai == 2)
                        factory = new QuatFactory();
                    else if (loai == 3)
                        factory = new DieuHoaFactory();

                    if (factory != null) {
                        ThietBi tb = factory.taoThietBi();
                        danhSach.add(tb);
                    }
                    break;

                case 3:
                    if (danhSach.isEmpty()) {
                        System.out.println("Chưa có thiết bị!");
                        break;
                    }

                    hienDanhSach(danhSach);
                    System.out.print("Chọn thiết bị: ");
                    int i1 = sc.nextInt() - 1;

                    if (i1 >= 0 && i1 < danhSach.size()) {
                        danhSach.get(i1).bat();
                    }
                    break;

                case 4:
                    if (danhSach.isEmpty()) {
                        System.out.println("Chưa có thiết bị!");
                        break;
                    }

                    hienDanhSach(danhSach);
                    System.out.print("Chọn thiết bị: ");
                    int i2 = sc.nextInt() - 1;

                    if (i2 >= 0 && i2 < danhSach.size()) {
                        danhSach.get(i2).tat();
                    }
                    break;

                case 0:
                    return;
            }
        }
    }

    // hiển thị danh sách
    public static void hienDanhSach(List<ThietBi> ds) {
        for (int i = 0; i < ds.size(); i++) {
            System.out.println((i + 1) + ". " + ds.get(i).getClass().getSimpleName());
        }
    }
}