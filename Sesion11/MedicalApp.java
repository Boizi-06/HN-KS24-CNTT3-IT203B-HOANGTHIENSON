package Sesion11;

import java.sql.*;
import java.util.*;

// ===== MAIN =====
public class MedicalApp {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        AppointmentRepository repo = new AppointmentRepository();

        while (true) {
            System.out.println("\n===== QUẢN LÝ LỊCH KHÁM =====");
            System.out.println("1. Thêm lịch khám");
            System.out.println("2. Cập nhật lịch khám");
            System.out.println("3. Xóa lịch khám");
            System.out.println("4. Xem tất cả");
            System.out.println("5. Tìm theo ID");
            System.out.println("6. Thoát");
            System.out.print("Chọn: ");

            int choice;
            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (Exception e) {
                System.out.println("❌ Nhập số hợp lệ!");
                continue;
            }

            switch (choice) {
                case 1:
                    System.out.print("Tên bệnh nhân: ");
                    String pname = sc.nextLine();

                    System.out.print("Ngày khám (yyyy-mm-dd): ");
                    String date = sc.nextLine();

                    System.out.print("Tên bác sĩ: ");
                    String dname = sc.nextLine();

                    System.out.print("Trạng thái: ");
                    String status = sc.nextLine();

                    repo.addAppointment(new Appointment(0, pname, date, dname, status));
                    break;

                case 2:
                    System.out.print("Nhập ID cần sửa: ");
                    int uid = Integer.parseInt(sc.nextLine());

                    System.out.print("Tên bệnh nhân: ");
                    pname = sc.nextLine();

                    System.out.print("Ngày khám (yyyy-mm-dd): ");
                    date = sc.nextLine();

                    System.out.print("Tên bác sĩ: ");
                    dname = sc.nextLine();

                    System.out.print("Trạng thái: ");
                    status = sc.nextLine();

                    repo.updateAppointment(new Appointment(uid, pname, date, dname, status));
                    break;

                case 3:
                    System.out.print("Nhập ID cần xóa: ");
                    int did = Integer.parseInt(sc.nextLine());
                    repo.deleteAppointment(did);
                    break;

                case 4:
                    repo.getAllAppointments();
                    break;

                case 5:
                    System.out.print("Nhập ID: ");
                    int fid = Integer.parseInt(sc.nextLine());
                    repo.getAppointmentById(fid);
                    break;

                case 6:
                    System.out.println("Thoát...");
                    return;

                default:
                    System.out.println("❌ Sai lựa chọn!");
            }
        }
    }
}

// ===== MODEL =====
class Appointment {
    private int id;
    private String patientName;
    private String appointmentDate;
    private String doctorName;
    private String status;

    public Appointment() {
    }

    public Appointment(int id, String patientName, String appointmentDate, String doctorName, String status) {
        this.id = id;
        this.patientName = patientName;
        this.appointmentDate = appointmentDate;
        this.doctorName = doctorName;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public String getPatientName() {
        return patientName;
    }

    public String getAppointmentDate() {
        return appointmentDate;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public String getStatus() {
        return status;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public void setAppointmentDate(String appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

// ===== DB CONNECTION =====
class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/MedicalAppointmentDB";
    private static final String USER = "root";
    private static final String PASSWORD = "123456";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}

// ===== REPOSITORY =====
class AppointmentRepository {

    public void addAppointment(Appointment a) {
        String sql = "INSERT INTO appointments (patient_name, appointment_date, doctor_name, status) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, a.getPatientName());
            ps.setDate(2, Date.valueOf(a.getAppointmentDate()));
            ps.setString(3, a.getDoctorName());
            ps.setString(4, a.getStatus());

            ps.executeUpdate();
            System.out.println("✅ Thêm thành công!");

        } catch (Exception e) {
            System.out.println("❌ Lỗi thêm lịch!");
        }
    }

    public void updateAppointment(Appointment a) {
        String sql = "UPDATE appointments SET patient_name=?, appointment_date=?, doctor_name=?, status=? WHERE id=?";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, a.getPatientName());
            ps.setDate(2, Date.valueOf(a.getAppointmentDate()));
            ps.setString(3, a.getDoctorName());
            ps.setString(4, a.getStatus());
            ps.setInt(5, a.getId());

            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("✅ Cập nhật thành công!");
            } else {
                System.out.println("❌ Không tìm thấy ID!");
            }

        } catch (Exception e) {
            System.out.println("❌ Lỗi cập nhật!");
        }
    }

    public void deleteAppointment(int id) {
        String sql = "DELETE FROM appointments WHERE id=?";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("✅ Xóa thành công!");
            } else {
                System.out.println("❌ Không tìm thấy ID!");
            }

        } catch (Exception e) {
            System.out.println("❌ Lỗi xóa!");
        }
    }

    public void getAppointmentById(int id) {
        String sql = "SELECT * FROM appointments WHERE id=?";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                System.out.println(rs.getInt("id") + " | "
                        + rs.getString("patient_name") + " | "
                        + rs.getDate("appointment_date") + " | "
                        + rs.getString("doctor_name") + " | "
                        + rs.getString("status"));
            } else {
                System.out.println("❌ Không tìm thấy!");
            }

        } catch (Exception e) {
            System.out.println("❌ Lỗi tìm!");
        }
    }

    public void getAllAppointments() {
        String sql = "SELECT * FROM appointments";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            System.out.println("===== DANH SÁCH =====");
            while (rs.next()) {
                System.out.println(rs.getInt("id") + " | "
                        + rs.getString("patient_name") + " | "
                        + rs.getDate("appointment_date") + " | "
                        + rs.getString("doctor_name") + " | "
                        + rs.getString("status"));
            }

        } catch (Exception e) {
            System.out.println("❌ Lỗi hiển thị!");
        }
    }
}