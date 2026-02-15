package tpo;

public class SecantSeries {
    public double approximate(double x, double epsilon, int maxTerms) {
        if (epsilon <= 0) {
            throw new IllegalArgumentException("epsilon must be positive");
        }
        if (maxTerms < 1) {
            throw new IllegalArgumentException("maxTerms must be at least 1");
        }
        double[] eulerNumbers = computeEvenEulerNumbers(maxTerms);
        double sum = 0.0;
        double power = 1.0; // x^(2n)
        double factorial = 1.0; // (2n)!
        for (int n = 0; n < maxTerms; n++) {
            if (n > 0) {
                power *= x * x;
                int m = 2 * n;
                factorial *= (m - 1);
                factorial *= m;
            }
            double term = Math.abs(eulerNumbers[n]) * power / factorial;
            sum += term;
            if (Math.abs(term) < epsilon) {
                break;
            }
        }
        return sum;
    }

    private double[] computeEvenEulerNumbers(int terms) {
        double[] euler = new double[terms];
        euler[0] = 1.0;
        for (int n = 1; n < terms; n++) {
            double acc = 0.0;
            for (int k = 0; k < n; k++) {
                acc += binomial(2 * n, 2 * k) * euler[k];
            }
            euler[n] = -acc;
        }
        return euler;
    }

    private double binomial(int n, int k) {
        int effectiveK = Math.min(k, n - k);
        double result = 1.0;
        for (int i = 1; i <= effectiveK; i++) {
            result *= (n - effectiveK + i);
            result /= i;
        }
        return result;
    }
}
