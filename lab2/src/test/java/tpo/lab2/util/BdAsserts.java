package tpo.lab2.util;

public final class BdAsserts {
    private BdAsserts() {}

    public static void assertClose(double expected, double actual, double tol) {
        double diff = Math.abs(expected - actual);
        if (diff > tol) {
            throw new AssertionError("Expected " + expected + ", actual " + actual + ", diff " + diff);
        }
    }

    public static double bd(double x) {
        return x;
    }
}