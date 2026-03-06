package Sesion01;

import java.util.Scanner;

public class Bai1 {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        try {
            System.out.print("Nhập năm sinh của bạn: ");
            String input = sc.nextLine();

            int year = Integer.parseInt(input); // parse String -> int
            int age = 2026 - year;

            System.out.println("Tuổi của bạn là: " + age);
        } catch (NumberFormatException e) {
            System.out.println("Lỗi: Vui lòng nhập năm sinh là số hợp lệ!");
        } finally {
            sc.close();
            System.out.println("Thực hiện dọn dẹp tài nguyên trong finally...");
        }
    }
}
