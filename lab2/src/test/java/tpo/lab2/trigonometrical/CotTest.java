package tpo.lab2.trigonometrical;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import tpo.lab2.trigonometric.Cos;
import tpo.lab2.trigonometric.Cot;
import tpo.lab2.trigonometric.Sin;
import tpo.lab2.util.BdAsserts;
import tpo.lab2.util.CsvGenerator;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class CotTest {

    private final double epsilon = 1e-6;
    private final double tol = 1e-2;
    private Sin sin = new Sin(epsilon);
    private Cos cos = new Cos(sin);
    private Cot cot = new Cot(sin, cos);

    @BeforeAll
    static void refreshStubTablesFromRealFunctions() throws Exception {
        CsvGenerator.generateDefaultTestResources();
    }

    @ParameterizedTest
    @CsvSource({
            "-2.0, 0.45765755436028570254",
            "-1.0, -0.64209261593433076420",
            "-0.5, -1.8304877217124519805",
            "-0.2, -4.9331548755868937186",
            "0.2, 4.9331548755868935962",
            "0.5, 1.8304877217124518581",
            "1.0, 0.64209261593433063756",
            "2.0, -0.45765755436028582497"
    })
    void cot_matches_reference(String xs, String expectedS) {
        double x = Double.parseDouble(xs);
        double actual = cot.calculate(x);
        double expected = Double.parseDouble(expectedS);
        BdAsserts.assertClose(expected, actual, tol);
    }

    @ParameterizedTest
    @CsvSource({
            "0.5235987755982989, 1.7320508075688772935",
            "0.7853981633974483, 1",
            "1.0471975511965977, 0.57735026918962576451",
            "1.5707963267948966, 0"
    })
    void cot_matches_reference_key_points(String xs, String expectedS) {
        double x = Double.parseDouble(xs);
        double actual = cot.calculate(x);
        double expected = Double.parseDouble(expectedS);
        BdAsserts.assertClose(expected, actual, tol);
    }

    @Test
    void cot_throws_when_sin_is_zero() {
        assertThrows(ArithmeticException.class, () -> cot.calculate(0));
    }

    @Test
    void cot_throws_when_x_is_multiple_of_pi() {
        assertThrows(ArithmeticException.class, () -> cot.calculate(Math.PI * 2));
    }

    @ParameterizedTest
    @CsvSource({
            "2.0",
            "2.8",
            "3.4",
            "4.0"
    })
    void cot_matches_reference_in_second_and_third_quadrants(String xs) {
        double x = Double.parseDouble(xs);
        double actual = cot.calculate(x);
        double expected = 1.0d / Math.tan(x);
        BdAsserts.assertClose(expected, actual, 1e-2);
    }

    @Test
    void cot_changes_sign_around_pi() {
        double left = cot.calculate(3.131592653589793);
        double right = cot.calculate(3.151592653589793);

        assertTrue(left < 0);
        assertTrue(right > 0);
    }

    @Test
    void cot_is_periodic_with_pi() {
        double x = 0.37;
        double base = cot.calculate(x);
        double shifted = cot.calculate(x + Math.PI);
        BdAsserts.assertClose(base, shifted, 1e-2);
    }

    @Test
    void cot_is_odd() {
        double x = 0.37;
        double positive = cot.calculate(x);
        double negative = cot.calculate(-x);
        BdAsserts.assertClose(-positive, negative, 1e-2);
    }

    @Test
    void cot_uses_sin_and_cos_dependencies_with_mocks() {
        Sin sinMock = mock(Sin.class);
        Cos cosMock = mock(Cos.class);
        Cot cot = new Cot(sinMock, cosMock);

        double x = 0.25;
        when(sinMock.calculate(x)).thenReturn(2.0);
        when(cosMock.calculate(x)).thenReturn(4.0);

        double actual = cot.calculate(x);

        BdAsserts.assertClose(2.0, actual, 1e-12);
        verify(sinMock).calculate(x);
        verify(cosMock).calculate(x);
    }
}
