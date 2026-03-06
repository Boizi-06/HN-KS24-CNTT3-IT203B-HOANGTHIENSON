package Sesion01;

import java.io.IOException;

public class Bai4 {

    // Method C
    public static void saveToFile() throws IOException {
        // giả lập lỗi ghi file
        throw new IOException("Lỗi khi ghi file!");
    }

    // Method B
    public static void processUserData() throws IOException {
        saveToFile(); // gọi method C và tiếp tục đẩy lỗi lên
    }

    // Method A
    public static void main(String[] args) {

        try {
            processUserData(); // gọi method B
        } catch (IOException e) {
            System.out.println("Đã xảy ra lỗi: " + e.getMessage());
        }

        System.out.println("Chương trình vẫn tiếp tục chạy...");
    }
}