package Sesion11;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class Bai3 {

    public void updateBedStatus(String bedId) {
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = Bai1.getConnection();

            String sql = "UPDATE Bed SET status = 'Đang sử dụng' WHERE bed_id = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, bedId);

            int rowsAffected = ps.executeUpdate();

            // ===== Kiểm tra kết quả =====
            if (rowsAffected > 0) {
                System.out.println("Cập nhật thành công! Giường đã được sử dụng.");
            } else {
                System.out.println("Lỗi: Mã giường không tồn tại!");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (ps != null)
                    ps.close();
                Bai1.closeConnection(conn);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}