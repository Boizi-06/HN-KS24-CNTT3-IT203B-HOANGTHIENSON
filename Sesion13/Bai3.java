package Sesion13;

import java.sql.*;

public class Bai3 {

    public void xuatVienVaThanhToan(int maBenhNhan, double tienVienPhi) {
        Connection conn = null;

        try {
            conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/hospital",
                    "root",
                    "123456");

            conn.setAutoCommit(false);

            String sqlCheck = "SELECT balance FROM Patient WHERE patient_id = ?";
            PreparedStatement psCheck = conn.prepareStatement(sqlCheck);
            psCheck.setInt(1, maBenhNhan);
            ResultSet rs = psCheck.executeQuery();

            if (!rs.next()) {
                throw new Exception("Bệnh nhân không tồn tại");
            }

            double balance = rs.getDouble("balance");

            // ===== BẪY 1: KIỂM TRA THIẾU TIỀN =====
            if (balance < tienVienPhi) {
                throw new Exception("Không đủ tiền để thanh toán");
            }

            String sqlUpdateMoney = "UPDATE Patient SET balance = balance - ? WHERE patient_id = ?";
            PreparedStatement ps1 = conn.prepareStatement(sqlUpdateMoney);
            ps1.setDouble(1, tienVienPhi);
            ps1.setInt(2, maBenhNhan);
            int row1 = ps1.executeUpdate();

            // ===== BẪY 2: ROW AFFECTED =====
            if (row1 == 0) {
                throw new Exception("Trừ tiền thất bại");
            }

            String sqlUpdateBed = "UPDATE Bed SET status = 'EMPTY' WHERE patient_id = ?";
            PreparedStatement ps2 = conn.prepareStatement(sqlUpdateBed);
            ps2.setInt(1, maBenhNhan);
            int row2 = ps2.executeUpdate();

            if (row2 == 0) {
                throw new Exception("Giải phóng giường thất bại");
            }

            String sqlUpdatePatient = "UPDATE Patient SET status = 'DISCHARGED' WHERE patient_id = ?";
            PreparedStatement ps3 = conn.prepareStatement(sqlUpdatePatient);
            ps3.setInt(1, maBenhNhan);
            int row3 = ps3.executeUpdate();

            if (row3 == 0) {
                throw new Exception("Cập nhật trạng thái bệnh nhân thất bại");
            }

            conn.commit();
            System.out.println("Xuất viện và thanh toán thành công");

        } catch (Exception e) {
            System.out.println("Lỗi: " + e.getMessage());

            try {
                if (conn != null) {
                    conn.rollback();
                    System.out.println("Đã rollback giao dịch");
                }
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
}