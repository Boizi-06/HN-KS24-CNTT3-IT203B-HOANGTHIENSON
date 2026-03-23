package Sesion10.Hackatthon.ennity;

import java.util.Scanner;
import java.util.*;

public class HKT {
    public class Book {
        private String bookId;
        private String bookName;
        private String author;
        private int year;
        private String description;
        private Boolean isAvailable;

        public Book() {

        }

        public Book(String bookId, String bookName, String author, int year, String description, Boolean isAvailable) {
            this.bookId = bookId;
            this.bookName = bookName;
            this.author = author;
            this.year = year;
            this.description = description;
            this.isAvailable = isAvailable;
        }

        public String getbookId() {
            return bookId;
        }

        public String getbookName() {
            return bookName;
        }

        public String geAuthor() {
            return author;
        }

        public int getYear() {
            return year;
        }

        public String getdescription() {
            return bookName;
        }

        public String getBookId() {
            return bookId;
        }

        public void setBookId(String bookId) {
            this.bookId = bookId;
        }

        public String getBookName() {
            return bookName;
        }

        public void setBookName(String bookName) {
            this.bookName = bookName;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public void setYear(int year) {
            this.year = year;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public Boolean getIsAvailable() {
            return isAvailable;
        }

        public void setIsAvailable(Boolean isAvailable) {
            this.isAvailable = isAvailable;
        }

        public void inputData(Scanner sc) {

            while (true) {
                System.out.println("moi ban nhap ma cua sach vd : 'B001'");
                String bookId = sc.nextLine();

                break;
            }

            System.out.println(" moi ban nhap ten cua sach");
            String bookName = sc.next();
            sc.nextLine();
            System.out.println("Moi ban nhap tac gia cua sach");
            String author = sc.next();
            sc.nextLine();
            System.out.println("Moi ban nhap dinh dang nam xuat ban");
        }

    }

    public class Business {
        List<Book> listBook;

    }

    public static void main(String[] args) {
        System.out.println("===== Quan Ly Kho Hang =====");
        System.out.println("1.Hien thi toan bo sach");
        System.out.println("2.Them moi sach ");
        System.out.println("3.Cap nhat thong tin sach theo ma");
        System.out.println("4.Xoa sach theo ma");
        System.out.println("5.Tim kiem sach theo ten tac gia");
        System.out.println("6.Thong ke tinh trang sach");
        System.out.println("7.Xap xep sach theo nam xuat ban giam dan");
        System.out.println("8.Thoat chuong trinh");
        System.out.println("Moi nhap lua chon cua ban");
        Scanner sc = new Scanner(System.in);
        int choice = Integer.parseInt(sc.nextLine());
        switch (choice) {
            case 1:

                break;
            case 2:

                break;
            case 3:

                break;
            case 4:

                break;
            case 5:

                break;

            case 6:

                break;
            case 7:

                break;
            case 8:

                break;
            default:
                break;
        }

    }
}
