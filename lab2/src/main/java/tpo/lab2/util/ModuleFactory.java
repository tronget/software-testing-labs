package tpo.lab2.util;

import tpo.lab2.SystemFunction;
import tpo.lab2.log.Ln;
import tpo.lab2.log.Log;
import tpo.lab2.trigonometric.Cos;
import tpo.lab2.trigonometric.Cot;
import tpo.lab2.trigonometric.Csc;
import tpo.lab2.trigonometric.Sec;
import tpo.lab2.trigonometric.Sin;
import tpo.lab2.trigonometric.Tan;

import java.math.BigDecimal;
import java.util.function.DoubleUnaryOperator;

public class ModuleFactory {

    public static DoubleUnaryOperator build(String name, BigDecimal epsForBase) {
        double precision = epsForBase.doubleValue();

        Sin sin = new Sin(precision);
        Cos cos = new Cos(sin);
        Tan tan = new Tan(sin, cos);
        Cot cot = new Cot(sin, cos);
        Sec sec = new Sec(cos);
        Csc csc = new Csc(sin);

        Ln ln = new Ln(precision);
        Log log = new Log(ln);

        SystemFunction system = new SystemFunction(sin, cos, tan, cot, sec, csc, ln, log);

        return switch (name.toLowerCase()) {
            case "sin" -> sin::calculate;
            case "cos" -> cos::calculate;
            case "tan" -> tan::calculate;
            case "cot" -> cot::calculate;
            case "sec" -> sec::calculate;
            case "csc" -> csc::calculate;

            case "ln" -> ln::calculate;
            case "log2" -> x -> log.calculate(x, 2);
            case "log3" -> x -> log.calculate(x, 3);
            case "log10" -> x -> log.calculate(x, 10);

            case "system" -> system::calculate;
            default -> throw new IllegalArgumentException("Unknown module: " + name);
        };
    }
}
