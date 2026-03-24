package Sesion12;

import java.sql.*;
import java.util.Scanner;

public class Bai5 {

    static final String URL = "jdbc:mysql://localhost:3306/hospital";
    static final String USER = "root";
    static final String PASS = "123456";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        try (Connection conn = DriverManager.getConnection(URL, USER, PASS)) {

            while (true) {
                System.out.println("\n===== RHMS MENU =====");
                System.out.println("1. Danh sách bệnh nhân");
                System.out.println("2. Tiếp nhận bệnh nhân");
                System.out.println("3. Cập nhật bệnh án");
                System.out.println("4. Xuất viện & tính phí");
                System.out.println("5. Thoát");
                System.out.print("Chọn: ");

                int choice = sc.nextInt();
                sc.nextLine();

                switch (choice) {
                    case 1:
                        showPatients(conn);
                        break;
                    case 2:
                        addPatient(conn, sc);
                        break;
                    case 3:
                        updatePatient(conn, sc);
                        break;
                    case 4:
                        discharge(conn, sc);
                        break;
                    case 5:
                        System.out.println("Thoát...");
                        return;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void showPatients(Connection conn) throws Exception {
        String sql = "SELECT id, name, age, department FROM patient";
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            System.out.println(
                    rs.getInt("id") + " | " +
                            rs.getString("name") + " | " +
                            rs.getInt("age") + " | " +
                            rs.getString("department"));
        }

        rs.close();
        ps.close();
    }

    static void addPatient(Connection conn, Scanner sc) throws Exception {
        System.out.print("Tên: ");
        String name = sc.nextLine();

        System.out.print("Tuổi: ");
        int age = sc.nextInt();
        sc.nextLine();

        System.out.print("Khoa: ");
        String dept = sc.nextLine();

        String sql = "INSERT INTO patient(name, age, department) VALUES (?, ?, ?)";
        PreparedStatement ps = conn.prepareStatement(sql);

        ps.setString(1, name);
        ps.setInt(2, age);
        ps.setString(3, dept);

        ps.executeUpdate();
        System.out.println("Thêm thành công!");

        ps.close();
    }

    static void updatePatient(Connection conn, Scanner sc) throws Exception {
        System.out.print("Nhập ID bệnh nhân: ");
        int id = sc.nextInt();
        sc.nextLine();

        System.out.print("Nhập bệnh lý mới: ");
        String disease = sc.nextLine();

        String sql = "UPDATE patient SET disease = ? WHERE id = ?";
        PreparedStatement ps = conn.prepareStatement(sql);

        ps.setString(1, disease);
        ps.setInt(2, id);

        int rows = ps.executeUpdate();

        if (rows > 0) {
            System.out.println("Cập nhật thành công!");
        } else {
            System.out.println("Không tìm thấy!");
        }

        ps.close();
    }

    static void discharge(Connection conn, Scanner sc) throws Exception {
        System.out.print("Nhập ID bệnh nhân: ");
        int id = sc.nextInt();

        String sql = "{CALL CALCULATE_DISCHARGE_FEE(?, ?)}";
        CallableStatement cs = conn.prepareCall(sql);

        cs.setInt(1, id);

        // OUT parameter
        cs.registerOutParameter(2, Types.DECIMAL);

        cs.execute();

        double fee = cs.getDouble(2);

        System.out.println("Tổng viện phi:" + fee);

        cs.close();
    }
}