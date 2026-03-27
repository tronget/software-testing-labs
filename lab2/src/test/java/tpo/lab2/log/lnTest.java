package tpo.lab2.log;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import tpo.lab2.util.BdAsserts.*;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static tpo.lab2.util.BdAsserts.assertClose;

public class lnTest {


    private final double epsilon = 1e-6;
    private final double tol = 1e-3;
    private final Ln ln = new Ln(epsilon);

    @ParameterizedTest
    @CsvSource({
            "0.1, -2.30258509299",
            "0.2, -1.60943791243",
            "0.5, -0.69314718056",
            "1, 0",
            "2, 0.69314718056",
            "5, 1.60943791243",
            "10, 2.30258509299"
    })
    void ln_check(String px, String py) {
        double fact = ln.calculate(Double.parseDouble(px));
        double expected = Double.parseDouble(py);
        assertClose(expected, fact, tol);
    }

    @Test
    void ln_check_negative() {
        assertThrows(IllegalArgumentException.class, () -> ln.calculate(0));
        assertThrows(IllegalArgumentException.class, () -> ln.calculate(-1));
    }

}
