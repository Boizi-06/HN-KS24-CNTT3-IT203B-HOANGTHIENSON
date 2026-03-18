package Sesion08;

import java.util.*;

// ===== COMMAND =====
interface Lenh {
    void execute();

    void undo();
}

// ===== THIẾT BỊ =====
class Den {
    public void bat() {
        System.out.println("Đèn: Bật");
    }

    public void tat() {
        System.out.println("Đèn: Tắt");
    }
}

class Quat {
    public void bat() {
        System.out.println("Quạt: Bật");
    }

    public void tat() {
        System.out.println("Quạt: Tắt");
    }
}

class DieuHoa {
    private int nhietDo = 25;

    public void setNhietDo(int temp) {
        System.out.println("Điều hòa: Nhiệt độ = " + temp);
        this.nhietDo = temp;
    }

    public int getNhietDo() {
        return nhietDo;
    }
}

// ===== COMMAND CỤ THỂ =====
class DenBatLenh implements Lenh {
    private Den den;

    public DenBatLenh(Den den) {
        this.den = den;
    }

    public void execute() {
        den.bat();
    }

    public void undo() {
        den.tat();
    }
}

class DenTatLenh implements Lenh {
    private Den den;

    public DenTatLenh(Den den) {
        this.den = den;
    }

    public void execute() {
        den.tat();
    }

    public void undo() {
        den.bat();
    }
}

class QuatBatLenh implements Lenh {
    private Quat quat;

    public QuatBatLenh(Quat quat) {
        this.quat = quat;
    }

    public void execute() {
        quat.bat();
    }

    public void undo() {
        quat.tat();
    }
}

class QuatTatLenh implements Lenh {
    private Quat quat;

    public QuatTatLenh(Quat quat) {
        this.quat = quat;
    }

    public void execute() {
        quat.tat();
    }

    public void undo() {
        quat.bat();
    }
}

class DieuHoaSetNhietDoLenh implements Lenh {
    private DieuHoa dieuHoa;
    private int nhietDoMoi;
    private int nhietDoCu;

    public DieuHoaSetNhietDoLenh(DieuHoa dieuHoa, int nhietDoMoi) {
        this.dieuHoa = dieuHoa;
        this.nhietDoMoi = nhietDoMoi;
    }

    public void execute() {
        nhietDoCu = dieuHoa.getNhietDo();
        dieuHoa.setNhietDo(nhietDoMoi);
    }

    public void undo() {
        dieuHoa.setNhietDo(nhietDoCu);
        System.out.println("Undo: Điều hòa quay lại " + nhietDoCu);
    }
}

// ===== REMOTE CONTROL =====
class RemoteControl {
    private Map<Integer, Lenh> nut = new HashMap<>();
    private Stack<Lenh> lichSu = new Stack<>();

    public void ganLenh(int soNut, Lenh lenh) {
        nut.put(soNut, lenh);
        System.out.println("Đã gán lệnh cho nút " + soNut);
    }

    public void bamNut(int soNut) {
        Lenh lenh = nut.get(soNut);
        if (lenh != null) {
            lenh.execute();
            lichSu.push(lenh);
        } else {
            System.out.println("Chưa gán lệnh!");
        }
    }

    public void undo() {
        if (!lichSu.isEmpty()) {
            Lenh lenh = lichSu.pop();
            System.out.print("Undo: ");
            lenh.undo();
        } else {
            System.out.println("Không có lệnh để undo!");
        }
    }
}

// ===== MAIN =====
public class Bai3 {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        Den den = new Den();
        Quat quat = new Quat();
        DieuHoa dieuHoa = new DieuHoa();

        RemoteControl remote = new RemoteControl();

        while (true) {
            System.out.println("\n===== MENU =====");
            System.out.println("1. Gán lệnh");
            System.out.println("2. Bấm nút");
            System.out.println("3. Undo");
            System.out.println("0. Thoát");

            int chon = sc.nextInt();

            switch (chon) {

                case 1:
                    System.out.println("Chọn nút:");
                    int nut = sc.nextInt();

                    System.out.println("Chọn lệnh:");
                    System.out.println("1. Bật đèn");
                    System.out.println("2. Tắt đèn");
                    System.out.println("3. Bật quạt");
                    System.out.println("4. Tắt quạt");
                    System.out.println("5. Set điều hòa");

                    int loai = sc.nextInt();
                    Lenh lenh = null;

                    if (loai == 1)
                        lenh = new DenBatLenh(den);
                    else if (loai == 2)
                        lenh = new DenTatLenh(den);
                    else if (loai == 3)
                        lenh = new QuatBatLenh(quat);
                    else if (loai == 4)
                        lenh = new QuatTatLenh(quat);
                    else if (loai == 5) {
                        System.out.print("Nhập nhiệt độ: ");
                        int temp = sc.nextInt();
                        lenh = new DieuHoaSetNhietDoLenh(dieuHoa, temp);
                    }

                    if (lenh != null) {
                        remote.ganLenh(nut, lenh);
                    }
                    break;

                case 2:
                    System.out.print("Nhập nút: ");
                    int b = sc.nextInt();
                    remote.bamNut(b);
                    break;

                case 3:
                    remote.undo();
                    break;

                case 0:
                    return;
            }
        }
    }
}