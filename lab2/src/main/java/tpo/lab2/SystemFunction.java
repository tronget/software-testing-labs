package tpo.lab2;

import tpo.lab2.log.Ln;
import tpo.lab2.log.Log;
import tpo.lab2.trigonometric.*;

public class SystemFunction {
    private Sin sin;
    private Cos cos;
    private Tan tan;
    private Cot cot;
    private Sec sec;
    private Csc csc;
    private Log log;
    private Ln ln;

    public SystemFunction(Sin sin, Cos cos, Tan tan, Cot cot, Sec sec, Csc csc, Ln ln, Log log) {
        this.sin = sin;
        this.cos = cos;
        this.tan = tan;
        this.cot = cot;
        this.sec = sec;
        this.csc = csc;
        this.log = log;
        this.ln = ln;
    }


    public double calculate(double x) {
        if (x <= 0) {
            return calculateForNegative(x);
        } else {
            return calculateForPositive(x);
        }
    }

    private double calculateForNegative(double x) {
        double sinX = sin.calculate(x);
        double cosX = cos.calculate(x);
        double tanX = tan.calculate(x);
        double cotX = cot.calculate(x);
        double secX = sec.calculate(x);
        double cscX = csc.calculate(x);

        double numerator = cotX / sinX;
        numerator += secX;
        numerator /= cotX;
        numerator /= tanX;
        numerator /= tanX;
        numerator -= cotX;
        numerator -= tanX;
        numerator *= sinX;

        double denominator = tanX * tanX;
        double inner = tanX - (tanX + cosX);
        inner -= cscX;
        denominator -= secX * inner;

        if (Math.abs(denominator) < 1e-10) {
            throw new ArithmeticException("System function denominator is zero for x = " + x);
        }

        return numerator / denominator;
    }

    private double calculateForPositive(double x) {
        double log2X = log.calculate(x, 2);
        double log3X = log.calculate(x, 3);
        double log5X = log.calculate(x, 5);
        double lnX = ln.calculate(x);

        double part1 = log2X + log3X;
        part1 += log5X;
        part1 /= log3X;
        part1 += (lnX - log2X);

        double part2 = log2X * (log3X * log3X * log3X);
        part2 *= log3X;

        return part1 * part2;
    }
}
