package tpo.lab2.integration;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import tpo.lab2.SystemFunction;
import tpo.lab2.log.Ln;
import tpo.lab2.log.Log;
import tpo.lab2.trigonometric.Cos;
import tpo.lab2.trigonometric.Cot;
import tpo.lab2.trigonometric.Csc;
import tpo.lab2.trigonometric.Sec;
import tpo.lab2.trigonometric.Sin;
import tpo.lab2.trigonometric.Tan;
import tpo.lab2.util.CsvGenerator;
import tpo.lab2.util.CsvTableStub;

import java.io.BufferedWriter;
import java.nio.file.Files;
import java.nio.file.Path;

import static tpo.lab2.util.BdAsserts.assertClose;

class FuncSystemTopDownIntegrationTest {

    private static final double EPS = 1e-12;
    private static final double TOL = 1e-6;

    private static final String[] NEGATIVE_BRANCH_POINTS = {
            "-0.1", "-0.2", "-0.5", "-1.0", "-2.0"
    };

    private static final String[] POSITIVE_BRANCH_POINTS = {
            "0.1", "0.2", "0.5", "2.0", "3.0", "10.0"
    };

    @BeforeAll
    static void refreshStubTablesFromRealFunctions() throws Exception {
        CsvGenerator.generateDefaultTestResources();
        generateLog5Stub();
    }

    @Test
    void trig_stage_0_all_stubs() throws Exception {
        Modules m = baseModules();
        assertNegativeBranchMatches(m.sinStub, m.cosStub, m.tanStub, m.cotStub, m.secStub, m.cscStub, m.lnStub, m.logAllStub);
    }

    @Test
    void trig_stage_1_real_sin_rest_stubs() throws Exception {
        Modules m = baseModules();
        Sin sinReal = new Sin(EPS);
        assertNegativeBranchMatches(sinReal, m.cosStub, m.tanStub, m.cotStub, m.secStub, m.cscStub, m.lnStub, m.logAllStub);
    }

    @Test
    void trig_stage_2_real_sin_cos_rest_stubs() throws Exception {
        Modules m = baseModules();
        Sin sinReal = new Sin(EPS);
        Cos cosReal = new Cos(sinReal);
        assertNegativeBranchMatches(sinReal, cosReal, m.tanStub, m.cotStub, m.secStub, m.cscStub, m.lnStub, m.logAllStub);
    }

    @Test
    void trig_stage_3_real_sin_cos_tan_rest_stubs() throws Exception {
        Modules m = baseModules();
        Sin sinReal = new Sin(EPS);
        Cos cosReal = new Cos(sinReal);
        Tan tanReal = new Tan(sinReal, cosReal);
        assertNegativeBranchMatches(sinReal, cosReal, tanReal, m.cotStub, m.secStub, m.cscStub, m.lnStub, m.logAllStub);
    }

    @Test
    void trig_stage_4_real_trig_logs_stubs() throws Exception {
        Modules m = baseModules();
        Sin sinReal = new Sin(EPS);
        Cos cosReal = new Cos(sinReal);
        Tan tanReal = new Tan(sinReal, cosReal);
        Cot cotReal = new Cot(sinReal, cosReal);
        Sec secReal = new Sec(cosReal);
        Csc cscReal = new Csc(sinReal);
        assertNegativeBranchMatches(sinReal, cosReal, tanReal, cotReal, secReal, cscReal, m.lnStub, m.logAllStub);
    }

    @Test
    void log_stage_0_all_stubs() throws Exception {
        Modules m = baseModules();
        assertPositiveBranchMatches(m.sinStub, m.cosStub, m.tanStub, m.cotStub, m.secStub, m.cscStub, m.lnStub, m.logAllStub);
    }

    @Test
    void log_stage_1_real_ln_rest_stubs() throws Exception {
        Modules m = baseModules();
        Ln lnReal = new Ln(EPS);
        Log logStage = new LogStageStub(lnReal, m.log2Table, m.log3Table, m.log5Table, false, false, false);
        assertPositiveBranchMatches(m.sinStub, m.cosStub, m.tanStub, m.cotStub, m.secStub, m.cscStub, lnReal, logStage);
    }

