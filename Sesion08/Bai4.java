package Sesion08;

import java.util.*;

// ===== OBSERVER =====
interface NguoiQuanSat {
    void capNhat(int nhietDo);
}

// ===== SUBJECT =====
interface ChuDe {
    void dangKy(NguoiQuanSat o);

    void huyDangKy(NguoiQuanSat o);

    void thongBao();
}

// ===== TEMPERATURE SENSOR =====
class CamBienNhietDo implements ChuDe {
    private List<NguoiQuanSat> danhSach = new ArrayList<>();
    private int nhietDo;

    public void dangKy(NguoiQuanSat o) {
        danhSach.add(o);
    }

    public void huyDangKy(NguoiQuanSat o) {
        danhSach.remove(o);
    }

    public void thongBao() {
        for (NguoiQuanSat o : danhSach) {
            o.capNhat(nhietDo);
        }
    }

    public void setNhietDo(int nhietDo) {
        this.nhietDo = nhietDo;
        System.out.println("Cảm biến: Nhiệt độ = " + nhietDo);
        thongBao();
    }
}

// ===== OBSERVER CỤ THỂ =====
class Quat implements NguoiQuanSat {

    public void capNhat(int nhietDo) {
        if (nhietDo < 20) {
            System.out.println("Quạt: Nhiệt độ thấp, tự động TẮT");
        } else if (nhietDo <= 25) {
            System.out.println("Quạt: Nhiệt độ trung bình, chạy mức VỪA");
        } else {
            System.out.println("Quạt: Nhiệt độ cao, chạy tốc độ MẠNH");
        }
    }
}

class MayTaoAm implements NguoiQuanSat {

    public void capNhat(int nhietDo) {
        System.out.println("Máy tạo ẩm: Điều chỉnh độ ẩm cho nhiệt độ " + nhietDo);
    }
}

// ===== MAIN =====
public class Bai4 {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        CamBienNhietDo camBien = new CamBienNhietDo();
        Quat quat = new Quat();
        MayTaoAm mayTaoAm = new MayTaoAm();

        while (true) {
            System.out.println("\n===== MENU =====");
            System.out.println("1. Đăng ký Quạt");
            System.out.println("2. Đăng ký Máy tạo ẩm");
            System.out.println("3. Set nhiệt độ");
            System.out.println("0. Thoát");

            int chon = sc.nextInt();

            switch (chon) {
                case 1:
                    camBien.dangKy(quat);
                    System.out.println("Quạt: Đã đăng ký nhận thông báo");
                    break;

                case 2:
                    camBien.dangKy(mayTaoAm);
                    System.out.println("Máy tạo ẩm: Đã đăng ký");
                    break;

                case 3:
                    System.out.print("Nhập nhiệt độ: ");
                    int t = sc.nextInt();
                    camBien.setNhietDo(t);
                    break;

                case 0:
                    return;
            }
        }
    }
}