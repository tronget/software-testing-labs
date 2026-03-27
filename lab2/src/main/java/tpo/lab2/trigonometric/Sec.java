package tpo.lab2.trigonometric;

public class Sec {
    private Cos cos;

    public Sec(Cos cos) {
        this.cos = cos;
    }

    public double calculate(double x) {
        double cosValue = cos.calculate(x);
        if (Math.abs(cosValue) < 1e-10) {
            throw new ArithmeticException("sec(" + x + ") is undefined (cos = 0)");
        }
        return 1 / cosValue;
    }
}
