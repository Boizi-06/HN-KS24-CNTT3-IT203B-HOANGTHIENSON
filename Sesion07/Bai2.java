package Sesion07;

// ===== Discount Strategy =====
interface DiscountStrategy {
    double applyDiscount(double totalAmount);
}

// ===== Percentage Discount =====
class PercentageDiscount implements DiscountStrategy {
    private double percent;

    public PercentageDiscount(double percent) {
        this.percent = percent;
    }

    @Override
    public double applyDiscount(double totalAmount) {
        return totalAmount * (1 - percent / 100);
    }
}

// ===== Fixed Discount =====
class FixedDiscount implements DiscountStrategy {
    private double amount;

    public FixedDiscount(double amount) {
        this.amount = amount;
    }

    @Override
    public double applyDiscount(double totalAmount) {
        return totalAmount - amount;
    }
}

// ===== No Discount =====
class NoDiscount implements DiscountStrategy {
    @Override
    public double applyDiscount(double totalAmount) {
        return totalAmount;
    }
}

// ===== Holiday Discount (NEW - không sửa code cũ) =====
class HolidayDiscount implements DiscountStrategy {
    @Override
    public double applyDiscount(double totalAmount) {
        return totalAmount * 0.85; // giảm 15%
    }
}

// ===== Order Calculator =====
class OrderCalculator {
    private DiscountStrategy discountStrategy;

    public OrderCalculator(DiscountStrategy discountStrategy) {
        this.discountStrategy = discountStrategy;
    }

    public double calculate(double totalAmount) {
        return discountStrategy.applyDiscount(totalAmount);
    }
}

// ===== MAIN =====
public class Bai2 {
    public static void main(String[] args) {

        double total = 1_000_000;

        // 1. Percentage 10%
        OrderCalculator calc1 = new OrderCalculator(new PercentageDiscount(10));
        System.out.println("Số tiền sau giảm: " + calc1.calculate(total));

        // 2. Fixed 50k
        OrderCalculator calc2 = new OrderCalculator(new FixedDiscount(50000));
        System.out.println("Số tiền sau giảm: " + calc2.calculate(total));

        // 3. No Discount
        OrderCalculator calc3 = new OrderCalculator(new NoDiscount());
        System.out.println("Số tiền sau giảm: " + calc3.calculate(total));

        // 4. Holiday 15% (thêm mới, KHÔNG sửa code cũ)
        OrderCalculator calc4 = new OrderCalculator(new HolidayDiscount());
        System.out.println("Số tiền sau giảm: " + calc4.calculate(total));
    }
}