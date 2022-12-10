package ru.itmo.sdcourse.hw6.tokenizer;

import org.apache.commons.lang3.Validate;
import ru.itmo.sdcourse.hw6.token.NumberToken;

public class NumberState implements State<NumberToken> {
    private long number = 0;
    private boolean dirty = false;

    @Override
    public void process(char ch) {
        Validate.isTrue(isCharAcceptable(ch), "Illegal argument");

        dirty = true;
        number *= 10;
        number += ch - '0';
    }

    @Override
    public NumberToken createToken() {
        return new NumberToken(number);
    }

    @Override
    public boolean isCharAcceptable(char ch) {
        return ch >= '0' && ch <= '9';
    }

    @Override
    public void reset() {
        number = 0;
        dirty = false;
    }

    @Override
    public boolean needFinalize(Character ch) {
        return dirty && (ch == null || !isCharAcceptable(ch));
    }
}
