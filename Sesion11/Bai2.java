package Sesion11;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Bai2 {

    public void printMedicineList() {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = Bai1.getConnection();

            String sql = "SELECT name, quantity FROM Medicine";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            // ===== Dùng while để duyệt toàn bộ =====
            while (rs.next()) {
                String name = rs.getString("name");
                int quantity = rs.getInt("quantity");

                System.out.println("Tên thuốc: " + name + " | Số lượng: " + quantity);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null)
                    rs.close();
                if (ps != null)
                    ps.close();
                Bai1.closeConnection(conn);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}