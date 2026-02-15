package tpo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BubbleSorterTest {
    private final BubbleSorter sorter = new BubbleSorter();

    @Test
    @DisplayName("Уже отсортированный массив - ранний выход")
    void sortsAlreadySortedArrayAndExitsEarly() {
        int[] input = {1, 2, 3};
        BubbleSortTrace trace = sorter.sortWithTrace(input);

        assertArrayEquals(new int[]{1, 2, 3}, trace.getSorted());
        List<TracePoint> expected = List.of(
                TracePoint.START_PASS,
                TracePoint.COMPARE,
                TracePoint.COMPARE,
                TracePoint.PASS_END,
                TracePoint.EARLY_EXIT,
                TracePoint.COMPLETE
        );
        assertIterableEquals(expected, trace.getPoints());
    }

    @Test
    @DisplayName("Обратный порядок - максимальные свапы")
    void sortsReverseOrder() {
        int[] input = {3, 2, 1};
        BubbleSortTrace trace = sorter.sortWithTrace(input);

        assertArrayEquals(new int[]{1, 2, 3}, trace.getSorted());
        List<TracePoint> expected = List.of(
                TracePoint.START_PASS,
                TracePoint.COMPARE,
                TracePoint.SWAP,
                TracePoint.COMPARE,
                TracePoint.SWAP,
                TracePoint.PASS_END,
                TracePoint.START_PASS,
                TracePoint.COMPARE,
                TracePoint.SWAP,
                TracePoint.PASS_END,
                TracePoint.COMPLETE
        );
        assertIterableEquals(expected, trace.getPoints());
    }

    @Test
    @DisplayName("Дубликаты - без лишних свапов")
    void handlesDuplicatesWithoutUnnecessarySwaps() {
        int[] input = {2, 2, 1};
        BubbleSortTrace trace = sorter.sortWithTrace(input);

        assertArrayEquals(new int[]{1, 2, 2}, trace.getSorted());
        List<TracePoint> expected = List.of(
                TracePoint.START_PASS,
                TracePoint.COMPARE,
                TracePoint.COMPARE,
                TracePoint.SWAP,
                TracePoint.PASS_END,
                TracePoint.START_PASS,
                TracePoint.COMPARE,
                TracePoint.SWAP,
                TracePoint.PASS_END,
                TracePoint.COMPLETE
        );
        assertIterableEquals(expected, trace.getPoints());
    }

    @Test
    @DisplayName("Все элементы равны - ранний выход")
    void earlyExitWhenAllElementsEqual() {
        int[] input = {5, 5, 5, 5};
        BubbleSortTrace trace = sorter.sortWithTrace(input);

        assertArrayEquals(new int[]{5, 5, 5, 5}, trace.getSorted());
        List<TracePoint> expected = List.of(
                TracePoint.START_PASS,
                TracePoint.COMPARE,
                TracePoint.COMPARE,
                TracePoint.COMPARE,
                TracePoint.PASS_END,
                TracePoint.EARLY_EXIT,
                TracePoint.COMPLETE
        );
        assertIterableEquals(expected, trace.getPoints());
    }
}
