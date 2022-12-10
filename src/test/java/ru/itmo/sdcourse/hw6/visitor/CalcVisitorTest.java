package ru.itmo.sdcourse.hw6.visitor;

import org.junit.Assert;
import org.junit.Test;
import ru.itmo.sdcourse.hw6.token.NumberToken;
import ru.itmo.sdcourse.hw6.token.Operation;
import ru.itmo.sdcourse.hw6.token.Token;

import java.io.StringWriter;

public class CalcVisitorTest {
    @Test(expected = RuntimeException.class)
    public void testEmpty() {
        calc();
    }

    @Test
    public void testSimple() {
        Assert.assertEquals(4.0, calc(
                new NumberToken(2),
                new NumberToken(2),
                new Operation(Operation.Type.SUM)
        ), 1e-10);
    }

    @Test
    public void testComplex() {
        Assert.assertEquals(2.31428571428, calc(
                new NumberToken(2),
                new NumberToken(2),
                new NumberToken(5),
                new NumberToken(2),
                new Operation(Operation.Type.DIVIDE),
                new NumberToken(3),
                new Operation(Operation.Type.SUM),
                new Operation(Operation.Type.MULTIPLY),
                new NumberToken(7),
                new NumberToken(8),
                new Operation(Operation.Type.DIVIDE),
                new Operation(Operation.Type.DIVIDE),
                new NumberToken(5),
                new NumberToken(2),
                new NumberToken(7),
                new Operation(Operation.Type.SUM),
                new Operation(Operation.Type.MULTIPLY),
                new NumberToken(5),
                new Operation(Operation.Type.SUBTRACT),
                new Operation(Operation.Type.DIVIDE),
                new Operation(Operation.Type.SUM)
        ), 1e-8);
    }

    private double calc(Token... tokens) {
        CalcVisitor calcVisitor = new CalcVisitor();
        for (Token token : tokens) {
            token.accept(calcVisitor);
        }
        return calcVisitor.getResult();
    }
}
