package Sesion12;

import java.sql.*;

public class Bai3 {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/hospital";
        String user = "root";
        String password = "123456";

        int surgeryId = 1;

        try {
            Connection conn = DriverManager.getConnection(url, user, password);

            // Gọi stored procedure
            String sql = "{CALL GET_SURGERY_FEE(?, ?)}";
            CallableStatement cs = conn.prepareCall(sql);

            // Set tham số IN
            cs.setInt(1, surgeryId);

            // Đăng ký tham số OUT (DECIMAL)
            cs.registerOutParameter(2, Types.DECIMAL);

            // Thực thi
            cs.execute();

            // Lấy kết quả OUT
            double totalCost = cs.getDouble(2);

            System.out.println("Chi phí phẫu thuật: " + totalCost);

            cs.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}