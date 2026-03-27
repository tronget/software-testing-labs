package tpo.lab2.trigonometrical;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import tpo.lab2.trigonometric.Sin;
import tpo.lab2.util.CsvGenerator;

import static tpo.lab2.util.BdAsserts.assertClose;

public class SinTest {

    private final double epsilon = 1e-6;
    private final double tol = 1e-3;
    private Sin sin = new Sin(epsilon);

    @BeforeAll
    static void refreshStubTablesFromRealFunctions() throws Exception {
        CsvGenerator.generateDefaultTestResources();
    }

    @ParameterizedTest
    @CsvSource({
            "-2.0, -0.90929742682568169542",
            "-1.0, -0.84147098480789650666",
            "-0.5, -0.47942553860420300026",
            "-0.2, -0.19866933079506121546",
            "0.0, 0",
            "0.2, 0.19866933079506121546",
            "0.5, 0.47942553860420300026",
            "2.0, 0.90929742682568169542"
    })
    void sin_check(String px, String py) {
        double fact = sin.calculate(Double.parseDouble(px));
        double expected = Double.parseDouble(py);
        assertClose(expected, fact, tol);
    }

    @ParameterizedTest
    @CsvSource({
            "0, 0",
            "0.5235987755982989, 0.5",
            "0.7853981633974483, 0.70710678118654752440",
            "1.0471975511965977, 0.86602540378443864676",
            "3.141592653589793, 0"
    })
    void sin_check_radian_points(String px, String py) {
        double fact = sin.calculate(Double.parseDouble(px));
        double expected = Double.parseDouble(py);
        assertClose(expected, fact, tol);
    }

    @Test
    void sin_check_radian_points_periodic() {
        double x = 0.37;
        double fact = sin.calculate(x);
        double shifted = sin.calculate(x+Math.PI*2);
        assertClose(fact, shifted, tol);
    }

    @Test
    void sin_is_odd() {
        double x = 0.37;
        double positive = sin.calculate(x);
        double negative = sin.calculate(-x);
        assertClose(-positive, negative, tol);
    }
}
