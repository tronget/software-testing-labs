package tpo.lab2.integration;

import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import tpo.lab2.SystemFunction;
import tpo.lab2.log.Ln;
import tpo.lab2.log.Log;
import tpo.lab2.trigonometric.Cos;
import tpo.lab2.trigonometric.Cot;
import tpo.lab2.trigonometric.Csc;
import tpo.lab2.trigonometric.Sec;
import tpo.lab2.trigonometric.Sin;
import tpo.lab2.trigonometric.Tan;

import static org.mockito.Mockito.*;

class FuncSystemIntegrationTest {

    @Test
    void trig_branch_call_order() {
        Sin sin = mock(Sin.class);
        Cos cos = mock(Cos.class);
        Tan tan = mock(Tan.class);
        Cot cot = mock(Cot.class);
        Sec sec = mock(Sec.class);
        Csc csc = mock(Csc.class);
        Ln ln = mock(Ln.class);
        Log log = mock(Log.class);

        double x = -0.5;

        when(sin.calculate(x)).thenReturn(2.0);
        when(cos.calculate(x)).thenReturn(1.0);
        when(tan.calculate(x)).thenReturn(1.0);
        when(cot.calculate(x)).thenReturn(1.0);
        when(sec.calculate(x)).thenReturn(1.0);
        when(csc.calculate(x)).thenReturn(1.0);

        SystemFunction system =
                new SystemFunction(sin, cos, tan, cot, sec, csc, ln, log);

        system.calculate(x);

        InOrder order = inOrder(sin, cos, tan, cot, sec, csc);

        order.verify(sin).calculate(x);
        order.verify(cos).calculate(x);
        order.verify(tan).calculate(x);
        order.verify(cot).calculate(x);
        order.verify(sec).calculate(x);
        order.verify(csc).calculate(x);

        verifyNoInteractions(ln, log);
    }

    @Test
    void log_branch_call_order() {
        Sin sin = mock(Sin.class);
        Cos cos = mock(Cos.class);
        Tan tan = mock(Tan.class);
        Cot cot = mock(Cot.class);
        Sec sec = mock(Sec.class);
        Csc csc = mock(Csc.class);
        Ln ln = mock(Ln.class);
        Log log = mock(Log.class);

        double x = 2.0;

        when(log.calculate(x, 2)).thenReturn(1.0);
        when(log.calculate(x, 3)).thenReturn(1.0);
        when(log.calculate(x, 5)).thenReturn(1.0);
        when(ln.calculate(x)).thenReturn(1.0);

        SystemFunction system =
                new SystemFunction(sin, cos, tan, cot, sec, csc, ln, log);

        system.calculate(x);

        InOrder order = inOrder(log, ln);

        order.verify(log).calculate(x, 2);
        order.verify(log).calculate(x, 3);
        order.verify(log).calculate(x, 5);
        order.verify(ln).calculate(x);

        verifyNoInteractions(sin, cos, tan, cot, sec, csc);
    }
}
