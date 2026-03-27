package tpo.lab2.trigonometric;

public class Csc {
    private final Sin sin;

    public Csc(Sin sin) {
        this.sin = sin;
    }

    public double calculate(double x) {
        double sinValue = sin.calculate(x);
        if (Math.abs(sinValue) < 1e-10) {
            throw new ArithmeticException("csc(" + x + ") is undefined (sin = 0)");
        }
        
        return 1 / sinValue;
    }
}
