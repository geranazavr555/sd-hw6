package ru.itmo.sdcourse.hw6.tokenizer;

import org.apache.commons.lang3.Validate;
import ru.itmo.sdcourse.hw6.token.Token;

import java.util.Map;

public abstract class SingleCharState<T extends Token, TT extends Token.Type> implements State<T> {
    private static final String EXCEPTION_ILLEGAL_STATE_MESSAGE = "%s#process() must be called exactly once";
    private static final String EXCEPTION_ILLEGAL_ARGUMENT_MESSAGE = "Illegal argument";

    private T token;

    @Override
    public void process(char ch) {
        Validate.validState(token == null, EXCEPTION_ILLEGAL_STATE_MESSAGE, getClass().getSimpleName());
        Validate.isTrue(isCharAcceptable(ch), EXCEPTION_ILLEGAL_ARGUMENT_MESSAGE);

        token = internalCreateToken(getCharToTypeMap().get(ch));
    }

    @Override
    public T createToken() {
        T token = this.token;
        Validate.validState(token != null, EXCEPTION_ILLEGAL_STATE_MESSAGE, getClass().getSimpleName());
        return token;
    }

    protected abstract Map<Character, TT> getCharToTypeMap();
    protected abstract T internalCreateToken(TT type);

    @Override
    public boolean isCharAcceptable(char ch) {
        return getCharToTypeMap().containsKey(ch);
    }

    @Override
    public void reset() {
        token = null;
    }

    @Override
    public boolean needFinalize(Character ch) {
        return token != null;
    }
}
