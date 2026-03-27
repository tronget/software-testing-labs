package tpo.lab2.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.function.DoubleUnaryOperator;

public class CsvTableStub implements DoubleUnaryOperator {

    private final Map<String, Double> table = new HashMap<>();

    public CsvTableStub(Path path) throws IOException {

        try (BufferedReader reader = Files.newBufferedReader(path)) {
            String line;
            while ((line = reader.readLine()) != null) {

                if (line.startsWith("x") || line.isBlank()) {
                    continue;
                }

                String[] parts = line.split(",");
                if (parts.length < 2) {
                    continue;
                }

                String valueText = parts[1].trim();
                if ("ERROR".equalsIgnoreCase(valueText)) {
                    continue;
                }

                double x = Double.parseDouble(parts[0].trim());
                double value = Double.parseDouble(valueText);

                table.put(key(x), value);
            }
        }
    }

    @Override
    public double applyAsDouble(double operand) {
        return calculate(operand);
    }

    public double calculate(double x) {

        Double value = table.get(key(x));

        if (value == null) {
            throw new IllegalArgumentException("Value not found for x=" + x);
        }

        return value;
    }

    private String key(double x) {
        return BigDecimal.valueOf(x).stripTrailingZeros().toPlainString();
    }
}
