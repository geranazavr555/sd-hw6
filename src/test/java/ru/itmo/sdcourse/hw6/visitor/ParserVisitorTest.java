package ru.itmo.sdcourse.hw6.visitor;

import org.junit.Assert;
import org.junit.Test;
import ru.itmo.sdcourse.hw6.token.Brace;
import ru.itmo.sdcourse.hw6.token.NumberToken;
import ru.itmo.sdcourse.hw6.token.Operation;
import ru.itmo.sdcourse.hw6.token.Token;

import java.util.Collections;
import java.util.List;

public class ParserVisitorTest {
    @Test
    public void testEmpty() {
        List<Token> actual = parse();
        Assert.assertEquals(Collections.emptyList(), actual);
    }

    @Test
    public void testSimple() {
        List<Token> actual = parse(
                new NumberToken(2),
                new Operation(Operation.Type.SUM),
                new NumberToken(2)
        );

        List<Token> expected = List.of(
                new NumberToken(2),
                new NumberToken(2),
                new Operation(Operation.Type.SUM)
        );

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testComplex() {
        List<Token> actual = parse(
                new NumberToken(2),
                new Operation(Operation.Type.SUM),
                new NumberToken(2),
                new Operation(Operation.Type.MULTIPLY),
                new Brace(Brace.Type.LEFT),
                new NumberToken(5),
                new Operation(Operation.Type.DIVIDE),
                new NumberToken(2),
                new Operation(Operation.Type.SUM),
                new NumberToken(3),
                new Brace(Brace.Type.RIGHT),
                new Operation(Operation.Type.DIVIDE),
                new Brace(Brace.Type.LEFT),
                new NumberToken(7),
                new Operation(Operation.Type.DIVIDE),
                new NumberToken(8),
                new Brace(Brace.Type.RIGHT),
                new Operation(Operation.Type.DIVIDE),
                new Brace(Brace.Type.LEFT),
                new NumberToken(5),
                new Operation(Operation.Type.MULTIPLY),
                new Brace(Brace.Type.LEFT),
                new NumberToken(2),
                new Operation(Operation.Type.SUM),
                new Brace(Brace.Type.LEFT),
                new NumberToken(7),
                new Brace(Brace.Type.RIGHT),
                new Brace(Brace.Type.RIGHT),
                new Operation(Operation.Type.SUBTRACT),
                new NumberToken(5),
                new Brace(Brace.Type.RIGHT)
        );

        List<Token> expected = List.of(
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
        );

        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void testError() {
        parse(
                new NumberToken(2),
                new Operation(Operation.Type.SUM),
                new NumberToken(2),
                new Operation(Operation.Type.MULTIPLY),
                new Brace(Brace.Type.LEFT),
                new NumberToken(5),
                new Operation(Operation.Type.DIVIDE),
                new NumberToken(2),
                new Operation(Operation.Type.SUM),
                new NumberToken(3)
        );
    }

    private List<Token> parse(Token... tokens) {
        ParserVisitor parserVisitor = new ParserVisitor();
        for (Token token : tokens) {
            token.accept(parserVisitor);
        }
        return parserVisitor.getOutput();
    }
}
