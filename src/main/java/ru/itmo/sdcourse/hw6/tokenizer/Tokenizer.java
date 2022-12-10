package ru.itmo.sdcourse.hw6.tokenizer;

import ru.itmo.sdcourse.hw6.token.Token;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Tokenizer {
    private final List<State<? extends Token>> states = List.of(
            new BraceState(), new OperationState(), new NumberState()
    );

    private final Reader reader;
    private final List<Token> tokens = new ArrayList<>();

    private int position;
    private State<? extends Token> state;

    public Tokenizer(Reader reader) {
        this.reader = reader;
    }

    public List<Token> tokenize() {
        while (true) {
            Character ch = nextNonWhitespace();
            if (ch == null) {
                tryFinalizeState(ch);
                return tokens;
            }

            updateState(ch);
            state.process(ch);
        }
    }

    private Character nextChar() {
        try {
            int code = reader.read();
            if (code == -1)
                return null;
            position++;
            char[] chars = Character.toChars(code);
            if (chars.length > 1)
                throwException();
            return chars[0];
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private Character nextNonWhitespace() {
        Character ch;
        while (true) {
            ch = nextChar();
            if (ch == null)
                return null;

            if (!Character.isWhitespace(ch))
                return ch;
            else
                tryFinalizeState(ch);
        }
    }

    private void tryFinalizeState(Character ch) {
        if (state != null && this.state.needFinalize(ch)) {
            tokens.add(state.createToken());
            state.reset();
        }
    }

    private void updateState(char ch) {
        for (State<? extends Token> state : states) {
            if (state.isCharAcceptable(ch)) {
                tryFinalizeState(ch);
                this.state = state;
                return;
            }
        }

        throwException();
    }

    private void throwException() {
        throw new RuntimeException("Can't parse: unexpected char at position " + position);
    }

    public static Tokenizer of(String string) {
        return new Tokenizer(new StringReader(string));
    }

    public static Tokenizer of(InputStream inputStream) {
        return new Tokenizer(new InputStreamReader(inputStream));
    }
}
