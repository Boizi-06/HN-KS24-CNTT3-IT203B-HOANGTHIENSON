package Sesion11;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class Bai4 {

    public void searchPatient(String keyword) {
        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;

        try {
            conn = Bai1.getConnection();

            // ===== Lọc input =====
            keyword = sanitizeInput(keyword);

            String sql = "SELECT * FROM Patient WHERE name = '" + keyword + "'";
            st = conn.createStatement();
            rs = st.executeQuery(sql);

            while (rs.next()) {
                System.out.println("Tên: " + rs.getString("name"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null)
                    rs.close();
                if (st != null)
                    st.close();
                Bai1.closeConnection(conn);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // ===== Hàm lọc =====
    private String sanitizeInput(String input) {
        if (input == null)
            return "";
        input = input.replace("--", "");
        input = input.replace(";", "");
        input = input.replace("'", "");
        return input;
    }
}