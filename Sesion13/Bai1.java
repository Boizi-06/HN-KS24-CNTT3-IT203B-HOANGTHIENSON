package Sesion13;

import java.sql.*;

public class Bai1 {
    public static void main(String[] args) {
        Connection conn = null;

        try {
            conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/hospital",
                    "root",
                    "123456");

            conn.setAutoCommit(false);

            String updateStock = "UPDATE Medicine_Inventory SET quantity = quantity - 1 WHERE medicine_id = ?";
            PreparedStatement ps1 = conn.prepareStatement(updateStock);
            ps1.setInt(1, 1);
            ps1.executeUpdate();

            String insertHistory = "INSERT INTO Prescription_History(patient_id, medicine_id) VALUES (?, ?)";
            PreparedStatement ps2 = conn.prepareStatement(insertHistory);
            ps2.setInt(1, 101);
            ps2.setInt(2, 1);
            ps2.executeUpdate();

            conn.commit();
            System.out.println("Giao dịch thành công!");

        } catch (Exception e) {
            try {
                if (conn != null) {
                    conn.rollback();
                    System.out.println("Rollback do lỗi!");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();

        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}