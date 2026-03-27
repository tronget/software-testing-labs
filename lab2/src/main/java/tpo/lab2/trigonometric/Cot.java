package tpo.lab2.trigonometric;

public class Cot {
    private final Sin sin;
    private final Cos cos;

    public Cot(Sin sin, Cos cos) {
        this.sin = sin;
        this.cos = cos;
    }

    public double calculate(double x) {
        double sinValue = sin.calculate(x);
        if (Math.abs(sinValue) < 1e-10) {
            throw new ArithmeticException("cot(" + x + ") is undefined (sin = 0)");
        }
        
        return cos.calculate(x) / sinValue;
    }
}
