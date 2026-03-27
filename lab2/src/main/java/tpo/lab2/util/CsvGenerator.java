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

import java.io.BufferedWriter;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.function.DoubleUnaryOperator;

public class CsvGenerator {

    private static final List<BigDecimal> DEFAULT_XS = List.of(
            new BigDecimal("-0.1"),
            new BigDecimal("-0.2"),
            new BigDecimal("-0.5"),
            new BigDecimal("-1.0"),
            new BigDecimal("-2.0"),
            new BigDecimal("0.1"),
            new BigDecimal("0.2"),
            new BigDecimal("0.5"),
            new BigDecimal("2.0"),
            new BigDecimal("3.0"),
            new BigDecimal("10.0")
    );

    public static void main(String[] args) throws Exception {
        generateDefaultTestResources();
    }

    public static void generateDefaultTestResources() throws Exception {
        generateForPoints(DEFAULT_XS, new BigDecimal("1E-12"), Path.of("src/test/resources"));
    }

    public static void generateForPoints(
            List<BigDecimal> xs,
            BigDecimal eps,
            Path outputDir
    ) throws Exception {

        Files.createDirectories(outputDir);

        double precision = eps.doubleValue();
        Sin sin = new Sin(precision);
        Cos cos = new Cos(sin);
        Tan tan = new Tan(sin, cos);
        Cot cot = new Cot(sin, cos);
        Sec sec = new Sec(cos);
        Csc csc = new Csc(sin);

        Ln ln = new Ln(precision);
        Log log = new Log(ln);

        SystemFunction system = new SystemFunction(
                sin, cos, tan, cot, sec, csc, ln, log
        );

        writeCsv(xs, sin::calculate, outputDir.resolve("sin.csv"));
        writeCsv(xs, cos::calculate, outputDir.resolve("cos.csv"));
        writeCsv(xs, tan::calculate, outputDir.resolve("tan.csv"));
        writeCsv(xs, cot::calculate, outputDir.resolve("cot.csv"));
        writeCsv(xs, sec::calculate, outputDir.resolve("sec.csv"));
        writeCsv(xs, csc::calculate, outputDir.resolve("csc.csv"));

        writeCsv(xs, ln::calculate, outputDir.resolve("ln.csv"));
        writeCsv(xs, x -> log.calculate(x, 2), outputDir.resolve("log2.csv"));
        writeCsv(xs, x -> log.calculate(x, 3), outputDir.resolve("log3.csv"));
        writeCsv(xs, x -> log.calculate(x, 10), outputDir.resolve("log5.csv"));

        writeCsv(xs, system::calculate, outputDir.resolve("system_expected.csv"));
    }

    private static void writeCsv(
            List<BigDecimal> xs,
            DoubleUnaryOperator fn,
            Path path
    ) throws Exception {

        try (BufferedWriter w = Files.newBufferedWriter(path)) {

            w.write("x,value");
            w.newLine();

            for (BigDecimal x : xs) {

                try {
                    double y = fn.applyAsDouble(x.doubleValue());

                    w.write(x.toPlainString());
                    w.write(",");
                    w.write(BigDecimal.valueOf(y).stripTrailingZeros().toPlainString());
                    w.newLine();

                } catch (Exception ignored) {
                }
            }
        }
    }
}
