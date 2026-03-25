package Sesion13;

import java.sql.*;
import java.util.*;

public class Bai5 {

    // ===== DB HELPER =====
    public static Connection getConnection() throws Exception {
        return DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/hospital",
                "root",
                "123456");
    }

    // ===== HIỂN THỊ GIƯỜNG TRỐNG =====
    public static void showEmptyBeds() {
        try (Connection conn = getConnection()) {
            String sql = "SELECT * FROM Bed WHERE status = 'EMPTY'";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            System.out.println("Danh sách giường trống:");
            while (rs.next()) {
                System.out.println("Bed ID: " + rs.getInt("id"));
            }

        } catch (Exception e) {
            System.out.println("Lỗi: " + e.getMessage());
        }
    }

    // ===== CORE TRANSACTION =====
    public static void tiepNhan(String name, int age, int bedId, double amount) {
        Connection conn = null;

        try {
            conn = getConnection();
            conn.setAutoCommit(false);

            String checkBed = "SELECT status FROM Bed WHERE id = ?";
            PreparedStatement psCheck = conn.prepareStatement(checkBed);
            psCheck.setInt(1, bedId);
            ResultSet rs = psCheck.executeQuery();

            if (!rs.next() || !"EMPTY".equals(rs.getString("status"))) {
                throw new Exception("Giường không hợp lệ hoặc đã có người");
            }

            String insertPatient = "INSERT INTO Patient(name, age) VALUES (?, ?)";
            PreparedStatement ps1 = conn.prepareStatement(insertPatient, Statement.RETURN_GENERATED_KEYS);
            ps1.setString(1, name);
            ps1.setInt(2, age);
            ps1.executeUpdate();

            ResultSet key = ps1.getGeneratedKeys();
            key.next();
            int patientId = key.getInt(1);

            String updateBed = "UPDATE Bed SET status = 'OCCUPIED' WHERE id = ?";
            PreparedStatement ps2 = conn.prepareStatement(updateBed);
            ps2.setInt(1, bedId);
            if (ps2.executeUpdate() == 0) {
                throw new Exception("Update giường thất bại");
            }

            String insertFinance = "INSERT INTO Finance(patient_id, amount) VALUES (?, ?)";
            PreparedStatement ps3 = conn.prepareStatement(insertFinance);
            ps3.setInt(1, patientId);
            ps3.setDouble(2, amount);
            if (ps3.executeUpdate() == 0) {
                throw new Exception("Lưu tài chính thất bại");
            }

            conn.commit();
            System.out.println("Tiếp nhận thành công!");

        } catch (Exception e) {
            System.out.println("Lỗi: " + e.getMessage());

            try {
                if (conn != null)
                    conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

        } finally {
            try {
                if (conn != null) {
                    conn.setAutoCommit(true);
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // ===== MAIN MENU =====
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n===== RIKKEI HOSPITAL =====");
            System.out.println("1. Xem giường trống");
            System.out.println("2. Tiếp nhận bệnh nhân");
            System.out.println("3. Thoát");
            System.out.print("Chọn: ");

            int choice;
            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (Exception e) {
                System.out.println("Nhập số hợp lệ!");
                continue;
            }

            switch (choice) {
                case 1:
                    showEmptyBeds();
                    break;

                case 2:
                    try {
                        System.out.print("Tên: ");
                        String name = sc.nextLine();

                        System.out.print("Tuổi: ");
                        int age = Integer.parseInt(sc.nextLine());

                        System.out.print("Mã giường: ");
                        int bedId = Integer.parseInt(sc.nextLine());

                        System.out.print("Tiền tạm ứng: ");
                        double amount = Double.parseDouble(sc.nextLine());

                        tiepNhan(name, age, bedId, amount);

                    } catch (Exception e) {
                        System.out.println("Dữ liệu không hợp lệ!");
                    }
                    break;

                case 3:
                    System.out.println("Thoát...");
                    return;

                default:
                    System.out.println("Chọn sai!");
            }
        }
    }
}