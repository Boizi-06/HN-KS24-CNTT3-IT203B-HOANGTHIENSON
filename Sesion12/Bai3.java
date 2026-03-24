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

            String sql = "{CALL GET_SURGERY_FEE(?, ?)}";
            CallableStatement cs = conn.prepareCall(sql);

            cs.setInt(1, surgeryId);

            cs.registerOutParameter(2, Types.DECIMAL);

            cs.execute();

            double totalCost = cs.getDouble(2);

            System.out.println("Chi phí phẫu thuật: " + totalCost);

            cs.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}