    @Test
    void log_stage_2_real_ln_log2_rest_stubs() throws Exception {
        Modules m = baseModules();
        Ln lnReal = new Ln(EPS);
        Log logStage = new LogStageStub(lnReal, m.log2Table, m.log3Table, m.log5Table, true, false, false);
        assertPositiveBranchMatches(m.sinStub, m.cosStub, m.tanStub, m.cotStub, m.secStub, m.cscStub, lnReal, logStage);
    }

    @Test
    void log_stage_3_real_ln_log2_log3_rest_stubs() throws Exception {
        Modules m = baseModules();
        Ln lnReal = new Ln(EPS);
        Log logStage = new LogStageStub(lnReal, m.log2Table, m.log3Table, m.log5Table, true, true, false);
        assertPositiveBranchMatches(m.sinStub, m.cosStub, m.tanStub, m.cotStub, m.secStub, m.cscStub, lnReal, logStage);
    }

    @Test
    void log_stage_4_real_logs_trig_stubs() throws Exception {
        Modules m = baseModules();
        Ln lnReal = new Ln(EPS);
        Log logReal = new Log(lnReal);
        assertPositiveBranchMatches(m.sinStub, m.cosStub, m.tanStub, m.cotStub, m.secStub, m.cscStub, lnReal, logReal);
    }

    private static Modules baseModules() throws Exception {
        CsvTableStub sinTable = stub("sin.csv");
        CsvTableStub cosTable = stub("cos.csv");
        CsvTableStub tanTable = stub("tan.csv");
        CsvTableStub cotTable = stub("cot.csv");
        CsvTableStub secTable = stub("sec.csv");
        CsvTableStub cscTable = stub("csc.csv");
        CsvTableStub lnTable = stub("ln.csv");
        CsvTableStub log2Table = stub("log2.csv");
        CsvTableStub log3Table = stub("log3.csv");
        CsvTableStub log5Table = stub("log5.csv");

        Sin sinStub = new SinCsvStub(sinTable);
        Cos cosStub = new CosCsvStub(cosTable);
        Tan tanStub = new TanCsvStub(tanTable);
        Cot cotStub = new CotCsvStub(cotTable);
        Sec secStub = new SecCsvStub(secTable);
        Csc cscStub = new CscCsvStub(cscTable);
        Ln lnStub = new LnCsvStub(lnTable);
        Log logAllStub = new LogStageStub(lnStub, log2Table, log3Table, log5Table, false, false, false);

        return new Modules(
                sinStub,
                cosStub,
                tanStub,
                cotStub,
                secStub,
                cscStub,
                lnStub,
                logAllStub,
                log2Table,
                log3Table,
                log5Table
        );
    }

    private static CsvTableStub stub(String fileName) throws Exception {
        return new CsvTableStub(Path.of("src/test/resources/" + fileName));
    }

    private static void assertNegativeBranchMatches(
            Sin sin,
            Cos cos,
            Tan tan,
            Cot cot,
            Sec sec,
            Csc csc,
            Ln ln,
            Log log
    ) {
        assertSystemMatches(
                new SystemFunction(sin, cos, tan, cot, sec, csc, ln, log),
                NEGATIVE_BRANCH_POINTS
        );
    }

    private static void assertPositiveBranchMatches(
            Sin sin,
            Cos cos,
            Tan tan,
            Cot cot,
            Sec sec,
            Csc csc,
            Ln ln,
            Log log
    ) {
        assertSystemMatches(
                new SystemFunction(sin, cos, tan, cot, sec, csc, ln, log),
                POSITIVE_BRANCH_POINTS
        );
    }

