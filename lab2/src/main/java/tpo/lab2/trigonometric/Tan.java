package tpo.lab2.trigonometric;

public class Tan {
    private final Sin sin;
    private final Cos cos;

    public Tan(Sin sin, Cos cos) {
        this.sin = sin;
        this.cos = cos;
    }

    public double calculate(double x) {
        double cosValue = cos.calculate(x);
        if (Math.abs(cosValue) < 1e-10) {
            throw new ArithmeticException("tan(" + x + ") is undefined (cos = 0)");
        }
        return sin.calculate(x) / cosValue;
    }
}
