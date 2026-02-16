package tpo.secant;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class SecantSeriesTest {
    private final SecantSeries series = new SecantSeries();

    @Test
    @DisplayName("sec(0) = 1")
    void computesValueAtZero() {
        double result = series.approximate(0.0, 1e-12, 5);
        assertEquals(1.0, result, 1e-12);
    }

    @ParameterizedTest(name = "Чётность sec(x): x={0}")
    @ValueSource(doubles = {0.1, 0.4, 0.7, 1.0, 1.4})
    @DisplayName("Функция чётная: sec(x)=sec(-x) для множества точек")
    void isEvenFunction(double x) {
        double positive = series.approximate(x, 1e-12, 50);
        double negative = series.approximate(-x, 1e-12, 50);
        assertEquals(positive, negative, 1e-12);
    }

    @ParameterizedTest(name = "sec({0}) ≈ {1}")
    @CsvSource({
            "0.1, 1.0050209184004553, 1e-12",
            "0.4, 1.0857044283832387, 1e-12",
            "0.5, 1.139493927324549, 1e-11",
            "0.7, 1.3074592597335937, 1e-10",
            "1.0, 1.8508157176809255, 1e-9",
            "1.4, 5.883490084827342, 1e-5",
            "-0.4, 1.0857044283832387, 1e-12",
            "-0.7, 1.3074592597335937, 1e-10"
    })
    @DisplayName("Сходимость к табличным значениям sec(x) в области сходимости")
    void matchesPrecomputedSecants(double x, double expected, double tolerance) {
        double actual = series.approximate(x, 1e-12, 120);
        assertEquals(expected, actual, tolerance);
    }

    @Test
    @DisplayName("Точность улучшается при увеличении числа членов ряда")
    void accuracyImprovesWithMoreTerms() {
        double x = 0.7;
        double expected = 1.3074592597335937;
        double coarse = series.approximate(x, 1e-12, 5);
        double fine = series.approximate(x, 1e-12, 30);
        assertTrue(Math.abs(fine - expected) < Math.abs(coarse - expected));
    }

    @Test
    @DisplayName("Неверные параметры отклоняются")
    void rejectsInvalidParameters() {
        assertThrows(IllegalArgumentException.class, () -> series.approximate(0.1, 0.0, 5));
        assertThrows(IllegalArgumentException.class, () -> series.approximate(0.1, 1e-6, 0));
    }

    @Test
    @DisplayName("Вычисление у границы сходимости |x| < π/2 остаётся точным")
    void worksNearConvergenceBoundary() {
        double x = 1.4;
        double expected = 5.883490084827342;
        double actual = series.approximate(x, 1e-12, 150);
        assertEquals(expected, actual, 1e-5);
    }

    @ParameterizedTest(name = "Вне сходимости: x={0}")
    @ValueSource(doubles = {1.7, 2.0})
    @DisplayName("Завершается корректно вне области сходимости (|x| ≥ π/2)")
    void handlesDivergentRegionGracefully(double x) {
        assertDoesNotThrow(() -> {
            double result = series.approximate(x, 1e-12, 40);
            assertFalse(Double.isNaN(result) || Double.isInfinite(result),
                    "Result should be finite even if inaccurate");
        });
    }
}
