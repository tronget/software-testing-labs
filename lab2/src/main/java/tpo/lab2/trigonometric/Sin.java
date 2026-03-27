package tpo.lab2.trigonometric;


public class Sin {
    private final double precision;


    public Sin(double precision) {
        this.precision = precision;
    }


    public double calculate(double x) {
        x = normalize(x);

        double result = 0;
        double term = x;
        int n = 1;

        while (Math.abs(term) > precision) {
            result += term;
            n += 2;
            term *= -x * x / (n * (n - 1));
        }

        return result;
    }


    private double normalize(double x) {
        double twoPi = 2 * Math.PI;
        while (x > Math.PI) {
            x -= twoPi;
        }
        while (x < -Math.PI) {
            x += twoPi;
        }
        return x;
    }
}
