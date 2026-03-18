package Sesion08;

import java.util.*;

// ===== DISCOUNT =====
interface GiamGia {
    double apDung(double tongTien);
}

class GiamGiaWebsite implements GiamGia {
    public double apDung(double tongTien) {
        double giam = tongTien * 0.1;
        System.out.println("Áp dụng giảm giá 10%: " + giam);
        return tongTien - giam;
    }
}

class GiamGiaMobile implements GiamGia {
    public double apDung(double tongTien) {
        double giam = tongTien * 0.15;
        System.out.println("Áp dụng giảm giá 15% (lần đầu): " + giam);
        return tongTien - giam;
    }
}

class GiamGiaPOS implements GiamGia {
    public double apDung(double tongTien) {
        double giam = tongTien * 0.05;
        System.out.println("Áp dụng giảm giá 5%: " + giam);
        return tongTien - giam;
    }
}

// ===== PAYMENT =====
interface ThanhToan {
    void thanhToan(double soTien);
}

class ThanhToanThe implements ThanhToan {
    public void thanhToan(double soTien) {
        System.out.println("Xử lý thanh toán thẻ tín dụng: " + soTien);
    }
}

class ThanhToanMomo implements ThanhToan {
    public void thanhToan(double soTien) {
        System.out.println("Xử lý thanh toán MoMo: " + soTien);
    }
}

class ThanhToanCOD implements ThanhToan {
    public void thanhToan(double soTien) {
        System.out.println("Thanh toán khi nhận hàng: " + soTien);
    }
}

// ===== NOTIFICATION =====
interface ThongBao {
    void gui(String msg);
}

class ThongBaoEmail implements ThongBao {
    public void gui(String msg) {
        System.out.println("Gửi email: " + msg);
    }
}

class ThongBaoPush implements ThongBao {
    public void gui(String msg) {
        System.out.println("Gửi push notification: " + msg);
    }
}

class InHoaDon implements ThongBao {
    public void gui(String msg) {
        System.out.println("In hóa đơn: " + msg);
    }
}

// ===== ABSTRACT FACTORY =====
interface NhaMayKenhBan {
    GiamGia taoGiamGia();
    ThanhToan taoThanhToan();
    ThongBao taoThongBao();
}

// ===== FACTORY CỤ THỂ =====
class WebsiteFactory implements NhaMayKenhBan {
    public GiamGia taoGiamGia() { return new GiamGiaWebsite(); }
    public ThanhToan taoThanhToan() { return new ThanhToanThe(); }
    public ThongBao taoThongBao() { return new ThongBaoEmail(); }
}

class MobileFactory implements NhaMayKenhBan {
    public GiamGia taoGiamGia() { return new GiamGiaMobile(); }
    public ThanhToan taoThanhToan() { return new ThanhToanMomo(); }
    public ThongBao taoThongBao() { return new ThongBaoPush(); }
}

class POSFactory implements NhaMayKenhBan {
    public GiamGia taoGiamGia() { return new GiamGiaPOS(); }
    public ThanhToan taoThanhToan() { return new ThanhToanCOD(); }
    public ThongBao taoThongBao() { return new InHoaDon(); }
}

// ===== ORDER SERVICE =====
class DichVuDonHang {
    private GiamGia giamGia;
    private ThanhToan thanhToan;
    private ThongBao thongBao;

    public DichVuDonHang(NhaMayKenhBan factory) {
        this.giamGia = factory.taoGiamGia();
        this.thanhToan = factory.taoThanhToan();
        this.thongBao = factory.taoThongBao();
    }

    public void xuLyDon(String tenSP, double gia, int soLuong) {
        double tong = gia * soLuong;
        System.out.println("Tổng tiền: " + tong);

        double sauGiam = giamGia.apDung(tong);

        thanhToan.thanhToan(sauGiam);

        thongBao.gui("Đơn hàng thành công");
    }
}

// ===== MAIN =====
public class Bai6 {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n===== CHỌN KÊNH =====");
            System.out.println("1. Website");
            System.out.println("2. Mobile");
            System.out.println("3. POS");
            System.out.println("0. Thoát");

            int chon = sc.nextInt();

            NhaMayKenhBan factory = null;

            if (chon == 1) {
                factory = new WebsiteFactory();
                System.out.println("Bạn đã chọn kênh Website");
            } else if (chon == 2) {
                factory = new MobileFactory();
                System.out.println("Bạn đã chọn kênh Mobile App");
            } else if (chon == 3) {
                factory = new POSFactory();
                System.out.println("Bạn đã chọn kênh POS");
            } else {
                break;
            }

            System.out.print("Nhập tên sản phẩm: ");
            sc.nextLine();
            String ten = sc.nextLine();

            System.out.print("Giá: ");
            double gia = sc.nextDouble();

            System.out.print("Số lượng: ");
            int sl = sc.nextInt();

            DichVuDonHang dv = new DichVuDonHang(factory);
            dv.xuLyDon(ten, gia, sl);
        }
    }
}