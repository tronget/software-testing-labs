package tpo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SecantSeriesTest {
    private final SecantSeries series = new SecantSeries();

    @Test
    @DisplayName("sec(0) = 1")
    void computesValueAtZero() {
        double result = series.approximate(0.0, 1e-12, 5);
        assertEquals(1.0, result, 1e-12);
    }

    @Test
    @DisplayName("Функция чётная: sec(x)=sec(-x)")
    void isEvenFunction() {
        double positive = series.approximate(0.4, 1e-12, 10);
        double negative = series.approximate(-0.4, 1e-12, 10);
        assertEquals(positive, negative, 1e-12);
    }

    @Test
    @DisplayName("Сходимость к 1/cos(x) при малых x")
    void matchesMathCosForSmallArgument() {
        double x = 0.5;
        double expected = 1.0 / Math.cos(x);
        double actual = series.approximate(x, 1e-12, 20);
        assertEquals(expected, actual, 1e-10);
    }

    @Test
    @DisplayName("Точность улучшается при большем числе членов")
    void accuracyImprovesWithMoreTerms() {
        double x = 0.7;
        double expected = 1.0 / Math.cos(x);
        double coarse = series.approximate(x, 1e-2, 5);
        double fine = series.approximate(x, 1e-12, 15);
        assertTrue(Math.abs(fine - expected) < Math.abs(coarse - expected));
    }

    @Test
    @DisplayName("Неверные параметры отклоняются")
    void rejectsInvalidParameters() {
        assertThrows(IllegalArgumentException.class, () -> series.approximate(0.1, 0.0, 5));
        assertThrows(IllegalArgumentException.class, () -> series.approximate(0.1, 1e-6, 0));
    }
}
