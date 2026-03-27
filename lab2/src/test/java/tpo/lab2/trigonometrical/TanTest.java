package tpo.lab2.trigonometrical;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import tpo.lab2.trigonometric.Cos;
import tpo.lab2.trigonometric.Sin;
import tpo.lab2.trigonometric.Tan;
import tpo.lab2.util.BdAsserts;
import tpo.lab2.util.CsvGenerator;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class TanTest {

    private final double epsilon = 1e-6;
    private final double tol = 1e-3;
    private Sin sin = new Sin(epsilon);
    private Cos cos = new Cos(sin);
    private Tan tan = new Tan(sin, cos);

    @BeforeAll
    static void refreshStubTablesFromRealFunctions() throws Exception {
        CsvGenerator.generateDefaultTestResources();
    }

    @ParameterizedTest
    @CsvSource({
            "-1.0, -1.5574077246549020821",
            "-0.5, -0.54630248984379049497",
            "-0.2, -0.20271003550867248081",
            "0.2, 0.20271003550867248584",
            "0.5, 0.54630248984379053151",
            "1.0, 1.5574077246549022305"
    })
    void tan_matches_reference(String xs, String expectedS) {
        double x = Double.parseDouble(xs);
        double actual = tan.calculate(x);
        double expected = Double.parseDouble(expectedS);
        BdAsserts.assertClose(expected, actual, tol);
    }

    @ParameterizedTest
    @CsvSource({
            "0, 0",
            "0.5235987755982989, 0.57735026918962576451",
            "0.7853981633974483, 1",
            "1.0471975511965977, 1.7320508075688772935",
            "3.141592653589793, 0"
    })
    void tan_matches_reference_key_points(String xs, String expectedS) {
        double x = Double.parseDouble(xs);
        double actual = tan.calculate(x);
        double expected = Double.parseDouble(expectedS);
        BdAsserts.assertClose(expected, actual, tol);
    }

    @Test
    void tan_throws_when_cos_is_zero() {
        assertThrows(ArithmeticException.class, () -> tan.calculate(Math.PI / 2));
    }

    @ParameterizedTest
    @CsvSource({
            "2.0",
            "2.8",
            "3.4",
            "4.0"
    })
    void tan_matches_reference_in_second_and_third_quadrants(String xs) {
        double x = Double.parseDouble(xs);
        double actual = tan.calculate(x);
        double expected = Math.tan(x);
        BdAsserts.assertClose(expected, actual, 1e-2);
    }

    @Test
    void tan_changes_sign_around_pi_over_two() {
        double left = tan.calculate(1.5706963267948966);
        double right = tan.calculate(1.5708963267948966);

        assertTrue(left > 0);
        assertTrue(right < 0);
    }

    @Test
    void tan_changes_sign_around_pi() {
        double left = tan.calculate(3.131592653589793);
        double right = tan.calculate(3.151592653589793);

        assertTrue(left < 0);
        assertTrue(right > 0);
    }

    @Test
    void tan_is_periodic_with_pi() {
        double x = 0.37;
        double base = tan.calculate(x);
        double shifted = tan.calculate(x + Math.PI);
        BdAsserts.assertClose(base, shifted, 1e-2);
    }

    @Test
    void tan_is_odd() {
        double x = 0.37;
        double positive = tan.calculate(x);
        double negative = tan.calculate(-x);
        BdAsserts.assertClose(-positive, negative, 1e-2);
    }

    @Test
    void tan_uses_sin_and_cos_dependencies_with_mocks() {
        Sin sinMock = mock(Sin.class);
        Cos cosMock = mock(Cos.class);
        Tan tan = new Tan(sinMock, cosMock);

        double x = 0.25;
        when(sinMock.calculate(x)).thenReturn(2.0);
        when(cosMock.calculate(x)).thenReturn(4.0);

        double actual = tan.calculate(x);

        BdAsserts.assertClose(0.5, actual, 1e-12);
        verify(sinMock).calculate(x);
        verify(cosMock).calculate(x);
    }
}
