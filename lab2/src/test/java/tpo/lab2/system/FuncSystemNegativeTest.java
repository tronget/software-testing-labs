package tpo.lab2.system;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import tpo.lab2.SystemFunction;
import tpo.lab2.log.Ln;
import tpo.lab2.log.Log;
import tpo.lab2.trigonometric.Cos;
import tpo.lab2.trigonometric.Cot;
import tpo.lab2.trigonometric.Csc;
import tpo.lab2.trigonometric.Sec;
import tpo.lab2.trigonometric.Sin;
import tpo.lab2.trigonometric.Tan;
import tpo.lab2.util.CsvGenerator;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FuncSystemNegativeTest {

    private static final double EPSILON = 1e-6;

    @BeforeAll
    static void refreshStubTablesFromRealFunctions() throws Exception {
        CsvGenerator.generateDefaultTestResources();
    }

    @Test
    void system_returns_nan_when_x_equals_one() {
        SystemFunction system = realSystem();

        double value = assertDoesNotThrow(() -> system.calculate(1.0));
        assertTrue(Double.isNaN(value), "Expected NaN for x=1.0, got " + value);
    }

    @Test
    void system_throws_when_x_equals_zero() {
        SystemFunction system = realSystem();
        assertThrows(ArithmeticException.class, () -> system.calculate(0.0));
    }

    @ParameterizedTest
    @CsvSource({
            "-6.283185307179586",
            "-4.71238898038469",
            "1.0"
    })
    void system_is_undefined_in_declared_special_points(String xs) {
        SystemFunction system = realSystem();
        double x = Double.parseDouble(xs);
        assertUndefinedAt(system, x);
    }

    @Test
    void system_changes_sign_around_minus_pi_over_four_shift() {
        SystemFunction system = realSystem();

        double left = system.calculate(-3.9369908169872414);
        double right = system.calculate(-3.9169908169872414);

        assertTrue(Math.signum(left) != 0.0);
        assertTrue(Math.signum(right) != 0.0);
        assertTrue(Math.signum(left) == -Math.signum(right));
    }

    @ParameterizedTest
    @CsvSource({
            "-6.283185307179586",
            "-4.71238898038469",
            "0"
    })
    void system_throws_on_periodic_singularities(String xs) {
        SystemFunction system = realSystem();
        double x = Double.parseDouble(xs);
        assertThrows(ArithmeticException.class, () -> system.calculate(x));
    }

    @ParameterizedTest
    @CsvSource({
            "-6.293185307179586,-6.273185307179586",
            "-4.72238898038469,-4.70238898038469",
            "-3.151592653589793,-3.131592653589793",
            "-1.5807963267948966,-1.5607963267948966"
    })
    void system_changes_sign_around_periodic_singularities(
            String leftXText,
            String rightXText
    ) {
        SystemFunction system = realSystem();

        double leftValue = system.calculate(Double.parseDouble(leftXText));
        double rightValue = system.calculate(Double.parseDouble(rightXText));

        assertTrue(Math.signum(leftValue) != 0.0);
        assertTrue(Math.signum(rightValue) != 0.0);
        assertTrue(Math.signum(leftValue) == -Math.signum(rightValue));
    }

    private static SystemFunction realSystem() {
        Sin sin = new Sin(EPSILON);
        Cos cos = new Cos(sin);
        Tan tan = new Tan(sin, cos);
        Cot cot = new Cot(sin, cos);
        Sec sec = new Sec(cos);
        Csc csc = new Csc(sin);

        Ln ln = new Ln(EPSILON);
        Log log = new Log(ln);

        return new SystemFunction(sin, cos, tan, cot, sec, csc, ln, log);
    }

    private static void assertUndefinedAt(SystemFunction system, double x) {
        try {
            double value = system.calculate(x);
            assertTrue(!Double.isFinite(value), "Expected undefined result at x=" + x + ", got " + value);
        } catch (ArithmeticException ignored) {
        }
    }
}
