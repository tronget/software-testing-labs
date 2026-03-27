package tpo.lab2.util;


import java.io.BufferedWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.DoubleUnaryOperator;

public class CsvExporter {

    public enum OnErrorMode {
        SKIP_ROW,
        WRITE_ERROR
    }

    public static void export(
            DoubleUnaryOperator fn,
            BigDecimal fromX,
            BigDecimal toX,
            BigDecimal step,
            Path out,
            OnErrorMode onErrorMode
    ) throws IOException {

        if (step.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("step must be > 0");
        }
        if (fromX.compareTo(toX) > 0) {
            throw new IllegalArgumentException("fromX must be <= toX");
        }

        try (BufferedWriter w = Files.newBufferedWriter(out)) {
            w.write("x,value");
            w.newLine();

            for (BigDecimal x = fromX; x.compareTo(toX) <= 0; x = x.add(step)) {
                try {
                    double y = fn.applyAsDouble(x.doubleValue());
                    w.write(x.toPlainString());
                    w.write(",");
                    w.write(BigDecimal.valueOf(y).stripTrailingZeros().toPlainString());
                    w.newLine();
                } catch (Exception ex) {
                    if (onErrorMode == OnErrorMode.WRITE_ERROR) {
                        w.write(x.toPlainString());
                        w.write(",ERROR");
                        w.newLine();
                    }
                }
            }
        }
    }
}