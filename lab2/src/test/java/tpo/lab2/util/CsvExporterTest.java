package tpo.lab2.util;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.function.DoubleUnaryOperator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CsvExporterTest {

    @TempDir
    Path tempDir;

    @Test
    void export_throws_when_step_is_non_positive() {
        Path out = tempDir.resolve("bad_step.csv");

        assertThrows(
                IllegalArgumentException.class,
                () -> CsvExporter.export(
                        x -> x,
                        BigDecimal.ZERO,
                        BigDecimal.ONE,
                        BigDecimal.ZERO,
                        out,
                        CsvExporter.OnErrorMode.SKIP_ROW
                )
        );
    }

    @Test
    void export_throws_when_from_is_greater_than_to() {
        Path out = tempDir.resolve("bad_bounds.csv");

        assertThrows(
                IllegalArgumentException.class,
                () -> CsvExporter.export(
                        x -> x,
                        BigDecimal.ONE,
                        BigDecimal.ZERO,
                        BigDecimal.ONE,
                        out,
                        CsvExporter.OnErrorMode.SKIP_ROW
                )
        );
    }

    @Test
    void export_skips_failed_points_in_skip_row_mode() throws Exception {
        Path out = tempDir.resolve("skip.csv");
        DoubleUnaryOperator function = x -> {
            if (Math.abs(x) < 1e-12) {
                throw new ArithmeticException("point is undefined");
            }
            return x * x;
        };

        CsvExporter.export(
                function,
                new BigDecimal("-1"),
                new BigDecimal("1"),
                BigDecimal.ONE,
                out,
                CsvExporter.OnErrorMode.SKIP_ROW
        );

        List<String> lines = Files.readAllLines(out);
        assertEquals(List.of(
                "x,value",
                "-1,1",
                "1,1"
        ), lines);
    }

    @Test
    void export_writes_error_marker_in_write_error_mode() throws Exception {
        Path out = tempDir.resolve("write_error.csv");
        DoubleUnaryOperator function = x -> {
            if (Math.abs(x) < 1e-12) {
                throw new ArithmeticException("point is undefined");
            }
            return x * x;
        };

        CsvExporter.export(
                function,
                new BigDecimal("-1"),
                new BigDecimal("1"),
                BigDecimal.ONE,
                out,
                CsvExporter.OnErrorMode.WRITE_ERROR
        );

        List<String> lines = Files.readAllLines(out);
        assertEquals(List.of(
                "x,value",
                "-1,1",
                "0,ERROR",
                "1,1"
        ), lines);
    }
}
