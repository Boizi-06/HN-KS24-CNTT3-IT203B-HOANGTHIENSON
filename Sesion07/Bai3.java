package Sesion07;

// ===== Base Interface =====
interface PaymentMethod {
    void pay(double amount);
}

// ===== ISP: Interface nhỏ =====
interface CODPayable {
    void processCOD(double amount);
}

interface CardPayable {
    void processCard(double amount);
}

interface EWalletPayable {
    void processEWallet(double amount);
}

// ===== COD Payment =====
class CODPayment implements PaymentMethod, CODPayable {

    @Override
    public void processCOD(double amount) {
        System.out.println("Xử lý thanh toán COD: " + amount + " - Thành công");
    }

    @Override
    public void pay(double amount) {
        processCOD(amount);
    }
}

// ===== Credit Card Payment =====
class CreditCardPayment implements PaymentMethod, CardPayable {

    @Override
    public void processCard(double amount) {
        System.out.println("Xử lý thanh toán thẻ tín dụng: " + amount + " - Thành công");
    }

    @Override
    public void pay(double amount) {
        processCard(amount);
    }
}

// ===== Momo Payment =====
class MomoPayment implements PaymentMethod, EWalletPayable {

    @Override
    public void processEWallet(double amount) {
        System.out.println("Xử lý thanh toán MoMo: " + amount + " - Thành công");
    }

    @Override
    public void pay(double amount) {
        processEWallet(amount);
    }
}

// ===== Processor =====
class PaymentProcessor {
    public void process(PaymentMethod method, double amount) {
        method.pay(amount);
    }
}

// ===== MAIN =====
public class Bai3 {
    public static void main(String[] args) {

        PaymentProcessor processor = new PaymentProcessor();

        // 1. COD
        PaymentMethod cod = new CODPayment();
        processor.process(cod, 500000);

        // 2. Credit Card
        PaymentMethod card = new CreditCardPayment();
        processor.process(card, 1000000);

        // 3. Momo
        PaymentMethod momo = new MomoPayment();
        processor.process(momo, 750000);

        // ===== LSP Test =====
        System.out.println("\nTest LSP:");

        PaymentMethod method;

        method = new CreditCardPayment();
        processor.process(method, 1000000);

        // Thay bằng Momo (không sửa code)
        method = new MomoPayment();
        processor.process(method, 1000000);
    }
}