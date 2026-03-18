package Sesion07;

import java.util.Scanner;

// ===== GIẢM GIÁ =====
interface GiamGia {
    double apDung(double tongTien);
}

class GiamGiaWebsite implements GiamGia {
    public double apDung(double tongTien) {
        System.out.println("Áp dụng giảm giá 10% cho website");
        return tongTien * 0.9;
    }
}

class GiamGiaMobile implements GiamGia {
    public double apDung(double tongTien) {
        System.out.println("Giảm 15% cho khách hàng mới (mobile)");
        return tongTien * 0.85;
    }
}

class GiamGiaPOS implements GiamGia {
    public double apDung(double tongTien) {
        System.out.println("Giảm 5% tại cửa hàng");
        return tongTien * 0.95;
    }
}

// ===== THANH TOÁN =====
interface ThanhToan {
    void thanhToan(double soTien);
}

class ThanhToanThe implements ThanhToan {
    public void thanhToan(double soTien) {
        System.out.println("Thanh toán thẻ tín dụng qua online");
    }
}

class ThanhToanMomo implements ThanhToan {
    public void thanhToan(double soTien) {
        System.out.println("Thanh toán MoMo tích hợp");
    }
}

class ThanhToanTienMat implements ThanhToan {
    public void thanhToan(double soTien) {
        System.out.println("Thanh toán tiền mặt tại POS");
    }
}

// ===== THÔNG BÁO =====
interface DichVuThongBao {
    void thongBao(String msg);
}

class ThongBaoEmail implements DichVuThongBao {
    public void thongBao(String msg) {
        System.out.println("Gửi email: " + msg);
    }
}

class ThongBaoDay implements DichVuThongBao {
    public void thongBao(String msg) {
        System.out.println("Gửi push notification: " + msg);
    }
}

class InHoaDon implements DichVuThongBao {
    public void thongBao(String msg) {
        System.out.println("In hóa đơn giấy tại cửa hàng");
    }
}

// ===== ABSTRACT FACTORY =====
interface NhaMayKenhBan {
    GiamGia taoGiamGia();

    ThanhToan taoThanhToan();

    DichVuThongBao taoThongBao();
}

// ===== WEBSITE =====
class KenhWebsite implements NhaMayKenhBan {
    public GiamGia taoGiamGia() {
        return new GiamGiaWebsite();
    }

    public ThanhToan taoThanhToan() {
        return new ThanhToanThe();
    }

    public DichVuThongBao taoThongBao() {
        return new ThongBaoEmail();
    }
}

// ===== MOBILE =====
class KenhMobile implements NhaMayKenhBan {
    public GiamGia taoGiamGia() {
        return new GiamGiaMobile();
    }

    public ThanhToan taoThanhToan() {
        return new ThanhToanMomo();
    }

    public DichVuThongBao taoThongBao() {
        return new ThongBaoDay();
    }
}

// ===== POS =====
class KenhPOS implements NhaMayKenhBan {
    public GiamGia taoGiamGia() {
        return new GiamGiaPOS();
    }

    public ThanhToan taoThanhToan() {
        return new ThanhToanTienMat();
    }

    public DichVuThongBao taoThongBao() {
        return new InHoaDon();
    }
}

// ===== SERVICE =====
class DichVuDonHang {
    private NhaMayKenhBan nhaMay;

    public DichVuDonHang(NhaMayKenhBan nhaMay) {
        this.nhaMay = nhaMay;
    }

    public void xuLyDon(double tongTien) {
        GiamGia giamGia = nhaMay.taoGiamGia();
        ThanhToan thanhToan = nhaMay.taoThanhToan();
        DichVuThongBao thongBao = nhaMay.taoThongBao();

        double tienSauGiam = giamGia.apDung(tongTien);
        System.out.println("Tổng sau giảm: " + tienSauGiam);

        thanhToan.thanhToan(tienSauGiam);

        thongBao.thongBao("Đơn hàng thành công");
    }
}

// ===== MAIN =====
public class Bai6 {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\nChọn kênh: 1.Website  2.Mobile  3.POS  0.Thoát");
            int chon = sc.nextInt();

            NhaMayKenhBan nhaMay = null;

            if (chon == 1) {
                nhaMay = new KenhWebsite();
                System.out.println("Bạn đã chọn Website");
            } else if (chon == 2) {
                nhaMay = new KenhMobile();
                System.out.println("Bạn đã chọn Mobile");
            } else if (chon == 3) {
                nhaMay = new KenhPOS();
                System.out.println("Bạn đã chọn POS");
            } else {
                break;
            }

            DichVuDonHang dv = new DichVuDonHang(nhaMay);
            dv.xuLyDon(1000000);
        }
    }
}