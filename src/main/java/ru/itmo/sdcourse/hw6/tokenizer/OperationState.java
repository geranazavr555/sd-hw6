package ru.itmo.sdcourse.hw6.tokenizer;

import ru.itmo.sdcourse.hw6.token.Operation;

import java.util.Map;

public class OperationState extends SingleCharState<Operation, Operation.Type> {
    private static final Map<Character, Operation.Type> CHARACTER_TYPE_MAP = Map.of(
        '+', Operation.Type.SUM,
        '-', Operation.Type.SUBTRACT,
        '*', Operation.Type.MULTIPLY,
        '/', Operation.Type.DIVIDE
    );

    @Override
    protected Map<Character, Operation.Type> getCharToTypeMap() {
        return CHARACTER_TYPE_MAP;
    }

    @Override
    protected Operation internalCreateToken(Operation.Type type) {
        return new Operation(type);
    }
}
