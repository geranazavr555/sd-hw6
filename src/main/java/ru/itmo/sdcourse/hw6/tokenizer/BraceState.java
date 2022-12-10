package ru.itmo.sdcourse.hw6.tokenizer;

import ru.itmo.sdcourse.hw6.token.Brace;

import java.util.Map;

public class BraceState extends SingleCharState<Brace, Brace.Type> {
    private static final Map<Character, Brace.Type> CHARACTER_TYPE_MAP = Map.of(
            '(', Brace.Type.LEFT,
            ')', Brace.Type.RIGHT
    );

    @Override
    protected Map<Character, Brace.Type> getCharToTypeMap() {
        return CHARACTER_TYPE_MAP;
    }

    @Override
    protected Brace internalCreateToken(Brace.Type type) {
        return new Brace(type);
    }
}
