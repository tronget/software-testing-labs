package tpo.bubblesort;

import java.util.ArrayList;
import java.util.List;

public class BubbleSorter {
    public BubbleSortTrace sortWithTrace(int[] input) {
        int[] data = input.clone();
        List<TracePoint> trace = new ArrayList<>();
        int n = data.length;
        for (int i = 0; i < n - 1; i++) {
            trace.add(TracePoint.START_PASS);
            boolean swapped = false;
            for (int j = 0; j < n - 1 - i; j++) {
                trace.add(TracePoint.COMPARE);
                if (data[j] > data[j + 1]) {
                    int temp = data[j];
                    data[j] = data[j + 1];
                    data[j + 1] = temp;
                    swapped = true;
                    trace.add(TracePoint.SWAP);
                }
            }
            trace.add(TracePoint.PASS_END);
            if (!swapped) {
                trace.add(TracePoint.EARLY_EXIT);
                break;
            }
        }
        trace.add(TracePoint.COMPLETE);
        return new BubbleSortTrace(data, trace);
    }
}
