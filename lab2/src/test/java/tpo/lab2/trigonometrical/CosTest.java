package tpo.lab2.trigonometrical;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import tpo.lab2.trigonometric.Cos;
import tpo.lab2.trigonometric.Sin;
import tpo.lab2.util.CsvGenerator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static tpo.lab2.util.BdAsserts.assertClose;

public class CosTest {
    private final double epsilon = 1e-6;
    private final double tol = 1e-3;
    private Sin sin = new Sin(epsilon);
    private Cos cos = new Cos(sin);

    @BeforeAll
    static void refreshStubTablesFromRealFunctions() throws Exception {
        CsvGenerator.generateDefaultTestResources();
    }

    @ParameterizedTest
    @CsvSource({
            "-2.0, -0.41614683654714233135",
            "-1.0, 0.54030230586813976890",
            "-0.5, 0.87758256189037274547",
            "-0.2, 0.98006657784124164329",
            "0.0, 1",
            "0.2, 0.98006657784124161897",
            "0.5, 0.87758256189037268676",
            "2.0, -0.41614683654714244268"
    })
    void cos_check(String px, String py) {
        double fact = cos.calculate(Double.parseDouble(px));
        double expected = Double.parseDouble(py);
        assertClose(expected, fact, tol);
    }

    @ParameterizedTest
    @CsvSource({
            "0, 1",
            "0.5235987755982989, 0.86602540378443864676",
            "0.7853981633974483, 0.70710678118654752440",
            "1.0471975511965977, 0.5",
            "3.141592653589793, -1"
    })
    void cos_check_radian_points(String px, String py) {
        double fact = cos.calculate(Double.parseDouble(px));
        double expected = Double.parseDouble(py);
        assertClose(expected, fact, tol);
    }

    @Test
    void cos_check_radian_points_periodic() {
        double x = 0.37;
        double fact = cos.calculate(x);
        double shifted = cos.calculate(x + Math.PI * 2);
        assertClose(fact, shifted, tol);
    }

    @Test
    void cos_is_even() {
        double x = 0.37;
        double positive = cos.calculate(x);
        double negative = cos.calculate(-x);
        assertClose(positive, negative, tol);
    }

    @Test
    void cos_uses_sin_dependency_with_mock() {
        Sin sinMock = mock(Sin.class);
        Cos cos = new Cos(sinMock);

        double x = 1.23;
        double arg = Math.PI / 2 - x;
        double value = 0.123456;

        when(sinMock.calculate(arg)).thenReturn(value);

        double actual = cos.calculate(x);

        assertEquals(value, actual);
        verify(sinMock).calculate(arg);
    }


}
