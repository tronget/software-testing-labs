package tpo.lab2.log;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static tpo.lab2.util.BdAsserts.assertClose;

public class LogTest {

    private final double epsilon = 1e-6;
    private final double tol = 1e-3;

    private final Ln ln = new Ln(epsilon);
    private final Log log = new Log(ln);

    @ParameterizedTest
    @CsvSource({
            "0.1, -3.3219280948873623473",
            "0.2, -2.3219280948873623479",
            "0.5, -1",
            "2.0, 1",
            "3.0, 1.5849625007211561815",
            "10.0, 3.3219280948873623473"
    })
    void log2_check(String px, String py) {
        double actual = log.calculate(Double.parseDouble(px), 2);
        double expected = Double.parseDouble(py);
        assertClose(expected, actual, tol);
    }

    @ParameterizedTest
    @CsvSource({
            "0.1, -2.0959032742893846039",
            "0.2, -1.4649735207179271672",
            "0.5, -0.63092975357145743708",
            "2.0, 0.63092975357145743708",
            "3.0, 1",
            "10.0, 2.0959032742893846039"
    })
    void log3_check(String px, String py) {
        double actual = log.calculate(Double.parseDouble(px), 3);
        double expected = Double.parseDouble(py);
        assertClose(expected, actual, tol);
    }

    @ParameterizedTest
    @CsvSource({
            "0.1, -1.4306765580733931",
            "0.2, -1",
            "0.5, -0.43067655807339306",
            "2.0, 0.43067655807339306",
            "3.0, 0.6826061944859853",
            "10.0, 1.4306765580733931"
    })
    void log5_check(String px, String py) {
        double actual = log.calculate(Double.parseDouble(px), 5);
        double expected = Double.parseDouble(py);
        assertClose(expected, actual, tol);
    }

    @Test
    void log_returns_zero_when_x_equals_one() {
        assertClose(0.0, log.calculate(1.0, 2), 1e-12);
        assertClose(0.0, log.calculate(1.0, 3), 1e-12);
        assertClose(0.0, log.calculate(1.0, 5), 1e-12);
    }

    @Test
    void log_returns_one_for_base_power_points() {
        assertClose(1.0, log.calculate(2.0, 2), 1e-12);
        assertClose(1.0, log.calculate(3.0, 3), 1e-12);
        assertClose(1.0, log.calculate(5.0, 5), 1e-12);
    }

    @Test
    void log_check_negative() {
        assertThrows(IllegalArgumentException.class, () -> log.calculate(0, 2));
        assertThrows(IllegalArgumentException.class, () -> log.calculate(-1, 2));

        assertThrows(IllegalArgumentException.class, () -> log.calculate(0, 3));
        assertThrows(IllegalArgumentException.class, () -> log.calculate(-1, 3));

        assertThrows(IllegalArgumentException.class, () -> log.calculate(0, 5));
        assertThrows(IllegalArgumentException.class, () -> log.calculate(-1, 5));

    }

    @Test
    void log_uses_ln_dependency_with_mock() {
        Ln lnMock = mock(Ln.class);
        Log log2 = new Log(lnMock);

        double x = 8.0;
        double base = 2.0;

        when(lnMock.calculate(x)).thenReturn(6.0);
        when(lnMock.calculate(base)).thenReturn(2.0);

        double actual = log2.calculate(x, base);

        assertClose(3.0, actual, 1e-12);
        verify(lnMock).calculate(x);
        verify(lnMock).calculate(base);
    }



}
