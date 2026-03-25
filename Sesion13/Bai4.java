package Sesion13;

import java.sql.*;
import java.util.*;

class DichVu {
    int id;
    String name;

    public DichVu(int id, String name) {
        this.id = id;
        this.name = name;
    }
}

class BenhNhanDTO {
    int id;
    String name;
    List<DichVu> dsDichVu = new ArrayList<>();

    public BenhNhanDTO(int id, String name) {
        this.id = id;
        this.name = name;
    }
}

public class Bai4 {

    public List<BenhNhanDTO> getAllBenhNhan() {
        List<BenhNhanDTO> result = new ArrayList<>();
        Map<Integer, BenhNhanDTO> map = new LinkedHashMap<>();

        try (Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/hospital", "root", "123456")) {

            String sql = "SELECT bn.id, bn.name, dv.id AS dvid, dv.name AS dvname " +
                    "FROM BenhNhan bn " +
                    "LEFT JOIN DichVu dv ON bn.id = dv.maBenhNhan";

            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int bnId = rs.getInt("id");

                BenhNhanDTO bn = map.get(bnId);

                if (bn == null) {
                    bn = new BenhNhanDTO(bnId, rs.getString("name"));
                    map.put(bnId, bn);
                }

                int dvId = rs.getInt("dvid");

                // ===== BẪY 2: BỆNH NHÂN KHÔNG CÓ DỊCH VỤ =====
                if (!rs.wasNull()) {
                    String dvName = rs.getString("dvname");
                    bn.dsDichVu.add(new DichVu(dvId, dvName));
                }
            }

            result.addAll(map.values());

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
}