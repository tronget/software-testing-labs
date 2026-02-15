package tpo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BubbleSortTrace {
    private final int[] sorted;
    private final List<TracePoint> points;

    public BubbleSortTrace(int[] sorted, List<TracePoint> points) {
        this.sorted = sorted.clone();
        this.points = new ArrayList<>(points);
    }

    public int[] getSorted() {
        return sorted.clone();
    }

    public List<TracePoint> getPoints() {
        return Collections.unmodifiableList(points);
    }
}