    private static void assertSystemMatches(SystemFunction system, String[] xValues) {
        CsvTableStub expected;
        try {
            expected = stub("system_expected.csv");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        for (String xText : xValues) {
            double x = Double.parseDouble(xText);
            double actual = system.calculate(x);
            double expectedValue = expected.calculate(x);
            assertClose(expectedValue, actual, TOL);
        }
    }

    private static void generateLog5Stub() throws Exception {
        Path log5Path = Path.of("src/test/resources/log5.csv");
        Ln ln = new Ln(EPS);
        Log log = new Log(ln);

        try (BufferedWriter w = Files.newBufferedWriter(log5Path)) {
            w.write("x,value");
            w.newLine();
            for (String xText : POSITIVE_BRANCH_POINTS) {
                double x = Double.parseDouble(xText);
                w.write(xText);
                w.write(",");
                w.write(Double.toString(log.calculate(x, 5)));
                w.newLine();
            }
        }
    }

    private record Modules(
            Sin sinStub,
            Cos cosStub,
            Tan tanStub,
            Cot cotStub,
            Sec secStub,
            Csc cscStub,
            Ln lnStub,
            Log logAllStub,
            CsvTableStub log2Table,
            CsvTableStub log3Table,
            CsvTableStub log5Table
    ) {}

    private static final class SinCsvStub extends Sin {
        private final CsvTableStub table;

        private SinCsvStub(CsvTableStub table) {
            super(EPS);
            this.table = table;
        }

        @Override
        public double calculate(double x) {
            return table.calculate(x);
        }
    }

    private static final class CosCsvStub extends Cos {
        private final CsvTableStub table;

        private CosCsvStub(CsvTableStub table) {
            super(new Sin(EPS));
            this.table = table;
        }

        @Override
        public double calculate(double x) {
            return table.calculate(x);
        }
    }

    private static final class TanCsvStub extends Tan {
        private final CsvTableStub table;

        private TanCsvStub(CsvTableStub table) {
            super(new Sin(EPS), new Cos(new Sin(EPS)));
            this.table = table;
        }

        @Override
        public double calculate(double x) {
            return table.calculate(x);
        }
    }

    private static final class CotCsvStub extends Cot {
        private final CsvTableStub table;

        private CotCsvStub(CsvTableStub table) {
            super(new Sin(EPS), new Cos(new Sin(EPS)));
            this.table = table;
        }

        @Override
        public double calculate(double x) {
            return table.calculate(x);
        }
    }

    private static final class SecCsvStub extends Sec {
        private final CsvTableStub table;

        private SecCsvStub(CsvTableStub table) {
            super(new Cos(new Sin(EPS)));
            this.table = table;
        }

        @Override
        public double calculate(double x) {
            return table.calculate(x);
        }
    }

    private static final class CscCsvStub extends Csc {
        private final CsvTableStub table;

        private CscCsvStub(CsvTableStub table) {
            super(new Sin(EPS));
            this.table = table;
        }

        @Override
        public double calculate(double x) {
            return table.calculate(x);
        }
    }

    private static final class LnCsvStub extends Ln {
        private final CsvTableStub table;

        private LnCsvStub(CsvTableStub table) {
            super(EPS);
            this.table = table;
        }

        @Override
        public double calculate(double x) {
            return table.calculate(x);
        }
    }

    private static final class LogStageStub extends Log {
        private final CsvTableStub log2Table;
        private final CsvTableStub log3Table;
        private final CsvTableStub log5Table;
        private final boolean realLog2;
        private final boolean realLog3;
        private final boolean realLog5;

        private LogStageStub(
                Ln ln,
                CsvTableStub log2Table,
                CsvTableStub log3Table,
                CsvTableStub log5Table,
                boolean realLog2,
                boolean realLog3,
                boolean realLog5
        ) {
            super(ln);
            this.log2Table = log2Table;
            this.log3Table = log3Table;
            this.log5Table = log5Table;
            this.realLog2 = realLog2;
            this.realLog3 = realLog3;
            this.realLog5 = realLog5;
        }

        @Override
        public double calculate(double x, double base) {
            if (Math.abs(base - 2.0) < 1e-10 && !realLog2) {
                return log2Table.calculate(x);
            }
            if (Math.abs(base - 3.0) < 1e-10 && !realLog3) {
                return log3Table.calculate(x);
            }
            if (Math.abs(base - 5.0) < 1e-10 && !realLog5) {
                return log5Table.calculate(x);
            }
            return super.calculate(x, base);
        }
    }
}
