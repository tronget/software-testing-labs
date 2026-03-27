package tpo.lab2.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;
import java.util.function.DoubleUnaryOperator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ModuleFactoryTest {

    private static final BigDecimal EPS = new BigDecimal("1E-6");

    @ParameterizedTest
    @CsvSource({
            "sin, 0.5",
            "cos, 0.5",
            "tan, 0.5",
            "cot, 0.5",
            "sec, 0.5",
            "csc, 0.5",
            "ln, 2.0",
            "log2, 2.0",
            "log3, 3.0",
            "log10, 10.0",
            "system, 2.0"
    })
    void build_returns_working_function(String moduleName, double x) {
        DoubleUnaryOperator function = ModuleFactory.build(moduleName, EPS);
        assertNotNull(function);
        assertTrue(Double.isFinite(function.applyAsDouble(x)));
    }

    @Test
    void build_is_case_insensitive() {
        double x = 0.5;
        assertEquals(
                ModuleFactory.build("sin", EPS).applyAsDouble(x),
                ModuleFactory.build("SiN", EPS).applyAsDouble(x)
        );
        assertEquals(
                ModuleFactory.build("system", EPS).applyAsDouble(2.0),
                ModuleFactory.build("SyStEm", EPS).applyAsDouble(2.0)
        );
    }

    @Test
    void build_throws_for_unknown_module() {
        assertThrows(IllegalArgumentException.class, () -> ModuleFactory.build("unknown", EPS));
    }
}
