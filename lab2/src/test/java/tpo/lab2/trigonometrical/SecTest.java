package tpo.lab2.trigonometrical;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import tpo.lab2.trigonometric.Cos;
import tpo.lab2.trigonometric.Sec;
import tpo.lab2.trigonometric.Sin;
import tpo.lab2.util.BdAsserts;
import tpo.lab2.util.CsvGenerator;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class SecTest {

    private static final double TWO_PI = Math.PI * 2;

    private final double epsilon = 1e-6;
    private final double tol = 1e-2;
    private Sin sin = new Sin(epsilon);
    private Cos cos = new Cos(sin);
    private Sec sec = new Sec(cos);

    @BeforeAll
    static void refreshStubTablesFromRealFunctions() throws Exception {
        CsvGenerator.generateDefaultTestResources();
    }

    @ParameterizedTest
    @CsvSource({
            "-2.0, -2.4029979617223813111",
            "-1.0, 1.8508157176809254415",
            "-0.5, 1.1394939273245490842",
            "-0.2, 1.0203388449411926771",
            "0.2, 1.0203388449411927024",
            "0.5, 1.1394939273245491604",
            "1.0, 1.8508157176809256179",
            "2.0, -2.4029979617223806682"
    })
    void sec_matches_reference(String xs, String expectedS) {
        double x = Double.parseDouble(xs);
        double actual = sec.calculate(x);
        double expected = Double.parseDouble(expectedS);
        BdAsserts.assertClose(expected, actual, tol);
    }

    @Test
    void sec_matches_reference_key_points() {
        BdAsserts.assertClose(1.0, sec.calculate(0), tol);
        BdAsserts.assertClose(1.1547005383792515290, sec.calculate(0.5235987755982989), tol);
        BdAsserts.assertClose(1.4142135623730950488, sec.calculate(0.7853981633974483), tol);
        BdAsserts.assertClose(2.0, sec.calculate(1.0471975511965977), tol);
        BdAsserts.assertClose(-1.0, sec.calculate(Math.PI), tol);
    }

    @Test
    void sec_is_periodic_with_2pi() {
        double x = 0.37;
        double base = sec.calculate(x);
        double shifted = sec.calculate(x + TWO_PI);
        BdAsserts.assertClose(base, shifted, 1e-2);
    }

    @Test
    void sec_throws_when_cos_is_zero() {
        assertThrows(ArithmeticException.class, () -> sec.calculate(Math.PI / 2));
        assertThrows(ArithmeticException.class, () -> sec.calculate(5 * Math.PI / 2));
    }

    @Test
    void sec_throws_when_x_equals_negative_three_pi_over_two() {
        assertThrows(ArithmeticException.class, () -> sec.calculate(-3 * Math.PI / 2));
    }

    @ParameterizedTest
    @CsvSource({
            "2.0",
            "2.8",
            "3.4",
            "4.0"
    })
    void sec_matches_reference_in_second_and_third_quadrants(String xs) {
        double x = Double.parseDouble(xs);
        double actual = sec.calculate(x);
        double expected = 1.0d / Math.cos(x);
        BdAsserts.assertClose(expected, actual, 1e-2);
    }

    @Test
    void sec_is_negative_in_second_and_third_quadrants() {
        double q2 = sec.calculate(2.3);
        double q3 = sec.calculate(4.0);

        assertTrue(q2 < 0);
        assertTrue(q3 < 0);
    }

    @Test
    void sec_is_even() {
        double x = 0.37;
        double positive = sec.calculate(x);
        double negative = sec.calculate(-x);
        BdAsserts.assertClose(positive, negative, 1e-2);
    }

    @Test
    void sec_uses_cos_dependency_with_mock() {
        Cos cosMock = mock(Cos.class);
        Sec sec = new Sec(cosMock);

        double x = 0.25;
        when(cosMock.calculate(x)).thenReturn(0.5);

        double actual = sec.calculate(x);

        BdAsserts.assertClose(2.0, actual, 1e-12);
        verify(cosMock).calculate(x);
    }
}
