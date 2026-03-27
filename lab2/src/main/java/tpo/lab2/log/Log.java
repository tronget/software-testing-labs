package tpo.lab2.log;

public class Log {
    private Ln ln;

    public Log(Ln ln) {
        this.ln = ln;
    }

    public double calculate(double x, double base) {
        if (x <= 0) {
            throw new IllegalArgumentException("log(x) undefined for x <= 0");
        }
        if (base <= 0 || Math.abs(base - 1) < 1e-10) {
            throw new IllegalArgumentException("Invalid logarithm base: " + base);
        }

        return ln.calculate(x) / ln.calculate(base);
    }
}
