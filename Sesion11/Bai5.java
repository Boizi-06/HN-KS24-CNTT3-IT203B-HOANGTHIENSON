package Sesion11;

import java.sql.*;
import java.util.*;

// ===== MAIN =====
public class Bai5 {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        DoctorService service = new DoctorService();

        while (true) {
            System.out.println("\n===== RIKKEI CARE =====");
            System.out.println("1. Xem danh sách bác sĩ");
            System.out.println("2. Thêm bác sĩ");
            System.out.println("3. Thống kê chuyên khoa");
            System.out.println("4. Thoát");
            System.out.print("Chọn: ");

            int choice;
            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (Exception e) {
                System.out.println("❌ Nhập số hợp lệ!");
                continue;
            }

            switch (choice) {
                case 1:
                    service.showAll();
                    break;

                case 2:
                    System.out.print("Nhập ID: ");
                    String id = sc.nextLine();

                    System.out.print("Nhập tên: ");
                    String name = sc.nextLine();

                    System.out.print("Nhập chuyên khoa: ");
                    String sp = sc.nextLine();

                    service.addDoctor(new Doctor(id, name, sp));
                    break;

                case 3:
                    service.statistic();
                    break;

                case 4:
                    System.out.println("Thoát...");
                    return;

                default:
                    System.out.println("❌ Sai lựa chọn!");
            }
        }
    }
}

// ===== MODEL =====
class Doctor {
    private String id;
    private String name;
    private String specialty;

    public Doctor() {
    }

    public Doctor(String id, String name, String specialty) {
        this.id = id;
        this.name = name;
        this.specialty = specialty;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }
}

// ===== DB CONTEXT =====
class DBContext {
    private static final String URL = "jdbc:mysql://localhost:3306/Hospital_DB";
    private static final String USER = "root";
    private static final String PASSWORD = "123456";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}

// ===== DAO =====
class DoctorDAO {

    public List<Doctor> getAll() {
        List<Doctor> list = new ArrayList<>();

        try (Connection conn = DBContext.getConnection();
                PreparedStatement ps = conn.prepareStatement("SELECT * FROM Doctors");
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(new Doctor(
                        rs.getString("id"),
                        rs.getString("name"),
                        rs.getString("specialty")));
            }

        } catch (Exception e) {
            System.out.println("❌ Lỗi khi lấy danh sách!");
        }

        return list;
    }

    public boolean insert(Doctor d) {
        String sql = "INSERT INTO Doctors VALUES (?, ?, ?)";

        try (Connection conn = DBContext.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, d.getId());
            ps.setString(2, d.getName());
            ps.setString(3, d.getSpecialty());

            return ps.executeUpdate() > 0;

        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("❌ Mã bác sĩ đã tồn tại!");
        } catch (Exception e) {
            System.out.println("❌ Lỗi khi thêm bác sĩ!");
        }

        return false;
    }

    public void statisticBySpecialty() {
        String sql = "SELECT specialty, COUNT(*) AS total FROM Doctors GROUP BY specialty";

        try (Connection conn = DBContext.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            System.out.println("===== THỐNG KÊ =====");
            while (rs.next()) {
                System.out.println(
                        rs.getString("specialty") + " | " + rs.getInt("total"));
            }

        } catch (Exception e) {
            System.out.println("❌ Lỗi thống kê!");
        }
    }
}

// ===== SERVICE =====
class DoctorService {
    private DoctorDAO dao = new DoctorDAO();

    public void showAll() {
        List<Doctor> list = dao.getAll();

        if (list.isEmpty()) {
            System.out.println("Danh sách trống!");
        } else {
            System.out.println("===== DANH SÁCH =====");
            for (Doctor d : list) {
                System.out.println(
                        d.getId() + " | " + d.getName() + " | " + d.getSpecialty());
            }
        }
    }

    public void addDoctor(Doctor d) {
        if (d.getId().trim().isEmpty() || d.getName().trim().isEmpty()) {
            System.out.println("❌ Không được để trống!");
            return;
        }

        if (d.getSpecialty().length() > 50) {
            System.out.println("❌ Chuyên khoa quá dài!");
            return;
        }

        if (dao.insert(d)) {
            System.out.println("✅ Thêm thành công!");
        }
    }

    public void statistic() {
        dao.statisticBySpecialty();
    }
}