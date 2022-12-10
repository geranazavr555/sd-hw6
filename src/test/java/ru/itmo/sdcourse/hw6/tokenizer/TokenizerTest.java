package ru.itmo.sdcourse.hw6.tokenizer;

import org.junit.Assert;
import org.junit.Test;
import ru.itmo.sdcourse.hw6.token.Brace;
import ru.itmo.sdcourse.hw6.token.NumberToken;
import ru.itmo.sdcourse.hw6.token.Operation;
import ru.itmo.sdcourse.hw6.token.Token;

import java.util.Collections;
import java.util.List;

public class TokenizerTest {
    @Test
    public void testEmpty() {
        Tokenizer tokenizer = Tokenizer.of("");
        List<Token> tokens = tokenizer.tokenize();
        List<Token> expected = Collections.emptyList();
        Assert.assertEquals(expected, tokens);
    }

    @Test
    public void testSimple() {
        Tokenizer tokenizer = Tokenizer.of("2 + 2");
        List<Token> tokens = tokenizer.tokenize();
        List<Token> expected = List.of(
                new NumberToken(2),
                new Operation(Operation.Type.SUM),
                new NumberToken(2)
        );
        Assert.assertEquals(expected, tokens);
    }

    @Test
    public void testComplex() {
        Tokenizer tokenizer = Tokenizer.of("2 + 2 * (5 / 2 + 3) / (7 / 8) / (5 * (2 + (7)) - 5)");
        List<Token> tokens = tokenizer.tokenize();
        List<Token> expected = List.of(
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
        Assert.assertEquals(expected, tokens);
    }

    @Test
    public void testWhitespaces() {
        Tokenizer tokenizer = Tokenizer.of("  2   \t   \n 2   \n  \n    +\n\n 2  2   ");
        List<Token> tokens = tokenizer.tokenize();
        List<Token> expected = List.of(
                new NumberToken(2),
                new NumberToken(2),
                new Operation(Operation.Type.SUM),
                new NumberToken(2),
                new NumberToken(2)
        );
        Assert.assertEquals(expected, tokens);
    }

    @Test(expected = RuntimeException.class)
    public void testError() {
        Tokenizer.of("漢語").tokenize();
    }
}
