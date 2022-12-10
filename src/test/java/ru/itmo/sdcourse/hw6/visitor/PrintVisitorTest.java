package ru.itmo.sdcourse.hw6.visitor;

import org.junit.Assert;
import org.junit.Test;
import ru.itmo.sdcourse.hw6.token.NumberToken;
import ru.itmo.sdcourse.hw6.token.Operation;
import ru.itmo.sdcourse.hw6.token.Token;

import java.io.StringWriter;

public class PrintVisitorTest {
    @Test
    public void testEmpty() {
        Assert.assertEquals("", print());
    }

    @Test
    public void testSimple() {
        Assert.assertEquals("2 2 +", print(
                new NumberToken(2),
                new NumberToken(2),
                new Operation(Operation.Type.SUM)
        ));
    }

    @Test
    public void testComplex() {
        Assert.assertEquals("2 2 5 2 / 3 + * 7 8 / / 5 2 7 + * 5 - / +", print(
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
        ));
    }

    private String print(Token... tokens) {
        StringWriter stringWriter = new StringWriter();
        PrintVisitor printVisitor = PrintVisitor.of(stringWriter);
        for (Token token : tokens) {
            token.accept(printVisitor);
        }
        return stringWriter.toString();
    }
}
