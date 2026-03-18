package Sesion08;

import java.util.*;

// ===== COMMAND =====
interface Lenh {
    void execute();
}

// ===== THIẾT BỊ =====
class Den {
    public void tat() {
        System.out.println("Đèn: Tắt");
    }
}

class Quat implements NguoiQuanSat {
    private String tocDo = "TẮT";

    public void tocDoThap() {
        tocDo = "THẤP";
        System.out.println("Quạt: Chạy tốc độ thấp");
    }

    public void tocDoManh() {
        tocDo = "MẠNH";
        System.out.println("Quạt: Chạy tốc độ mạnh");
    }

    public void capNhat(int nhietDo) {
        if (nhietDo > 30) {
            tocDoManh();
        }
    }

    public String getTrangThai() {
        return tocDo;
    }
}

class DieuHoa implements NguoiQuanSat {
    private int nhietDo = 25;

    public void setNhietDo(int t) {
        nhietDo = t;
        System.out.println("Điều hòa: Nhiệt độ = " + t);
    }

    public void capNhat(int nhietDoPhong) {
        // giữ nguyên 28 nhưng có thể mở rộng
        if (nhietDoPhong > 30) {
            System.out.println("Điều hòa: Duy trì 28°C");
        }
    }

    public int getNhietDo() {
        return nhietDo;
    }
}

// ===== OBSERVER =====
interface NguoiQuanSat {
    void capNhat(int nhietDo);
}

interface ChuDe {
    void dangKy(NguoiQuanSat o);

    void thongBao();
}

// ===== SENSOR =====
class CamBienNhietDo implements ChuDe {
    private List<NguoiQuanSat> ds = new ArrayList<>();
    private int nhietDo;

    public void dangKy(NguoiQuanSat o) {
        ds.add(o);
    }

    public void thongBao() {
        for (NguoiQuanSat o : ds) {
            o.capNhat(nhietDo);
        }
    }

    public void setNhietDo(int t) {
        nhietDo = t;
        System.out.println("Cảm biến: Nhiệt độ = " + t);
        thongBao();
    }
}

// ===== COMMAND CỤ THỂ =====
class TatDenLenh implements Lenh {
    private Den den;

    public TatDenLenh(Den den) {
        this.den = den;
    }

    public void execute() {
        System.out.println("SleepMode: Tắt đèn");
        den.tat();
    }
}

class SetNhietDoLenh implements Lenh {
    private DieuHoa dieuHoa;

    public SetNhietDoLenh(DieuHoa dieuHoa) {
        this.dieuHoa = dieuHoa;
    }

    public void execute() {
        System.out.println("SleepMode: Điều hòa set 28°C");
        dieuHoa.setNhietDo(28);
    }
}

class QuatThapLenh implements Lenh {
    private Quat quat;

    public QuatThapLenh(Quat quat) {
        this.quat = quat;
    }

    public void execute() {
        System.out.println("SleepMode: Quạt tốc độ thấp");
        quat.tocDoThap();
    }
}

// ===== MACRO COMMAND =====
class SleepModeLenh implements Lenh {
    private List<Lenh> danhSach;

    public SleepModeLenh(List<Lenh> ds) {
        this.danhSach = ds;
    }

    public void execute() {
        for (Lenh l : danhSach) {
            l.execute();
        }
    }
}

// ===== MAIN =====
public class Bai5 {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        // thiết bị
        Den den = new Den();
        Quat quat = new Quat();
        DieuHoa dieuHoa = new DieuHoa();

        // sensor
        CamBienNhietDo camBien = new CamBienNhietDo();
        camBien.dangKy(quat);
        camBien.dangKy(dieuHoa);

        // command
        List<Lenh> ds = new ArrayList<>();
        ds.add(new TatDenLenh(den));
        ds.add(new SetNhietDoLenh(dieuHoa));
        ds.add(new QuatThapLenh(quat));

        Lenh sleepMode = new SleepModeLenh(ds);

        while (true) {
            System.out.println("\n===== MENU =====");
            System.out.println("1. Kích hoạt chế độ ngủ");
            System.out.println("2. Thay đổi nhiệt độ");
            System.out.println("3. Xem trạng thái");
            System.out.println("0. Thoát");

            int chon = sc.nextInt();

            switch (chon) {
                case 1:
                    sleepMode.execute();
                    break;

                case 2:
                    System.out.print("Nhập nhiệt độ: ");
                    int t = sc.nextInt();
                    camBien.setNhietDo(t);
                    break;

                case 3:
                    System.out.println("Quạt: " + quat.getTrangThai());
                    System.out.println("Điều hòa: " + dieuHoa.getNhietDo());
                    break;

                case 0:
                    return;
            }
        }
    }
}