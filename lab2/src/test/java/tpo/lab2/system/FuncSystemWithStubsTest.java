package tpo.lab2.system;

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

import java.nio.file.Path;

import static tpo.lab2.util.BdAsserts.assertClose;

public class FuncSystemWithStubsTest {

    private static final double EPSILON = 1e-12;

    @BeforeAll
    static void refreshStubTablesFromRealFunctions() throws Exception {
        CsvGenerator.generateDefaultTestResources();
    }

    @Test
    void system_matches_expected_using_stubs() throws Exception {
        CsvTableStub systemExpected = new CsvTableStub(Path.of("src/test/resources/system_expected.csv"));
        SystemFunction system = realSystem();

        for (String xs : new String[]{
                "-0.1","-0.2","-0.5","-1.0","-2.0",
                "0.1","0.2","0.5","2.0","3.0","10.0"
        }) {
            double x = Double.parseDouble(xs);
            double actual = system.calculate(x);
            double expected = systemExpected.calculate(x);
            assertClose(expected, actual, 1e-6);
        }
    }

    @Test
    void system_real_trig_stub_logs() throws Exception {
        CsvTableStub systemExpected = new CsvTableStub(Path.of("src/test/resources/system_expected.csv"));
        SystemFunction system = realSystem();

        for (String xs : new String[]{"-0.1","-0.2","-0.5","-1.0","-2.0"}) {
            double x = Double.parseDouble(xs);
            double actual = system.calculate(x);
            double expected = systemExpected.calculate(x);
            assertClose(expected, actual, 1e-6);
        }
    }

    @Test
    void system_real_logs_stub_trig() throws Exception {
        CsvTableStub systemExpected = new CsvTableStub(Path.of("src/test/resources/system_expected.csv"));
        SystemFunction system = realSystem();

        for (String xs : new String[]{"0.1","0.2","0.5","2.0","3.0","10.0"}) {
            double x = Double.parseDouble(xs);
            double actual = system.calculate(x);
            double expected = systemExpected.calculate(x);
            assertClose(expected, actual, 1e-6);
        }
    }

    @Test
    void system_real() throws Exception {
        CsvTableStub systemExpected = new CsvTableStub(Path.of("src/test/resources/system_expected.csv"));
        SystemFunction system = realSystem();

        for (String xs : new String[]{
                "-0.1","-0.2","-0.5","-1.0","-2.0",
                "0.1","0.2","0.5","2.0","3.0","10.0"
        }) {
            double x = Double.parseDouble(xs);
            double actual = system.calculate(x);
            double expected = systemExpected.calculate(x);
            assertClose(expected, actual, 1e-6);
        }
    }

    private static SystemFunction realSystem() {
        Sin sin = new Sin(EPSILON);
        Cos cos = new Cos(sin);
        Tan tan = new Tan(sin, cos);
        Cot cot = new Cot(sin, cos);
        Sec sec = new Sec(cos);
        Csc csc = new Csc(sin);

        Ln ln = new Ln(EPSILON);
        Log log = new Log(ln);

        return new SystemFunction(sin, cos, tan, cot, sec, csc, ln, log);
    }
}
