package tpo.lab2.trigonometrical;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import tpo.lab2.trigonometric.Csc;
import tpo.lab2.trigonometric.Sin;
import tpo.lab2.util.BdAsserts;
import tpo.lab2.util.CsvGenerator;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CscTest {

    private static final double TWO_PI = Math.PI * 2;

    private final double epsilon = 1e-6;
    private final double tol = 1e-2;
    private Sin sin = new Sin(epsilon);
    private Csc csc = new Csc(sin);

    @BeforeAll
    static void refreshStubTablesFromRealFunctions() throws Exception {
        CsvGenerator.generateDefaultTestResources();
    }

    @ParameterizedTest
    @CsvSource({
            "-2.0, -1.0997501702946164668",
            "-1.0, -1.1883951057781212163",
            "-0.5, -2.0858296429334881858",
            "-0.2, -5.0334895476723448089",
            "0.2, 5.0334895476723448089",
            "0.5, 2.0858296429334881858",
            "1.0, 1.1883951057781212163",
            "2.0, 1.0997501702946164668"
    })
    void csc_matches_reference(String xs, String expectedS) {
        double x = Double.parseDouble(xs);
        double actual = csc.calculate(x);
        double expected = Double.parseDouble(expectedS);
        BdAsserts.assertClose(expected, actual, tol);
    }

    @Test
    void csc_matches_reference_key_points() {
        BdAsserts.assertClose(2.0, csc.calculate(0.5235987755982989), tol);
        BdAsserts.assertClose(1.4142135623730950488, csc.calculate(0.7853981633974483), tol);
        BdAsserts.assertClose(1.1547005383792515290, csc.calculate(1.0471975511965977), tol);
    }

    @Test
    void csc_is_periodic_with_2pi() {
        double x = 0.37;
        double base = csc.calculate(x);
        double shifted = csc.calculate(x + TWO_PI);
        BdAsserts.assertClose(base, shifted, 1e-2);
    }

    @Test
    void csc_is_odd() {
        double x = 0.37;
        double positive = csc.calculate(x);
        double negative = csc.calculate(-x);
        BdAsserts.assertClose(-positive, negative, 1e-2);
    }

    @Test
    void csc_throws_when_sin_is_zero() {
        assertUndefinedAt(0.0);
        assertUndefinedAt(Math.PI);
        assertUndefinedAt(-2 * Math.PI);
    }

    @Test
    void csc_uses_sin_dependency_with_mock() {
        Sin sinMock = mock(Sin.class);
        Csc csc = new Csc(sinMock);

        double x = 0.25;
        when(sinMock.calculate(x)).thenReturn(0.5);

        double actual = csc.calculate(x);

        BdAsserts.assertClose(2.0, actual, 1e-12);
        verify(sinMock).calculate(x);
    }

    private void assertUndefinedAt(double x) {
        try {
            double value = csc.calculate(x);
            assertTrue(!Double.isFinite(value) || Math.abs(value) > 1e6,
                    "Expected undefined behavior at x=" + x + ", got " + value);
        } catch (ArithmeticException ignored) {
        }
    }
}



